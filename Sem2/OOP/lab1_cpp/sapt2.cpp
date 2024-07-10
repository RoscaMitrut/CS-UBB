#include <iostream>
#include <cmath>
using namespace std;
//problema 8
//Tipareste exponentul la care numarul prim p apare in descompunerea
//    in factori primi a numarului N = 1*2*...*n (n natural nenul dat).

int exponent_p(int n, int p) {
    //param:
    //n: nr natural
    //p: nr natural prim > 1
    // 
    //functia returneaza exponentul la care apare p in descompunerea lui n!
    //
    //return: 
    //contor: nr natural (in care avem rezultatul de mai sus)
    int temp;
    int contor = 0;

    for (int i = 1;i <= n;i++) {
        temp = i;
        while (temp >= 2) {
            if (temp % p == 0) {
                contor++;
                temp = (temp / p);
            }
            else {
                temp = 1;
            }
        }
    }
    return contor;
}

int input_cmd() {
    //functia returneaza inputul dat de user
    //
    //return:
    //cmd: numar natural (momentan 1 sau 0)
    int cmd;
    printf("Ce doriti sa faceti? (1-executie program 0-iesire)");
    cin >> cmd;
    return cmd;
}

int main() {
    int cmd;
    int n;
    int p;

    while (1) {
    cmd = input_cmd();
    if (cmd == 1) {
        cout << "Dati n: ";
        cin >> n;
        cout << "Dati p (prim): ";
        cin >> p;

        cout << "Exponentul la care apare " << p << " in descompunerea lui " << n << "! este:" << exponent_p(n, p) << endl;
    }
    else {
        break;
    }
    }
    return 0;
}