;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |hw 1a|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;Authors: Brenden Nelson-Weiss and Brandon Snow 
(require 2htdp/universe)
(require 2htdp/image)


;N -> Boolean
;helper function for deternmining whether a number is valid to represent a disk
(define(inBound x)
  (if(and (not(null? x)) (and (>= x 1) (<= x 5))) #t #f))


;list-of-N -> World
;BIG BANG DEF THAT RUNS A INTERACTIVE SINGLE TOWER WITH 5 corresponding disk colors to numbers 1-5
;starts the program to create a stack
(define (launch init-disk-stack)
  (big-bang init-disk-stack
            (to-draw draw-peg 200 200)
            (on-key key-press)
            (on-mouse mouse-click)
            ))
;list-of-N -> Image
;draw the peg
;underlay (make peg) (place it under a empty stack) (call image tower stack))
;call ends in 
(define (draw-peg stack)
  (underlay/align "middle" "bottom" (place-image (rectangle 20 150 "solid" "black") 100 125 (empty-scene 200 200)) (img-twr stack))
  )

;key press handler, how it will move the images around to stack them in the order the buttons are pressed
;adds a element of the key pressed to the top and removes the old on
(define (key-press init-stack key-pressed)
  (cond
    [(key=? key-pressed "1") (cons 1 (remove 1 init-stack))]
    [(key=? key-pressed "2") (cons 2 (remove 2 init-stack))]
    [(key=? key-pressed "3") (cons 3 (remove 3 init-stack))]
    [(key=? key-pressed "4") (cons 4 (remove 4 init-stack))]
    [(key=? key-pressed "5") (cons 5 (remove 5 init-stack))]
    [else init-stack]
    )
  )

;mouse click handler, when someone clicks the window the first element will be removed
(define (mouse-click init-stack xpos ypos input)
  (if (and (mouse=? input "button-down") (not (empty? init-stack))) (remove (first init-stack) init-stack)
      init-stack)
  )

; N -> Color
;returns a 2htdp/image valid color corresponding to size number
(define(size->color x)
      (cond
        [(= x 1) "blue"]
        [(= x 2) "red"]
        [(= x 3) "green"]
        [(= x 4) "violet"]
        [(= x 5) "orange"]
        ))
; N -> Image
;calls the size->color to get color and returns a appropriately sized rectangle image
 (define(img-disk size)
   (if (inBound size)
       (rectangle (+ 10 (* 25 size)) 20 "solid" (size->color size))
       empty-image))
 ;list-of-N -> Image
 ;create a tower function
 ;takes list and draws top down
 ;base case return empty-image
 ;empty-image is just null for images
 (define (img-twr stack)
   (cond
     [(empty? stack) empty-image]
     [else (above (img-disk (first stack)) (img-twr (rest stack)))]
     ))



;CURRENTLY NOT BEING USED BUT COMPILES
;Check if the  previous disk is smaller, or bigger than the next one a.k.a disk 0 > disk 1 return false
(define (size-checker stack prev)
  (cond
    [(= (length stack) 0) #t]
    [(> prev (list-ref stack 0)) #f]
    [(<= prev (list-ref stack 0)) (size-checker (rest stack) (list-ref stack 0))]
   )
 )


;CURRENTLY NOT BEING USED BUT COMPLIES
;checks stack to see if its in correct order from
;takes the stack of disk and calls a function called size checker that goes
;through the list to find a disk that is out of order
(define (check-stack-correctness stack)  
  (size-checker stack (list-ref stack 0))
  )


;EXAMPLE START OF THE PROGRAM
;calls the big bang function that draws the pegs
;(launch (cons 1 (cons 2 (cons 3 (cons 4 (cons 5 '()))))))


 

   
   