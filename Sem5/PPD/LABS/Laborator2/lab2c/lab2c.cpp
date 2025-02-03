#include <iostream>
#include <fstream>
#include <chrono>
#include <thread>
#include <vector>
#include <barrier>
#include <mutex>
#include <condition_variable>

#define p 4

#include "my_barrier.h"
using namespace std;
using namespace std::chrono;

ifstream fin("input1000_1000_3.txt");
ofstream fout("output1000_1000_3.txt");
string validFile = "valid1000_1000_3.txt";

int N, M, n, m;
vector< vector<int> > matrix;
vector< vector<int> > kernel;
vector< vector<int> > answerMatrix;
std::barrier b(p);
vector< vector<int>> buffer;

void read() {
    fin >> N >> M;
    matrix = vector<std::vector<int>>(N, std::vector<int>(M));

    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++) {
            fin >> matrix[i][j];
        }

     fin >> n >> m;
    kernel = vector<std::vector<int>>(n, std::vector<int>(m));

    for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
            fin >> kernel[i][j];
}
void readAnswer() {
    ifstream fin_ans(validFile);
    answerMatrix = vector<std::vector<int>>(N, std::vector<int>(M));

    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++) {
            fin_ans >> answerMatrix[i][j];
        }
}
void write() {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++)
            fout << matrix[i][j] << " ";
        fout << endl;
    }

    fout.close();
}
bool checkAnswer() {
    readAnswer();

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if (matrix[i][j] != answerMatrix[i][j])
                return 0;
        }
    }
    return 1;
}

int convolution(int x, int y) {
    int output = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            int ii = x - (n - 1) / 2 + i;
            int jj = y - (m - 1) / 2 + j;

            if (ii < 0) ii = 0;
            else if (ii >= N) ii = N - 1;

            if (jj < 0) jj = 0;
            else if (jj >= M) jj = M - 1;
            output += matrix[ii][jj] * kernel[i][j];
        }
    }
    return output;
}

int convolution2(vector<int> values, int j, int convRow) {
    int leftIndex = max(j - 1, 0);
    int rightIndex = min(M - 1, j + 1);

    int leftValue = values[leftIndex] * kernel[convRow][0];
    int centerValue = values[j] * kernel[convRow][1];
    int rightValue = values[rightIndex] * kernel[convRow][2];

    return leftValue + centerValue + rightValue;
}

void secventialConvolution() {
    //int buffer[3][M];
    buffer = vector<std::vector<int>>(3, std::vector<int>(M));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            if (i >= 3) {
                matrix[i-3][j] = buffer[(i-3)%3][j];
            }
            buffer[i%3][j] = convolution(i, j);
        }
    }

    for (int j = 0; j < M; j++) {
        matrix[N-3][j] = buffer[1][j];
        matrix[N-2][j] = buffer[2][j];
        matrix[N-1][j] = buffer[0][j];
    }
}

void parallel(my_barrier& barrier, int offset, int start, int end) {
    vector<int> prevLine;
    vector<int> currLine;

    if (start > 0) {
        prevLine = matrix[start - 1];
    }
    else {
        prevLine = matrix[start];
    }

    currLine = matrix[start];

    vector<int> firstRow;
    vector<int> lastRow;
    firstRow.clear();
    lastRow.clear();

    for (int i = start; i < end; i++) {
        for (int j = 0; j < M; j++) {
            int output = 0;
            output = convolution2(prevLine, j, 0) + convolution2(currLine, j, 1) + convolution2(matrix[min(N - 1, i + 1)], j, 2);

            if (i == end - 1) {
                lastRow.push_back(output);
            }
            else if (i == start) {
                firstRow.push_back(output);
            }
            else {
                matrix[i][j] = output;
            }
        }

        prevLine = currLine;
        currLine = matrix[min(N - 1, i + 1)];
    }

    barrier.wait();

    matrix[start] = firstRow;
    matrix[end - 1] = lastRow;
}

void parallelization(my_barrier& barrier) {
    vector<thread> t;

    int start = 0, end = 0;
    int cat = N / p;
    int rest = N % p;

    auto startTime = high_resolution_clock::now();

    for (size_t i = 0; i < p; i++) {
        start = end;
        end = start + cat;
        if (rest > 0)
        {
            end++;
            rest--;
        }
        int offset = (n - 1) / 2;
        thread thr = thread(parallel, ref(barrier), offset, start, end);
        t.push_back(std::move(thr));
    }

    for (auto& th : t)
    {
        if (th.joinable())
            th.join();
    }

    auto endTime = high_resolution_clock::now();
    double difference = duration<double, milli>(endTime - startTime).count();
    cout << difference;
}

int main() {
    my_barrier barrier{ p };
    read();

    auto startTime = high_resolution_clock::now();

    if (p == 1)
        secventialConvolution();
    else {
        parallelization(barrier);
    }

    auto endTime = high_resolution_clock::now();

    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference;

    write();

    if (!checkAnswer())
        cout << "Tests failed!\n";

    return 0;
}