package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static final int K_MAX = 6;
	private static final int N_MAX = 10000;
	private static final int M_MAX = 10000;
	private static int N, M, k, nrThreads;
	private static int[][] matrix = new int[N_MAX][M_MAX];
	private static int[][] kernel = new int[K_MAX][K_MAX];
	private static int[][] finalMatrix = new int[N_MAX][M_MAX];

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
				k = Integer.parseInt(myReader.nextLine());
			}
			if (myReader.hasNextLine()) {
				for (int i = 0; i < k; i++) {
					String data = myReader.nextLine();
					String[] line = data.split(" ");
					for (int j = 0; j < k; j++) {
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
					bw.write(finalMatrix[i][j] + " ");
				}
				bw.newLine();
			}
			bw.flush();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public static int singlePixelCalc(int x, int y, int offset) {
		int output = 0;

		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
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

	public static void sequentialConvolution(int offset) {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				finalMatrix[i][j] = singlePixelCalc(i, j, offset);
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println((double)endTime - startTime);
	}

	public static class ConvolutionThread extends Thread {
		private int offset;
		private int start, end;

		public ConvolutionThread(int offset, int start, int end) {
			this.offset = offset;
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			if (N > M) {
				for (int i = start; i < end; i++) {
					for (int j = 0; j < M; j++) {
						finalMatrix[i][j] = singlePixelCalc(i, j, offset);
					}
				}
			} else {
				for (int j = 0; j < M; j++) {
					for (int i = start; i < end; i++) {
						finalMatrix[i][j] = singlePixelCalc(i, j, offset);
					}
				}
			}
		}
	}

	public static void parallelization(int offset) throws InterruptedException {
		List<Thread> threads = new ArrayList<>();

		int start = 0, end = 0;
		int chunk = N / nrThreads;
		int rest = N % nrThreads;
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < nrThreads; i++) {
			start = end;
			end = start + chunk + (rest-- > 0 ? 1 : 0);

			Thread thread = new ConvolutionThread(offset, start, end);
			threads.add(thread);
			thread.start();
		}

		for (Thread thread : threads) {
			thread.join();
		}

		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
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
		nrThreads = 16; // 0 = sequential
		 //String choice = "10_10_3";
		 String choice = "1000_1000_5";
		//String choice = "10_10000_5";
		 //String choice = "10000_10_5";
		 //String choice = "10000_10000_5";

		String inputFile = String.format("C:\\Users\\RoscaMitrut\\Desktop\\Sem_V\\PPD\\Lab\\Laborator1\\lab1java\\src\\main\\java\\org\\example\\input%s.txt", choice);
		String outputFile = String.format("C:\\Users\\RoscaMitrut\\Desktop\\Sem_V\\PPD\\Lab\\Laborator1\\lab1java\\src\\main\\java\\org\\example\\output%s.txt", choice);
		String validFile = String.format("C:\\Users\\RoscaMitrut\\Desktop\\Sem_V\\PPD\\Lab\\Laborator1\\lab1java\\src\\main\\java\\org\\example\\valid%s.txt", choice);

		readFile(inputFile);

		int offset = (k - 1) / 2;

		if (nrThreads == 0) {
			sequentialConvolution(offset);
			writeFile(outputFile);
			writeFile(validFile);
		} else {
			parallelization(offset);
			writeFile(outputFile);
			checkEqual(outputFile, validFile);
		}
	}
}
