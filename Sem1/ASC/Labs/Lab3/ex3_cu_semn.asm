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
        x dq -2000
segment code use32 class=code
    start:
        mov al,[a]
        cbw
        cwde
        mov edx,eax
        ;edx = a
        add edx, [b] 
        ;edx = a+b
        mov al, [c]
        imul byte [d] 
        ;ax=c*d
        cwde
        ;eax=c*b
        add edx, eax
        ;edx = (a+b+c*d)
        push edx
        ;punem pe stiva prima paranteza
        mov bx,9
        mov al,[a]
        cbw
        sub bx,ax
        ;bx = 9-a
        pop ax
        pop dx
        ;dx:ax = prima paranteza
        idiv bx
        ;cat = ax
        cwde
        ;cat = eax
        cdq
        ;cat = edx:eax
        mov ebx,[x]
        mov ecx,[x+4]
        ;ecx:ebx = x
        sub ebx,eax
        sbb ecx,edx
        ;rezultatul => ecx:ebx = x-(a+b+c*d)/(9-a)
        push    dword 0
        call    [exit]