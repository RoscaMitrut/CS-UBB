#pragma once

#include <QLabel>
#include "GUI.h"

void GUI::init_GUI() {

    tabel->setModel(model);

    this->setLayout(layout);
    
    layout->addWidget(tabel);
    
    layout->addWidget(new QLabel("Titlu"));
    layout->addWidget(editTitlu);
    layout->addWidget(new QLabel("Artist"));
    layout->addWidget(editArtist);
    layout->addWidget(new QLabel("Gen"));
    layout->addWidget(editGen);
    
    layout->addWidget(btnAdaugare);
    layout->addWidget(btnStergere);
}

void GUI::reload_GUI() {
    model->setProduse(service.getAll());
}

void GUI::connectSignalSlots() {
    QObject::connect(btnAdaugare, &QPushButton::clicked, [this] {
        string titlu = editTitlu->text().toStdString();
        string artist = editArtist->text().toStdString();
        string gen = editGen->text().toStdString();
        editTitlu->clear();
        editArtist->clear();
        editGen->clear();
        //qDebug() << id << " " << QString::fromStdString(titlu) << " " << QString::fromStdString(artist) << " " << QString::fromStdString(gen) << "\n";
        string exceptii = service.adaugare(titlu, artist, gen);
        
        reload_GUI();

        if (exceptii != "") {
            QMessageBox msgBox;
            msgBox.setText(QString::fromStdString(exceptii));
            msgBox.exec();
        }

        });
    QObject::connect(btnStergere, &QPushButton::clicked, this, [&]() {
        int a = tabel->currentIndex().row();
        QMessageBox::warning(this, "Warning",QString::number(a));
        if (a == -1)
        {
            QMessageBox::warning(this, "Warning", "Trebuie sa fie selectata o celula cel putin!");
        }
       else {
            service.deleter(a);
            reload_GUI();
        }
        });
        
}

