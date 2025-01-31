#pragma once
#include <string.h>
#include <assert.h>

void trimTrailing(char* str) {
	int i, poz = -1;
	for (i = 0; i < strlen(str); i++) {
		if (str[i] != ' ' && str[i] != '\t' && str[i] != '\n')
			poz = i;
	}
	str[poz + 1] = '\0';
}

void test_trimTrailing(){
	char ceva[30] = "blabla   ";
	assert(strcmp(ceva, "blabla   ") == 0);
	trimTrailing(ceva);
	assert(strcmp(ceva, "blabla")==0 );
}