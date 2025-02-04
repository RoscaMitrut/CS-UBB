program Calcul;
var
    a, b, result, temp : integer;
begin
    writeln('Enter two numbers:');
    readln(a, b);
    
    temp := (a + b) * 2;
    result := temp ;
    
    writeln('Result is: ', result);
end.