#include "GUILocatar.h"
#include "locatar.h"
#include <algorithm>
#include <string.h>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QFormLayout>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMessageBox>
#include <qpalette.h>
#include <qbrush.h>
#include<qdebug.h>
#include <string>
#include <algorithm>
#include <iostream>
#include "GUIlista.h"



int sortNr(const Locatar& l1, const Locatar& l2) {
	return l1.get_numar() > l2.get_numar();
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

void LocatarGUI::initGUICmps() {

	setLayout(lyMain);

	QWidget* widStanga = new QWidget;
	QVBoxLayout* vl1 = new QVBoxLayout;
	widStanga->setLayout(vl1);


	int noLines = 10;
	int noColumns = 4;
	tableLocatari = new QTableWidget{ noLines,noColumns };
	tableLocatari->setSelectionMode(QTableWidget::SingleSelection);
	QStringList tblHeaderList;
	tblHeaderList << "Nume" << "Numar" << "Suprafata" << "Tip";
	this->tableLocatari->setHorizontalHeaderLabels(tblHeaderList);

	this->tableLocatari->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);


	vl1->addWidget(tableLocatar);
	//lst = new QListWidget;
	//vl1->addWidget(lst);

	QWidget* widBtnStanga = new QWidget;
	QHBoxLayout* lyBtnsDr = new QHBoxLayout;
	widBtnStanga->setLayout(lyBtnsDr);

	btnSortByNr = new QPushButton("Sort nr");
	lyBtnsDr->addWidget(btnSortByNr);

	btnSortByNume = new QPushButton("Sort nume");
	lyBtnsDr->addWidget(btnSortByNume);

	btnSortBySuprafata = new QPushButton("Sort suprafata");
	lyBtnsDr->addWidget(btnSortBySuprafata);

	btnSortByTip = new QPushButton("Sort tip");
	lyBtnsDr->addWidget(btnSortByTip);

	QWidget* widBtnStanga2 = new QWidget;
	QHBoxLayout* lyBtnsDr2 = new QHBoxLayout;
	widBtnStanga2->setLayout(lyBtnsDr2);

	btnReload = new QPushButton("Reload");
	lyBtnsDr2->addWidget(btnReload);

	btnStergere = new QPushButton("Stergere");
	lyBtnsDr2->addWidget(btnStergere);
	//btnStergere->setDisabled(true);

	btnUndo = new QPushButton("Undo");
	lyBtnsDr2->addWidget(btnUndo);



	btnLista = new QPushButton("Lista");
	lyBtnsDr2->addWidget(btnLista);
	btnListaReadOnly = new QPushButton("ROnly");
	lyBtnsDr2->addWidget(btnListaReadOnly);

	//btnAddToCos = new QPushButton("Adauga in cos");
	//lyBtnsDr2->addWidget(btnAddToCos);
	//btnAddToCos->setDisabled(true);

	vl1->addWidget(widBtnStanga);
	vl1->addWidget(widBtnStanga2);

	lyMain->addWidget(widStanga);


	QWidget* widDetalii = new QWidget;
	QFormLayout* layout2 = new QFormLayout;
	widDetalii->setLayout(layout2);

	txtNumar = new QLineEdit;
	layout2->addRow(new QLabel("Numar:"), txtNumar);

	txtNume = new QLineEdit;
	layout2->addRow(new QLabel("Nume:"), txtNume);
	txtSuprafata = new QLineEdit;
	layout2->addRow(new QLabel("Suprafata:"), txtSuprafata);
	txtTip = new QLineEdit;
	layout2->addRow(new QLabel("Tip:"), txtTip);

	btnAdd = new QPushButton("Add Locatar");
	layout2->addWidget(btnAdd);

	btnModifica = new QPushButton("Modifica");
	layout2->addWidget(btnModifica);

	layout2->addWidget(new QLabel);

	txtCautare = new QLineEdit;
	btnCautare = new QPushButton("Cauta");
	layout2->addRow(new QLabel("Cautare:"), txtCautare);
	layout2->addWidget(btnCautare);

	layout2->addWidget(new QLabel);

	txtFiltrareSuprafata = new QLineEdit;
	btnFiltrareSuprafata = new QPushButton("FiltrareSuprafata");
	layout2->addRow(new QLabel("Filtru suprafata:"), txtFiltrareSuprafata);
	layout2->addWidget(btnFiltrareSuprafata);

	layout2->addWidget(new QLabel);

	txtFiltrareTip = new QLineEdit;
	btnFiltrareTip = new QPushButton("FiltrareTip");
	layout2->addRow(new QLabel("Filtru tip:"), txtFiltrareTip);
	layout2->addWidget(btnFiltrareTip);

	lyMain->addWidget(widDetalii);


	btnDyn->setLayout(lyBtnDy);
	lyMain->addWidget(btnDyn);

}

