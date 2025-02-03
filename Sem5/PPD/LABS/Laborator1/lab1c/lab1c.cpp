#include <thread>
#include <iostream>
#include <fstream>
#include <format>
#include <vector>
using namespace std;
using namespace std::chrono;

/**/
const int K_MAX = 6;
const int N_MAX = 10000;
const int M_MAX = 10000;
/**/

int N, M, k, nr_threads;

//
//vector<vector<int>> matrix;
//vector<vector<int>> kernel;
//vector<vector<int>> final_matrix;
//

/**/
int matrix[N_MAX][M_MAX];
int kernel[K_MAX][K_MAX];
int final_matrix[N_MAX][M_MAX];
/**/

void readFile(string path) {
    ifstream fin(path);

    fin >> N >> M;

    //
//    matrix = vector<std::vector<int>>(N, std::vector<int>(M));
//   final_matrix = vector<std::vector<int>>(N, std::vector<int>(M));
    //

    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            fin >> matrix[i][j];
        }
    }

    fin >> k;
    
    //
//    kernel = vector<std::vector<int>>(k, std::vector<int>(k));
    //

    for (int i = 0; i < k; ++i) {
        for (int j = 0; j < k; ++j) {
            fin >> kernel[i][j];
        }
    }

    fin.close();
}

void writeFile(string path) {
    ofstream fout(path);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            fout << final_matrix[i][j] << " ";
        }
        fout << endl;
    }

    fout.close();
}

int singlePixelCalc(int x, int y, int offset) {
    int output = 0;

    for (int i = 0; i < k; ++i) {
        for (int j = 0; j < k; ++j) {
            int ii = x - offset + i;
            int jj = y - offset + j;

            if (ii < 0) ii = 0;
            else if (ii >= N) ii = N - 1;

            if (jj < 0) jj = 0;
            else if (jj >= M) jj = M - 1;

            output += matrix[ii][jj] * kernel[i][j];
        }
    }

    return output;
}

void sequentialConvolution(int offset) {
    auto startTime = high_resolution_clock::now();

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            final_matrix[i][j] = singlePixelCalc(i, j, offset);
        }
    }

    auto endTime = high_resolution_clock::now();

    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}

void checkEqual(string path_t, string path_v) {
    ifstream fin_t(path_t);
    ifstream fin_v(path_v);

    int x, y;
    while (fin_t >> x && fin_v >> y) {
        if (x != y) {
            throw exception();
        }
    }

    if (fin_t >> x || fin_v >> x) {
        throw exception();
    }
}

void parallelConvolution(int offset, int start, int end) {
// randuri intregi
    if (N > M) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < M; j++) {
                final_matrix[i][j] = singlePixelCalc(i, j, offset);
            }
        }
    }
// coloane intregi
   else {

        for (int j = 0; j < M; j++) {
            for (int i = start; i < end; i++) {
                final_matrix[i][j] = singlePixelCalc(i, j, offset);
            }
        }

    }
}

void parallelization(int offset) {
    vector<thread> t;

    int start = 0, end = 0;
    int chunk = N / nr_threads;
    int rest = N % nr_threads;

    auto startTime = high_resolution_clock::now();

    for (size_t i = 0; i < nr_threads; i++) {
        start = end;
        end = start + chunk + (rest-- > 0);
        if (rest > 0)
        {
            end++;
            rest--;
        }
        thread thr = thread(parallelConvolution, offset, start, end);
        t.push_back(std::move(thr));
        start = end;
    }

    for (auto& th : t)
    {
        if (th.joinable())
            th.join();
    }

    auto endTime = high_resolution_clock::now();

    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}


int main(int argc, char** argv) {
   
    nr_threads = 16 ; //0 = seq
    
    //string choice = "10_10_3";
    string choice = "1000_1000_5";
    //string choice = "10_10000_5";
    //string choice = "10000_10_5";
    //string choice = "10000_10000_5" ;

    string input_file = std::format("input{}.txt", choice);
    string output_file = std::format("output{}.txt", choice);
    string valid_file = std::format("valid{}.txt", choice);

    readFile(input_file);

    int offset = (k - 1) / 2;

    if (nr_threads == 0) {
        sequentialConvolution(offset);
    }
    else {
        parallelization(offset);
    }

    writeFile(output_file);
    
    if (nr_threads == 0) {
        writeFile(valid_file);
    }
    else {
        checkEqual(output_file, valid_file);
    }
}