;Authors: Brenden Nelson-Weiss and Brandon Snow 
(require 2htdp/universe)
(require 2htdp/image)
(require 2htdp/planetcute)

; A BinTree is a node (bt-node) or an empty tree (bt-empty)
; bt-node: (Number bt bt) -> bt
; bt-empty: () -> down-tree

(define-struct bt-node (val left right))
(define-struct bt-empty ())

;hw2c
;ws definition has a binary tree and a list

(define-struct ws (bt lst))

;hw2c
;defines the launch
;(struct) -> worldstate
(define (launch xs)
  (windowState (make-ws xs '())))



;hw2c
;list-of-N -> World
;ws that is a interactive binary tree window
;has a on key listerner to move through the tree
(define (windowState aWorld)
  (big-bang aWorld 
            (to-draw render)
            (on-key key-click)
            ))


;hw2c
;keyevent->worldstate
;handles a key event and moves if its a l,r,u,^, <,>, and returns a new worldstate with a new list and binary tree 
(define (key-click ws keyEvent)
  (cond
    [(or (key=? "u" keyEvent) (key=? "up" keyEvent))
     (if (null? (ws-lst ws)) ws (make-ws (ws-bt ws) (reverse(rest(reverse(ws-lst ws))))))]
    [(or (key=? "l" keyEvent) (key=? "left" keyEvent))
     (if(path-in-tree? (ws-bt ws) (append (ws-lst ws) (list "l"))) (make-ws (ws-bt ws) (append (ws-lst ws) (list "l"))) (make-ws (ws-bt ws) '()))]
    [(or (key=? "r" keyEvent) (key=? "right" keyEvent))
     (if (path-in-tree? (ws-bt ws) (append (ws-lst ws) (list "r"))) (make-ws (ws-bt ws) (append (ws-lst ws) (list "r"))) (make-ws (ws-bt ws) '()))]
    [else ws]))
  

;hw2c
;worldstate->image
;renders the world
;helper function to draw tree
(define (render wrld)
  (img-highlight-tree (ws-bt wrld) (ws-lst wrld))
  )

;hw2b
(define empty-node (text "Empty" 12 "black"))


  

;hw2b
;helper function
;creates a selected node and returns it
;copy of img-node just instead changes to 
(define (img-node-selected n color)
  (let* ([num-img (text (number->string n) 10 "white")]
         [num-width (+ 8 (/ (image-width num-img) 2))])
    (overlay
     num-img
     (overlay
      (circle num-width "outline" "black")
      (circle num-width "solid" color)))))



  
;hw2c
;updated to when the path is null highlight that node
;hw2b
;tree->image of tree
;cond
;1st path is null
;2nd checks if string is l
;3rd checks if string is r
(define (img-highlight-tree t p)
(cond
  [(bt-empty? t) (error "path leaves tree")]
  [(null? p)
   (cond
     [(and (bt-empty? (bt-node-left t)) (bt-empty? (bt-node-right t)))
      (connect-level (img-node-selected (bt-node-val t) "darkblue") empty-node empty-node)]
     [(bt-empty? (bt-node-left t))
      (connect-level (img-node-selected (bt-node-val t) "darkblue") empty-node (img-bt (bt-node-right t)))]
     [ (bt-empty? (bt-node-right t))
      (connect-level (img-node-selected (bt-node-val t) "darkblue") (img-bt (bt-node-left t)) empty-node)]
     [(and (bt-node? (bt-node-left t)) (bt-node? (bt-node-right t)))
      (connect-level (img-node-selected (bt-node-val t) "darkblue")  (img-bt (bt-node-left t)) (img-bt (bt-node-right t)))])]
  [(string=? (first p) "l")
   (cond
     [(and (bt-empty? (bt-node-left t)) (bt-empty? (bt-node-right t)))
      (error "path leaves tree")] ;pun intended
     [(bt-empty? (bt-node-left t))
      (error "path leaves tree")] ;pun intended 
     [ (bt-empty? (bt-node-right t))
      (connect-level (img-node (bt-node-val t) ) (img-highlight-tree (bt-node-left t) (rest p)) empty-node)]
     [(and (bt-node? (bt-node-left t)) (bt-node? (bt-node-right t)))
      (connect-level (img-node (bt-node-val t) )  (img-highlight-tree (bt-node-left t) (rest p)) (img-bt (bt-node-right t)))])
   ]
  [(string=? (first p) "r")
   (cond
     [(and (bt-empty? (bt-node-left t)) (bt-empty? (bt-node-right t)))
      (error "path leaves tree")] ;pun intended
     [(bt-empty? (bt-node-left t))
      (connect-level (img-node (bt-node-val t)) empty-node (img-highlight-tree (bt-node-right t) (rest p)))]
     [ (bt-empty? (bt-node-right t))
      (error "path leaves tree")] ;pun intended
     [(and (bt-node? (bt-node-left t)) (bt-node? (bt-node-right t)))
      (connect-level (img-node (bt-node-val t))  (img-bt (bt-node-left t)) (img-highlight-tree (bt-node-right t) (rest p)))])]))

;hw2b
;tree->value
;take tree and  go through the path
(define (value-at-path t p)
  (cond
    [(null? p) (bt-node-val t)]
    [(string=? "l" (first p)) (if (not (bt-empty? (bt-node-left t))) (value-at-path (bt-node-left t) (rest p)) (error "path leaves tree"))]
    [(string=? "r" (first p)) (if (not (bt-empty? (bt-node-right t))) (value-at-path (bt-node-right t) (rest p)) (error "path leaves tree"))]))

;hw2b
;tree->value
 (define (path-in-tree? t p)
   (cond
      [(bt-empty? t) #false]
      [(empty? p) #true]
      [(string=? "l" (first p)) (path-in-tree? (bt-node-left t) (rest p))]
      [(string=? "r" (first p)) (path-in-tree? (bt-node-right t) (rest p))]))



;given definitions of EX
 (define EX
  (make-bt-node
   4
    (make-bt-node
      pi
        (make-bt-node 2 (make-bt-empty) (make-bt-empty))
        (make-bt-node 3 (make-bt-empty) (make-bt-empty)))
    (make-bt-node 10 (make-bt-node 20 (make-bt-empty ) (make-bt-empty)) (make-bt-node 14 (make-bt-empty) (make-bt-empty)))))








;hw1a copy
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



;hw2b test cases
;(value-at-path EX '())             
;(value-at-path EX (list "l" "r")) 
;(value-at-path EX (list "l"))

;(path-in-tree? EX '())                   
;(path-in-tree? EX (list "l" "r" ))      
;(path-in-tree? EX (list "r"))              
;(path-in-tree? EX (list "l" "l" "r" "l"))

;(img-highlight-tree EX '())
;(img-highlight-tree EX '("l"))
;(img-highlight-tree EX (list "l" "r"))



;hw2c launch case

(launch EX)
