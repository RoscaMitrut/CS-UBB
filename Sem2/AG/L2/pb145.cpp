#include <iostream>
#include <fstream>
#include <queue>
#define INT_MAX 999999
using namespace std;

ifstream f("graf.txt");
int a[20][20] = {0} ;//graf
int n;
int timp;
int lant[20]; //lant
int parinte[20] = {0}; //parinte
int distanta[20] = {0}; //distanta
int culoare[20]; //0-alb, 1-gri, 2-negru
int i,j;
queue<int> coada;

void init_l(){
    for (int i = 0; i < 20; i++) lant[i] = INT_MAX;
}

void Moore(int v){
    init_l();
    lant[v] = 0;
    int i, x;
    coada.push(v);
    while (!coada.empty()){
        x = coada.front();
        coada.pop();
        for (i = 1; i <= n; i++)
            if (a[x][i] == 1 && lant[i] == INT_MAX){
                parinte[i] = x;
                lant[i] = lant[x] + 1;
                coada.push(i);
            }
    }
}

void lant_(int u, int v){
    int lat[20] = {0};
    int i = 1;
    lat[0] = u;
    if (parinte[u] == 0){
        if (u == v){
            cout<<"Nod initial";
        }
        cout<<"Nu exista lant catre varf";
    }

    while (parinte[u] != 0){
        lat[i++] = parinte[u];
        u = parinte[u];
    }
    for (int j = i-1; j >=0; j--) cout<<lat[j]<<" ";
}

void BFS(int s){
    for (int i = 1; i <= n; i++){
       	culoare[i] = 0;
        distanta[i] = INT_MAX;
        parinte[i] = 0;
    }
    culoare[s] = 1;
    distanta[s] = 0;
    parinte[s] = 0;
    coada.push(s);
    int x, i;
    while(!coada.empty()){
        x = coada.front();
        coada.pop();
        for (i = 1; i <= n; i++)
            if (a[x][i] == 1 && culoare[i] == 0) {
                culoare[i] = 1;
                distanta[i] = distanta[x] + 1;
                parinte[i] = x;
                coada.push(i);
            }
        culoare[x] = 2;
    }
}

void DFS_VISIT(int s){
    timp++;
    distanta[s] = timp;
    culoare[s] = 1;
    int i;
    for (i = 1; i <= n; i++)
        if (a[s][i] == 1 && culoare[i] == 0) {
            parinte[i] = s;
            cout << i<<endl;
            DFS_VISIT(i);
        }
    culoare[s] = 2;
    timp++;
}


int main(){
	//citire graf
    f>>n;

    while(!f.eof()){
        f>>i>>j;
        a[i][j] = 1;
    }
    f.close();
    
    int cmd;
    cout<<"1.Moore\n4.BFS\n5.DFS\n";
    cin>>cmd;

    if (cmd == 1) {
        cout << "Introduceti varf:";
        int start;
        cin >> start;
        Moore(start);
        for (int i = 1; i <= n; i++) {
            cout << i << ": ";
            lant_(i, start);
            cout << endl;
        }
    }

    else if (cmd == 4){
        cout << "Introduceti varf:";
        int start;
        cin >> start;
        BFS(start);
        for (int i = 1; i <= n; i++)
            if (culoare[i] == 2){
                cout << i << ": "<< distanta[i];
                cout << endl;
        }
    }

    else if (cmd == 5) {
        cout << "Introduceti varf:";
        int start;
        cin >> start;
        timp = 0;
        for (int i = 1; i <= n; i++) culoare[i] = 0;
        DFS_VISIT(start);
        cout << "Varfurile descoperite de DFS din varful " << start << " sunt:\n";
        for (int i = 1; i <= n; i++){
        	if (culoare[i] == 2){
           		cout << i;
        	    cout << endl;
        	}
    	}
    }
    return 0;
}
