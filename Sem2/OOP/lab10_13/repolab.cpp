#include <algorithm>
#include "repo.h"
#include "validator.h"


/*
class Exception {
	string msg;
public:
	explicit Exception(string m) : msg{ std::move(std::move(m)) } {
	}

	string getMessage() {
		return msg;
	}
};
*/
void RepoLab::pass() const {
	double random = ((double)rand() / (RAND_MAX));
	cout << random<<"\n";
	if (random > prob) {
		throw (Exception("Nu s-a facut executia\n\n"));
	}
}



bool RepoLab::exist(const Locatar& l) {
	int i = 0;
	while (i < locatari.size()) {
		if (locatari[i].get_numar() == l.get_numar()) {
			return true;
		}
		i++;
	}
	return false;
}

void RepoLab::setProb(double p) {
	prob = p;
}

void RepoLab::store(const Locatar& l) {
	pass();
	if (exist(l)) {
		throw LocatarRepoException("Oferta exista deja in lista.");
	}
	int i = int(locatari.size());
	locatari.insert(std::make_pair(i, l));
}

const void RepoLab::deleteLocatar(const Locatar& l) {
	pass();
	int i = 0;
	while (i < locatari.size() && locatari.at(i).get_numar() != l.get_numar()) {
		i++;
	}
	if (i > locatari.size()) throw LocatarRepoException("Oferta nu exista!");
	for (int j = i; j < locatari.size() - 1; j++) {
		locatari[j] = locatari[j + 1];
	}
	locatari.erase((int)locatari.size() - 1);
}

const Locatar& RepoLab::find(int numar) const {
	pass();
	auto it = find_if(locatari.begin(), locatari.end(), [=](const std::pair<int, Locatar> l) {
		return l.second.get_numar() == numar;
		});
	if (it == locatari.end())
		throw LocatarRepoException("Oferta nu exista.");
	return (*it).second;
}

vector<Locatar> LOC;

const vector<Locatar>& RepoLab::getAll()const  {
	pass();
	LOC.clear();
	for (auto& o : locatari) {
		LOC.push_back(o.second);
	}
	return LOC;
}


const Locatar& RepoLab::find_by_name_and_number(int numar, string nume) const {
	pass();
	auto it = find_if(locatari.begin(), locatari.end(), [=](const std::pair<int, Locatar> l) {
		return l.second.get_numar() == numar && l.second.get_nume()==nume;
		});
	if (it == locatari.end())
		throw LocatarRepoException("Oferta nu exista.");
	return (*it).second;
}

void RepoLab::emptyList() noexcept {
	pass();

	listaa.clear();
}

const int RepoLab::marime() noexcept {
	pass();

	return static_cast<int>(locatari.size());
}

int RepoLab::get_random() {
	pass();
	std::mt19937 mt{ std::random_device{}() };
	const int i = marime();
	std::uniform_int_distribution<> dist(1, i);
	const int rnd = dist(mt);
	return rnd;
}

void RepoLab::addList(int index) {
	pass();

	bool adv = true;
	if (!listaa.empty())
		for (auto a : listaa) {
			if (a.get_numar() == locatari.at(index-1).get_numar()) adv = false;
		}
	if (adv)
		listaa.push_back(locatari.at(index - 1));
	//	else throw LocatarRepoException("Apartamentul cu numarul dat exista deja pe lista! \n");
}

void RepoLab::addRandom(int count) {
	pass();

	emptyList();
	while (listaa.size() != count) {
		//		try {
		addList(get_random());
		//		}
		//		catch(LocatarRepoException){}
	}
}
const vector<Locatar>& RepoLab::getList() noexcept {
	pass();

	return listaa;
}
