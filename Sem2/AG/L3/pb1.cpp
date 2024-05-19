#include <iostream>
#include <fstream>
#define inf 999999
using namespace std;

ifstream f("input.txt");
int a[100][100];
int nr_noduri;
int nr_arce;
int distanta[105];

void fct_Bellman(int nod_start) {
    //initializare
    for (int i = 0; i < nr_noduri; i++) {
        distanta[i] = inf;
    }
    distanta[nod_start] = 0;

    //procesare
    for (int i = 0; i < nr_noduri; i++) {
        for (int j = 0; j < nr_noduri; j++) {
            if (a[i][j]) {
                if (distanta[j] > distanta[i] + a[i][j]) {
                    distanta[j] = distanta[i] + a[i][j];
                }
            }
        }
    }

    //printare
    for (int i = 0; i < nr_noduri; i++) {
            if (distanta[i] != inf) {
                cout << nod_start << " -> " << i << " = " << distanta[i] << '\n';
            } else {
                cout << nod_start << " -> " << i << " = infinit\n";
            }
    }
}

int main() {
    int nod_start;
    f >> nr_noduri >> nr_arce >> nod_start;

    int x, y, cost;
    for (int i = 0; i < nr_arce; i++) {
        f >> x >> y >> cost;
        a[x][y] = cost;
    }

    fct_Bellman(nod_start);

    return 0;
}
