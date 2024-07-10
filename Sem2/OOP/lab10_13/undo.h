#pragma once
#include "locatar.h"
#include "repo.h"

class ActiuneUndo {
public:
	virtual void doUndo() = 0;
	virtual ~ActiuneUndo() = default;
};

class UndoAdauga : public ActiuneUndo {
private:
	RepoAbstract& rep;
	Locatar loc;

public:
	UndoAdauga(RepoAbstract& rep, const Locatar& loc) :rep{ rep }, loc{ loc } {};
	void doUndo() override {
		rep.deleteLocatar(loc);
	}
};

class UndoSterge : public ActiuneUndo {
private:
	RepoAbstract& rep;
	Locatar loc;

public:
	UndoSterge(RepoAbstract& rep, const Locatar& loc) : rep{ rep }, loc{ loc } {};
	void doUndo() override {
		rep.store(loc);
	}
};

class UndoModifica : public ActiuneUndo {
private:
	RepoAbstract& rep;
	int nr;
	string nume;
	int suprafata;
	string tip;
public:
	UndoModifica(RepoAbstract& rep, int nr, string nume, int suprafata, string tip) :rep{ rep }, nr{ nr }, nume{ nume }, suprafata{ suprafata }, tip{ tip } {};
	void doUndo() override {
		Locatar l = rep.find(nr);
		rep.deleteLocatar(l);
		Locatar nou{ nr,nume,suprafata,tip };
		rep.store(nou);
	}
};