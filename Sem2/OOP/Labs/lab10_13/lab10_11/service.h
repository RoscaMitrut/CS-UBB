#pragma once

#include "locatar.h"
#include "repo.h"
#include "validator.h"
#include "undo.h"
//"vectorTemplate.h"
//#include <vector>
#include <utility>
#include <memory>
#include <string>
#include <functional>

#include "Observer.h"

using std::unique_ptr;
using std::make_unique;
//using std::vector;
using std::function;

class ServiceException {
private:
	string errorMsg;
public:
	ServiceException(string errorMsg) :errorMsg{ errorMsg } {};
	string getErrorMessage() {
		return this->errorMsg;
	}
};

typedef int(*Sort)(const Locatar&, const Locatar&);

class Service: public Observable {
	RepoAbstract& rep;
	Validator& vali;
	vector<Locatar> filtrare(const function<bool(const Locatar&)>& fct);
	vector<ActiuneUndo*> undoActions;
public:
	Service(class RepoAbstract& rep, Validator& vali) noexcept :rep{ rep }, vali{ vali } {}
	
	
	Service() = delete;
	
	//returneaza toti locatarii in ordinea in care au fost adaugati
	const vector<Locatar>& getAll() {
		return rep.getAll();
	}
	


	//Adauga un locatar nou
	//int numar: numarul apartamentului
	//string nume: numele proprietarului
	//int suprafata: suprafata apartamentului
	//string tip: tipul apartamentului
	void addLocatar(const int numar, const string nume, const int suprafata, const string tip);
	
	//Sterge un locatar
	//int numar: numarul apartamentului pe care dorim sa il stergem
	void deleteLocatar(const int numar);
	
	//cauta un locatar si il returneaza
	//int numar: numarul locatarului cautat
	//string nume: numele locatarului cautat
	//return: locatarul cu numarul/numele date
	const Locatar& cautaLocatar(const int numar,const string nume);
	
	const Locatar& cautaLocatarByNumber(int numar);

	//modifica un locatar
	//int numar: numarul apartamentului locatarului de modificat
	//string nume: numele nou
	//int suprafata: suprafata noua
	//string tip: tipul nou
	void modificaLocatar(const int numar, const string nume, const int suprafata, const string tip);


	vector<Locatar> filtrareSuprafata(const int suprafata);

	vector<Locatar> filtrareTip(const string& tip);

	vector<Locatar> sortareLocatari(Sort functieSortare);

	void emptyList() noexcept;
	void addList(int index);
	void addRandom(int count);
	void exportare(string name);
	const vector<Locatar>& getListaa() {
		return rep.getList();
	}

	void save();
	void undo();
	
	vector < std::pair<string, DTO>> getRaport();

	~Service() {
		for (auto x : undoActions) delete x;
	}
};

void test_serv();
void test_serv2();
void test_serv3();