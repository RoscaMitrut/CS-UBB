#include <iostream>
#include <fstream>
using namespace std;
int n, m, p, a[100][100], L[100];
ifstream fisier("input_p3.txt");

void Euler(int k){
   for(int i=1; i<=n; i++)
		if(a[k][i]){
            a[k][i]=a[i][k]=0;
			Euler(i);
		}
	L[++p]=k;
}
int main(){
	int i, j;
    fisier>>n>>m;
    for(int K=1; K<=m; K++){
        fisier>>i>>j;
        a[i][j]=a[j][i]=1;
    }
    Euler(1);
	for(int i=1;i<p;i++) cout<<L[i]<<" ";
	return 0;
}