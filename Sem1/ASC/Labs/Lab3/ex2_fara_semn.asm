bits 32
;exercitiul 13
;(b-a+c-d)-(d+c-a-b)
global start
extern exit
import exit msvcrt.dll
        ;a-byte, b-word, c-double word, d-qword - Interpretare cu semn
segment data use32 class=data
a db 100
b dw 500
c dd 300
d dq 400

segment code use32 class=code
    start:
        mov ebx,0
        mov bx,[b]
        ;ebx = b
        mov eax,0
        mov al,[a]
        ;eax=a
        sub ebx,eax
        ;ebx=b-a
        mov eax,[c]
        ;eax=c
        add ebx,eax
        ;ebx=b-a+c
        mov eax,0
        ;eax=0
        ;eax:ebx = b-a+c
        sub ebx,[d]
        sbb eax,[d+4]
        ;eax:ebx = b-a+c-d
        push eax
        mov edx,[d]
        mov ecx,[d+4]
        ;ecx:edx = d
        add edx,[c]
        adc ecx,0
        ;ecx:edx = d+c
        mov eax,0
        mov al,[a]
        sub edx,eax
        sbb ecx,0
        ;ecx:edx=d+c-a
        mov eax,0
        mov ax,[b]
        sub edx,eax
        sbb ecx,0
        ;ecx:edx = d+a-c-b
        pop eax
        ;eax:ebx = b-a+c-d
        ;ecx:edx = d+a-c-b
        sub ebx,edx
        sbb eax,ecx
        ;rezultatul => eax:ebx = (b-a+c-d)-(d+c-a-b)
        push    dword 0
        call    [exit]