bits 32
global start

extern exit,printf,scanf,fprintf,fopen,fclose

import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data
	n dd 0
	mesaj db "Dati un numar: ",0
    
	nr times 100 dd 0
	format db "%d",0
    format_afisare db "%x",0
    
    nume_fisier db "min.txt",0
    mod_acces db 'w',0
    descriptor_fisier dd 0

segment code use32 class=code
    start:
       	cld
       	mov edi,nr
        
        citire:
        	push dword mesaj
        	call [printf]
        	add esp,1*4
        	;afisam mesajul
        	push dword n
        	push format
        	call [scanf]
        	add esp,2*4
        	;citim numarul
        	mov eax,[n]
        	cmp eax,0
        	je _out
        	stosd
        jmp citire
        
       	_out:
       		mov ecx,99
       		cld
       		mov esi,nr
            
            push ecx
            lodsd
            mov ebx,eax
            cmp eax,0
            pop ecx
            je final

            
       	compare:
       		push ecx
            
       		lodsd
       		cmp eax,0
       		je final
            
            cmp ebx,eax
            pop ecx
            jb gata
            mov ebx,eax
        gata:
       	loop compare
        
        
       	final:
        	push dword ebx
            push dword format_afisare
        	call [printf]
        	add esp,2*4

        eroare:
        push dword 0        
        call [exit]