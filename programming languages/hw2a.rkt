;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.

;Authors: Brenden Nelson-Weiss and Brandon Snow 
(require 2htdp/universe)
(require 2htdp/image)
(require 2htdp/planetcute)

;given structs

(define-struct bt-node (val left right))
(define-struct bt-empty ())


;1. define img-node
;num->image of a struct
(define (img-node n)
  (let* ([num-img (text (number->string n) 10 "black")]
         [num-width (+ 8 (/ ( image-width num-img) 2))])
    (overlay num-img
           (circle num-width "outline" "black")
           (circle num-width "solid" "beige" )
           
  )))

;2.
;connect parent to child with a line image
;coords -> image
;part 2 defind child-lines
(define (child-lines lwidth rwidth)
  (add-line
   (add-line
    (rectangle (+ lwidth rwidth) 50 "outline" "white")
    (/ lwidth 2) 100
    (/ (+ lwidth rwidth) 2) 0
    "black")
   (/ (+ lwidth rwidth) 2) 0
   (+ (/ rwidth 2) lwidth) 100
   "black"
   )
  )
;3.
;node ->image
;connect the different levels of the tree
;node-img is a node with a number nested in it
;left subtree and right subtree are nodes that need to be connected to node-img
(define (connect-level  node-img left-subtree-img right-subtree-img)
  (let* ([offset (/ (image-width node-img) 2)])
    (above node-img
                 (child-lines (+ 20(image-width left-subtree-img)) (+ 20(image-width right-subtree-img)))
         (beside/align "top" left-subtree-img (rectangle 20 1 "outline" "white") right-subtree-img))))

;4.
;tree->image
(define (img-bt bt)
  (cond
    [(null? bt) empty-image]
    [(bt-empty? bt) (text "Empty" 12 "black")]
    [else (connect-level (img-node (bt-node-val bt))
                         (img-bt (bt-node-left bt))
                         (img-bt (bt-node-right bt))
                         )]
    )
  )
