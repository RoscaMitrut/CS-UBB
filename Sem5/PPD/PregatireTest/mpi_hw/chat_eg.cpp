//last one
#include <mpi.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <numeric>
#include <cmath>
#include <string>

// Helper function to calculate the sum of digits
int sum_of_digits(int num) {
    int sum = 0;
    while (num) {
        sum += std::abs(num % 10);
        num /= 10;
    }
    return sum;
}

// Function to modify numbers based on the given rule
void modify_numbers(const std::vector<int>& input, std::vector<int>& output, int X, int& count_case1, int& count_case2) {
    count_case1 = 0;
    count_case2 = 0;
    output.resize(input.size());
    for (size_t i = 0; i < input.size(); ++i) {
        int sum = sum_of_digits(input[i]);
        if (sum < X) {
            output[i] = input[i] * 2;
            count_case1++;
        }
        else {
            output[i] = input[i] / 2;
            count_case2++;
        }
    }
}

int main(int argc, char* argv[]) {
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    int X = 0;
    std::vector<int> numbers;

    // Process 0 reads input and distributes work
    if (rank == 0) {
        std::ifstream infile("numbers.txt");
        if (!infile) {
            std::cerr << "Error: Unable to open numbers.txt\n";
            MPI_Abort(MPI_COMM_WORLD, 1);
        }

        infile >> X; // Assuming X is provided in the first line of the file
        int num;
        while (infile >> num) {
            numbers.push_back(num);
        }
        infile.close();

        if (numbers.size() % size != 0) {
            std::cerr << "Error: N must be divisible by P\n";
            MPI_Abort(MPI_COMM_WORLD, 1);
        }
    }

    // Broadcast X to all processes
    MPI_Bcast(&X, 1, MPI_INT, 0, MPI_COMM_WORLD);

    // Determine local data size and scatter numbers
    int local_size = (rank == 0) ? numbers.size() / size : 0;
    MPI_Bcast(&local_size, 1, MPI_INT, 0, MPI_COMM_WORLD);
    std::vector<int> local_numbers(local_size);

    MPI_Scatter(
        rank == 0 ? numbers.data() : nullptr,
        local_size,
        MPI_INT,
        local_numbers.data(),
        local_size,
        MPI_INT,
        0,
        MPI_COMM_WORLD
    );

    // Modify numbers locally
    std::vector<int> local_result;
    int local_case1 = 0, local_case2 = 0;
    modify_numbers(local_numbers, local_result, X, local_case1, local_case2);

    // Gather modified numbers at process 0
    std::vector<int> global_result;
    if (rank == 0) {
        global_result.resize(numbers.size());
    }
    MPI_Gather(
        local_result.data(),
        local_size,
        MPI_INT,
        global_result.data(),
        local_size,
        MPI_INT,
        0,
        MPI_COMM_WORLD
    );

    // Reduce counts for case1 and case2 to process 1
    int global_case1 = 0, global_case2 = 0;
    MPI_Reduce(&local_case1, &global_case1, 1, MPI_INT, MPI_SUM, 1, MPI_COMM_WORLD);
    MPI_Reduce(&local_case2, &global_case2, 1, MPI_INT, MPI_SUM, 1, MPI_COMM_WORLD);

    // Process 1 prints the totals
    if (rank == 1) {
        std::cout << "Total A (case1): " << global_case1 << "\n";
        std::cout << "Total B (case2): " << global_case2 << "\n";
    }

    // Process 0 writes results to file
    if (rank == 0) {
        std::ofstream outfile("result.txt");
        for (const int& num : global_result) {
            outfile << num << "\n";
        }
        outfile.close();
    }

    MPI_Finalize();
    return 0;
}
