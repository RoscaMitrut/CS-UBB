program cmmdc;
uses crt;

var
  a:integer;b:integer;

begin
clrscr;
  readln(a);
  readln(b);

  while a <> b do
  begin
    if a > b then
      a := a - b
    else
      b := b - a;
  end;

  writeln(a);

end.
