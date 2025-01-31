#pragma once
#include "domain.h"
#include "service.h"

#include <QAbstractTableModel>
#include <Qt>

using std::vector;

class MyTableModel : public QAbstractTableModel {

private:
    vector<Melodie> melodii;
public:
    MyTableModel(const vector<Melodie> melodii) : melodii(melodii) {}

    int rowCount(const QModelIndex& parent = QModelIndex()) const override {
        return melodii.size();
    }

    int columnCount(const QModelIndex& parent = QModelIndex()) const override {
        return 6;
    }

    QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
        if (role == Qt::DisplayRole) {
            Melodie p = melodii[index.row()];
            if (index.column() == 0) {
                return QString::number(p.get_id());
            }
            if (index.column() == 1) {
                return QString::fromStdString(p.get_titlu());
            }
            if (index.column() == 2) {
                return QString::fromStdString(p.get_artist());
            }
            if (index.column() == 3) {
                return QString::fromStdString(p.get_gen());
            }
            if (index.column() == 4) {
                int temp = 0;
                for (auto m : melodii) {
                    if (m.get_artist() == p.get_artist()) {
                        temp++;
                    }
                }
                return QString::number(temp);
            }
            if (index.column() == 5) {
                int temp = 0;
                for (auto m : melodii) {
                    if (m.get_gen() == p.get_gen()) {
                        temp++;
                    }
                }
                return QString::number(temp);
            }
        }
        return QVariant{};
    }
    void setProduse(vector<Melodie> melodii) {
        this->melodii = melodii;
        auto topLeft = createIndex(0, 0);
        auto bottomRight = createIndex(rowCount(), columnCount());
        emit layoutChanged();
        emit dataChanged(topLeft, bottomRight);
    }
};
