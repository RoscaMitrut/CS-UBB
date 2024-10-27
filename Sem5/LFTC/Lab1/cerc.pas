program cerc ;
uses crt ;
var raza : real ; perimetru : real ; aria : real ; detest : string ;
begin $
clrscr ;
readln ( raza ) ;$
detest := 'SirDeCaractere' ;
writeln ( detest ) $ ;
perimetru := 2 * 3.14 * raza ;
aria := 3.14 * raza * raza ;
writeln ( perimetru ) ;
writeln ( aria ) ;
end.