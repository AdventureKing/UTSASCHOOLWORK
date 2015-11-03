/*Brandon Snow started 9:11am tue april 15th 
			update: 10:30am tue april 15th 
		driver.c/buffer.h/buffer.c
 *insert <a positive integer>-.c.h.c
 * find <a positive integer>-.c.h.c
 * delete <a positive integer>-.c.h.c
 * list inorder-.c.h.c
 * list preorder-.c.h.c
 * list postorder-.c.h.c
 * list levelorder-.c.h.c
 * max-.c.h.c
 * min-.c.h.c
 * height-.c.h.c
 * count-.c.h.c
 * sum-.c.h.c
 * Display-.c.h.c
 */

#ifndef _buffer_h
#define _buffer_h

typedef struct nodeT nodeT, *treeT;



int height(nodeT *t);/*driver.c checked .h*/

nodeT *FindNode(nodeT *t, int key);/*driver.c checked .h */

void DisplayTree(nodeT *t);/*driver.c checked .h */

void PostOrderWalk(nodeT *t);/*driver.c checked .h */

void PreOrderWalk(nodeT *t);/*driver.c checked .h */

int add(nodeT *p);/*driver.c checked .h */

void InsertNode(nodeT **tptr, int key);/*driver.c checked .h */

void DeleteNode(nodeT **p);/*driver.c checked .h */

void NodeLevelOrder(nodeT *t); /*driver.c checked .h */

void ListInOrder(nodeT *t); /*driver.c checked .h */

void Min(nodeT *t); /*driver.c checked .h */

void Max(nodeT *t); /*driver.c checked .h */
void Count(nodeT *t); /*driver.c checked .h */
void maximumof(nodeT *p, nodeT *t)/*only in buffer.h*/




#endif
