program sum;
uses crt;
var
  suma:integer; n:integer; temp:integer;

begin
clrscr;
  readln(n);

  while(n>0) do
    begin
    readln(temp);
    suma := suma + temp;
    n := n-1;
    end;

  writeln(suma);
end.
