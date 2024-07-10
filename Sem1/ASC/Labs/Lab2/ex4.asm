bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process.   It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;(d-b*c+b*2)/a
    ;a,b,c - byte, d - word
    a db 5
    b db 5
    c db 3
    d dw 50
; our code starts here
segment code use32 class=code
    start:
        ;b*c
        mov al,[b]
        mul BYTE [c]
        mov cx,ax
        ;b*2
        mov ax,2
        mov bl,[b]
        mul bl
        mov bx,ax
        ;d-b*c
        mov dx,[d]
        sub dx,cx
        ;d-b*c+b*2
        add dx,bx
        ;(d-b*c+b*2)/a
        mov ax,dx
        div BYTE [a]
        ;rezultatul se gaseste in ax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
