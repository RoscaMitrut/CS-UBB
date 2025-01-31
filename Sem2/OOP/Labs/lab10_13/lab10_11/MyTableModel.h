#pragma once
#pragma once
#include <QAbstractTableModel>
#include <QBrush>
#include <Qt>
#include "locatar.h"
#include <vector>
#include <qdebug.h>
#include <qfont.h>
using std::vector;


class MyTableModel :public QAbstractTableModel {
	std::vector<Locatar> locatari;
public:
	MyTableModel(const std::vector<Locatar>& movies) :locatari{ locatari } {
	}

	int rowCount(const QModelIndex& parent = QModelIndex()) const override {
		return locatari.size();
		return 100;
	}
	int columnCount(const QModelIndex& parent = QModelIndex()) const override {
		return 4;
	}

	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
		if (role == Qt::FontRole) {
			QFont f;
			f.setItalic(index.row() % 2 == 1);
			f.setBold(index.row() % 2 == 1);
			return f;
		}

		if (role == Qt::DisplayRole) {
			if (index.row() < locatari.size()) {
				Locatar p = locatari[index.row()];
				if (index.column() == 0) {
					return QString::number(p.get_numar());
				}
				else if (index.column() == 1) {
					return QString::fromStdString(p.get_nume());
				}
				else if (index.column() == 2) {
					return QString::number(p.get_suprafata());
				}
				else if (index.column() == 3) {
					return QString::fromStdString(p.get_tip());
				}
			}
			else return QString::fromStdString("");
		}

		return QVariant{};
	}

	void setLocatari(const vector<Locatar> locatari) {
		this->locatari = locatari;
		auto topLeft = createIndex(0, 0);
		auto bottomR = createIndex(rowCount(), columnCount());
		emit layoutChanged();
		emit dataChanged(topLeft, bottomR);
	}

	Qt::ItemFlags flags(const QModelIndex& /*index*/) const {
		return Qt::ItemIsSelectable | Qt::ItemIsEditable | Qt::ItemIsEnabled;
	}

	QVariant headerData(int section, Qt::Orientation orientation, int role) const{
		if (role == Qt::DisplayRole)
		{
			if (orientation == Qt::Horizontal) {
				switch (section)
				{
				case 0:
					return tr("Numar");
				case 1:
					return tr("Nume");
				case 2:
					return tr("Suprafata");
				case 3:
					return tr("Tip");
				default:
					return QString("");
				}
			}
		}
		return QVariant();
	}
};
