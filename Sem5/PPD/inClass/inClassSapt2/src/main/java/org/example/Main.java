package org.example;


import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Main {

	static double operatie(double a, double b){
		return sqrt(pow(a,3)+pow(b,3));
	}

	static class SumThread extends Thread{
		double[] A;
		double[] B;
		double[] C;
		int startPos,endPos;
		public SumThread(double[] A, double[] B, double[] C, int startPos, int endPos){
			this.A=A;
			this.B=B;
			this.C=C;
			this.startPos=startPos;
			this.endPos=endPos;
		}
		@Override
		public void run(){
			for(int i=startPos;i<endPos;i++){
				C[i] = operatie(A[i],B[i]);
			}
		}
	}
	static class SumThread2 extends Thread{
		double[] A;
		double[] B;
		double[] C;
		int startPos,endPos, nrTh;
		public SumThread2(double[] a, double[] b, double[] c, int startPos, int endPos, int nrTh) {
			A = a;
			B = b;
			C = c;
			this.startPos = startPos;
			this.endPos = endPos;
			this.nrTh=nrTh;
		}

		@Override
		public void run() {
			for (int i =startPos;i<endPos;i+=nrTh){
				C[i] = operatie(A[i],B[i]);
			}
		}
	}

	public static void addSecv(double[] A, double[] B, double[] C, int N) {
		for(int i=0;i<N;i++){
			C[i] = operatie(A[i],B[i]);
		}
	}

	public static boolean suntItentici(double[] A, double[] B, int N){
		for(int i=0;i<N;i++){
			if(A[i] != B[i]) return false;
		}
		return true;
	}

	public static void adunareParalela(double[] A, double[] B, double[] C, int N, int noOfThreads) throws InterruptedException {
		int cat=N/noOfThreads,rest=N%noOfThreads;
		int start=0;
		ArrayList<Thread> activeThreads=new ArrayList<>();
		for(int i=1;i<=noOfThreads;i++){
			int end;
			if(rest>0){
				end=start+cat+1;
				rest--;
			}else{
				end=start+cat;
			}
			SumThread sumThread = new SumThread(A,B,C,start,end);
			sumThread.start();
			activeThreads.add(sumThread);
			start=end;
		}
		for(Thread t:activeThreads){
			t.join();
		}
	}

	public static void adunareParalela2(double[] A, double[] B, double[] C, int N, int noOfThreads) throws InterruptedException {
		ArrayList<Thread> activeThreads2=new ArrayList<>();
		for(int i=0;i<noOfThreads;i++){
			SumThread2 sumThread2 = new SumThread2(A,B,C,i,N,noOfThreads);
			sumThread2.start();
			activeThreads2.add(sumThread2);
		}
		for(Thread t:activeThreads2){
			t.join();
		}
	}

	public static void main(String[] args) {
		double[] A,B,C,D,E;
		int MAX=10000000;
		int nrTh=16;
		A=new double[MAX];
		B=new double[MAX];
		C=new double[MAX];
		D=new double[MAX];
 		E=new double[MAX];
		for(int i=0;i<MAX;i++){
			A[i]=B[i]=i;
		}

		long startTime=System.nanoTime();
		addSecv(A,B,C,MAX);
		long endTime=System.nanoTime();
		System.out.println("Timp de executie secvential:           " + (endTime-startTime));

		long startTimeTh=System.nanoTime();
		try {
			adunareParalela(A,B,D,MAX,nrTh);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		long endTimeTh=System.nanoTime();
		System.out.println("Timp de executie distributie liniara:  " + (endTimeTh-startTimeTh));

		long startTimeTh2=System.nanoTime();
		try {
			adunareParalela2(A,B,E,MAX,nrTh);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		long endTimeTh2=System.nanoTime();
		System.out.println("Timp de executie distributie ciclica:  " + (endTimeTh2-startTimeTh2));


		if(suntItentici(C,D,MAX)){
			System.out.println("A,D Sunt identici");
		}else {
			System.out.println("A,D Nu sunt identici");
		}

		if(suntItentici(C,E,MAX)){
			System.out.println("A,E Sunt identici");
		}else {
			System.out.println("A,E Nu sunt identici");
		}
	}

}