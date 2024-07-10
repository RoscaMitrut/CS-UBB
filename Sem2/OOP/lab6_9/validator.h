#pragma once
//#include "locatar.h"
#include <string>
using namespace std;


class Exception {
	string mesaj;
public:
	Exception(string m) : mesaj{ m } {
	}

	string getMesaj() {
		return mesaj;
	}
};

class Validator {
public:
//	string errors;
	void validateLocatar(int numar, string nume, int suprafata, string tip);
};