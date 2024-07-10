bits 32

;a,c,d-byte, b-doubleword, x-qword
;x-(a+b+c*d)/(9-a)

global start
extern exit
import exit msvcrt.dll
segment data use32 class=data
        a db 8
        c db 10
        d db 20
        b dd 192
        x dq 2000
segment code use32 class=code
    start:
        mov ecx, 0
        mov cl, [a]
        add ecx, [b] 
        ;ecx = a+b
        mov eax, 0
        mov al, [c] 
        mul byte [d] 
        ;eax=c*b
        add eax, ecx
        mov edx,0
        ;edx:eax = (a+b+c*d)
        mov bx, 9
        sub bl, [a] 
        sbb bh,0
        ;bx = 9-a
        push bx
        mov ebx,0
        pop bx
        ;ebx=9-a
        div ebx
        ;cat = eax
        mov edx,[x]
        mov ecx,[x+4]
        ;ecx:edx = x
        sub edx,eax
        sbb ecx,0
        ;rezultatul => edx = x-(a+b+c*d)/(9-a)
        push    dword 0
        call    [exit]