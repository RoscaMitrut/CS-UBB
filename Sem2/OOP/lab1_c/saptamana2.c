#include <stdio.h>

//problema 8
//Tipareste exponentul la care numarul prim p apare in descompunerea
//    in factori primi a numarului N = 1*2*...*n (n natural nenul dat).

//problema 15
//Determina primele n perechi de numere prime gemene, unde n este un
//  numar natural nenul dat.Doua numere prime p si q sunt gemene
//  daca q - p = 2.

int exponent_p(int n, int p) {
    //param:
    //  n: nr natural
    //  p: nr natural prim > 1
    // 
    //functia returneaza exponentul la care apare p in descompunerea lui n!
    //
    //return: 
    //  contor: nr natural (in care avem rezultatul de mai sus)
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

int is_prime(int n) {
    //param:
    //  n: nr natural
    //
    //functia returneaza 1 daca numarul n este prim si 0 daca nu e
    //
    //return:
    //  1 - n prim   0 - n nu e prim
    int flag = 1;

    if (n == 0 || n == 1)
        flag = 0;

    for (int i = 2; i <= n / 2; ++i) {
        if (n % i == 0) {
            flag = 0;
            return flag;
        }
    }
    return flag;
}

void functionalitate2(int n) {
    //param:
    //  n:nr natural nenul
    //
    //functia returneaza primele n perechi de numere prime gemene
    int ultimul_prim = 0;
    int penultimul_prim = 0;
    int nr_perechi=0;
    int i = 0;
    while(nr_perechi<n) {
        i++;
        if (is_prime(i) == 1) {
            penultimul_prim = ultimul_prim;
            ultimul_prim = i;
            if ((ultimul_prim - penultimul_prim == 2) && (ultimul_prim != 0) && (penultimul_prim != 0)) {
                printf("%d  %d\n", penultimul_prim, ultimul_prim);
                nr_perechi +=1;
            }
        }

    }
}

int input_cmd() {
    //functia returneaza inputul dat de user
    //
    //return:
    //  cmd: numar natural (momentan 1 sau 0)
    int cmd;
    printf("Ce doriti sa faceti? (1-executie functionalitate1  2-executie functionalitate2  0-iesire): ");
    scanf("%d", &cmd);
    return cmd;
}

int main(){
    int cmd;
    int n;
    int p;

    while (1) {
        cmd = input_cmd();

        switch(cmd){

        case 1:
            printf("Dati n: ");
            scanf("%d", &n);
            printf("Dati p (prim): ");
            scanf("%d", &p);
            printf("Exponentul la care apare %d in descompunerea lui %d! este:%d \n", p, n, exponent_p(n, p));
            break;

        case 2:
            printf("Dati n: ");
            scanf("%d", &n);
            functionalitate2(n);
            break;

        default:
            return 0;
        }
    }
}