#pragma once
#include <qwidget.h>
#include <qlistwidget.h>
#include <qpushbutton.h>
#include <qlineedit.h>
#include <qboxlayout.h>
#include <qdebug.h>
#include "service.h"
#include "locatar.h"
#include <vector>
#include <qlabel.h>
#include "MyTableModel.h"
#include <vector>
#include <string>
#include <QtWidgets/QApplication>
#include <QLabel>
#include <QHBoxLayout>
#include <QFormLayout>
#include <QTableWidget>
#include <QMessageBox>
#include <QHeaderView>
#include <QGroupBox>
#include <QRadioButton>
#include <QDebug>
#include "Service.h"
#include <iostream>
#include <fstream>
#include "GUIlista.h"

class LocatarGUI : public QWidget {
private:
	Service& ctr;
	QTableWidget* tableLocatari;
	QTableView* tableLocatar = new QTableView;
	MyTableModel* model;

	QHBoxLayout* lyMain = new QHBoxLayout;
	QListWidget* lst;
	QListWidget* listaa;
	QVBoxLayout* lyLista= new QVBoxLayout;
	QPushButton* btnSortByNume;
	QPushButton* btnSortBySuprafata;
	QPushButton* btnSortByTip;
	QPushButton* btnSortByNr;
	QPushButton* btnReload;
	QPushButton* btnAddToCos;
	QPushButton* btnStergere;
	QPushButton* btnUndo;

	QPushButton* btnLista;
	QPushButton* btnListaReadOnly;

	QLineEdit* txtFiltrareSuprafata;
	QPushButton* btnFiltrareSuprafata;
	QPushButton* btnGolire;
	QPushButton* btnExport;
	QLineEdit* txtFiltrareTip;
	QPushButton* btnFiltrareTip;

	QLineEdit* txtNumar;
	QLineEdit* txtNume;
	QLineEdit* txtSuprafata;
	QLineEdit* txtTip;
	QPushButton* btnAdd;
	QPushButton* btnModifica;
	QLineEdit* txtCautare;
	QPushButton* btnCautare;
	QPushButton* btnRandom;




	QVBoxLayout* lyBtnDy = new QVBoxLayout;
	QWidget* btnDyn = new QWidget;

	void initGUICmps();
	void connectSignalsSlots();
	void reloadList(const std::vector<Locatar>& locatari);

//	void reloadListaa(const std::vector<Locatar>& locatari);

	void adaugaDinamic(const std::vector<Locatar>& locatari);
public:
	LocatarGUI(Service& ctr) :ctr{ ctr } {
		this->model = new MyTableModel{ ctr.getAll() };
		this->tableLocatar->setModel(model);
		initGUICmps();
		connectSignalsSlots();
		reloadList(ctr.getAll());
		adaugaDinamic(ctr.getAll());
	}
	void addNewLocatar();
};
