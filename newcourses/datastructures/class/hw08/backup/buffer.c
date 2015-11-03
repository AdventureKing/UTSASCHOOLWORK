#include <stdio.h>
#include <stdlib.h>

/*update: apr 15th 9:52am
8 function*/

/*need to write
 **listInOrder**
 **NodeLevelOrder**
 **Min**
 **Max**
 **count**
*/

/*Height of list*/
typedef struct nodeT {
    int key;
    struct nodeT *left, *right;
} nodeT, *treeT;

int height(nodeT *t)
{
    if (t == NULL)
        return 0;
    else
        return (1 + maximumof(
                    height(t->left),
                    height(t->right)));
}
/*find integer*/

nodeT* FindNode(nodeT *t, int key)
{
    while(t !=NULL) 
		{
        if (key == t->key) 
				{
						return t;
				}
        if (key < t->key) {
            t = t->left;
        }
				 else {
            t = t->right;
        }
		}
        return (NULL);
}
    /*display my tree*/
void DisplayTree(nodeT *t)
{
        if (t != NULL) {
            DisplayTree(t->left);
            printf("%c",t->key);
            DisplayTree(t->right);
        }
}
    /*Sum of tree*/

int add(nodeT *p)
{
        if (p == NULL)
            return 0;
        else
            return (p->key +
                    add(p->left) +
                    add(p->right) );
}


    /*post order of list before walk*/


void PostOrderWalk(nodeT *t)
{
        if (t != NULL) {
            DisplayTree(t->left);
            DisplayTree(t->right);
            printf("%c" ,t->key);
        }
}


    /*list order after walk*/
void PreOrderWalk(nodeT *t)
{
        if (t != NULL) {
            printf( "%c",t->key);
            DisplayTree(t->left);
            DisplayTree(t->right);
        }
}

    /*Insert a node for me with a int key!!!!*/

void InsertNode(nodeT **tptr, int key)
{
    nodeT *tmp;
    tmp=*tptr;
    if (tmp == NULL) {
        tmp=(nodeT *)malloc(sizeof(nodeT));/*this fucking line :got it working with marcus tue april 16th*/
        tmp->key = key;
        tmp->left=NULL;
        tmp->right=NULL;
        *tptr=tmp;
        /*testprintf("Inserted node %d\n", tmp->key); tue 15th*/
        return;
    }
    else if (key < tmp->key) 
		{
        InsertNode(&tmp->left, key);
      printf("traversed left\n"); 
    } 
else {
        InsertNode(&tmp->right, key);
        /*testprintf("traversed right\n"); tue 15th*/
	   }
}


void DeleteNode(nodeT **p)
{

}
void NodeLevelOrder(nodeT *t)
{
}
void ListInOrder(nodeT *t)
{

}
void Min(nodeT *t)
{

}
void Max(nodeT *t)
{

}
void Count(nodeT *t)
{

}

void maximumof(nodeT *p, nodeT *t)
{


}
