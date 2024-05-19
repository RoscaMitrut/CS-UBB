;(defun inverseaza_lista(l)
;	;lista pe care dorim sa o inversam
;	(
;	cond
;		((null (car l)) 0)
;		(t (list(inverseaza_lista (cdr l)) (car l)))
;	)
;)
;(defun inverseaza(l)(
;	;lista in care dorim sa inversam tot
;	cond
;		((atom (car l)) 0)
;		((listp (car l)) (list (inverseaza_lista (car l)) (inverseaza (cdr l))))		
;	)
;)

;(inverseaza_lista '(1 2 3 4 5))



;NOT GOOD :)