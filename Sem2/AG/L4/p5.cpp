#include <iostream>
#include <fstream>
using namespace std;
ifstream fisier("input_p5.txt");
struct muchie{int x,y,c;} u[100], sol[100];

void Kruskal(){
    int n, m, L[100];
    fisier>>n>>m;
    int i, j;
    for(i=1; i<=m; i++)
        fisier>>u[i].x>>u[i].y>>u[i].c;

    for(i=1; i<m; i++)
        for(j=i+1; j<m; j++)
            if(u[i].c>u[j].c){
                muchie aux=u[i]; u[i]=u[j]; u[j]=aux;
            }

    for(i=1; i<=n; i++) L[i]=i;
    int cost=0, k=0;
    i=1;

    while(k<n-1){
        int arbx=L[u[i].x], arby=L[u[i].y];
        if(arbx!=arby){
            sol[++k]=u[i]; cost+=u[i].c;
            for(j=1; j<=n; j++)
                if(L[j]==arby) L[j]=arbx;
        }
        i++;
    }
    cout<<cost<<'\n';
    for(i=1; i<=k; i++) cout<<sol[i].x<<" "<<sol[i].y<<'\n';
}
 
int main(){
    Kruskal();
    return 0;
}