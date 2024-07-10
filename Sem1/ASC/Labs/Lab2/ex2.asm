bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a,b,c,d - byte
    ;(a+d-c)-(b+b)
    a db 5
    b db 3
    c db 32
    d db -13
; our code starts here
segment code use32 class=code
    start:
        ;(a+d-c)
        mov al,[a]
        add al,[d]
        sub al,[c]
        
        ;b+b
        mov ah,[b]
        add ah,[b]
        
        ;(a+d-c)-(b+b)
        sub al,ah
        ;rezultatul se gaseste in al
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
