%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hash.h"

typedef struct {
    int atomCode;
    int tsPosition;
} FipElem;

#define MAX_LINE_LENGTH 100

HashMap TS;
FipElem *FIP;
int fipSize = 0;

int currentLine = 1;
int errorsFound = 0;
int getCurrentLine(){return currentLine;}
int getErrorsFound(){return errorsFound;}

char fipFileContent[4096];
int currentFipFileContentSize = 0;

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

void initFlexResources(){
	init_hashmap(&TS);
	FIP = (FipElem *)malloc(500 * sizeof(FipElem));
	initIdentifierAndConsts();
}

void destroyFlexResources(){
	free_hashmap(&TS);
	free(FIP);
	deleteCustom();
}

void addInFipFileContent(char* str){
	sprintf(fipFileContent + currentFipFileContentSize, "%s ", str);
	currentFipFileContentSize += strlen(str)+1;
}

%}

%option noyywrap

digit [0-9]
nzdigit [1-9]
letter [a-zA-Z_]
number ([+-]?({nzdigit}{digit}*)|"0")("."{digit}+)? 
string \'[^\'\n]*\'  
char \'\\?.\'           
identifier {letter}({letter}|{digit})*

%%
"," {addInFIP(2, -1); addInFipFileContent("COMMA") ;} 
"integer" {addInFIP(3, -1); addInFipFileContent("INTEGER") ;} 
"real" {addInFIP(4, -1); addInFipFileContent("REAL") ;} 
"string" {addInFIP(5, -1); addInFipFileContent("STRING") ;} 
"(" {addInFIP(6, -1); addInFipFileContent("LPAREN") ;} 
")" {addInFIP(7, -1); addInFipFileContent("RPAREN") ;} 
"*" {addInFIP(8, -1); addInFipFileContent("MUL") ;} 
"++" {addInFIP(9, -1); addInFipFileContent("INC") ;} 
"--" {addInFIP(10, -1); addInFipFileContent("DEC") ;} 
";" {addInFIP(11, -1); addInFipFileContent("SEMICOLON");} 
"begin" {addInFIP(12, -1); addInFipFileContent("BEGINNING");} 
"clrscr" {addInFIP(13, -1); addInFipFileContent("CLRSCR");} 
"crt" {addInFIP(14, -1); addInFipFileContent("CRT");} 
"end." {addInFIP(15, -1); addInFipFileContent("ENDPROG");} 
"program" {addInFIP(16, -1); addInFipFileContent("PROG");} 
"readln" {addInFIP(17, -1); addInFipFileContent("READLN");} 
"uses" {addInFIP(18, -1); addInFipFileContent("USES");} 
"var" {addInFIP(19, -1); addInFipFileContent("VAR") ;} 
"writeln" {addInFIP(20, -1); addInFipFileContent("WRITELN"); } 
"if" {addInFIP(21, -1); addInFipFileContent("IF"); } 
"then" {addInFIP(22, -1); addInFipFileContent("THEN"); } 
"else" {addInFIP(23, -1); addInFipFileContent("ELSE"); } 
"while" {addInFIP(24, -1); addInFipFileContent("WHILE"); } 
"do" {addInFIP(25, -1); addInFipFileContent("DO"); } 
"/" {addInFIP(26, -1); addInFipFileContent("DIV"); } 
"+" {addInFIP(27, -1); addInFipFileContent("PLUS"); } 
"-" {addInFIP(28, -1); addInFipFileContent("MINUS"); } 
"<>" {addInFIP(29, -1); addInFipFileContent("NEQ"); } 
">" {addInFIP(30, -1); addInFipFileContent("GT"); } 
"'" {addInFIP(31, -1); addInFipFileContent("STR"); }
"=" {addInFIP(32, -1); addInFipFileContent("EQ"); }
"type" {addInFIP(33, -1); addInFipFileContent("TYPE"); } 
"record" {addInFIP(34, -1); addInFipFileContent("RECORD"); } 
"end" {addInFIP(35, -1); addInFipFileContent("END"); } 
"." {addInFIP(36, -1); addInFipFileContent("DOT"); } 
"+=" {addInFIP(37, -1); addInFipFileContent("COMPINC"); } 
"-=" {addInFIP(38, -1); addInFipFileContent("COMPDEC"); } 
"/=" {addInFIP(39, -1); addInFipFileContent("COMPDIV"); } 
"%=" {addInFIP(40, -1); addInFipFileContent("COMPMOD"); } 
":" {addInFIP(41, -1); addInFipFileContent("COLON"); } 
":=" {addInFIP(42, -1); addInFipFileContent("ASSIGN"); } 
"repeta" {addInFIP(43, -1); addInFipFileContent("REPETA"); } 
"pana cand" {addInFIP(44, -1); addInFipFileContent("PANACAND"); } 

{number}|{string}|{char}        {addCustom(yytext, 1);addInFipFileContent("CONST");}
{identifier}                    {addCustom(yytext, 0);addInFipFileContent("IDENTIFIER");}
[\n]                            {currentLine++;}
[ \n\t]                         {;}
.                               {errorsFound++; printf("Unknown token at line %d: %s\n", currentLine, yytext);}

%%
// .                               {errorsFound++; printf("Unknown token at line %d: %s\n", currentLine, yytext);addInFipFileContent("UNKNOWN");}
int main(int argc, char* argv[]) {
  if (argc != 3) {
    printf("Invalid syntax!\nUsage: %s <in_file> <out_file>\n", argv[0]);
    exit(1);
  }

	char in_file[256], out_file[256];
	sprintf(in_file, "../files/%s", argv[1]);
	sprintf(out_file, "../files/%s", argv[2]);

  FILE* f_in = fopen(in_file, "r");
  FILE* f_out = fopen(out_file, "w");
  if (f_in == NULL || f_out == NULL) {
    perror("File not found!");
    return 1;
  }

  initFlexResources();

  yyin = f_in;
	yyrestart(yyin);
	yylex();

  if (errorsFound == 0) {
    assignTsDataToFIP();
    printTS();
    printFIP();
    fipFileContent[strlen(fipFileContent) - 1] = '\0';
    fprintf(f_out, "%s", fipFileContent);
	//printf(fipFileContent);
  }

  fclose(f_in);
  fclose(f_out);
  destroyFlexResources();
  return 0;
}