#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

const char* MATRIX_A_FILE = "matrixA.txt";
const char* MATRIX_B_FILE = "matrixB.txt";
const char* OUTPUT_FILE = "output.txt";
const char* LOG_FILE = "log.csv";

void readMatrixFromFile(const char* filename, int** matrix, int* rows, int* cols) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        fprintf(stderr, "Failed to open file %s\n", filename);
        exit(EXIT_FAILURE);
    }

    fscanf(file, "%d %d", rows, cols);
    *matrix = (int*)malloc((*rows) * (*cols) * sizeof(int));

    for (int i = 0; i < (*rows) * (*cols); i++) {
        fscanf(file, "%d", &(*matrix)[i]);
    }

    fclose(file);
}

void writeMatrixToFile(const char* filename, const int* matrix, int rows, int cols) {
    FILE* file = fopen(filename, "w");
    if (!file) {
        fprintf(stderr, "Failed to open file %s\n", filename);
        exit(EXIT_FAILURE);
    }

    fprintf(file, "%d %d\n", rows, cols);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            fprintf(file, "%d ", matrix[i * cols + j]);
        }
        fprintf(file, "\n");
    }

    fclose(file);
}

void logExecutionTime(const char* logFile, int rowsA, int colsA, int rowsB, int colsB, double timeTaken) {
    FILE* file = fopen(logFile, "a");
    if (!file) {
        fprintf(stderr, "Failed to open log file %s\n", logFile);
        exit(EXIT_FAILURE);
    }
    fprintf(file, "%d,%d,%d,%d,%.6f\n", rowsA, colsA, rowsB, colsB, timeTaken);
    fclose(file);
}

__global__ void matrixMultiplyKernel(int* c, const int* a, const int* b, int rowsA, int colsA, int colsB) {
    int row = blockIdx.y * blockDim.y + threadIdx.y; // Row index of C
    int col = blockIdx.x * blockDim.x + threadIdx.x; // Column index of C

    if (row < rowsA && col < colsB) {
        int value = 0;
        for (int k = 0; k < colsA; k++) {
            value += a[row * colsA + k] * b[k * colsB + col];
        }
        c[row * colsB + col] = value;
    }
}

cudaError_t matrixMultiplyWithCuda(int* c, const int* a, const int* b, int rowsA, int colsA, int colsB) {
    int* dev_a = 0;
    int* dev_b = 0;
    int* dev_c = 0;
    cudaError_t cudaStatus;

    cudaStatus = cudaSetDevice(0);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaSetDevice failed! Do you have a CUDA-capable GPU installed?\n");
        goto Error;
    }

    // Allocate GPU buffers for matrices.
    cudaStatus = cudaMalloc((void**)&dev_a, rowsA * colsA * sizeof(int));
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMalloc failed for A!\n");
        goto Error;
    }

    cudaStatus = cudaMalloc((void**)&dev_b, colsA * colsB * sizeof(int));
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMalloc failed for B!\n");
        goto Error;
    }

    cudaStatus = cudaMalloc((void**)&dev_c, rowsA * colsB * sizeof(int));
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMalloc failed for C!\n");
        goto Error;
    }

    // Copy input matrices from to device.
    cudaStatus = cudaMemcpy(dev_a, a, rowsA * colsA * sizeof(int), cudaMemcpyHostToDevice);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy failed for A!\n");
        goto Error;
    }

    cudaStatus = cudaMemcpy(dev_b, b, colsA * colsB * sizeof(int), cudaMemcpyHostToDevice);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy failed for B!\n");
        goto Error;
    }

    // Define the block and grid dimensions.
    dim3 threadsPerBlock(16, 16);
    dim3 blocksPerGrid((colsB + threadsPerBlock.x - 1) / threadsPerBlock.x, (rowsA + threadsPerBlock.y - 1) / threadsPerBlock.y);


    matrixMultiplyKernel <<<blocksPerGrid, threadsPerBlock >>> (dev_c, dev_a, dev_b, rowsA, colsA, colsB);


    // Check for kernel launch errors.
    cudaStatus = cudaGetLastError();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "matrixMultiplyKernel launch failed: %s\n", cudaGetErrorString(cudaStatus));
        goto Error;
    }

    // Synchronize and check for errors.
    cudaStatus = cudaDeviceSynchronize();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaDeviceSynchronize returned error code %d!\n", cudaStatus);
        goto Error;
    }

    // Copy the result matrix to host.
    cudaStatus = cudaMemcpy(c, dev_c, rowsA * colsB * sizeof(int), cudaMemcpyDeviceToHost);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy failed for C!\n");
        goto Error;
    }

Error:
    cudaFree(dev_a);
    cudaFree(dev_b);
    cudaFree(dev_c);

    return cudaStatus;
}

int main() {
    clock_t start = clock();

    int* a = NULL, * b = NULL, * c = NULL;
    int rowsA, colsA, rowsB, colsB;

    readMatrixFromFile(MATRIX_A_FILE, &a, &rowsA, &colsA);
    readMatrixFromFile(MATRIX_B_FILE, &b, &rowsB, &colsB);

    // Check if multiplication is possible
    if (colsA != rowsB) {
        fprintf(stderr, "Matrix multiplication not possible: colsA (%d) != rowsB (%d)\n", colsA, rowsB);
        free(a);
        free(b);
        return 1;
    }

    int rowsC = rowsA, colsC = colsB;
    c = (int*)malloc(rowsC * colsC * sizeof(int));


    cudaError_t cudaStatus = matrixMultiplyWithCuda(c, a, b, rowsA, colsA, colsB);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "matrixMultiplyWithCuda failed!\n");
        free(a);
        free(b);
        free(c);
        return 1;
    }

    writeMatrixToFile(OUTPUT_FILE, c, rowsC, colsC);

    clock_t end = clock();
    double timeTaken = ((double)(end - start)) / CLOCKS_PER_SEC;

    logExecutionTime(LOG_FILE, rowsA, colsA, rowsB, colsB, timeTaken);

    free(a);
    free(b);
    free(c);

    cudaStatus = cudaDeviceReset();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaDeviceReset failed!\n");
        return 1;
    }

    return 0;
}

