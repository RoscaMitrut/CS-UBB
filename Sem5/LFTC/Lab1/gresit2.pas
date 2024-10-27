program suma;
uses crt;
var
  suma,n,temp: integer; {eroare}

begin
clrscr;
  read(n); {nu avem read in mlp}

  while(0<n) do {nu avem < in mlp}
    begin
    readln(temp);
    suma = sum + temp;
    n = n-1;
    end

  writeln(suma);
end.
