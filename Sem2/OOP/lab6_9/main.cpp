#include "console.h"
#include "service.h"
#include "repo.h"
#include "validator.h"
#include <crtdbg.h>
#include <iostream>
void test_all() {
	test_repo();
	test_repo2();
	test_serv();
	test_serv2();
	test_serv3();
	test_file();
	cout << "Gata testele \n\n\n";
}



int main() {
	test_all();

	LocatarRepoFile rep{ "fisier.txt" };
	//LocatarRepo rep;
	
	//RepoLab rep;
	//rep.setProb(0.50);
	
	Validator valid;
	Service serv{ rep,valid };
	consola_ui ui{ serv };
	ui.start();
	
	_CrtDumpMemoryLeaks();
	return 0;
}