#include <QtWidgets/QApplication>
#include "locatar.h"
#include "validator.h"
#include "service.h"
#include "GUILocatar.h"

void test_all() {
	test_repo();
	test_repo2();
	test_serv();
	test_serv2();
	test_serv3();
	test_file();
}
void runGUI(int argc, char* argv[]) {
	test_all();

	QApplication a(argc, argv);
	//LocatarRepo rep;
	LocatarRepoFile rep{ "fisier.txt" };
	Validator valid;
	Service ctr{ rep,valid };

	LocatarGUI gui{ ctr };
	
	gui.show();
	a.exec();
}

int main(int argc, char* argv[]) {
	runGUI(argc, argv);
	return 0;
}