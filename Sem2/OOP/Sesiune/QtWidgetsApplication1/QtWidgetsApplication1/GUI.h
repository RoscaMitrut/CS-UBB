#pragma once
#include "Service.h"
#include "Model.h"
#include <QDebug>
#include "QTableView"
#include "QLayout"
#include "QWidget"
#include "QAbstractItemView"
#include "QVBoxLayout"
#include "QTableWidget"
#include <QBrush>
#include "QLabel"
#include "QLineEdit"
#include "QPushButton"
#include "QMessageBox"

class GUI : public QWidget {
private:
    Service& service;

    QTableView* tabel = new QTableView;
    MyTableModel* model = new MyTableModel{ service.getAll() };

    QVBoxLayout* layout = new QVBoxLayout();

    QLineEdit* editTitlu = new QLineEdit;
    QLineEdit* editArtist = new QLineEdit;
    QLineEdit* editGen = new QLineEdit;

    QPushButton* btnAdaugare = new QPushButton("Adaugare");

    QPushButton* btnStergere = new QPushButton("Stergere");


    void init_GUI();
    void connectSignalSlots();
    void reload_GUI();

public:
    GUI(Service& service) : service(service) {
        init_GUI();
        connectSignalSlots();
        //reload_GUI();
    }
};