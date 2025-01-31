//ff
#include <limits.h>
#include <string.h>
#include <fstream>
#include <iostream>
#include <queue>
using namespace std;


// Using BFS as a searching algorithm
bool bfs(int grafR[100][100], int s, int t, int parinte[]) {
	bool vizitat[100];
	memset(vizitat, 0, sizeof(vizitat));

	queue<int> q;
	q.push(s);
	vizitat[s] = true;
	parinte[s] = -1;

	while (!q.empty()) {
		int u = q.front();
		q.pop();

		for (int v = 0; v < 100; v++) {
			if (vizitat[v] == false && grafR[u][v] > 0) {
				q.push(v);
				parinte[v] = u;
				vizitat[v] = true;
			}
		}
	}
	return (vizitat[t] == true);
}

// Applying fordfulkerson algorithm
int fordFulkerson(int graf[100][100], int s, int t) {
	int u, v;

	int grafR[100][100];
	for (u = 0; u < 100; u++)
	for (v = 0; v < 100; v++)
		grafR[u][v] = graf[u][v];

	int parinte[100];
	int max_flow = 0;

	// Updating the residual values of edges
	while (bfs(grafR, s, t, parinte)) {
		int flux_drum = INT_MAX;
		for (v = t; v != s; v = parinte[v]) {
			u = parinte[v];
			flux_drum = min(flux_drum, grafR[u][v]);
		}	

		for (v = t; v != s; v = parinte[v]) {
			u = parinte[v];
			grafR[u][v] -= flux_drum;
			grafR[v][u] += flux_drum;
		}

	// Adding the path flows
	max_flow += flux_drum;
	}

	return max_flow;
}

int main() {
	int n,m;
	ifstream fisier("input_p1.txt");
	int graf[100][100];
	fisier>>n>>m;
	int i, j, x, y,temp;

	for(i=0; i<m; i++){
		fisier>>x>>y;
		fisier >> graf[x][y];
	}
	
	cout << "Flux maxim " << fordFulkerson(graf, 0, n-1) << endl;
}

ifstream fisier("level1_1.in");

int dim_harta;
char matrice[100][100];
int coord_cerute;


fisier>>dim_harta;
for(int i=0;i<dim_harta;i++){
	for(int j=0;j<dim_harta;j++){
		fisier>>matrice[i][j];
	}
}

for(int i=0;i<dim_harta;i++){
	for(int j=0;j<dim_harta;j++){
		cout >> matrice[i][j];
	}
}