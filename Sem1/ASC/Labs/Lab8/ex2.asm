bits 32 
;Sa se citeasca de la tastatura un nume de fisier si un text.
;Sa se creeze un fisier cu numele dat in directorul curent si sa se scrie textul in acel fisier.
;Observatii: Numele de fisier este de maxim 30 de caractere.
;Textul este de maxim 120 de caractere.
global start        
extern exit,gets,printf,fprintf,fopen,fclose
import fopen msvcrt.dll
import exit msvcrt.dll   
import gets msvcrt.dll
import printf msvcrt.dll
import fprintf msvcrt.dll   
import fclose msvcrt.dll
segment data use32 class=data
    sir times 120 db "",0
    nume_fisier times 30 db "",0
    descriptor_fisier dd 0
    mesaj1 db "Nume fisier: ",0
    mesaj2 db "Scrieti o propozitie: ",0
    format db "%s",0   
    mod_acces db 'w', 0 
    mesaj_eroare db "Eroare la deschidere fisier!",0
segment code use32 class=code
    start:
        push dword mesaj1
        call [printf]
        add esp,1*4

        push dword nume_fisier
        call [gets]
        add esp,1*4
        ; am citit numele fisierului
        push dword mesaj2
        call [printf]
        add esp,1*4

        push dword sir
        call [gets]
        add esp,1*4
        ; am citit propozitia
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp,2*4

        cmp eax,0
    	je eroare
    	mov [descriptor_fisier],eax
        ; am deschis fisierul si am verificat daca e valid
        push dword sir
        push dword [descriptor_fisier]
        call [fprintf]
        add esp,2*4
        ; am scris propozitia in fisier
        push dword [descriptor_fisier]
        call [fclose]
        add esp, 4
        ; am inchis fisierul
        jmp sfarsit
        eroare:
            push dword mesaj_eroare
            call [printf]
            add esp,1*4
            ;afisam un mesaj daca fisierul nu se deschide
        sfarsit:
        push    dword 0      
        call    [exit]    