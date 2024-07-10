bits 32
global start

extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data
    a dd 0
    b dd 0
    sum dd 0
    
    msg_a db "a: ", 0
    msg_b db "b: ", 0
    
    format_r db "%d", 0
    format_p db "Suma: %d", 0

; Acest program exemplifică:
; - apelul unei proceduri (subprogram) scrise în asamblare
; - modurile în care se transmit argumentele (registri sau stivă)
; - modurile în care se returnează rezultatul (registri sau stivă)
segment code use32 class=code
    ; Procedura sum_1
    ; EAX = sum_1(a, b)
    ; calculeaza suma a 2 numere intregi
    ; input: EAX = a, EDX = b
    ; output: EAX = a+b
    sum_1:
        add eax, edx
        
        ret
    
    ; Procedura sum_2
    ; EAX = sum_2(a, b)
    ; calculeaza suma a 2 numere intregi
    ; input: stiva
    ;  --------------------
    ; | adresa de revenire | <-- ESP (varful stivei)
    ;  --------------------
    ;  --------------------
    ; | valoarea lui b     | ESP+4
    ;  --------------------
    ;  --------------------
    ; | valoarea lui a     | ESP+8
    ;  --------------------
    ;  --------------------
    ; | ....               | ESP initial 
    ;  --------------------
    ; output: EAX = a+b
    sum_2:
        mov eax, [esp+8]    ; EAX = a
        mov edx, [esp+4]    ; EAX = b
        
        add eax, edx        ; EAX = a+b
    
        ret 2*4             ; eliberez stiva
    
    ; Procedura sum_3
    ; sum_3(a, b)
    ; calculeaza suma a 2 numere intregi
    ; input: stiva
    ;  --------------------
    ; | adresa de revenire | <-- ESP (varful stivei)
    ;  --------------------
    ;  --------------------
    ; | valoarea lui b     | ESP+4
    ;  --------------------
    ;  --------------------
    ; | valoarea lui a     | ESP+8
    ;  --------------------
    ;  --------------------
    ; | spatiu rezervat    | ESP+12 (aici stochez suma)
    ;  --------------------
    ;  --------------------
    ; | ....               | ESP initial
    ;  --------------------
    
    ; output: stiva
    ;  --------------------
    ; | suma               | <-- ESP (varful stivei)
    ;  --------------------
    sum_3:
        mov eax, [esp+8]    ; EAX = a
        mov edx, [esp+4]    ; EAX = b
        
        add eax, edx        ; EAX = a+b
        mov [esp+12], eax   ; pun rezultatul pe stivă

        ret 2*4             ; eliberez stiva

    ; programul principal
    start:
        push dword msg_a
        call [printf]
        add esp, 4*1
        
        push dword a
        push dword format_r
        call [scanf]
        add esp, 4*2
        
        push dword msg_b
        call [printf]
        add esp, 4*1
        
        push dword b
        push dword format_r
        call [scanf]
        add esp, 4*2
        
        ; ; EAX = sum_1(a, b)
        ; ; pun argumentele în registrii EAX și EDX
        ; mov eax, [a]        ; EAX = primul argument
        ; mov edx, [b]        ; EDX = al doilea argument
        ; ; apelez procedura
        ; call sum_1
        ; mov [sum], eax      ; rezultat returnat în EAX
        
        ; ; EAX = sum_2(a, b)
        ; ; pun argumentele pe stivă
        ; mov eax, [a]
        ; push eax
        ; mov edx, [b]
        ; push edx
        ; ; apelez procedura
        ; call sum_2
        ; ; add esp, 4*2      ; dacă nu ați eliberat stiva în procedură
        ; mov [sum], eax      ; rezultat returnat în EAX
        
        ; sum_3(a, b)
        ; rezerv 4 octeți pentru a stoca rezultatul
        push dword 0
        ; pun argumentele pe stivă
        mov eax, [a]
        push eax
        mov edx, [b]
        push edx
        ; apelez procedura
        call sum_3
        pop eax             ; extrag rezultatul din vârful stivei
        mov [sum], eax
        
        ; printf("Suma: %d", sum)
        push dword [sum]
        push dword format_p
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push dword 0        ; push the parameter for exit onto the stack
        call [exit]         ; call exit to terminate the program
