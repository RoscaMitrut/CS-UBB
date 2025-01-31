#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include <string.h>

/*
Functie care ia ca input un sir de caractere si sterge spatiile, tab-urile sau endline-urile de la sfarsit, daca acestea exista
input: sir de caractere 
return: sir de caractere procesat
*/
void trimTrailing(char* str);

void test_trimTrailing();