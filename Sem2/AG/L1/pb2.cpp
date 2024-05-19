#include <iostream>
#include <fstream>
#define inf 999999
//lista de muchii > matrice de adiacenta > matrice de incidenta

using namespace std;

//initializam date
ifstream fisier("input.txt");
int nr_noduri;
int nr_muchii = 0;
int matrice_adiacenta[20][20] = { 0 };
int matrice_distana[20][20] = { 0 };
int grade[20] = { 0 };
int vizitat[20] = { 0 };
int i, j, boolean;

void DFS(int a) {
	int index;
	vizitat[a] = 1;
	for (index = 1; index <= nr_noduri; index++) {
		if ((matrice_adiacenta[a][index] == 1) && (vizitat[index] == 0)) {
			DFS(index);
		}
	}
}

int main() {
	//citim datele din fisier
	fisier >> nr_noduri;
	while (!fisier.eof()) {
		fisier >> i;
		fisier >> j;
		nr_muchii++;
		matrice_adiacenta[i][j] = 1;
		matrice_adiacenta[j][i] = 1;
	}


	printf("Noduri izolate: ");
	for (int index = 1; index <= nr_noduri; index++) {
		boolean = 1;
		for (int index2 = 1; index2 <= nr_noduri; index2++) {
			if (matrice_adiacenta[index2][index] != 0) {
				boolean = 0;
			}
		}
		if (boolean == 1) {
			printf("%d ", index);
		}
	}
	printf("\n\n");


	for (int index = 1; index <= nr_noduri; index++) {
		for (int index2 = index + 1; index2 <= nr_noduri; index2++) {
			if (matrice_adiacenta[index][index2] == 1) {
				grade[index]++;
				grade[index2]++;
			}
		}
	}
	boolean = 1;
	for (int i = 1; i <= nr_noduri - 1; i++) {
		if (grade[i] != grade[i + 1]) {
			boolean = 0;
		}
	}
	if (boolean == 1) {
		printf("Avem graf %d regular", grade[1]);
	} else {
		printf("Graful nu este regular");
	}
	printf("\n\n");




// initializare matrice distanta
	for (int i = 1; i <= nr_noduri; i++) {
		for (int j = 1; j <= nr_noduri; j++) {
			if (matrice_adiacenta[i][j] == 1) {
				matrice_distana[i][j] = 1;
			}
			else {
				matrice_distana[i][j] = inf;
			}
		}
	}

// roy floyd
	for (int k = 1; k <= nr_noduri; k++) {
		for (int i = 1; i <= nr_noduri; i++) {
			for (int j = 1; j <= nr_noduri; j++) {
				if (matrice_distana[i][k] != inf && matrice_distana[k][j] != inf) {
					if (matrice_distana[i][j] > matrice_distana[i][k] + matrice_distana[k][j]) {
						matrice_distana[i][j] = matrice_distana[i][k] + matrice_distana[k][j];
					}
				}
			}
		}
	}

// printare matrice distanta
	for (int i = 1; i <= nr_noduri; i++) {
		matrice_distana[i][i] = 0;
		for (int j = 1; j <= nr_noduri; j++) {
			if (matrice_distana[i][j] == inf) {
				printf("inf ");
			} else {
				printf("%d   ", matrice_distana[i][j]);
			}

		}
		printf("\n");
	}
printf("\n");
//conexitate graf
	boolean = 1;
	DFS(1);

	for (int index = 1; index <= nr_noduri; index++) {
		if (vizitat[index] == 0) boolean = 0;
	}
	if (boolean == 1) printf("Graful e conex\n");
	else printf("Graful nu e conex\n");

}