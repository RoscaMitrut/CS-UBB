#include "GUIlista.h"


void GUIlista::initializeGUI() {
	QHBoxLayout* lyMain = new QHBoxLayout;
	this->setLayout(lyMain);
	QWidget* form = new QWidget;
	QFormLayout* lyform = new QFormLayout;

	QWidget* left = new QWidget;
	QVBoxLayout* lyleft = new QVBoxLayout;

	left->setLayout(lyleft);

	form->setLayout(lyform);

	lyform->addRow(this->numarlbl, this->numarEdit);
	//lyform->addRow(this->generatelbl, this->generateEdit);
	lyform->addRow(this->exportlbl, this->exportEdit);

	addbtn = new QPushButton{ "Adauga in lista" };
	generatebtn = new QPushButton{ "Genereaza random" };
	emptybtn = new QPushButton{ "Goleste lista" };
	exportbtn = new QPushButton{ "Export lista" };

	lyleft->addWidget(form);
	lyleft->addWidget(addbtn);
	lyleft->addWidget(generatebtn);
	lyleft->addWidget(emptybtn);
	lyleft->addWidget(exportbtn);
	lyMain->addWidget(left);

	QWidget* list = new QWidget;
	QVBoxLayout* lylist = new  QVBoxLayout;
	list->setLayout(lylist);
	locatarlist->setFixedWidth(400);
	locatarlist->setSelectionMode(QAbstractItemView::SingleSelection);
	lylist->addWidget(locatarlist);

	lyMain->addWidget(list);

}

void GUIlista::connectSignals()
{
	QObject::connect(addbtn, &QPushButton::clicked, [=]() {
		int numar = numarEdit->text().toInt();
		try {
			//Locatar locatar = serv.cautaLocatarByNumber(numar);
			serv.addList(numar);
			reloadList();
		}
		catch (LocatarRepoException& re) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(re.getErrorMessage()));
		}
		catch (ServiceException& se) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(se.getErrorMessage()));
		}
		});

	QObject::connect(emptybtn, &QPushButton::clicked, [=]() {serv.emptyList(); reloadList();});
	
	QObject::connect(generatebtn, &QPushButton::clicked, [=]() {
		//int a = generateEdit->text().toInt();
		//int b = serv.getAll().size();
		///if (a > b) {
		//	QMessageBox::warning(this, "Warning", "Numarul pe care doriti sa il generati este prea mare!");
		//	a = b;
		//}
		serv.addRandom(1);
		reloadList();
		});
	
	QObject::connect(exportbtn, &QPushButton::clicked, [=]() {
		string nume = exportEdit->text().toStdString();
		serv.exportare(nume);
		QMessageBox::information(this, "Reusit!", "Exportarea a fost realizata cu succes!");
		});

}

void GUIlista::reloadList()
{
	list = serv.getListaa();
	locatarlist->clear();
	for (auto x : list) {
		QString item = QString::fromStdString(std::to_string(x.get_numar()) + "\t" + x.get_nume() + "\t" + std::to_string(x.get_suprafata()) + "\t" + x.get_tip());
		locatarlist->addItem(item);
	}
}
