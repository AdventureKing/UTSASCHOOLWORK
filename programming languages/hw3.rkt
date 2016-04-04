#lang eopl
;Brandon snow and Branden Weiss





;hw3
;val (assending-sorted-list)->list
;puts n in the correct assending order
(define (insert-num n lst)
  (cond
    [(null? lst) (cons n '())]
    [(< n (car lst)) (cons n lst)]
    [else (cons (car lst) (insert-num n (cdr lst)))])
  )

;hw3
;lis->(assending-sorted) list
;uses insert-num to do a insert sort
(define (sort-num lst)
  (cond
   [(null? lst) '()]
   [(insert-num (car lst) (sort-num (cdr lst)))])
  )

;hw3
;lst->sorted-list
(define (sort-any in-order? lst)
  (cond
   [(null? lst) '()]
   [(insert-any in-order? (car lst) (sort-any in-order? (cdr lst)))])
  )

;hw3
;(comp number sorted-list) -> (sorted)list
(define (insert-any comp n lst)
  (cond
    [(null? lst) (cons n '())]
    [(comp n (car lst)) (cons n lst)]
    [else (cons (car lst) (insert-any comp n (cdr lst)))])
  )
