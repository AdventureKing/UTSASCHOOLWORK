;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |hw 1b|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
;Authors: Brenden Nelson-Weiss and Brandon Snow 
(require 2htdp/universe)
(require 2htdp/image)

;worldState( selected leftStack middleStack rightStack)
(define-struct wSt(sel lS mS rS))


;N -> Boolean
;helper function for deternmining whether a number is valid to represent a disk
(define(inBound x)
  (if(and (not(null? x)) (and (>= x 1) (<= x 5))) #t #f))

;N -> Boolean
;helper function for deternmining whether a number is valid to represent a stack
(define (validStack x)
  (if(and (not(null? x)) (and (>= x 1) (<= x 3))) #t #f))

(define (validHeight x)
  (if(and (not(null? x)) (and (>= x 0) (<= x 5))) #t #f))

(define (validSelect x)
  (if (or (validStack x) (= x -1)) #t #f))


;list-of-N -> World
;BIG BANG DEF THAT RUNS A INTERACTIVE SINGLE TOWER WITH 5 corresponding disk colors to numbers 1-5
;starts the program to create a stack
(define (launch aWorld)
  (big-bang aWorld 
            (to-draw draw-pegs 600 200)
            (on-mouse mouse-click)
            ))
;list-of-N -> Image
;draw the peg
;underlay (make peg) (place it under a empty stack) (call image tower stack))
;call ends in 
(define (draw-peg stack sel num)
  (underlay/align "middle" "bottom" (place-image (rectangle 20 150 "solid" "black") 100 125 (empty-scene 200 200)) (img-twr stack sel (= sel num)))
  )


;WorldState->Image
(define (draw-pegs aWorld)
  (cond
    [(not (and (and(validHeight (length(wSt-lS aWorld))) (validHeight (length(wSt-mS aWorld)))) (and (validHeight (length(wSt-rS aWorld))) (validSelect (wSt-sel aWorld))))) empty-image] 
    [else (beside (draw-peg (wSt-lS aWorld) (wSt-sel aWorld) 1) (beside (draw-peg (wSt-mS aWorld)(wSt-sel aWorld) 2) (draw-peg(wSt-rS aWorld)(wSt-sel aWorld) 3)))]))

;CurrentWorldState->NewWorldState
(define (updateWorld a newStack)
  (cond
    [(= (wSt-sel a)  newStack) (make-wSt -1 (wSt-lS a) (wSt-mS a) (wSt-rS a))]
    [(and (= (wSt-sel a) -1) (or (or (and (= newStack 1) (not(inBound (length(wSt-lS a))))) (and (= newStack 2) (not(inBound (length(wSt-mS a))))) ) (and (= newStack 3) (not(inBound (length(wSt-rS a))))))) a]
    [(= (wSt-sel a) -1) (make-wSt newStack (wSt-lS a) (wSt-mS a) (wSt-rS a))]
    [(and (= (wSt-sel a) 1) (= newStack 2)) (make-wSt -1 (rest (wSt-lS a)) (cons (first (wSt-lS a)) (wSt-mS a))(wSt-rS a))]
    [(and (= (wSt-sel a) 1) (= newStack 3)) (make-wSt -1 (rest (wSt-lS a))  (wSt-mS a) (cons (first (wSt-lS a))(wSt-rS a)))]
    [(and (= (wSt-sel a) 2) (= newStack 1)) (make-wSt -1 (cons (first(wSt-mS a)) (wSt-lS a)) (rest (wSt-mS a))(wSt-rS a))]
    [(and (= (wSt-sel a) 2) (= newStack 3)) (make-wSt -1 (wSt-lS a) (rest (wSt-mS a)) (cons (first (wSt-mS a)) (wSt-rS a)))]
    [(and (= (wSt-sel a) 3) (= newStack 1)) (make-wSt -1 (cons (first (wSt-rS a)) (wSt-lS a)) (wSt-mS a) (rest (wSt-rS a)))]  
    [(and (= (wSt-sel a) 3) (= newStack 2)) (make-wSt -1 (wSt-lS a) (cons (first (wSt-rS a)) (wSt-mS a)) (rest (wSt-rS a)))]))

;mouse-click ->worldState
;mouse click handler, when someone clicks the window the first element will be removed
(define (mouse-click aWorld xpos ypos input)
  (if (and (mouse=? input "button-down") (not (empty? aWorld)))
      (cond
        [(< xpos 200) (updateWorld aWorld 1)]
        [(> xpos 400) (updateWorld aWorld 3)]
        [else (updateWorld aWorld 2)])
        aWorld)
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

 ;list-of-N, N -> Image
 ;create a tower function
 ;takes list and draws top down
 ;base case return empty-image
 ;empty-image is just null for images
 (define (img-twr stack sel top)
   (cond
     [(or (empty? stack) (not(validSelect sel))) empty-image]
     [(or (= sel -1) (not top)) (above (img-disk (first stack)) (img-twr (rest stack) sel #f))]
     [else (above (above (img-disk (first stack)) (rectangle 20 40 "solid" "black")) (img-twr (rest stack) sel #f))]
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


;START OF THE PROGRAM
;calls the big bang function that draws the pegs
(launch (make-wSt -1 (list 1 2 3 4) (list ) (list )))



 

   
   