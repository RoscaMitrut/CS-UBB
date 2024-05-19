bits 32 
global start        
extern exit               
import exit msvcrt.dll    
segment data use32 class=data
;Se dau 2 siruri de octeti S1 si S2 de aceeasi lungime.
;Sa se construiasca sirul D astfel incat fiecare element din D sa reprezinte minumul dintre elementele de pe pozitiile corespunzatoare din S1 si S2
;Exemplu:
;S1: 1, 3, 6, 2, 3, 7
;S2: 6, 3, 8, 1, 2, 5
;D : 1, 3, 6, 1, 2, 5
	s1 db 1, 3, 6, 2, 3, 7 ; declararea sirului s1
	l equ $-s1 ; stabilirea lungimea sirului initial
	s2 db 6, 3, 8, 1, 2, 5 ; declararea sirului s2
    d times l db 0 ; rezervarea unui spatiu de dimensiune l pentru sirul destinatie d si initializarea acestuia
segment code use32 class=code
start:
    mov ecx,l ;punem in ecx lungimea sirului pt a realiza loop-ul de l ori
    mov esi,0
    jecxz sfarsit
    repeta:
        mov al,[s1+esi]
        mov bl,[s2+esi]
        cmp al,bl;comparam caracterele de pe pozitii egale
        JA mai_mare ; daca al > bl trecem la mai_mare
        ; daca al<bl continuam
        mov [d+esi],al ;schimbam cifrele din d
        jmp gata ; sarim la gata
        ;
        mai_mare:
        mov [d+esi],bl ;schimbam cifrele din d
        
        gata:
        inc esi ; incrementam esi ca sa luam caracterele pe rand
    loop repeta
    sfarsit:
        push    dword 0   
        call    [exit]       
        
        
