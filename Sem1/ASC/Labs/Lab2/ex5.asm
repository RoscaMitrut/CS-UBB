bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process.   It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a*d*e/(f-5)
    ;a,b,c,d-byte, e,f,g,h-word
    a db 7
    d db 9
    e dw 5
    f dw 10
    ; our code starts here
segment code use32 class=code
    start:
        ;a*d
        mov al,[a]
        mul BYTE [d]
        ;a*d*e
        mul WORD [e]
        ;f-5
        mov bx,[f]
        sub bx,5
        ;a*d*e/(f-5)
        div bx
        ;rezultatul se afla in ax
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
