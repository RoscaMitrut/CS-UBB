%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash.h"
#define MAX_LINE_LENGTH 100
typedef struct {
	int atomCode;
	int tsPosition;
} FipElem;

HashMap TS;
FipElem *FIP;
int fipSize = 0;

int currentLine = 1;
int errorsFound = 0;

void addInFIP(int atomCode, int tsPosition) {
	FIP[fipSize].atomCode = atomCode;
	FIP[fipSize++].tsPosition = tsPosition;
}

char* getKeyFromIndex(const char* filename, int n) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        perror("Error opening file");
        return NULL;
    }

    static char key[MAX_LINE_LENGTH];
    char line[MAX_LINE_LENGTH];
    int index;

    while (fgets(line, sizeof(line), file)) {
        // Parse the line in the format: key:index
        char* token = strtok(line, ":");
        if (!token) continue; // Skip invalid lines
        strncpy(key, token, sizeof(key) - 1);
        key[sizeof(key) - 1] = '\0';

        token = strtok(NULL, ":");
        if (!token) continue; // Skip invalid lines

        index = atoi(token); // Convert index part to integer

        if (index == n) {
            fclose(file);
            return key; // Return the key if the index matches
        }
    }

    fclose(file);
    return NULL; // Return NULL if no matching index is found
}

void printTS() {
	printf("==================\n");
	printf("Table of Symbols\n");
	printf("Code\tAtom\n");
	for (int i = 0; i < HASHMAP_CAPACITY; i++) {
		const char *symbol = get_by_index(&TS, i);
		if (symbol) {
			printf("%d\t%s\n", i, symbol);
		}
	}
	printf("==================\n");
}

void printFIP() {
	printf("==================\n");
	printf("FIP\n");
	printf("Code | TS Position\n");
	for (int i = 0; i < fipSize; i++) {
		if (FIP[i].tsPosition == -1) {
			if (getKeyFromIndex("keywords.csv", FIP[i].atomCode)){
			printf("%s %d\n", getKeyFromIndex("keywords.csv", FIP[i].atomCode) ,FIP[i].atomCode);
			}
			else{
			printf("%s %d\n", ":" ,FIP[i].atomCode);
			}

		}else {
			printf("%s %d....%d\n", get_by_index(&TS, FIP[i].tsPosition) ,FIP[i].atomCode, FIP[i].tsPosition);
		}
	}
	printf("==================\n");
}

char **custom;
int sizeCustom;

void initIdentifierAndConsts() {
	custom = (char **)malloc(500 * sizeof(char *));
	sizeCustom = 0;
}

void addCustom(char *elem, int isConst) {
	char *elemCpy = strdup(elem);
	custom[sizeCustom++] = elemCpy;

	addInFIP(isConst, -2);

	if (get_index(&TS, elemCpy) == -1) {
		insert(&TS, elemCpy);
	}
}

void deleteCustom() {
	for (int i = 0; i < sizeCustom; i++) {
		free(custom[i]);
	}
	sizeCustom = 0;
	free(custom);
}

void assignTsDataToFIP() {
	int elemsSoFar = 0;
	for (int i = 0; i < fipSize; i++) {
		if (FIP[i].atomCode == 0 || FIP[i].atomCode == 1) {
			FIP[i].tsPosition = get_index(&TS, custom[elemsSoFar]);
			elemsSoFar++;
		}
	}
}

%}

%option noyywrap

digit [0-9]
nzdigit [1-9]
letter [a-zA-Z_]
number ([+-]?({nzdigit}{digit}*)|"0")("."{digit}+)? 
string \'[^\"\n]*\'     
char \'\\?.\'           
identifier {letter}({letter}|{digit})*

%%
"," {addInFIP(2, -1);} 
"integer" {addInFIP(3, -1);} 
"real" {addInFIP(4, -1);} 
"string" {addInFIP(5, -1);} 
"(" {addInFIP(6, -1);} 
")" {addInFIP(7, -1);} 
"*" {addInFIP(8, -1);} 
"++" {addInFIP(9, -1);} 
"--" {addInFIP(10, -1);} 
";" {addInFIP(11, -1);} 
"begin" {addInFIP(12, -1);} 
"clrscr" {addInFIP(13, -1);} 
"crt" {addInFIP(14, -1);} 
"end." {addInFIP(15, -1);} 
"program" {addInFIP(16, -1);} 
"readln" {addInFIP(17, -1);} 
"uses" {addInFIP(18, -1);} 
"var" {addInFIP(19, -1);} 
"writeln" {addInFIP(20, -1);} 
"if" {addInFIP(21, -1);} 
"then" {addInFIP(22, -1);} 
"else" {addInFIP(23, -1);} 
"while" {addInFIP(24, -1);} 
"do" {addInFIP(25, -1);} 
"/" {addInFIP(26, -1);} 
"+" {addInFIP(27, -1);} 
"-" {addInFIP(28, -1);} 
"<>" {addInFIP(29, -1);} 
">" {addInFIP(30, -1);} 
"'" {addInFIP(31, -1);}
"=" {addInFIP(32, -1);}
"type" {addInFIP(33, -1);} 
"record" {addInFIP(34, -1);} 
"end" {addInFIP(35, -1);} 
"." {addInFIP(36, -1);} 
"+=" {addInFIP(37, -1);} 
"-=" {addInFIP(38, -1);} 
"/=" {addInFIP(39, -1);} 
"%=" {addInFIP(40, -1);} 
":" {addInFIP(41, -1);} 
":=" {addInFIP(42, -1);} 
"repeta" {addInFIP(43, -1);} 
"pana cand" {addInFIP(44, -1);} 

{number}|{string}|{char}        {addCustom(yytext, 1);}
{identifier}                    {addCustom(yytext, 0);}
[\n]                            {currentLine++;}
[ \n\t]                         {;}
.                               {errorsFound++; printf("Unknown token at line %d: %s\n", currentLine, yytext);}

%%

int main(int argc, char *argv[]) {
	
	FILE *fp = fopen(argv[1], "r");
	if (!fp) {
	perror("Failed to open file");
	return 1;
	}

	init_hashmap(&TS);
	FIP = (FipElem *)malloc(500 * sizeof(FipElem));
	initIdentifierAndConsts();

	yyin = fp;
	yyrestart(yyin);
	yylex();

	assignTsDataToFIP();
	printTS();
	printFIP();

	fclose(fp);
	free_hashmap(&TS);
	free(FIP);
	deleteCustom();

	return 0;
}
