# Lab1
Cerinta A.C
## 
### Tipuri de date
integer, string, real
record

### Instructiuni
#### atribuire
:=
#### intrare
readln()  
#### iesire
writeln()
#### conditionala
if <conditie> then
  <instructiune>
else
  <instructiune>;
#### ciclare
while <conditie> do
  <instructiune>;

### Identificatori
Restrictii identifier
- doar a-z, A-Z, _, numere
- nu poate incepe cu numar

### Operatori
/, +, -, *
:=
<>, >

### Separatori
;, ,,

### Cuvinte cheie
integer, string, type, ...

## EBNF
```
Program = "program" Identifier ";" "uses" "crt" ";" TypeDeclaration Declarations "begin" "clrscr" ";" Statements "end."

TypeDeclaration = "type" Identifier "=" "record" {Identifier ":" Type ";"} "end;" | ε

Declarations = "var" Identifier ":" Type ";" {Identifier ":" Type ";"} | ε

Type = "integer" | "real" | "string"

Statements = Statement ";" {Statement ";"}

Statement = Assignment | IfStatement | WhileStatement | WriteStatement | ReadStatement | CompoundAssignment | IncDec

Assignment = Identifier ":=" Expression | Identifier "." Identifier ":=" Expression

IfStatement = "if" <condition> "then" "begin" Statements "end" ["else" "begin" Statements "end"]

WhileStatement = "while" <condition> "do" "begin" Statements "end"

WriteStatement = "writeln" "(" Expression ")"

<read-statement> = "readln" "(" Identifier ")"

RelOperator = "<>" | ">"
<condition> = Expression RelOperator Expression

Op = "*" | "/" | "+" | "-"
Expression = Expression Op Expression | Identifier | Number | RealNumber | String | Identifier "." Identifier

CompoundOperator = "+=" | "-=" | "/=" | "%="
CompoundAssignment = Identifier CompoundOperator Expression | Identifier "." Identifier CompoundOperator Expression

IncDecOp = "++" | "--"
IncDec = Identifier IncDecOp | IncDecOp Identifier | Identifier "." IdentifierIncDecOp | IncDecOp Identifier "." Identifier



Identifier = [a-zA-Z_][a-zA-Z0-9_]*

Number = [0-9]+
RealNumber = [0-9]+\.[0-9]+
String = "'" [^']* "'"
```
Activitate:
a += 
a -=
a /=
a %=
a++
++a
a--
--a