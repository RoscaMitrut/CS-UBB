bits 32
; Sa se citeasca de la tastatura doua numere a si b de tip word.
;Sa se afiseze in baza 16 numarul c de tip dword pentru care partea low este suma celor doua numere, iar partea high este diferenta celor doua numere.
;Exemplu:
; a = 574, b = 136
; c = 01B602C6h
global start    
extern exit,printf,scanf
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
segment data use32 class=data
    a dw 0
    b dw 0
    mesaj db "Dati un numar: ",0
    format_citire db "%d",0 
    format_afisare db "%x",0 
segment code use32 class=code
    start:
        push dword mesaj
        call [printf]
        add esp,1*4
        push dword a
        push format_citire
        call [scanf]
        add esp,2*4
        ; am citit a
        push dword mesaj
        call [printf]
        add esp,1*4
        push dword b
        push format_citire
        call [scanf]
        add esp,2*4
        ;am citit b
        mov eax,0
        add ax,[a]
        sub ax,[b]
        ;eax = a-b
        ror eax,16
        ;high eax = a-b
        mov ecx,0
        add ecx,[a]
        add ecx,[b]
        ;ecx = a+b
        mov ax,cx
        ;eax = cerinta
        push dword eax
        push dword format_afisare
        call [printf]
        add esp,2*4
        ;printam eax
        push    dword 0   
        call    [exit]      
