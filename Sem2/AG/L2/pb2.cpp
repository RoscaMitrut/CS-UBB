#include <iostream>
#include <fstream>

using namespace std;

ifstream fisier("input.txt");
int nr_noduri;
int nr_muchii=0;
int i,j,k;
int matrice[20][20]={0};

int main(){
	fisier >> nr_noduri;
	while (!fisier.eof()) {
		fisier >> i;
		fisier >> j;
		nr_muchii++;
		matrice[i][j] = 1;
	}
	fisier.close();

	for(k = 1; k <= nr_noduri; k++)
		for(i = 1; i <= nr_noduri; i++)
			for(j = 1; j <= nr_noduri; j++)
				if(matrice[i][j] == 0) matrice[i][j] = (matrice[i][k] && matrice[k][j]);

	for (int i = 1; i <= nr_noduri; i++) {
		for (int j = 1; j <= nr_noduri; j++) {
			cout << matrice[i][j] <<" ";
		}
		cout << endl;
	}

	return 0;
}