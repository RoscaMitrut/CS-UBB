#include "console.h"
#include "locatar.h"
//#include "vectorTemplate.h"
#include <iostream>
#include <string>

using std::cout;
using std::cin;
using std::pair;

void consola_ui::tipareste(const vector<Locatar>& locatari) {
		for (const auto& locatar : locatari) {
			cout << " " << locatar.get_numar() << " " << locatar.get_nume() << " " << locatar.get_suprafata() << " " << locatar.get_tip() << "\n";
		}
}

void consola_ui::adaugaUI() {
	string tip, nume;
	int numar, suprafata;
	char temp_nr[20];
	char temp_supr[20];
	cout << "Dati numar: ";
	cin >> temp_nr;
	numar = atoi(temp_nr);
	cout << "Dati nume: ";
	cin >> nume;
	cout << "Dati suprafata: ";
	cin >> temp_supr;
	suprafata = atoi(temp_supr);
	cout << "Dati tip: ";
	cin >> tip;
	try {
		serv.addLocatar(numar, nume, suprafata, tip);
		cout << "Adaugare finalizata cu succes\n";
	}
	catch (Exception exception) {
		cout << exception.getMesaj();
	}
}

void consola_ui::stergeUI() {
	int numar;char temp_nr[20];
	cout << "Numar: ";
	cin >> temp_nr;
	numar = atoi(temp_nr);
	try {
		serv.deleteLocatar(numar);
		cout << "Stergere finalizata cu succes\n";
	}
	catch (Exception exception) {
		cout << exception.getMesaj();
	}
}

void consola_ui::cautaUI() {
	try {
		string nume;
		int numar;char temp_nr[20];
		cout << "Numar: ";
		cin >> temp_nr;
		numar = atoi(temp_nr);
		cout << "Numele proprietarului: ";
		cin >> nume;
		Locatar l = serv.cautaLocatar(numar, nume);
		cout << " nr | nume | suprafata | tip\n";
		cout << " " << l.get_numar() << " | " << l.get_nume() << " | " << l.get_suprafata() << " | " << l.get_tip() << "\n";
		cout << "--------------------\n";
	}
	catch (Exception exception) {
		cout << exception.getMesaj();
	}
}

void consola_ui::modificaUI() {
	int numar, suprafata;string nume, tip;char temp_nr[20], temp_supr[20];
	cout << "Numarul apartamentului pe care dorim sa il modificam: ";
	cin >> temp_nr;
	numar = atoi(temp_nr);
	cout << "Proprietar nou: ";
	cin >> nume;
	cout << "Suprafata noua: ";
	cin >> temp_supr;
	suprafata = atoi(temp_supr);
	cout << "Tip nou: ";
	cin >> tip;
	try {
		serv.modificaLocatar(numar, nume, suprafata, tip);
		cout << "Modificare realizata cu succes\n";
	}
	catch (Exception exception) {
		cout << exception.getMesaj();
	}
}

void consola_ui::filtrareSuprafataUI() {
	int suprafata;char temp_supr[20];
	cout << "Suprafata minima: ";
	cin >> temp_supr;
	suprafata = atoi(temp_supr);
	try {
		tipareste(serv.filtrareSuprafata(suprafata));
	}
	catch (Exception exception) {
		cout << exception.getMesaj();
	}
}

void consola_ui::filtrareTipUI() {
	string tip;
	cout << "Tip cautat: ";
	cin >> tip;
	try {
		tipareste(serv.filtrareTip(tip));
	}
	catch (Exception exception) {
		cout << exception.getMesaj();
	}
}

int sortNume(const Locatar& l1, const Locatar& l2) {
	return l1.get_nume().compare(l2.get_nume());
}

int sortSuprafata(const Locatar& l1, const Locatar& l2) {
	return l1.get_suprafata() > l2.get_suprafata();
}

int sortTip(const Locatar& l1, const Locatar& l2) {
	return l1.get_tip().compare(l2.get_tip());
}

void consola_ui::sortareUI() {
	cout << "1.Nume  2.Suprafata  3.Tip     ";
	string optiune;
	cin >> optiune;
	if (optiune == "1"){
		try {
			auto lista_sortata = serv.sortareLocatari(sortNume);
			tipareste(lista_sortata);
		}
		catch (Exception exception) {
			cout << exception.getMesaj();
		}
	}
	else if (optiune == "2") {
		try {
			auto lista_sortata = serv.sortareLocatari(sortSuprafata);
			tipareste(lista_sortata);
		}
		catch (Exception exception) {
			cout << exception.getMesaj();
		}

	}
	else if (optiune == "3") {
		try {
			auto lista_sortata = serv.sortareLocatari(sortTip);
			tipareste(lista_sortata);
		}
		catch (Exception exception) {
			cout << exception.getMesaj();
		}

	}
	else {
		cout << "Optiune introdusa gresit";
	}
}

