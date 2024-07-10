bits 32
global start        


;Să se scrie un program în limbaj de asamblare care:

; citește de la tastatură numele unui fișier text;
; citește toate cuvintele din fișierul dat;
; afișează pe ecran doar acele cuvinte care se află pe poziții PARE în fișier și care conțin cel puțin o literă mare.
; Primul cuvânt din fișier se află pe poziția 0.
; Fișierul text trebuie să existe și va conține doar cuvinte separate prin spații.


; Exemplu:

; Dacă fișierul conține:

; anA caMeLia victor tUdOr aLiNa DiAnA paul RoBErT IuLIa aNdA

; se va afișa pe ecran:

; anA aLiNa IuLIa

extern exit,fopen,fclose,printf,gets,fgets
import fgets msvcrt.dll
import exit msvcrt.dll   
import gets msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    sir times 100 db 0
    nume_fisier times 20 db 0
    descriptor_fisier dd 0
    mod_deschidere db "r",0
    mesaj db "Nume fisier: ",0
    nume times 20 db 0
segment code use32 class=code
    start:
    ;afisam mesaj pentru input
        push mesaj
        call [printf]
        add esp,1*4
    ;luam de la user numele fisierului
        push nume_fisier
        call [gets]
        add esp,1*4
    ;deschidem fisierul si iesim din program daca nu se deschide
        push mod_deschidere
        push nume_fisier
        call [fopen]
        add esp,2*4
        mov [descriptor_fisier],eax
        cmp eax,0
        je eroare
    ;salvam in memorie sirul din fisier
        push dword[descriptor_fisier]
        push dword 90
        push sir
        call [fgets]
        add esp,3*4   
    ;inchidem fisierul
        push dword [descriptor_fisier]
        call [fclose]
        add esp,1*4
    ;luam ebx 0 si daca se transforma in 1 pana la sfarsitul unui cuvant inseamna ca acesta contine o majuscula
    ;luam edx 0 si o incrementam pe parcurs. edx reprezinta pozitia din fisier la care ne aflam
    
        mov ebx,0
        mov edx,0
        mov esi,sir
        
    cuvant_nou:
    ;cuvintele noi le salvam in "nume"
    mov edi,nume
    
    cuvant:
        ;luam fiecare cuvant litera cu litera si verificam daca are majuscula
        lodsb
        
        mov [edi],al
        inc edi
        
        cmp eax,0
        je iesire
        
        cmp eax,' '
        je gata_cuvant
        
        cmp eax,"Z"
        ja fara_litera_mare
        mov ebx,1
        
        fara_litera_mare:
        jmp cuvant
    
    gata_cuvant:
    ;ebx = 0  => nu avem majuscula
        cmp ebx,0
        je nu_e_bun
        
    ;ultima cifra din edx => pozitie para/impara
        and edx,1
        cmp edx,1
        je nu_e_bun
     ;printam numele daca e ok
        push nume
        call [printf]
        add esp,1*4
        
        nu_e_bun:
     ;reinitializam ebx si incrementam edx
        mov ebx,0
        inc edx
        jmp cuvant_nou
        iesire:
        eroare:
        push    dword 0      
        call    [exit]       
