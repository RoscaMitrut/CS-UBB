#include <iostream>
#include <chrono>
#include <functional>
#include <thread>
#include <mutex>
#include <vector>

using namespace std;

//function<double(double, double)> f)

double functie(double unu, double doi) {
    return sqrt(pow(unu,2) + pow(doi,2));
}

void add_ciclica(double a[], double b[], double c[], int n, int th) {
    vector<thread*> trd = vector<thread*>();

    for (int i = 0; i < th; i++) {
        trd.push_back(new thread([=]() {
            for (int j = i; j < n; j += th) {
                c[j] = a[j] + b[j];
            }
            }));
    }

    for (auto i : trd) {
        i->join();
    }
}


void sequencial_sum(double* v1, double* v2, double*& v3, int start, int end) {
    for (int i = start;i < end;i++) {
        v3[i] = v1[i] + v2[i];
    }
}

void paralel_sum(double* v1, double* v2, double*& v3, int size, int no_threads) {

    thread* threads = new std::thread[no_threads];
    int cat = size / no_threads;
    int rest = size % no_threads;
    int start = 0;

    for (int i = 0;i < no_threads;i++) {
        int end = start + cat;
        if (rest > 0) {
            end++;
            rest--;
        }

        threads[i] = thread(sequencial_sum, v1, v2, std::ref(v3), start, end);

        start = end;
    }

    for (int i = 0;i < no_threads;i++) {
        threads[i].join();
    }

    delete[] threads;
}

bool same(double* x, double* y, int size) {
    for (int i = 0; i < size; i++) {
        if (x[i] != y[i]) {
            return false;
        }
    }
    return true;
}

int main()
{
    int size = 1000000;
    int no_threads = 10;

    double* v1 = new double[size];
    double* v2 = new double[size];
    double* v3 = new double[size];
    double* v4 = new double[size];
    double* v5 = new double[size];

    for (int i = 0;i < size;i++) {
        v1[i] = i;
        v2[i] = 2 * i;
    }

    auto start_seq = std::chrono::high_resolution_clock::now();
    sequencial_sum(v1, v2, v3, 0, size);
    auto stop_seq = std::chrono::high_resolution_clock::now();
    auto seq = std::chrono::duration_cast<std::chrono::microseconds>(stop_seq - start_seq);
    cout << "seq:  " << seq.count() << endl;

    auto start_para = std::chrono::high_resolution_clock::now();
    paralel_sum(v1, v2, v4, size, no_threads);
    auto stop_para = std::chrono::high_resolution_clock::now();
    auto para = std::chrono::duration_cast<std::chrono::microseconds>(stop_para - start_para);
    cout << "para: " << para.count() << endl;

    auto start_cicl = std::chrono::high_resolution_clock::now();
    add_ciclica(v1, v2, v5, size, no_threads);
    auto stop_cicl = std::chrono::high_resolution_clock::now();
    auto cicl = std::chrono::duration_cast<std::chrono::microseconds>(stop_cicl - start_cicl);
    cout << "cicl: " << cicl.count() << endl;

    if (same(v3, v4, size) == true && same(v4, v5, size) == true) {
        cout << "la fel";
    }

    delete[] v1;
    delete[] v2;
    delete[] v3;

    return 0;
}