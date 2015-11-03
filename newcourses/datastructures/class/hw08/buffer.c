#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/*update: apr 17th 6:27pm
					apr 19 2:45pm
					apr 22 10:26am
  13 function(s)*/


/*functions that are done:
 **Insert/ListInOrder/ListPostOrder/ListPreOrder/FindNode/Sum/Max/Min/height/maximumof/count/NodeLevelOrder**
 */
/*What needs works:
 **Delete***/


#include "buffer.h"




struct node{
    int key;
    nodeT *left, *right;
};


/*find integer*/

nodeT* FindNode(nodeT *t, int key)
{
    while(t !=NULL) 
    {
        if (key == t->key) 
        {
            printf("The number %d exists!!",key);
            return t;
        }
        if (key < t->key) {
            t = t->left;
        }
        else {
            t = t->right;
        }
    }
    printf("The number %d does not exist here!!", key);
    return (NULL);
}





/*Sum of tree*/

int add(nodeT *p)
{
    /*Testprintf("hit here"); wed 16th 6:13*/
    if (p == NULL)
        return 0;
    else
        return (p->key + add(p->left) + add(p->right));
}


/*list in order*/
void ListInOrder(nodeT *t)
{

    if(t == NULL)
        return;
    if(t->left ==NULL && t->right == NULL)
    {				
        printf("%d ",t->key);
        return;
    }
    ListInOrder(t->left);
    printf("%d ",t->key);
    ListInOrder(t->right);
}


/*post order of list before walk*/
void PostOrderWalk(nodeT *t)
{
    if(t == NULL)
    {
        return;
    }
    if(t->left ==NULL && t->right == NULL)
    {
        printf("%d ", t->key);
        return;
    }
    PostOrderWalk(t->left);
    PostOrderWalk(t->right);
    printf("%d ",t->key);
}


/*list order after walk*/
void PreOrderWalk(nodeT *t)
{
    if(t == NULL)
        return;
    if(t->left ==NULL && t->right == NULL)
    {
        printf("%d ", t->key);
        return;
    }
    printf("%d ",t->key);
    PreOrderWalk(t->left);
    PreOrderWalk(t->right);
}


void NodeLevelOrder(nodeT *t)
{
		/*edit mon 22 9:06am*/	
			int d;
	for(d=1; d<=height(t);d++)
			{
					printf("Level %d: ",d);
				printGivenLevel(t,d);
					printf("\n");
			}
}
void printGivenLevel(nodeT *t, int l)
{
			/*edit monday 22 9:35am*/
			if(t == NULL)
				{
				
				return;
				}
			if(l == 1)
				{
				printf("%d ",t->key);
				return;
				}
			else if(l > 1)
				{
				
				printGivenLevel(t->left,l-1);
				printGivenLevel(t->right,l-1);
				
				return;
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
    else if (key <= tmp->key) 
    {
					if(tmp->key == key)
				{
						printf("\nYou can't enter the same number twice!\n");
					return;
				}
				
        InsertNode(&tmp->left, key);
        /*printf("traversed left\n"); */
    } 
    else {
					
        InsertNode(&tmp->right, key);
        /*testprintf("traversed right\n"); tue 15th*/
    }
}

void DeleteNode(nodeT **p,int key)
{
		printf("Hit Here");

    nodeT *target, *lmd_r, *plmd_r, *tmp;
		tmp = *p;
		while(tmp!=NULL)
			{
				if(key > tmp->key)
					{
						tmp= tmp->right;
					}
				if(key < tmp->key)
					{
		
						tmp=tmp->left;
					}
				if(tmp->key == key)
					{
						break;
					}

			}
			target = tmp = *p;
		printf("Target:%d",target->key);
		
    if (target->left==NULL && target->right==NULL) 
		{
        *p=NULL;
    } 
		else if (target->left == NULL) 
		{
        *p=target->right;
    } 
		else if (target->right == NULL)
	  {
        *p=target->left;
			
    }
		else
		{
			plmd_r = target;
			lmd_r=lmd_r;
			lmd_r=lmd_r->left;

		}
		plmd_r->left=lmd_r->right;
		lmd_r->left=target -> left;
		lmd_r->right = target -> right;
		*p = lmd_r;
		free(target);
}

void Min(nodeT *t)
    {
        /* testprintf("Hit here!\n"); wed 16th 6:34 */
        while(1)
        {
            if(t->left == NULL)
            {
                printf("%d",t->key);
                return;
            }
            else
                t=t->left;
        }
    }
void Max(nodeT *t)
    {
        /* testprintf("Hit here!\n"); wed 16th 6:38 */
        while(1)
        {
            if(t->right == NULL)
            {
                printf("%d",t->key);
                return;
            }
            else
                t=t->right;
        }
    }
int Count(nodeT *t)
{		
/*edited at sat apr 19 3:22*/
    if(t == NULL)      
    return 0;
 else if(t->left == NULL && t->right==NULL)     
    return 1;           
  else 
    return (1+Count(t->left)+ Count(t->right));      
        
}
int height(nodeT *t)
{
    if (t == NULL)
        return 0;
    else
        return (1 + maximumof(
                    height(t->left),
                    height(t->right)));
}
/*editing sat april 19 2:23*/
int maximumof(int i, int j)
{
        /*find my lowest left or right*/
  if(i < j)
		return j;
	else
		return i;      
}


void free_all(nodeT *p)
{
			if(p != NULL)
    	{		
    
   free_all(p->left);
		free(p);
   free_all(p->right);
			}
}
