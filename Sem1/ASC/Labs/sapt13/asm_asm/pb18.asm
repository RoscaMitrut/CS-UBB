;Se citeste de la tastatura un sir de numere in baza 10 fara semn. Sa se determine valoarea minima din sir si sa se afiseze in fisierul min.txt (fisierul va fi creat) valoarea minima, in baza 16
bits 32
global start

extern exit,scanf,fprintf,fopen,fclose

import exit msvcrt.dll

import scanf msvcrt.dll

import fprintf msvcrt.dll
import fopen msvcrt.dll 
import fclose msvcrt.dll

segment data use32 class=data
    n dd 0
    nr times 3 dd 0
    
    format_citire db "%d", 0
    
    nume_fisier db "min.txt",0
    mod_acces db "w",0
    descriptor_fisier dd 0

segment code use32 class=code
    start:
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp,2*4
        
    	mov [descriptor_fisier],eax
        
        cmp eax,0
    	je sfarsit
        
        
        cld
        mov edi,nr
        mov ebx,0
        citire:
       		push dword n
    		push dword format_citire
            call [scanf]
            add esp,2*4
            ;apelam scanf^
            mov eax,[n]
            cmp eax,0
            je outside
            ;verificam daca am citit cu succes^
   		 	stosd
            add ebx,1
            ;stocam nr dat^
    		jmp citire
    outside:
        mov ecx,ebx
        sub ecx,1

        mov esi,nr
        push ecx
        
        lodsd
        mov ebx,eax    
        jecxz zero
        
        pop ecx
        ;mutam primul element in ebx^
        compare:
            push ecx
            lodsd
            cmp ebx,eax
            pop ecx
            jb gata
            mov ebx,eax
        gata:
        loop compare
        
        zero:

        
        push ebx
        push dword [descriptor_fisier]
        push format_citire
        call [fprintf]
        
        add esp,2*4
        
        push dword [descriptor_fisier]
        call [fclose]
        add esp, 4

        sfarsit:
        push    dword 0      
        call    [exit]    