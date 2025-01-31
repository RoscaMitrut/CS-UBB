#include "QtWidgetsApplication1.h"
#include <QtWidgets/QApplication>
#include "Repo.h"
#include "Service.h"
#include "GUI.h"
#include "Domain.h"

void teste() {
    test_domain();
    test_repo();
}

void RunGUI(int argc, char* argv[]) {
    QApplication a(argc, argv);
    Repository repo{ "input.txt" };
    Service serv{ repo };
    GUI gui{ serv };
    gui.show();
    a.exec();
}

int main(int argc, char* argv[]) {
    teste();
    RunGUI(argc, argv);
    return 0;
}