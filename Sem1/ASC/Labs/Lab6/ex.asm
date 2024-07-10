bits 32
global start        
extern exit               
import exit msvcrt.dll    
;Se da un sir de dublucuvinte. Sa se obtina sirul format din octetii superiori ai cuvitelor superioare din elementele sirului de dublucuvinte care sunt divizibili cu 3.
;Ex
;Se da sirul de dublucuvinte:
;s DD 12345678h, 1A2B3C4Dh, FE98DC76h
;Sa se obtina sirul
;d DB 12h.
segment data use32 class=data
    sir dd 012345678h, 01a2b3c4dh, 0fe98dc76h
    len equ ($-sir)/4
    trei db 3
    d times len db 0
segment code use32 class=code
    start:
        mov esi,sir
        mov edi,d
        cld
        mov ecx,len
        jecxz sfarsit
    repeta:    
        lodsd ; eax = sir
        shr eax,24 ; al = octetul care ne intereseaza
        mov ah,0 ;   ax = octetul care ne intereseaza
        mov bl,al  ; punem octetul si in bl deoarece o sa il pierdem din ax
        div byte[trei] ; vedem daca octetul e divizibil cu 3
        cmp ah,0       ; verificand restul (care se afla in ah)
        jnz nu_este    ; daca nu e divizibil, sarim la nu_este
        mov al,bl     
        stosb    ; daca este, mutam in d octetul
    nu_este:
        loop repeta
    sfarsit:
        push    dword 0     
        call    [exit]      