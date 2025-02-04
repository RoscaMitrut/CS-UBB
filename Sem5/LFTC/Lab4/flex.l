%{
#include "hash.h"
#include "flex.h"
#include "yacc.tab.h"

#define MAX_LINE_LENGTH 100

HashMap TS;
FipElem *FIP;

int fipSize = 0;

int currentLine = 1;
int getCurrentLine(){return currentLine;}
int errorsFound = 0;
int getErrorsFound(){return errorsFound;}

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
"," {addInFIP(2, -1); return COMMA ;} 
"integer" {addInFIP(3, -1); return INTEGER ;} 
"real" {addInFIP(4, -1); return REAL ;} 
"string" {addInFIP(5, -1); return STRING ;} 
"(" {addInFIP(6, -1); return LPAREN ;} 
")" {addInFIP(7, -1); return RPAREN ;} 
"*" {addInFIP(8, -1); return MUL ;} 
"++" {addInFIP(9, -1); return INC ;} 
"--" {addInFIP(10, -1); return DEC ;} 
";" {addInFIP(11, -1); return SEMICOLON;} 
"begin" {addInFIP(12, -1); return BEGINNING;} 
"clrscr" {addInFIP(13, -1); return CLRSCR;} 
"crt" {addInFIP(14, -1); return CRT;} 
"end." {addInFIP(15, -1); return ENDPROG;} 
"program" {addInFIP(16, -1); return PROG;} 
"readln" {addInFIP(17, -1); return READLN;} 
"uses" {addInFIP(18, -1); return USES;} 
"var" {addInFIP(19, -1); return VAR ;} 
"writeln" {addInFIP(20, -1); return WRITELN; } 
"if" {addInFIP(21, -1); return IF; } 
"then" {addInFIP(22, -1); return THEN; } 
"else" {addInFIP(23, -1); return ELSE; } 
"while" {addInFIP(24, -1); return WHILE; } 
"do" {addInFIP(25, -1); return DO; } 
"/" {addInFIP(26, -1); return DIV; } 
"+" {addInFIP(27, -1); return PLUS; } 
"-" {addInFIP(28, -1); return MINUS; } 
"<>" {addInFIP(29, -1); return NEQ; } 
">" {addInFIP(30, -1); return GT; } 
"'" {addInFIP(31, -1); return STR; }
"=" {addInFIP(32, -1); return EQ; }
"type" {addInFIP(33, -1); return TYPE; } 
"record" {addInFIP(34, -1); return RECORD; } 
"end" {addInFIP(35, -1); return END; } 
"." {addInFIP(36, -1); return DOT; } 
"+=" {addInFIP(37, -1); return COMPINC; } 
"-=" {addInFIP(38, -1); return COMPDEC; } 
"/=" {addInFIP(39, -1); return COMPDIV; } 
"%=" {addInFIP(40, -1); return COMPMOD; } 
":" {addInFIP(41, -1); return COLON; } 
":=" {addInFIP(42, -1); return ASSIGN; } 
"repeta" {addInFIP(43, -1); return REPETA; } 
"pana cand" {addInFIP(44, -1); return PANACAND; } 

{number}|{string}|{char}        {addCustom(yytext, 1);return CONST;}
{identifier}                    {addCustom(yytext, 0);return IDENTIFIER;}
[\n]                            {currentLine++;}
[ \n\t]                         {;}
.                               {errorsFound++; printf("Unknown token at line %d: %s\n", currentLine, yytext);return UNKNOWN;}

%%