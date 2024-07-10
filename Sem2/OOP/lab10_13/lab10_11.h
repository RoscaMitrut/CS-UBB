#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_lab10_11.h"

class lab10_11 : public QMainWindow
{
    Q_OBJECT

public:
    lab10_11(QWidget *parent = nullptr);
    ~lab10_11();

private:
    Ui::lab10_11Class ui;
};