void LocatarGUI::connectSignalsSlots() {

	QObject::connect(btnSortByNr, &QPushButton::clicked, [&]() {
		reloadList(ctr.sortareLocatari(sortNr));
		});

	QObject::connect(btnSortByNume, &QPushButton::clicked, [&]() {
		reloadList(ctr.sortareLocatari(sortNume));
		});

	QObject::connect(btnSortBySuprafata, &QPushButton::clicked, [&]() {
		reloadList(ctr.sortareLocatari(sortSuprafata));
		});

	QObject::connect(btnSortByTip, &QPushButton::clicked, [&]() {
		reloadList(ctr.sortareLocatari(sortTip));
		});

	QObject::connect(btnReload, &QPushButton::clicked, [&]() {
		reloadList(ctr.getAll());
		});

	QObject::connect(btnAdd, &QPushButton::clicked, this, [&]() {
		try {
			addNewLocatar();
		}
		catch (Exception ex) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(ex.getMesaj()));
		}

		});

	QObject::connect(btnUndo, &QPushButton::clicked, this, [&]() {
		try {
			ctr.undo();
			reloadList(ctr.getAll());
		}
		catch (ServiceException& se) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(se.getErrorMessage()));
		}
		});

	QObject::connect(btnModifica, &QPushButton::clicked, this, [&]() {
		try {
			ctr.modificaLocatar(txtNumar->text().toInt(), txtNume->text().toStdString(), txtSuprafata->text().toInt(), txtTip->text().toStdString());
			reloadList(ctr.getAll());
		}
		catch (Exception ex) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(ex.getMesaj()));
		}
		});

	QObject::connect(btnCautare, &QPushButton::clicked, [&]() {
		try {
			Locatar temp = ctr.cautaLocatarByNumber(txtCautare->text().toInt());
			string mesaj = "Am gasi Locatar cu urmatoarele date:\nNumar: " + std::to_string(temp.get_numar()) + "\nNume: " + temp.get_nume() + "\nSuprafata: " + std::to_string(temp.get_suprafata()) + "\nTip: " + temp.get_tip();
			QMessageBox::information(this, "Cautare", QString::fromStdString(mesaj));
		}
		catch (LocatarRepoException ex) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(ex.getErrorMessage()));
		}
		});

	QObject::connect(btnFiltrareSuprafata, &QPushButton::clicked, [&]() {
		reloadList(ctr.filtrareSuprafata(txtFiltrareSuprafata->text().toInt()));
		});

	QObject::connect(btnFiltrareTip, &QPushButton::clicked, [&]() {
		reloadList(ctr.filtrareTip(txtFiltrareTip->text().toStdString()));
		});
	/*
	QObject::connect(lst, &QListWidget::itemSelectionChanged, [&]() {
		auto sel = lst->selectedItems();
		if (sel.isEmpty()) {
			txtNumar->setText("");
			txtNume->setText("");
			txtSuprafata->setText("");
			txtTip->setText("");
			btnAddToCos->setDisabled(true);
			btnStergere->setDisabled(true);
		}
		else {
			btnAddToCos->setEnabled(true);
			btnStergere->setEnabled(true);
		}
		});*/

		/*
		QObject::connect(btnAddToCos, &QPushButton::clicked, this, [&]() {
			auto sel = lst->selectedItems();
			try {
				if (!sel.isEmpty()) {
					auto selItem = sel.at(0);
					auto text = selItem->text();
					int numar = atoi(strtok(&text.toStdString()[0], "\t"));
					Locatar l = ctr.cautaLocatarByNumber(numar);
					ctr.addList(numar);
					reloadListaa(ctr.getListaa());
							//auto type = selItem->data(Qt::UserRole).toString();
							//ctr.addToCos(type.toStdString(), species.toStdString());
				}
			}
			catch (Exception& ex) {
				QMessageBox::warning(this, "Warning", QString::fromStdString(ex.getMesaj()));
			}
			});
		*/
	QObject::connect(btnStergere, &QPushButton::clicked, this, [&]() {
		int a = tableLocatari->currentRow();
		if (a == -1)
		{
			QMessageBox::warning(this, "Warning", "Trebuie sa fie selectata o celula cel putin!");
		}
		else {
			int numar = tableLocatari->item(a, 0)->text().toInt();
			ctr.deleteLocatar(numar);
			reloadList(ctr.getAll());
		}
		});
	/*
auto sel = lst->selectedItems();
try {
	if (!sel.isEmpty()) {
		auto selItem = sel.at(0);
		auto text = selItem->text();
		int numar = atoi(strtok(&text.toStdString()[0],"\t"));
		ctr.deleteLocatar(numar);
		reloadList(ctr.getAll());
	}
}
catch (...) {
	QMessageBox::warning(this, "Warning", QString::fromStdString("Ceva nu a mers bine"));
}*/




	/*
	QObject::connect(btnLista, &QPushButton::clicked, this, [&]() {
		QWidget* fereastraLista = new QWidget;
		fereastraLista->setLayout(lyLista);

		lyLista->addWidget(listaa);

		btnGolire = new QPushButton("Golire");
		lyLista->addWidget(btnGolire);

		btnExport = new QPushButton("Export");
		lyLista->addWidget(btnExport);

		btnRandom = new QPushButton("Add Random");
		lyLista->addWidget(btnRandom);

		fereastraLista->show();

		QObject::connect(btnGolire, &QPushButton::clicked, this, [&]() {
			ctr.emptyList();
			reloadListaa(ctr.getListaa());
			});

		QObject::connect(btnExport, &QPushButton::clicked, this, [&]() {
			ctr.exportare("fisier_lista.txt");
			});

		QObject::connect(btnRandom, &QPushButton::clicked, this, [&]() {
			ctr.addRandom(1);
			reloadListaa(ctr.getListaa());
			});

		});
		*/

	QObject::connect(btnLista, &QPushButton::clicked, [=]() {
		GUIlista* cos = new GUIlista{ ctr };
		cos->show();
		});

	QObject::connect(btnListaReadOnly, &QPushButton::clicked, [=]() {
		GUIlistaDraw* cos = new GUIlistaDraw{ ctr };
		cos->show();
		});
}

