bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a,b,c,d - word
    ;(c+d)+(a-b)+a
    a dw 125
    b dw -123
    c dw 135
    d dw 1
; our code starts here
segment code use32 class=code
    start:
        ;(c+d)
        mov ax,[c]
        add ax,[d]
        ;(a-b)
        mov bx,[a]
        sub bx,[b]
        ;(c+d)+(a-b)
        add ax,bx
        ;(c+d)+(a-b)+a
        add ax,[a]
        ;rezultatul se gaseste in ax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
