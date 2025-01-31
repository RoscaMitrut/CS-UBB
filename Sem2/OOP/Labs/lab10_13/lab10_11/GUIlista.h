#pragma once
#include <QtWidgets/QApplication>
#include <qwidget.h>
#include <qlistwidget.h>
#include <qpushbutton.h>
#include <qlabel.h>
#include <qlineedit.h>
#include <qpainter.h>
#include "Observer.h"
#include "service.h"
#include <qboxlayout.h>
#include <qmessagebox.h>
#include <qformlayout.h>
#include <vector>
#include <string>
#include <QHBoxLayout>
#include <QTableWidget>
#include <QHeaderView>
#include <QGroupBox>
#include <QRadioButton>
#include <QDebug>
#include <QListWidget>
#include <QPainter>


class GUIlista : public QWidget, public Observer {
private:

	Service& serv;

	QWidget* left = new QWidget;
	QWidget* right = new QWidget;

	QListWidget* locatarlist = new QListWidget;

	QPushButton* addbtn;
	QPushButton* generatebtn;
	QPushButton* emptybtn;
	QPushButton* exportbtn;

	QLabel* numarlbl = new QLabel{ "Numar" };
	QLineEdit* numarEdit = new QLineEdit{};
	QLabel* exportlbl = new QLabel{ "Numele fisierului de export" };
	QLineEdit* exportEdit = new QLineEdit{};

	vector<Locatar> list;

	void initializeGUI();
	void connectSignals();

	void reloadList();

	void update() override {
		reloadList();
	};

	~GUIlista() {
		serv.removeObserver(this);
	}

public:
	GUIlista(Service& serv) : serv{ serv } {
		serv.addObserver(this);
		initializeGUI();
		connectSignals();
		reloadList();
	};

};


class GUIlistaDraw :public QWidget, public Observer {
private:
	Service& serv;
	void paintEvent(QPaintEvent*) override {
		QPainter p{ this };
		int x = 0;
		int y = 0;
		for (auto movie : serv.getListaa()) {
			x = rand() % 400 + 1;
			y = rand() % 400 + 1;
			qDebug() << x << " " << y;
			//QRectF target(x, y, 200, 150);
			//QRectF source(0, 0, 720, 540);
			//QImage image("scarla.jpg");

			QRectF target(x, y, 150, 138);
			QRectF source(0, 0, 300, 275);
			QImage image("six.png");

			p.drawImage(target, image, source);

			x += 40;

		}
	}

	void update() override {
		repaint();
	}
	~GUIlistaDraw() {
		serv.removeObserver(this);
	}
public:

	GUIlistaDraw(Service& serv) : serv{ serv } {
		serv.addObserver(this);
	}
};
