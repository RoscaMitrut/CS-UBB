#pragma once
#include "service.h"
#include <string>


class consola_ui {
	Service& serv;
	
	//citeste de la tastatura si adauga un locatar
	void adaugaUI();
	
	//citerste de la tastatura un numar si sterge locatarul cu numarul respectiv
	void stergeUI();
	
	//citeste de la tastatura un nume si un numar si tipareste locatarul cu datele respective
	void cautaUI();
	
	//citeste de la tastatura numar,nume,suprafata,tip si modifica locatarul care are numarul dat
	void modificaUI();
	
	//tipareste o lista de locatari
	void tipareste(const vector<Locatar>& locatari);

	//filtrare dupa suprafata
	void filtrareSuprafataUI();

	//filtrare dupa tip
	void filtrareTipUI();

	//sortare locatari
	void sortareUI();

	//intrare in interfata Lista
	void ListaUI();
	
	//Raport
	void RaportUI();

	//Salvare in fisier
	void SaveUI();

	//Undo
	void UndoUI();
public:
	consola_ui(Service& serv) noexcept :serv { serv } {}
	//nu permitem copierea obiectelor
	consola_ui(const consola_ui& ot) = delete;

	void start();
};