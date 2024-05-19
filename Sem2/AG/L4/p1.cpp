#include <iostream>
#include <fstream>
using namespace std;

void codare()
{
    int nr_nod;
    int arr[105];
    ifstream fisier("input_p1.txt");
    fisier>>nr_nod;
    for(int i=0; i<nr_nod; i++){
        fisier>>arr[i];
    }
    int count=nr_nod-1; // numerotarea nodurilor incepe de la 0
    while(count--){
        int i, w;
        for(i=0; i<nr_nod; i++){
            w=0;
            for(int j=0; j<nr_nod; j++){
                if(arr[j]==i){
                    w=1;
                }
            }
            if(w==0){
                break;
            } // luam frunza de pe indexul cel mai mic
        }
        cout<<arr[i]<<" ";
        arr[i]=i;
    }
    cout<<"\n";
}

int main(){
    codare();
    return 0;
}