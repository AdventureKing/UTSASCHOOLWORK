#include <stdio.h>
#include <string.h>
#include	<stdlib.h>
#include <math.h>
#include <time.h>
#include "tree.h"


int i;
float countCompare = 0;

int sizeOfTree = 5;

void ListInOrder(node *t)
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

//insert into tree tptr is a pointer to a pointer of a struct
void InsertNode(node **tptr, int key)
{
		//printf("traversed \n");
    node *tmp = NULL;
		
    tmp=*tptr;
		
    if (tmp == NULL) {
        tmp=(node *)malloc(sizeof(node));
        tmp->key = key;
        tmp->left=NULL;
        tmp->right=NULL;
        *tptr=tmp;
        //printf("%d\n",tmp->key);
        //return;
    }
    else if (key <= tmp->key) 
    {
					if(tmp->key == key)
				{
						//printf("\nYou can't enter the same number twice!\n");
						return;
				}
				
        InsertNode(&tmp->left, key);
        /*printf("traversed left\n"); */
    } 
    else {
					
        InsertNode(&tmp->right, key);
        /*testprintf("traversed right\n"); */
    }
}

//free all my nodes
void free_all(node *p)
{   
  if(p != NULL)
  {       
    
   free_all(p->left);     
   free_all(p->right);
	free(p);
  }
}



int generateRandomNumSequence()
{
	srand(time(NULL));
	node *tp=NULL;

	int i, ranNum;
	float adv = 0;
		for(i = 0; i < sizeOfTree; i++)
		{
			
			ranNum=generateRandom();
			//printf("\n%d ",ranNum);
			InsertNode(&tp,ranNum);
		}	
		for( i = 0;i < 100;i++)
		{
			
			ranNum = generateRandom();
			FindNode(&tp,ranNum);
		}

		//ListInOrder(tp);
		printf("\n");
		float logCal;

		logCal = Log2(countCompare);

		 adv = countCompare / 100;
		printf("%d%40.3f%35.5f",sizeOfTree,adv,logCal );
		//printf("countCompare: %d\n",countCompare);

		

		 

		//printf("log2n: %f\n", logCal);

		//printf("Run Summary\n");
		//printf("Level Difference   # of runs\n");
	countCompare = 0;
	sizeOfTree = sizeOfTree + 5;
	free_all(tp);
	
		return 0;
}

node* FindNode(node **tp, int key)
{
		node *t = *tp;
    while(t !=NULL)
    {
        if (key == t->key)
        {
        		countCompare++;
            //printf("The number %d exists!!\n",key);
            return NULL;
        }
        if (key < t->key) {
        		countCompare++;
            t = t->left;
        }
        else {
        		countCompare++;
            t = t->right;
        }
    }
   // printf("The number %d does not exist here!!\n", key);
    return (NULL);
}

float Log2( float n )  
{  
    // log(n)/log(2) is log2.  
    return log( n ) / log( 2 );  
}

int generateRandom()
 {
	return rand() % 10000 + 1;
}

