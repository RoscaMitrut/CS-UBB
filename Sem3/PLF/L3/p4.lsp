;4. Sa se construiascao functie care intoarce suma atomilor numerici dintr-o lista, de la orice nivel.


;suma(e)=e, daca e este numar
;        0, daca e este atom nenumeric
;        suma(e1)+suma(e2)+...+suma(en), unde e=(e1,e2...en)

;e - lista pentru care se vor insuma atomii numerici de la oricare nivel  
(defun suma(e)
    (cond
        ((numberp e) e)
        ((atom e) 0)
        ((listp e) (+(mapcar #' suma e)))
    )
)


(print (suma '(1 2 (3 (1) A) B (1) 9)))