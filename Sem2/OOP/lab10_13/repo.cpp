#include "repo.h"
#include <assert.h>
#include <iostream>
#include <ostream>
#include <sstream>
#include <algorithm>
#include <random>
#include <cstdlib>
#include <fstream>
using std::ostream;
using std::stringstream;

void LocatarRepo::store(const Locatar& l) {
	if (exist(l)) {
		throw LocatarRepoException("Exista deja");
	}
	all.push_back(l);
}

bool LocatarRepo::exist(const Locatar& l) const {
	try {
		find(l.get_numar());
		return true;
	}
	catch (LocatarRepoException&) {
		return false;
	}
}

const Locatar& LocatarRepo::find(int numar) const {
	if (all.empty()) throw LocatarRepoException("Lista goala!");
	else
		for (const auto& l : all) {
			if (l.get_numar() == numar) {
				return l;
			}
		}
	throw LocatarRepoException("Nu exista");
}

const Locatar& LocatarRepo::find_by_name_and_number(int numar, string nume) const {
	if (all.empty()) throw LocatarRepoException("Lista goala!");
	else
	for (const auto& l : all) {
		if ((l.get_nume() == nume) && (l.get_numar() == numar)) {
			return l;
		}
	}
	throw LocatarRepoException("Nu exista");
}

const void LocatarRepo::deleteLocatar(const Locatar& loc) {
/*	if (!exist(loc))
		throw LocatarRepoException("Nu exista\n");
*/
	int i = 0;
	if (all.empty()) throw LocatarRepoException("Lista goala!");

	else
		for (auto a : all) {
			if (a.get_numar() == loc.get_numar()) {
				all.erase(all.begin() + i);
				return;
			}
			i++;
		}
	throw LocatarRepoException("Nu exista");
}

const vector<Locatar>& LocatarRepo::getAll() const {
	return all;
}

ostream& operator<<(ostream& out, const LocatarRepoException& ex) {
	out << ex.msg;
	return out;
}

void LocatarRepo::emptyList() noexcept {
	listaa.clear();
}

const int LocatarRepo::marime() noexcept {
	return static_cast<int>(all.size());
}

int LocatarRepo::get_random() {
	std::mt19937 mt{ std::random_device{}() };
	const int i = marime();
	std::uniform_int_distribution<> dist(1, i);
	const int rnd = dist(mt);
	return rnd;
}

void LocatarRepo::addList(int index) {
	//index defapt = cu numarul
	bool adv = true;
	Locatar temp = find(index);
	if (!listaa.empty())
		for (auto a : listaa) {
			if (a.get_numar() == index) adv = false;
		}
	if (adv)
		listaa.push_back(temp);
//	else throw LocatarRepoException("Apartamentul cu numarul dat exista deja pe lista! \n");
}

void LocatarRepo::addRandom(int count) {
	//emptyList();
	//while (listaa.size() != count) {
//		try {
	if(listaa.size()<getAll().size()){
		int temp_int = listaa.size();
		int temp_int2 = listaa.size();
		while (temp_int == temp_int2) {
			int random = rand() % getAll().size();
			Locatar temp = getAll().at(random);
			addList(temp.get_numar());
			temp_int = listaa.size();
		}
	}
//		catch(LocatarRepoException){}
	//}
}
const vector<Locatar>& LocatarRepo::getList() noexcept {
	return listaa;
}


void LocatarRepoFile::loadFromFile() {
	std::ifstream in(file_name);
	if (!in.is_open()) {
		throw LocatarRepoException("Eroare deschidere fisier");
	}
	while (!in.eof()) {
		int numar,suprafata;
		std::string nume,tip;
		if (in.eof())break;
		in >> numar;
		if (in.eof())break;
		in >> nume;
		if (in.eof())break;
		in >> suprafata;
		if (in.eof())break;
		in >> tip;
		Locatar l{ numar,nume.c_str(),suprafata,tip.c_str() };
		LocatarRepo::store(l);
	}
	in.close();
}
void LocatarRepoFile::writeToFile() {
	std::ofstream out(file_name);
	if (!out.is_open()) {
		std::string msg("Eroare deschidere fisier");
		throw LocatarRepoException(msg);
	}
	for (auto& l : getAll()) {
		out << l.get_numar();
		out << std::endl;
		out << l.get_nume();
		out << std::endl;
		out << l.get_suprafata();
		out << std::endl;
		out << l.get_tip();
		out << std::endl;
	}
	out.close();
}






void test_repo() {
	LocatarRepo rep;
	rep.store(Locatar{ 1,"a",1,"a" });
	assert(rep.getAll().size() == 1);
	assert(rep.find(1).get_nume() == "a");
	
	rep.store(Locatar{ 2,"b",2,"b" });
	assert(rep.getAll().size() == 2);

	try {
		rep.store(Locatar{ 1,"a",1,"a" });
		assert(false);
	}
	catch (const LocatarRepoException&) {
		assert(true);
	}

	try {
		rep.find(123);
		assert(false);
	}
	catch (LocatarRepoException& ve) {
		string ceva = ve.getErrorMessage();
	}

	try {
		rep.find(3);
		assert(false);
	}
	catch (const LocatarRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista") >= 0);
	}
	
	try {
		rep.find_by_name_and_number(3, "c");
		assert(false);
	}
	catch (const LocatarRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista") >= 0);
	}

	Locatar l = rep.find_by_name_and_number(1, "a");
	assert(l.get_numar() == 1);
	assert(l.get_nume() == "a");
	assert(l.get_suprafata() == 1);
	assert(l.get_tip() == "a");

	rep.deleteLocatar(l);
	assert(rep.getAll().size() == 1);

	try {
		Locatar temp = Locatar{ 3,"c",3,"c" };
		rep.deleteLocatar(temp);
		assert(false);
	}
	catch (const LocatarRepoException& e) {
		stringstream os;
		os << e;
		assert(os.str().find("exista") >= 0);
	}
}
void test_repo2() {
	LocatarRepo repo;
	Locatar l1{ 1,"a",1,"a" };
	Locatar l2{ 2,"b",2,"b" };
	Locatar l3{ 3,"c",3,"c" };
	repo.store(l1);
	repo.store(l2);
	try {
		repo.deleteLocatar(l3);
		assert(false);
	}
	catch (LocatarRepoException) {
		assert(true);
	}
	Locatar temp;
	repo.get_random();
	repo.marime();
}

void test_file(){
	LocatarRepoFile repo{"fisier2.txt"};
	Locatar temp;
	repo.store(temp);
	repo.deleteLocatar(temp);
}