#pragma once
#include "locatar.h"
#include <sstream>
#include <algorithm>
#include <random>
//#include "vectorTemplate.h"
#include <vector>
#include <string>
#include <ostream>
#include <unordered_map>
using std::vector;
using std::string;
using std::ostream;

class RepoAbstract {
public:
	RepoAbstract() = default;

	RepoAbstract(const RepoAbstract & l) = delete;

	virtual void store(const Locatar& l) = 0;

	virtual const void deleteLocatar(const Locatar& l) = 0;

	virtual const Locatar& find(int numar)const = 0;

	virtual const vector<Locatar>& getAll()const  = 0;

	virtual const Locatar& find_by_name_and_number(int numar, string nume) const =0;
	virtual int get_random() =0;
	virtual void emptyList() noexcept =0;
	virtual void addList(int index) =0;
	virtual void addRandom(int count) =0;
	virtual const vector<Locatar>& getList() noexcept =0;
	virtual const int marime() noexcept =0;
};







class LocatarRepo : public RepoAbstract {
	vector<Locatar> all;
	vector<Locatar> listaa;
	//verificam daca exista deja l in repo
	bool exist(const Locatar& l) const;
public:
	LocatarRepo() = default;
	//nu permitem copierea
	LocatarRepo(const LocatarRepo& ot) = delete;

	//salvare locatar
	//Locatar l: locatarul pe care dorim sa il salvam
	void store(const Locatar& l) override;

	//stergere locatar
	//Locatar l: locatarul pe care dorim sa il stergem
	const void deleteLocatar(const Locatar& l) override;

	//cautarea unui locatar pe baza unui numar
	//int numar: numarul locatarului cautat
	//return: locatarul cautat
	const Locatar& find(int numar)const override;

	//cautarea unui locatar pe baza unui numar si unui nume
	//int numar: numarul locatarului cautat
	//string nume: numele locatarului cautat
	//return: locatarul cautat
	const Locatar& find_by_name_and_number(int numar,string nume) const override;
	
	//returneaza toti locatarii salvati
	const vector<Locatar>& getAll()const  override;

	int get_random() override;
	void emptyList() noexcept override;
	void addList(int index) override;
	void addRandom(int count) override ;
	const vector<Locatar>& getList() noexcept override ;
	const int marime() noexcept override;
};

class LocatarRepoFile :public LocatarRepo {
private:
	std::string file_name;
	void loadFromFile();
	void writeToFile();
public:
	LocatarRepoFile(std::string file_name) :LocatarRepo(), file_name{ file_name } {
		loadFromFile();
	}
	void store(const Locatar& l)override {
		LocatarRepo::store(l);
		writeToFile();
	}
	const void deleteLocatar(const Locatar& l)override {
		LocatarRepo::deleteLocatar(l);
		writeToFile();
	}
};











class RepoLab :public RepoAbstract {
private:
	std::unordered_map<int, Locatar> locatari;
	double prob;
	vector<Locatar> listaa;
	void pass() const;
	bool exist(const Locatar& l);

public:
	RepoLab() {
		std::mt19937 mt{ std::random_device{}() };
		std::uniform_real_distribution<double> dist(0.0, 1.0);
		prob = dist(mt);
	}

	void setProb(double p);

	RepoLab(const LocatarRepo& alt) = delete;

	void store(const Locatar& l) override;

	const void deleteLocatar(const Locatar& l) override;

	const Locatar& find(int numar) const override;

	const vector<Locatar>& getAll()const override;


	const Locatar& find_by_name_and_number(int numar, string nume) const;
	int get_random();
	void emptyList() noexcept;
	void addList(int index);
	void addRandom(int count);
	const vector<Locatar>& getList() noexcept;
	const int marime() noexcept;
};

//folosit pentru a semnala situtii exceptionale care pot aparea in repo
class LocatarRepoException {
	string msg;
public:
	LocatarRepoException(string m) :msg { m } {}

	string getErrorMessage() {
		return this->msg;
	}

	friend ostream& operator<<(ostream& out, const LocatarRepoException& ex);
};

ostream& operator<<(ostream& out, const LocatarRepoException& ex);

void test_repo();
void test_repo2();
void test_file();