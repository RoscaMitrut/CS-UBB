//nr nod   nr muchii   nod start   nod inceput

//10 22 4 8
//1000 5035 4 531
//1000 5035 448 800

#include <iostream>
#include <fstream>
#define inf 999999
using namespace std;

ifstream fisier("fis5.in");
int nr_noduri;
int nr_muchii;
int nod_start;
int nod_final;
int x,y,pondere;
int a[10001][10001]{0};
int distanta[10001];

void functie_bellman(int nod_start,int nod_final){
 for (int i = 0; i < nr_noduri; i++) {
        distanta[i] = inf;
    }
    distanta[nod_start] = 0;

    for (int i = 0; i < nr_noduri; i++) {
    	for (int j = 0; j < nr_noduri; j++) {
            if (a[i][j]) {
            	cout<<i<<" "<<j<<endl;
                if (distanta[j] > distanta[i] + a[i][j]) {
                    distanta[j] = distanta[i] + a[i][j];
                }
            }
        }
    }

    if (distanta[nod_final]==inf){
    	cout<<"distanta de la "<<nod_start<<" la "<< nod_final << " nu a fost gasita"<<endl;
    }else{
    	cout<<"distanta de la "<<nod_start<<" la "<< nod_final << " este "<<distanta[nod_final]<<endl;
    }
}

int main(){
	fisier >> nr_noduri;
	fisier >> nr_muchii;
	fisier >> nod_start;
	fisier >> nod_final;
	for(int i=0;i<nr_muchii;i++){
		fisier >> x;
		fisier >> y;
		fisier >> pondere;
		a[x][y]=pondere;
	}

	functie_bellman(nod_start,nod_final);

	return 0;
}