//Rosca Eduard-Mitrut 216/1
#include <iostream>
#include <fstream>
using namespace std;
int n, m, i, j, p, a[1000][1000], L[1000];
ifstream fisier("input3.txt");

void functie(int k){
   for(int i=0; i<n; i++)
		if(a[k][i]){
            a[k][i]=a[i][k]=0;
			functie(i);
		}
	L[++p]=k;
}

int main(){

    fisier>>n>>m;
    for(int index=0; index<m; index++){
        fisier>>i>>j;
        a[i][j]=a[j][i]=1;
    }
    functie(0);
	for(int i=1;i<p;i++) cout<<L[i]<<" ";
	//cout<<"\n"<<p-1;
	return 0;
}