void LocatarGUI::addNewLocatar() {
	try {
		ctr.addLocatar(txtNumar->text().toInt(), txtNume->text().toStdString(), txtSuprafata->text().toInt(), txtTip->text().toStdString());
		reloadList(ctr.getAll());
	}
	catch (LocatarRepoException& ex) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(ex.getErrorMessage()));
	}
}

void clearLayout(QLayout* layout, bool deleteWidgets = true)
{
	while (QLayoutItem* item = layout->takeAt(0))
	{
		if (deleteWidgets)
		{
			if (QWidget* widget = item->widget())
				widget->deleteLater();
		}
		if (QLayout* childLayout = item->layout())
			clearLayout(childLayout, deleteWidgets);
		delete item;
	}
}

void LocatarGUI::adaugaDinamic(const std::vector<Locatar>& locatari){
	clearLayout(lyBtnDy);

	vector<Locatar> temp;
	
	for (const auto& l : locatari) {
		bool switcher = true;
		for (const auto& t : temp) {
			if (l.get_tip() == t.get_tip()) {
				switcher = false;
			}
		}
		if (switcher==true) {
			temp.push_back(l);
		}
	}
	
	for (const auto& l : temp) {
		auto btn = new QPushButton{ QString::fromStdString(l.get_tip()) };
		lyBtnDy->addWidget(btn);

		QObject::connect(btn, &QPushButton::clicked, [this, btn, l]() {
			string tip = l.get_tip();
			int count = 0;
			for (const auto& loc : ctr.getAll()) {
				if (loc.get_tip() == tip) {
					count++;
				}
			}
			QMessageBox::information(nullptr, "Info", QString::number(count));
			});
	}
}

void LocatarGUI::reloadList(const std::vector<Locatar>& locatari) {
	//lst->clear();
	//for (const auto& l : locatari) {
	//	QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(std::to_string(l.get_numar()) + "\t" + l.get_nume() + "\t" + std::to_string(l.get_suprafata())+ "\t" + l.get_tip()));
	//	
	//	lst->addItem(item);
	//}
	this->model->setLocatari(locatari);
	adaugaDinamic(ctr.getAll());
}
/*
void LocatarGUI::reloadListaa(const std::vector<Locatar>& locatari) {
	listaa->clear();
	for (const auto& l : locatari) {
		QListWidgetItem* item = new QListWidgetItem(QString::fromStdString(std::to_string(l.get_numar()) + "\t" + l.get_nume() + "\t" + std::to_string(l.get_suprafata()) + "\t" + l.get_tip()));
	
		listaa->addItem(item);
	}
}*/