#include "service.h"
#include <assert.h>
#include "validator.h"
#include <fstream>
#include "repo.h"
#include <string>
#include <unordered_map>
#include <utility>
#include <algorithm>
using std::pair;
using std::unordered_map;

vector<Locatar> Service::filtrare(const function<bool(const Locatar&) > & fct) {
	vector<Locatar> rez;
	for (const auto& oferta : rep.getAll()) {
		if (fct(oferta)) {
			rez.push_back(oferta);
		}
	}
	return (rez);
}

void Service::addLocatar(const int numar, const string nume, const int suprafata, const string tip) {
	vali.validateLocatar(numar, nume, suprafata, tip);
	Locatar l{ numar,nume,suprafata,tip };
	rep.store(l);
	//
	undoActions.push_back(new UndoAdauga(rep, l));
}

void Service::deleteLocatar(const int numar) {
	vali.validateLocatar(numar, "a", 1, "a");
	Locatar l = rep.find(numar);
	rep.deleteLocatar(l);
	//
	undoActions.push_back(new UndoSterge(rep, l));
}
const Locatar& Service::cautaLocatar(const int numar,const string nume) {
	vali.validateLocatar(numar, nume, 1, "a");
	return rep.find_by_name_and_number(numar,nume);
}

const Locatar& Service::cautaLocatarByNumber(int numar) {
	vali.validateLocatar(numar, "a", 1, "a");
	return rep.find(numar);
}

void Service::modificaLocatar(const int numar, const string nume,const int suprafata,const string tip) {
	vali.validateLocatar(numar, nume, suprafata, tip);
	Locatar l = rep.find(numar);
	rep.deleteLocatar(l);
	Locatar nou{ numar,nume,suprafata,tip };
	rep.store(nou);
	//
	undoActions.push_back(new UndoModifica(rep, l.get_numar(), l.get_nume(), l.get_suprafata(), l.get_tip()));
}


vector<Locatar> Service::filtrareSuprafata(const int suprafata) {
	return filtrare([suprafata](const Locatar& l) {
		return l.get_suprafata() >= suprafata;
		});
}

vector<Locatar> Service::filtrareTip(const string& tip) {
	return filtrare([tip](const Locatar& l) {
		return l.get_tip() == tip;
		});
}

vector<Locatar> Service::sortareLocatari(Sort functieSortare) {
	vector<Locatar> lista_sortata;
	vector<Locatar> lista = rep.getAll();
	if (lista.size() == 0) {
		return lista_sortata;
	}
	else {
		for (int i = 0;i < lista.size();i++) {
			lista_sortata.push_back(Locatar(lista.at(i)));
		}
		bool sortat = false;
		while (!sortat) {
			sortat = true;
			for (int i = 0;i < lista_sortata.size() - 1;i++) {
				if (functieSortare(lista_sortata.at(i), lista_sortata.at(i + 1)) > 0) {
					Locatar aux = lista_sortata.at(i);
					lista_sortata.at(i) = lista_sortata.at(i + 1);
					lista_sortata.at(i + 1) = aux;
					sortat = false;
				}
			}
		}
		return lista_sortata;
	}
}


void Service::emptyList() noexcept {
	rep.emptyList();
	notify();
}
void Service::addList(int index) {
	rep.addList(index);
	notify();
}
void Service::addRandom(int count) {
	rep.addRandom(count);
	notify();
}
void Service::exportare(string name) {
	std::ofstream f;
	f.open(name, std::fstream::out);
	if (rep.getList().empty()) {
		f.close();
		return;
	}
	for (const auto& a : rep.getList()) {
		f << a.get_numar() << "," << a.get_nume() << "," << a.get_suprafata() << "," << a.get_tip() << "\n";
	}
	f.close();
}

vector<pair<string, DTO>> Service::getRaport() {
	std::unordered_map<string, DTO> mymap;
	for (const auto x : getAll()) {
		DTO dto{ 1,x.get_tip() };
		if (mymap.find(dto.tip) != mymap.end()) {
			mymap[dto.tip].count++;
		}
		else {
			mymap[dto.tip] = dto;
		}
	}
	vector<pair<string, DTO>> list;
	for (const pair<string, DTO> x : mymap) {
		list.push_back(x);
	}
	return list;
}

void Service::save() {
	std::ofstream f("date.txt");
	if (rep.getAll().empty()) {
		f.close();
		return;
	}
	for(const auto& x : this->rep.getAll()) {
		f << x.get_numar() << "\n" << x.get_nume() << "\n" << x.get_suprafata() << "\n" << x.get_tip() << "\n";
	}
	f.close();
}

