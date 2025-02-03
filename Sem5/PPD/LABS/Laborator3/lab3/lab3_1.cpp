#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

#define MAX_DIGITS 1000

void read_number(const char* filename, int* size, unsigned char* digits) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Eroare la deschiderea fișierului");
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    fscanf(file, "%d", size);
    for (int i = 0; i < *size; i++) {
        fscanf(file, "%hhu", &digits[i]);
    }

    fclose(file);
}

void write_result(const char* filename, int size, unsigned char* digits) {
    FILE* file = fopen(filename, "w");
    if (!file) {
        perror("Eroare la deschiderea fișierului");
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    fprintf(file, "%d\n", size);
    for (int i = size - 1; i >= 0; i--) {
        fprintf(file, "%hhu", digits[i]);
    }
    fprintf(file, "\n");

    fclose(file);
}

int main(int argc, char** argv) {
    int rank, size;
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    unsigned char num1[MAX_DIGITS] = { 0 }, num2[MAX_DIGITS] = { 0 };
    int N1 = 0, N2 = 0, max_digits = 0;

    if (rank == 0) {
        read_number("Numar1.txt", &N1, num1);
        read_number("Numar2.txt", &N2, num2);
        max_digits = (N1 > N2) ? N1 + 1 : N2 + 1;
    }

    MPI_Bcast(&max_digits, 1, MPI_INT, 0, MPI_COMM_WORLD);

    int chunk_size = (max_digits + size - 2) / (size - 1);
    unsigned char* local_num1 = (unsigned char*)calloc(chunk_size, sizeof(unsigned char));
    unsigned char* local_num2 = (unsigned char*)calloc(chunk_size, sizeof(unsigned char));
    unsigned char* local_result = (unsigned char*)calloc(chunk_size, sizeof(unsigned char));

    int carry = 0, recv_carry = 0;

    if (rank == 0) {
        int current_process = 1;
        for (int i = 0; i < max_digits; i += chunk_size) {
            int send_size = (i + chunk_size < max_digits) ? chunk_size : max_digits - i;
            MPI_Send(num1 + i, send_size, MPI_UNSIGNED_CHAR, current_process, 0, MPI_COMM_WORLD);
            MPI_Send(num2 + i, send_size, MPI_UNSIGNED_CHAR, current_process, 1, MPI_COMM_WORLD);
            current_process++;
        }
    }
    else {
        MPI_Status status;
        MPI_Recv(local_num1, chunk_size, MPI_UNSIGNED_CHAR, 0, 0, MPI_COMM_WORLD, &status);
        MPI_Recv(local_num2, chunk_size, MPI_UNSIGNED_CHAR, 0, 1, MPI_COMM_WORLD, &status);

        int local_size;
        MPI_Get_count(&status, MPI_UNSIGNED_CHAR, &local_size);

        if (rank > 1) {
            MPI_Recv(&recv_carry, 1, MPI_INT, rank - 1, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        else {
            recv_carry = 0;
        }

        for (int i = 0; i < local_size; i++) {
            int sum = local_num1[i] + local_num2[i] + recv_carry;
            local_result[i] = sum % 10;
            recv_carry = sum / 10;
        }

        carry = recv_carry;

        if (rank < size - 1) {
            MPI_Send(&carry, 1, MPI_INT, rank + 1, 2, MPI_COMM_WORLD);
        }

        MPI_Send(local_result, local_size, MPI_UNSIGNED_CHAR, 0, 3, MPI_COMM_WORLD);
    }

    if (rank == 0) {
        unsigned char result[MAX_DIGITS + 1] = { 0 };
        int result_size = max_digits;
        int current_process = 1;

        for (int i = 0; i < max_digits; i += chunk_size) {
            int recv_size = (i + chunk_size < max_digits) ? chunk_size : max_digits - i;
            MPI_Recv(result + i, recv_size, MPI_UNSIGNED_CHAR, current_process, 3, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            current_process++;
        }

        if (carry > 0) {
            result[max_digits] = carry;
            result_size++;
        }

        write_result("Numar3.txt", result_size, result);
    }

    free(local_num1);
    free(local_num2);
    free(local_result);

    MPI_Finalize();
    return 0;
}
