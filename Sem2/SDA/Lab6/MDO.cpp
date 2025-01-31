#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>
#include <algorithm>
#include <exception>
using namespace std;

//fct dispersie
int MDO::d(TCheie c) const {
    if (c < 0) {
        c = -c;
    }
    return c % m;
}

//Teta(m)
MDO::MDO(Relatie r) {
    relatie = r;
    m = MAX;
    n = 0;
    for (int i = 0; i < m; i++) {
        elemente[i].first = NULL_TVALOARE;
        elemente[i].second = NULL_TVALOARE;
        urm[i] = NULL_TVALOARE;
    }
    PrimPozLibera = 0;
}

// actualizare PrimPozLibera
//O(m)
void MDO::ActPrimPozLibera() {
    PrimPozLibera++;
    while (PrimPozLibera < m && elemente[PrimPozLibera].first != NULL_TVALOARE)
        PrimPozLibera++;
}


//O(n)
vector<TValoare> MDO::cauta(TCheie c) const {
    int poz = d(c);

    vector<TValoare> v;
    while (poz != -1 && elemente[poz].first != c) {
        poz = urm[poz];
    }

    while (poz != -1 && elemente[poz].first == c) {
        v.push_back(elemente[poz].second);
        poz = urm[poz];
    }
    return v;
}

//O(n)
bool MDO::sterge(TCheie c, TValoare v) {
    TElem elem(c, v);
    int i = d(c), j = NULL_TVALOARE, k = 0, p, pp;
    bool booleana;
    while (k < m && j == NULL_TVALOARE) {
        if (urm[k] == i) {
            j = k;
        }
        else {
            k++;
        }
    }
    while (i != NULL_TVALOARE && elemente[i] != elem) {
        j = i;
        i = urm[i];
    }
    if (i == NULL_TVALOARE){ 
        return false;
    }
    else {
        booleana = false;
        do {
            p = urm[i];
            pp = i;
            while (p != NULL_TVALOARE && d(elemente[p].first) != i) {
                pp = p;
                p = urm[p];
            }
            if (p == NULL_TVALOARE) booleana = true;
            else {
                elemente[i] = elemente[p];
                j = pp;
                i = p;
            }

        }while(!booleana);
        if (j != NULL_TVALOARE) {
            urm[j] = urm[i];
        }
        elemente[i].first = NULL_TVALOARE;
        urm[i] = NULL_TVALOARE;
        if (PrimPozLibera > i) {
            PrimPozLibera = i;
        }
        n--;
        return true;
    }
}

//O(n)
int MDO::adaugaInexistente(MDO& mdo) {
    
    int contor = 0;
    IteratorMDO it = mdo.iterator();
    while (it.valid()) {
        TElem el = it.element();
        vector<TValoare> v = mdo.cauta(el.first);
        vector<TValoare> v2 = cauta(el.first);
        for (int i = 0;i < v.size();i++) {
            if (!(find(v2.begin(), v2.end(), v[i])!=v2.end())) {
                adauga(el.first, v[i]);
                contor++;
            }
        }
        it.urmator();
    }
    return contor;

}

//O(n)
void MDO::adauga(TCheie c, TValoare v) {
    int i = d(c);
    int j;

    if (elemente[i].first == NULL_TVALOARE) {//daca este goala, adaugam elem pe pozitia = cu cheia
        elemente[i].first = c;
        elemente[i].second = v;
        if (i == PrimPozLibera) {
            ActPrimPozLibera();
        }
        n++;
    }
    else {//altfel, mutam toate elementele cu una "mai in fata" pana cand putem adauga elem pe pozitia respectiva
        while (i != NULL_TVALOARE) {
            if ((Relatie)(c, elemente[i].first) && d(elemente[i].first) == i) {
                int temp1 = c;
                int temp2 = v;
                c = elemente[i].first;
                v = elemente[i].second;
                elemente[i].first = temp1;
                elemente[i].second = temp2;
            }
            j = i;
            i = urm[i];
        }
        elemente[PrimPozLibera].first = c;
        elemente[PrimPozLibera].second = v;
        urm[j] = PrimPozLibera;
        ActPrimPozLibera();
        n++;
    }
}

//Teta(1)
int MDO::dim() const {
    return n;
}
//Teta(1)
bool MDO::vid() const {
    return n == 0;
}
//Teta(1)
IteratorMDO MDO::iterator() const {
    return IteratorMDO(*this);
}
//Teta(1)
MDO::~MDO() {
}
