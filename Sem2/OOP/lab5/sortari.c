#pragma once
#include "sortari.h"

void sort(Lista* v, functie_comparare cmpf) {
	for (int i = 0; i < size(v); i++) {
		for (int j = 0; j < size(v); j++) {
			Cheltuiala* c1 = get(v, i);
			Cheltuiala* c2 = get(v, j);
			if (cmpf(c1, c2) > 0) {
				set(v, i, c2);
				set(v, j, c1);
			}
		}
	}
}
