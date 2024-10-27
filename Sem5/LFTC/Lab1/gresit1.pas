program sum {eroare}
uses crt;
var
  suma,n,temp: integer {eroare}

begin
clrscr;
  readln(n);

  while(n>0) do
    begin
    readln(temp);
    suma = sum + temp;
    n = n-1;
    end

  writeln(suma);
end.
