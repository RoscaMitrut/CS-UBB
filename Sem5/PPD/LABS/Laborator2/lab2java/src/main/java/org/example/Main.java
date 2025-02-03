package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
	private static final int p = 4;
	private static final String choice = "1000_1000_3";

	private static final int K_MAX = 5;
	private static final int N_MAX = 10000;
	private static final int M_MAX = 10000;
	private static int N, M, n, m;
	private static final int[][] matrix = new int[N_MAX][M_MAX];
	private static final int[][] kernel = new int[K_MAX][K_MAX];
	private static final CyclicBarrier barrier = new CyclicBarrier(p);

	public static void readFile(String path) {
		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);

			if (myReader.hasNextLine()) {
				N = Integer.parseInt(myReader.nextLine());
				M = Integer.parseInt(myReader.nextLine());
			}
			if (myReader.hasNextLine()) {
				for (int i = 0; i < N; i++) {
					String data = myReader.nextLine();
					String[] line = data.split(" ");
					for (int j = 0; j < M; j++) {
						matrix[i][j] = Integer.parseInt(line[j]);
					}
				}
			}
			if (myReader.hasNextLine()) {
				n = Integer.parseInt(myReader.nextLine());
				m = Integer.parseInt(myReader.nextLine());
			}
			if (myReader.hasNextLine()) {
				for (int i = 0; i < n; i++) {
					String data = myReader.nextLine();
					String[] line = data.split(" ");
					for (int j = 0; j < n; j++) {
						kernel[i][j] = Integer.parseInt(line[j]);
					}
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void writeFile(String path) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					bw.write(matrix[i][j] + " ");
				}
				bw.newLine();
			}
			bw.flush();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private static int convolution(int x, int y) {
		int output = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int ii = max(0, min(N - 1, x - (n - 1) / 2 + i));
				int jj = max(0, min(M - 1, y - (m - 1) / 2 + j));
				output += matrix[ii][jj] * kernel[i][j];
			}
		}
		return output;
	}
	private static int convolution2(int[] values, int j, int convRow) {
		int leftIndex = max(j - 1, 0);
		int rightIndex = min(M - 1, j + 1);

		int leftValue = values[leftIndex] * kernel[convRow][0];
		int centerValue = values[j] * kernel[convRow][1];
		int rightValue = values[rightIndex] * kernel[convRow][2];

		return leftValue + centerValue + rightValue;
	}

	private static void sequentialConvolution() {
		int[][] buffer = new int[3][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (i >= 3) {
					matrix[i - 3][j] = buffer[(i - 3) % 3][j];
				}
				buffer[i % 3][j] = convolution(i, j);
			}
		}
		for (int j = 0; j < M; j++) {
			matrix[N - 3][j] = buffer[1][j];
			matrix[N - 2][j] = buffer[2][j];
			matrix[N - 1][j] = buffer[0][j];
		}
	}

	private static class Worker extends Thread {
		private final int start;
		private final int end;
		public Worker(int start, int end) {
			this.start = start;
			this.end = end;
		}
		public void run() {
			int[] prevLine = new int[M];
			int[] currLine = new int[M];

			System.arraycopy(matrix[max(start - 1, 0)], 0, prevLine, 0, M);
			System.arraycopy(matrix[start], 0, currLine, 0, M);

			int[] firstRow = new int[M];
			int[] lastRow = new int[M];

			for (int i = start; i < end; i++) {
				for (int j = 0; j < M; j++) {
					int output;
					output = convolution2(prevLine, j, 0) + convolution2(currLine, j, 1) + convolution2(matrix[min(N - 1, i + 1)], j, 2);
					//output = convolution(i,j);
					if (i == start) {
						firstRow[j] = output;
					} else if (i == end - 1) {
						lastRow[j] = output;
					} else {
						matrix[i][j] = output;
					}
				}
				System.arraycopy(currLine, 0, prevLine, 0, currLine.length);
				System.arraycopy(matrix[min(N - 1, i + 1)], 0, currLine, 0, currLine.length);
			}

			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException ignored) {}

			System.arraycopy(firstRow, 0, matrix[start], 0, M);
			System.arraycopy(lastRow, 0, matrix[end - 1], 0, M);
		}
	}
	public static void parallel() throws InterruptedException {
		Thread[] t = new Worker[p];

		int start, end = 0;
		int cat = N / p;
		int rest = N % p;

		for (int i = 0; i < t.length; i++) {
			start = end;
			end = start + cat;
			if (rest > 0) {
				end++;
				rest--;
			}
			t[i] = new Worker(start, end);
			t[i].start();
		}

		for (Thread thread : t) {
			thread.join();
		}
	}

	public static void checkEqual(String pathT, String pathV) throws IOException {
		try (BufferedReader brT = new BufferedReader(new FileReader(pathT));
			 BufferedReader brV = new BufferedReader(new FileReader(pathV))) {
			String lineT, lineV;
			while ((lineT = brT.readLine()) != null && (lineV = brV.readLine()) != null) {
				if (!lineT.equals(lineV)) {
					throw new RuntimeException("Files are not equal");
				}
			}

			if (brT.readLine() != null || brV.readLine() != null) {
				throw new RuntimeException("Files are not equal");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String inputFile = String.format("C:\\Users\\RoscaMitrut\\Desktop\\PPD\\Lab\\Laborator2\\lab2java\\src\\main\\java\\org\\example\\input%s.txt", choice);
		String outputFile = String.format("C:\\Users\\RoscaMitrut\\Desktop\\PPD\\Lab\\Laborator2\\lab2java\\src\\main\\java\\org\\example\\output%s.txt", choice);
		String validFile = String.format("C:\\Users\\RoscaMitrut\\Desktop\\PPD\\Lab\\Laborator2\\lab2java\\src\\main\\java\\org\\example\\valid%s.txt", choice);

		readFile(inputFile);

		if (p == 1) {
			long startTime = System.nanoTime();
			sequentialConvolution();
			long endTime = System.nanoTime();
			writeFile(outputFile);
			writeFile(validFile);
			System.out.printf("%.2f", (endTime - startTime) / 1_000_000.0);
		} else {
			long startTime = System.nanoTime();
			parallel();
			long endTime = System.nanoTime();
			writeFile(outputFile);
			checkEqual(outputFile,validFile);
			System.out.printf("%.2f", (endTime - startTime) / 1_000_000.0);
		}
	}
}
