#pragma once
#include <string>
#include <iostream>

using std::string;
using std::cout;

class Locatar {
	int numar;
	std::string nume;
	int suprafata;
	std::string tip;
public:
	Locatar() = default;

	Locatar(const int nr, const string n, const int s, const string t) :numar{ nr }, nume{ n }, suprafata{ s }, tip{ t } {}

	Locatar(const Locatar& ot) :numar{ ot.numar }, nume{ ot.nume }, suprafata{ ot.suprafata }, tip{ ot.tip } {
		cout << "";
		//placeholder
	}

	int get_numar() const noexcept{
		return numar;
	}

	string get_nume() const {
		return nume;
	}

	int get_suprafata() const noexcept{
		return suprafata;
	}

	string get_tip() const {
		return tip;
	}
};

class DTO {
public:
	int count;
	string tip;
	DTO() { count = 0;tip = ""; }
	DTO(int count, string tip) : count{ count }, tip{ tip } {};
};