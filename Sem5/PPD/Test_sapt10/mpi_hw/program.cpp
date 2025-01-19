//mpiexec -n 4 mpi_hw.exe
#include <mpi.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <numeric>
#include <cmath>
#include <string>
#include <stdio.h>

void read_input(const std::string& filename, std::vector<double>& data) {
	std::ifstream file(filename);
	double num;
	while (file >> num) {
		data.push_back(num);
	}
}

void read_polynomial(const std::string& filename, std::vector<double>& coef) {
	std::ifstream file(filename);
	double coeficient;
	while (file >> coeficient) {
		coef.push_back(coeficient);
	}
}

std::vector<double> compute_polynomial(const std::vector<double>& segment, const std::vector<double>& coefficients) {
	std::vector<double> results;
	for (double x : segment) {
		double value = 0;
		for (int i = 0; i < coefficients.size(); ++i) {
			value += coefficients[i] * std::pow(x, i);
		}
		results.push_back(value);
	}
	return results;
}

int main(int argc, char** argv)
{
	MPI_Init(&argc, &argv);
	int rank, size;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	
	std::vector<double> date;
	std::vector<double> coefficients;

	if (rank == 0) {
		read_polynomial("polinom.txt", coefficients);
		read_input("input.txt", date);
	}

	int coef_size = coefficients.size();
	MPI_Bcast(&coef_size, 1, MPI_INT, 0, MPI_COMM_WORLD);
	if (rank != 0) {
		coefficients.resize(coef_size);
	}
	MPI_Bcast(coefficients.data(), coef_size, MPI_DOUBLE, 0, MPI_COMM_WORLD);

	int n = date.size();
	int segment_size = n / size;
	MPI_Bcast(&segment_size, 1, MPI_INT, 0, MPI_COMM_WORLD);
	std::vector<double> segment(segment_size);

	if (rank == 0) {
		for (int i = 1; i < size; ++i) {
			MPI_Send(date.data() + i * segment_size, segment_size, MPI_DOUBLE, i, 0, MPI_COMM_WORLD);
		}
		segment.assign(date.begin(), date.begin() + segment_size);
	}
	else {
		MPI_Recv(segment.data(), segment_size, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
	}
	
	std::vector<double> results = compute_polynomial(segment, coefficients);

	std::vector<double> all_results;
	if (rank == 0) {
		all_results.resize(n);
	}

	MPI_Gather(results.data(), segment_size, MPI_DOUBLE,
		all_results.data(), segment_size, MPI_DOUBLE,
		0, MPI_COMM_WORLD);

	if (rank == 0) {
		std::ofstream output("output.txt");
		for (double result : all_results) {
			output << std::fixed << result << "\n";
		}
	}

	MPI_Finalize();
	return 0;
}