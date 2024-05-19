; a) Sa se construiasca o functie care intoarce produsul atomilor numerici dintr-o lista, de la nivelul superficial.
; produs (l: lista)
(defun produs (l)
    (cond
        ((null l) 1)
        ((numberp (car l)) (* (car l) (produs (cdr l))));  numberp "=" is_number
        (T (produs (cdr l)))
    )
)
(print (produs '(1 A 2 3 (3 4 5) F) ) )
;=> 6


; b) Sa se scrie o functie care, primind o lista, intoarce multimea tuturor perechilor din lista. De exemplu: (a b c d) --> ((a b) (a c) (a d) (b c) (b d) (c d))
; perechi(l: lista, a: element)                
(defun perechi (l a)
    (cond
        ((null l) nil)
        (T (cons (list a (car l)) (perechi (cdr l) a)))
    )
)

; toatePerechi(l: lista)
(defun toatePerechi (l)
    (cond
        ((null (cdr l)) nil)
        (T (append (perechi (cdr l) (car l)) (toatePerechi (cdr l))))
    )
)
(print (toatePerechi '(a b c d)))
;=>((A B) (A C) (A D) (B C) (B D) (C D))


; c) Sa se determine rezultatul unei expresii aritmetice memorate in preordine pe o stiva. Exemple:
; (+ 1 3) ==> 4 (1 + 3)
; (+ * 2 4 3) ==> 11 ((2 * 4) + 3)
; (+ * 2 4 - 5 * 2 2) ==> 9 ((2 * 4) + (5 - (2 * 2))

; operatie(op: operator, a: nr, b: nr)
(defun operatie (op a b)
	(cond
		((string= op "+") (+ a b))
		((string= op "-") (- a b))
		((string= op "*") (* a b))
		((string= op "/") (floor a b))
	)
)

; expresie(l: lista)
(defun expresie (l)
    (cond
        ((null l) nil)
        ((and (numberp (cadr l)) (numberp (caddr l))) (cons (operatie (car l) (cadr l) (caddr l)) (expresie (cdddr l))))
        (T (cons (car l) (expresie (cdr l))))
    )
)
; solve(l: lista)
(defun solve (l)
    (cond
        ((null (cdr l)) (car l))
        (T (solve (expresie l)))
    )
)

(print (solve '(+ * 2 4 - 5 * 2 2)))
; => 9   ((2 * 4) + (5 - (2 * 2))


; d) Definiti o functie care, dintr-o lista de atomi, produce o lista de perechi (atom n), 
; unde atom apare in lista initiala de n ori. De ex:
; (A B A B A C A) --> ((A 4) (B 2) (C 1)).

; nrAparitii(l: lista, e: element)
(defun nrAparitii (l e)
    (cond
        ((null l) 0)
        ((equal (car l) e) (+ 1 (nrAparitii (cdr l) e)))
        (T (nrAparitii (cdr l) e))
    )
)

; sterge(l: lista, e: element)
(defun sterge (l e)
    (cond
        ((null l) nil)
        ((equal (car l) e) (sterge (cdr l) e))
        (T (cons (car l) (sterge (cdr l) e)))
    )
)

; solve2(l: lista)
(defun solve2 (l)
    (cond
        ((null l) nil)
        (T (cons (list (car l) (nrAparitii l (car l))) (solve2 (sterge (cdr l) (car l)))))
    )
)

(print (solve2 '(A B A B A C A)))
;=> ((A 4) (B 2) (C 1))