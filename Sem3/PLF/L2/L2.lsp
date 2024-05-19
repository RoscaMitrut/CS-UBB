		; parcuregere_stanga(l:lista, nrN:int, nrM:int)
		; parcurgere_stanga(l1..n, nrN, nrM) = 
			; = nil, n=0 (lista goala)
			; = nil, nrN = 1 + nrM
			; = l1 (+) l2 (+) parcurgere_stanga(l3..n, nrN + 1, l2 + nrM), altfel
(defun parcurgere_stanga (l nrN nrM)
	(cond
		((null l) nil)
		((= nrN (+ 1 nrM)) nil)
		(t (cons (car l) (cons (cadr l) (parcurgere_stanga (cddr l) (+ 1 nrN) (+ (cadr l) nrM)))))))


		; parcurgere_dreapta(l:lista, nrN:int, nrM:int)
		; parcurgere_dreapta(l1..n, nrN, nrM) =
			; = nil, n=0 (lista goala)
			; = l, if nrN = 1 + nrM
			; = parcurgere_dreapta(l3..n, nrN + 1, nrM + l2), altfel
(defun parcurgere_dreapta (l nrN nrM)
	(cond
		((null l) nil)
		((= nrN (+ 1 nrM)) l)
		(t (parcurgere_dreapta (cddr l) (+ 1 nrN) (+ (cadr l) nrM)))))


		; stanga(l:lista)
		; stanga(l) =
			; = parcurgere_stanga(l3..n)
(defun stanga(l)
	(parcurgere_stanga (cddr l) 0 0))


		;dreapta(l:lista)
		; dreapta(l) =
			; = parcurgere_dreapta(l3..n)
(defun dreapta(l)
	(parcurgere_dreapta (cddr l) 0 0))



		; adancime(l:lista, element:str ,nivCurent:int)
		; adancime(l, element, nivCurrent) = 
			; = nil, n=0
			; = nivCurrent, l1 = element
			; = adancime(stanga(l), element, nivCurrent + 1) or adancime(dreapta(l), element, nivCurrent + 1), altfel
(defun adancime (l element nivCurrent)
	(cond
		((null l) nil)                                    
		((equal (car l) element) nivCurrent)               
		(t (or (adancime (stanga l) element (+ 1 nivCurrent))(adancime (dreapta l) element (+ 1 nivCurrent))))  
		)
)


		; main(l:lista, elemCautat:str)
		; main(l, elemCautat) = 
			; = adancime(l, elemCautat, 0)
(defun main(l elemCautat)
	(adancime l elemCautat 0))

(print (main '(A 2 B 1 D 1 E 1 F 2 G 0 H 2 J 0 K 0 C 2 L 2 N 1 V 2 S 0 T 0 Q 2 P 0 R 0 M 0) 'X))

;L3: 11