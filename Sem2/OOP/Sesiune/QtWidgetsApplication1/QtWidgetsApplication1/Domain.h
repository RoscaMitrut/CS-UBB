#pragma once
#include <string>
#include <assert.h>
using std::string;

class Melodie {
public:
	int id;
	string titlu;
	string artist;
	string gen;

	//Constructor
	Melodie(int id, string titlu, string artist, string gen) : id(id), titlu(titlu), artist(artist), gen(gen) {}

	//getter id melodie
	//return : id-ul respectiv
	int get_id() {
		return id;
	}

	//getter titlu
	//return : titlul respectiv
	string get_titlu() {
		return titlu;
	}

	//getter artist
	//return : artistul respectiv
	string get_artist() {
		return artist;
	}

	//getter gen
	//return : genul respectiv
	string get_gen() {
		return gen;
	}

};
void test_domain();