void consola_ui::ListaUI() {
	while (1){
		cout << "1.Golire lista  2.Adaugare  3.Generare  4.Export  0.Exit   ";
		int cmd;
		cin >> cmd;
		if (cmd == 1) {
			try {
				serv.emptyList();
				cout << "Golire lista realizata";
			}
			catch (...) {
				cout << "Nu s-a realizat executia\n";
			}

		}
		else if (cmd == 2) {
			try {
				int index;
				if (serv.getAll().size() == 0) {
					cout << "Nu exista apartamente in lista!\n";
					continue;
				}
				cout << "Introduceti numarul apartamentului ales: ";
				cin >> index;
				if (index<1 || index>serv.getAll().size()) {
					cout << "Index invalid!\n";
					continue;
				}
				try {
					serv.addList(index);
					cout << "Adaugare realizata!\n";
				}
				catch (LocatarRepoException& ve) {
					cout << ve.getErrorMessage() << "\n";
				}
			}
			catch (...) {
				cout << "Nu s-a realizat executia\n";
			}

		}
		else if (cmd == 3) {
			try {
				int count;
				cout << "Nr apartamente de adaugat: ";
				cin >> count;
				if (count<1 || count>serv.getAll().size()) {
					cout << "Numar invalid!\n";
					continue;
				}
				serv.addRandom(count);
				cout << "Adaugare realizata!\n";
			}
			catch (...) {
				cout << "Nu s-a realizat executia\n";
			}

		}
		else if (cmd == 4) {
			try {
				string name;
				cout << "Nume fisier de exportat: ";
				cin >> name;
				if (name != "") {
					serv.exportare(name);
					cout << "Exportare realizata!\n";
				}
				else
					cout << "Nume invalid!\n";
			}
			catch (...) {
				cout << "Nu s-a realizat executia\n";
			}

		}
		else if (cmd == 0) return;
		else {
			cout << "Comanda introdusa gresit!\n";
		}
	}
}

void consola_ui::RaportUI() {
	try {
		vector<pair<string, DTO>> list;
		list = serv.getRaport();
		cout << "\n";
		for (const pair<string, DTO> a : list) {
			cout << "Numarul de apartamente de tipul " << a.second.tip << " este: " << a.second.count << "\n";
		}
	}
	catch (...) {
		cout << "Nu s-a realizat executia\n";
	}
}

void consola_ui::SaveUI() {
	try {
		serv.save();
		cout << "\nSalvare realizata! \n";
	}
	catch (...) {
		cout << "Nu s-a realizat executia\n";
	}
}

void consola_ui::UndoUI() {
	try {
		serv.undo();
		cout << "\nUndo realizat!\n";
	}
	catch (ServiceException& se) {
		cout << "\n" << se.getErrorMessage() << "\n";
	}
}

void consola_ui::start() {
	while (true){
		int cmd;
		cout << "0.Iesire  1.Adaugare  2.Tiparire  3.Stergere  4.Cautare  5.Modificare  6.FiltrareSuprafata  7.FiltrareTip  8.Sortare  9.Lista  10.Raport  11.Salvare  12.Undo  ";
		cin >> cmd;
		try {
			switch (cmd){
			case 1:
				adaugaUI();
				break;
			case 2:
				try {
					vector<Locatar>loc_temp;
					loc_temp = serv.getAll();
					tipareste(loc_temp);
				}
				catch (Exception ex) {
					cout << ex.getMesaj();
				}
				break;
			case 3:
				stergeUI();
				break;
			case 4:
				cautaUI();
				break;
			case 5:
				modificaUI();
				break;
			case 6:
				filtrareSuprafataUI();
				break;
			case 7:
				filtrareTipUI();
				break;
			case 8:
				sortareUI();
				break;
			case 9:
				ListaUI();
				break;
			case 10:
				RaportUI();
				break;
			case 11:
				SaveUI();
				break;
			case 12:
				UndoUI();
				break;
			case 0:
				return;
			default:
				cout << "Comanda invalida\n";
				break;
			}
		}
		catch (const LocatarRepoException& ex) {
			cout << ex << "\n";
		}
	}
}