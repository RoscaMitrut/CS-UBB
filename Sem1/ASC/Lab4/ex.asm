;exercitiul 26
;Se dau 2 dublucuvinte R si T. Sa se obtina dublucuvantul Q astfel:
    ;bitii 0-6 din Q coincid cu bitii 10-16 a lui T
    ;bitii 7-24 din Q concid cu bitii obtinuti 7-24 in urma aplicarii R XOR T.
    ;bitii 25-31 din Q coincid cu bitii 5-11 a lui R.
bits 32 
global start        
extern exit            
import exit msvcrt.dll 
segment data use32 class=data
    r dd 12348a9fh
    t dd 5678af9ah
    q dd 0 
;a84c252b
segment code use32 class=code
    start:
        mov ebx, 0 ;in ebx vom stoca rezultatul
        
        mov eax,[t]
        and eax,00000000000000011111110000000000b ; masca pt bitii 10 16 din t
        mov cl,10 
        ror eax, cl ; rotim 10 pozitii spre dreapta 
        or ebx,eax ;punem bitii in rezultat
        
        mov eax,[r] 
        xor eax,[t] ; punem in eax r xor t
        and eax,00000001111111111111111110000000b ; izolam bitii de pe pozitiile 7-24 r xor t in eax
        or ebx,eax ; adaugam bitii de pe pozitile 7-24
        
        mov eax,[r]
        and eax,00000000000000000000111111100000b 
        mov cl,12
        ror eax,cl ; izolam in eax bitii 5-11 ai lui r pe pozitiile 25-31
        or ebx,eax ; terminam numarul
        mov [q],ebx ; rezultatul se afla in q si in ebx
        
        push    dword 0 
        call    [exit]
