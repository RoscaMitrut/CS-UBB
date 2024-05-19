#include <iostream>
#include <fstream>

//lista de muchii > matrice de adiacenta > matrice de incidenta

using namespace std;

int main() {
	//initializam date
	ifstream fisier("input.txt");
	int nr_noduri;
	int nr_muchii = 0;
	int i, j;
	int matrice_adiacenta[10][10] = { 0 };
	int matrice_incidenta[10][10] = { 0 };

	//citim datele din fisier
	fisier >> nr_noduri;
	while (!fisier.eof()) {
		fisier >> i;
		fisier >> j;
		nr_muchii++;
		matrice_adiacenta[i][j] = 1;
		matrice_adiacenta[j][i] = 1;
		//punem 1 in ambele deoarece graful e neorientat => simetrie
	}

	//convertim din matricea de adiacenta in matricea de incidenta
	int muchie_actuala = 1;
	for (int index = 1; index <= nr_noduri; index++) {
		for (int index2 = index + 1; index2 <= nr_noduri; index2++) {
			if (matrice_adiacenta[index][index2] == 1) {
				matrice_incidenta[index][muchie_actuala] = 1;
				matrice_incidenta[index2][muchie_actuala] = 1;
				muchie_actuala++;

			}
		}
	}

	//printare
	printf("Matrice de adiacenta:\n");
	for (int index = 1; index <= nr_noduri; index++) {

		for (int index2 = 1; index2 <= nr_noduri; index2++) {

			printf("%d  ", matrice_adiacenta[index][index2]);
		}
		printf("\n");
	}

	printf("Matrice de incidenta:\n");
	for (int index = 1; index <= nr_noduri; index++) {
		for (int index2 = 1; index2 < muchie_actuala; index2++) {
			printf("%d  ", matrice_incidenta[index][index2]);
		}
		printf("\n");
	}
	return 0;
}