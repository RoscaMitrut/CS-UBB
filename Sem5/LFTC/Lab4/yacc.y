%{
#include "flex.h"

extern int yylex();
extern FILE* yyin;

void yyerror(const char* str);
%}

%token COMMA INTEGER REAL STRING LPAREN RPAREN MUL INC DEC SEMICOLON
%token BEGINNING CLRSCR CRT ENDPROG PROG READLN USES VAR WRITELN
%token IF THEN ELSE WHILE DO DIV PLUS MINUS NEQ GT STR EQ
%token TYPE RECORD END DOT COMPINC COMPDEC COMPDIV COMPMOD COLON ASSIGN
%token REPETA PANACAND CONST IDENTIFIER UNKNOWN

%%
program: PROG IDENTIFIER SEMICOLON USES CRT SEMICOLON typedeclaration declarations BEGINNING CLRSCR SEMICOLON statements ENDPROG
;

typedeclaration: TYPE IDENTIFIER EQ RECORD typefieldlist END SEMICOLON
| eps
;

typefieldlist:
IDENTIFIER COLON type SEMICOLON typefieldlist
| eps
;

eps: /*empty*/;

declarations: VAR IDENTIFIER COLON type SEMICOLON moredeclarations
| eps
;

moredeclarations: IDENTIFIER COLON type SEMICOLON moredeclarations
| eps
;

type: INTEGER
| REAL
| STRING
;

statements: statement SEMICOLON statements
| eps
;

statement: assignment
| ifstatement
| whilestatement
| writestatement
| readstatement
| compoundassignment
| incdec
| repetastatement
;

assignment:
IDENTIFIER ASSIGN expression
| IDENTIFIER DOT IDENTIFIER ASSIGN expression
;

repetastatement: REPETA statements PANACAND condition

ifstatement: IF condition THEN BEGINNING statements END
| IF condition THEN BEGINNING statements END ELSE BEGINNING statements END
;

whilestatement: WHILE condition DO BEGINNING statements END
;

writestatement: WRITELN LPAREN expression RPAREN
;

readstatement: READLN LPAREN IDENTIFIER RPAREN
;

condition: expression reloperator expression
;

reloperator: NEQ
| EQ
| GT
;

expression: IDENTIFIER
| CONST
| IDENTIFIER DOT IDENTIFIER
| IDENTIFIER expression_tail
| CONST expression_tail
| IDENTIFIER DOT IDENTIFIER expression_tail
;

expression_tail: op expression
;

op: MINUS
| PLUS
| DIV
| MUL
;

compoundassignment: IDENTIFIER compoundoperator expression
| IDENTIFIER DOT IDENTIFIER compoundoperator expression
;

compoundoperator: COMPINC
| COMPDEC
| COMPDIV
| COMPMOD
;

incdec: IDENTIFIER incdecopp
| incdecopp IDENTIFIER
| IDENTIFIER DOT IDENTIFIER incdecopp
| incdecopp IDENTIFIER DOT IDENTIFIER
;

incdecopp: INC
| DEC
;

%%

void yyerror(const char* str) {
  fprintf(stderr, "Error on line %d: %s\n", getCurrentLine(), str);
}

int main(int argc, char* argv[]) {
  if (argc != 2) {
    printf("Invalid syntax!\nUsage: %s <file name>\n", argv[0]);
    exit(1);
  }

  FILE* fp = fopen(argv[1], "r");
  if (fp == NULL) {
    perror("File not found!");
    return 1;
  }

  initFlexResources();

  yyin = fp;
  yyparse();

  assignTsDataToFIP();
  printTS();
  printFIP();

  fclose(fp);
  destroyFlexResources();
  return 0;
}