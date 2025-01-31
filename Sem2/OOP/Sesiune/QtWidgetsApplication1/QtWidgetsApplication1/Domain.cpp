#include "Domain.h"

void test_domain() {
	Melodie test(1, "Primavara","Ana", "rock");
	int id = test.get_id();
	string titlu = test.get_titlu();
	string artist = test.get_artist();
	string gen = test.get_gen();

	assert(id == 1);
	assert(artist == "Ana");
	assert(titlu == "Primavara");
	assert(gen == "rock");
}