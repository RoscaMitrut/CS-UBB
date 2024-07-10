#include "validator.h"

bool validString(string str) {
	if (str.size() == 0) return false;
	for (int i = 0; i < str.size(); i++) {
		if (!((str.at(i) >= 'a' && str.at(i) <= 'z') || (str.at(i) >= 'A' && str.at(i) <= 'Z') || str.at(i) == ' ')) return false;
	}
	return true;
}

void Validator::validateLocatar(int numar, string nume, int suprafata, string tip) {
	string errors;
	if (numar < 0)
		errors.append("Numarul trebuie sa fie mai mare decat 0!\n");
	if (validString(nume) == false)
		errors.append("Numele introdus e invalid!\n");
	if (suprafata < 0)
		errors.append("Suprafata trebuie sa fie mai mare decat 0!\n");
	if (validString(tip) == false)
		errors.append("Tipul introdus e invalid!\n");
	if (errors.size() > 0) throw Exception(errors);
}