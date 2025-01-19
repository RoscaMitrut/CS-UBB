#include <iostream>
#include  <omp.h>
#include <chrono>

#define M 5000
#define N 5000

using namespace std;

void suma_vect_secv(int* a, int* b, int* rez, int n) {
    for (int i = 0; i < n; i++) {
        rez[i] = a[i] + b[i];
    }
}

void suma_vect_parallel(int threads, int* a, int* b, int* rez, int n) {
    int i;
    omp_set_num_threads(threads);
#pragma omp parallel default(none) shared(a, b, rez, n) private(i)
    {
#pragma omp for schedule(static)
        for (int i = 0; i < n; i++) {
            rez[i] = a[i] + b[i];
        }
    }
}

void produs_vect_secv(int* a, int* b, int* rez, int n) {
    for (int i = 0; i < n; i++) {
        rez[i] = a[i] * b[i];
    }
}

void produs_vect_parallel(int threads, int* a, int* b, int* rez, int n) {
    int i;
    omp_set_num_threads(threads);
#pragma omp parallel for default(none) shared(a, b, rez, n) private(i) schedule(static)
    for (int i = 0; i < n; i++) {
        rez[i] = a[i] * b[i];
    }
}

void vectorial_produs_vect_secv(int* a, int* b, int& sum, int n) {
    sum = 0;
    for (int i = 0; i < n; i++) {
        sum += a[i] * b[i];
    }
}

void vectorial_produs_vect_parallel(int threads, int* a, int* b, int& sum, int n) {
    int i;
    sum = 0;
    int local_sum = 0;
    omp_set_num_threads(threads);
#pragma omp parallel default(none) shared(a, b, sum, n) private(i, local_sum) //schedule(static)
    {
#pragma omp for
        for (int i = 0; i < n; i++) {
            local_sum += a[i] * b[i];
        }
#pragma omp critical
        {
            sum += local_sum;
        }
    }
}

int vectorial_produs_vect_parallel_reduction(int threads, int* a, int* b, int* rez, int n) {
    int i;
    int sum = 0;
    omp_set_num_threads(threads);
#pragma omp parallel for default(none) shared(a, b, rez, n) private(i) reduction(+:sum)
    for (int i = 0; i < n; i++) {
        rez[i] += a[i] * b[i];
        sum = sum + rez[i];
    }
    return sum;
}


void suma_matrix_secv(int a[M][N], int b[M][N], int rez[M][N]) {
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            rez[i][j] = a[i][j] + b[i][j];
        }
    }
}

void suma_matrix_parallel(int threads, int a[M][N], int b[M][N], int rez[M][N]) {
    int i, j;
#pragma omp parallel num_threads(threads) shared(a, b, rez, threads) private(i, j)
    {
#pragma omp for collapse(2)
        for (i = 0; i < M; i++)
            for (j = 0; j < N; j++)
                rez[i][j] = a[i][j] + b[i][j];
    }
}

void execute_secv(int* a, int* b, int* rez1, int* rez2, int n, int& sum) {
    omp_set_num_threads(4);
#pragma omp parallel sections
    {
#pragma omp section
        suma_vect_secv(a, b, rez1, n);
#pragma omp section
        produs_vect_secv(a, b, rez2, n);
#pragma omp section
        vectorial_produs_vect_secv(a, b, sum, n);
    }
}

int n1[M][N], n2[M][N], m_rez1[M][N], m_rez2[M][N];

int main() {
    int n = 1000000;
    int* a = new int[n];
    int* b = new int[n];
    int* rez = new int[n];
    int* rez_parallel = new int[n];

    for (int i = 0; i < n; i++) {
        a[i] = i;
        b[i] = i;
    }

    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            n1[i][j] = i * j % 10;
            n2[i][j] = i * j % 10;
        }
    }

    int sum = 0, sum_p = 0;

    auto start = chrono::high_resolution_clock::now();

    // vectorial_produs_vect_secv(a, b, sum, n);
    suma_matrix_secv(n1, n2, m_rez1);

    auto end = chrono::high_resolution_clock::now();
    auto diff = end - start;
    cout << "computation time init matrices= " << chrono::duration<double, milli>(diff).count() << " ms" << endl;

    auto start_p = chrono::high_resolution_clock::now();

    // vectorial_produs_vect_parallel(4, a, b, sum_p, n);
    // sum_p = vectorial_produs_vect_parallel_reduction(4, a, b, rez_parallel, n);
    suma_matrix_parallel(10, n1, n2, m_rez2);


    auto end_p = chrono::high_resolution_clock::now();
    auto diff_p = end_p - start_p;
    cout << "computation time init matrices= " << chrono::duration<double, milli>(diff_p).count() << " ms" << endl;

    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            if (m_rez1[i][j] != m_rez2[i][j]) {
                cout << "Error at " << i << " " << j << endl;
                break;
            }
        }
    }

    execute_secv(a, b, rez, rez_parallel, n, sum);

    // for (int i = 0; i < n; i++) {
    //     // if (rez[i] != rez_parallel[i]) {
    //     //     cout << "Error at " << i << endl;
    //     //     break;
    //     // }
    //
    // }

    // if (sum != sum_p) {
    //     cout << "Error" << endl;
    // }

    //     omp_set_num_threads(4);
    // #pragma omp parallel
    //     {
    //         int TID = omp_get_thread_num();
    //         cout << "Hello world from thread " << TID << endl;
    //     }

    return 0;
}
