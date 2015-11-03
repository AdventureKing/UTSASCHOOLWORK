#include <stdio.h>
#include <string.h>
#include	<stdlib.h>
#include <math.h>
#include <time.h>
#include "tree.h"
/*int count_0 = 0;
int count_1 = 0;
int count_2 = 0;
int count_3 = 0;
int count_4 = 0;
int count_5 = 0;
int count_6 = 0;
int count_7 = 0;
int count_8 = 0;
int count_9 = 0;
int count_10 = 0;
int count_11 = 0;
int count_12 = 0;
int count_13 = 0;
int count_14 = 0;
int count_15 = 0;*/

int i;

int numRuns = 0;
extern int ary[NUMBERS];
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
        return;
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
//get min
int Min(node *t)
{
		
        /* testprintf("Hit here!\n"); */
       int min_left = 0;
			 int min_right = 0;
		if(t != NULL)
		{
			if(leaf(t))
				return 0;
			if(t->left != NULL)
				min_left = Min(t->left);
			if(t->right != NULL)
				min_right = Min(t->right);
			if(min_left > min_right)
					return min_right + 1;
			else
					return min_left + 1;
    }
		else
				return -1;
}
//get max
int Max(node *t)
    {
        /* testprintf("Hit here!\n"); */
       int max_left = 0;
			 int max_right = 0;
		if(t != NULL)
		{
			if(leaf(t))
				return 0;
			if(t->left != NULL)
				max_left = Max(t->left);
			if(t->right != NULL)
				max_right = Max(t->right);
			if(max_left > max_right)
					return max_left + 1;
			else
					return max_right + 1;
    }
		else
				return -1;
}
int leaf(node *t)
{
  return (t != NULL && t->left == NULL && t->right == NULL);
}
//give me my sequence of 100 numbers and calculate the min and max of them.
int generateRandomNumSequence()
{
	node *tp=NULL;
	numRuns++;
	int i, ranNum, diff=0;
	int level_min = 0;
	int level_max = 0;

		for(i = 0; i < 100; i++)
		{
			
			ranNum=generateRandom();
			//printf("\n%d ",ranNum);
			InsertNode(&tp,ranNum);
		}	
		//ListInOrder(tp);
		level_min = Min(tp);
		level_max = Max(tp);
		
		//printf("\nLevel of smallest value: %d\n",level_min);
		//printf("\nLevel of largest value: %d\n",level_max);

		//level_cal(level_min,level_max);
		if(level_min > level_max)
		{
			diff = level_min - level_max;
		}
	else if( level_max > level_min)
		{
			diff = level_max - level_min;
		}
		//printf("diff:%d\n",diff);
		ary[diff]++;
		level_max = 0;
		level_min =0;
		
	free_all(tp);
	
		return 0;
}

//dont need this just leaving it in
//caluate and add to the level difference over all varibales named count_number
/*int level_cal(int min, int max)
{
	
	int level_diff = 0;
	if(min > max)
		{
			level_diff = min - max;
		}
	else if( max > min)
		{
			level_diff = max - min;
		}
	if(level_diff == 0)
		count_0++;
	else if(level_diff == 1)
		count_1++;	
	else if(level_diff == 2)
		count_2++;		
	else if(level_diff == 3)
		count_3++;	
	else if(level_diff == 4)
		count_4++;	
	else if(level_diff == 5)
		count_5++;	
	else if(level_diff == 6)
		count_6++;
	else if(level_diff == 7)
		count_7++;	
	else if(level_diff == 8)
		count_8++;	
	else if(level_diff == 9)
		count_9++;
	else if(level_diff == 10)
		count_10++;
	else if(level_diff == 11)
		count_11++;
	else if(level_diff == 12)
		count_12++;
	else if(level_diff == 13)
		count_13++;
	else if(level_diff == 14)
		count_14++;
	else if(level_diff == 15)
		count_15++;
	return 0;			
}*/

int generateRandom() {
	return rand() % 1000 + 1;
}