void Service::undo() {
	if (undoActions.empty())
		throw ServiceException("Nu putem da undo!");
	ActiuneUndo* actiune = undoActions.back();
	actiune->doUndo();
	undoActions.pop_back();
	delete actiune;
}

int sortNumeTest(const Locatar& l1, const Locatar& l2) {
	return l1.get_nume().compare(l2.get_nume());
}
int sortSuprafataTest(const Locatar& l1, const Locatar& l2) {
	return l1.get_suprafata() > l2.get_suprafata();
}
int sortTipTest(const Locatar& l1, const Locatar& l2) {
	return l1.get_tip().compare(l2.get_tip());
}

void test_serv2() {
	LocatarRepo rep;
	Validator valid;
	Service serv{ rep,valid };
	serv.sortareLocatari(sortSuprafataTest);
	serv.addLocatar(1, "a", 1, "aa");
	serv.addLocatar(3, "c", 3, "cc");
	serv.addLocatar(2, "b", 2, "bb");
	serv.addList(1);
	serv.addList(2);
	serv.addRandom(1);
	serv.exportare("test.txt");
	serv.emptyList();
	serv.exportare("test.txt");
}
void test_serv() {
	LocatarRepo rep;
	Validator valid;
	Service serv{ rep,valid };


	serv.addLocatar(1, "a", 1, "a");
	assert(serv.getAll().size() == 1);
	serv.deleteLocatar(1);
	assert(serv.getAll().size() == 0);

	serv.addLocatar(1, "a", 1, "a");
	Locatar temp = serv.cautaLocatar(1, "a");
	assert(temp.get_suprafata() == 1);
	assert(temp.get_tip() == "a");

	serv.modificaLocatar(1, "b", 2, "b");
	temp = serv.cautaLocatar(1, "b");
	assert(temp.get_nume() == "b");
	assert(temp.get_suprafata() == 2);
	assert(temp.get_tip() == "b");
	serv.deleteLocatar(1);
	assert(serv.getAll().size() == 0);

	serv.addLocatar(1, "a", 1, "aa");
	serv.addLocatar(3, "c", 3, "cc");
	serv.addLocatar(2, "b", 2, "bb");
	assert(serv.getAll().size() == 3);

	vector<Locatar> lista_filtrata1 = serv.filtrareSuprafata(3);
	assert(lista_filtrata1.size() == 1);
	assert(lista_filtrata1.at(0).get_nume() == "c");

	vector<Locatar> lista_filtrata2 = serv.filtrareTip("aa");
	assert(lista_filtrata2.size() == 1);
	assert(lista_filtrata2.at(0).get_nume() == "a");

	auto list1 = serv.sortareLocatari(sortSuprafataTest);
	auto list2 = serv.sortareLocatari(sortNumeTest);
	auto list3 = serv.sortareLocatari(sortTipTest);

	assert(list1.size() == 3);
	assert(list1.at(1).get_suprafata() == 2);
	assert(list1.at(2).get_suprafata() == 3);

	assert(list2.size() == 3);
	assert(list2.at(1).get_nume() == "b");
	assert(list2.at(2).get_nume() == "c");

	assert(list3.size() == 3);
	assert(list3.at(1).get_tip() == "bb");
	assert(list3.at(2).get_tip() == "cc");


	try {
		serv.addLocatar(-1, "", -1, "");
	}
	catch (Exception exception) {
		string mesaj = exception.getMesaj();
	}

	serv.addLocatar(4, "d", 4, "dd");
	serv.addLocatar(5, "e", 5, "ee");
	serv.addLocatar(6, "f", 6, "ff");
}

void test_serv3() {
	LocatarRepo rep;
	Validator valid;
	Service serv{ rep,valid };
	
	serv.save();
	try {
		serv.undo();
	}
	catch (ServiceException exception) {
		string mesaj = exception.getErrorMessage();
		cout << "Test undo";
	}

	serv.addLocatar(4, "d", 4, "dd");
	serv.addLocatar(5, "e", 5, "ee");
	serv.addLocatar(6, "e", 6, "ee");
	serv.save();
	vector<pair<string,DTO>> list = serv.getRaport();

	serv.undo();
	serv.deleteLocatar(4);
	serv.undo();
	serv.modificaLocatar(5, "test", 55, "test");
	serv.undo();
	serv.cautaLocatarByNumber(5);
	
	vector<Locatar> temp = serv.getListaa();
}