bits 32
;exercitiul 13
;d-b+a-(b+c)
global start
extern exit
import exit msvcrt.dll
        ;a-byte, b-word, c-double word, d-qword - Interpretare fara semn
segment data use32 class=data
a db 100
b dw 500
c dd 300
d dq 20000
segment code use32 class=code
    start:

        ;d-b+a-(b+c)  
        mov eax,[d]
        mov ebx,[d+4]
        ;ebx:eax = d
        mov ecx,0   
        mov edx,0
        mov cx,[b]
        ;edx:ecx=b
        sub eax,ecx
        sbb ebx,edx
        ;ebx:eax = d-b
        mov ecx,0
        mov cl,[a]
        add eax,ecx
        adc ebx,0
        ;ebx:eax = d-b+a
        mov edx,0
        mov ecx,0
        mov cx,[b]
        add ecx,[c]
        adc edx,0
        ;edx:ecx = b+c
        sub eax,ecx
        sbb ebx,edx
        ;rezultat => ebx:eax = d-b+a-(b+c)  
        
        push    dword 0
        call    [exit]