%{
#include <string.h>
#include "flex.h"
#include "yacc.tab.h"

int currentLine = 1;
int errorsFound = 0;

int getCurrentLine(){return currentLine;}
int getErrorsFound(){return errorsFound;}

%}

%option noyywrap

digit [0-9]
nzdigit [1-9]
letter [a-zA-Z_]
integer [+-]?({nzdigit}{digit}*)|"0"
real ([+-]?({nzdigit}{digit}*)|"0")("."{digit}+)?
string \'[^\'\n]*\'  
char \'\\?.\'           
identifier {letter}({letter}|{digit})*

%%
"program" { return  PROG ;} 
"(" { return  LPAREN  ;} 
")" { return  RPAREN  ;} 
"begin" { return  BEGINNING ;} 
"end." { return  ENDPROG ;} 
"end" { return END ;  } 
";" { return  SEMICOLON ;} 
"integer" { return  INT  ;} 
"var" { return VAR ;}
":=" { return ASSIGN ;  } 
"+" { return PLUS ;  } 
"-" { return MINUS ;  } 
"*" { return  MUL  ;} 
"/" { return DIV ;  }
"readln" { return  READLN ;} 
"writeln" { return WRITELN ;  } 
"," { return COMMA ;} 
":" { return COLON ;}

{integer}                       {strcpy(yylval.varname, yytext);return INTEGER;}
{string}                        {strcpy(yylval.varname, yytext);return STRING;}
{identifier}                    {strcpy(yylval.varname, yytext);return IDENTIFIER;}
[\n]                            {currentLine++;}
[ \n\t]                         {;}
.                               {errorsFound++; printf("Unknown token at line %d: %s\n", currentLine, yytext);}
%%