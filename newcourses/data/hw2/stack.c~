#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "stack.h"

node *growStack() {
		
		
		int newsize = stack->len + 10;
	  printf("Stack has grown from %d to %d\n", stack->len,newsize);
		stack->len = newsize;
		stack->num =(int *)realloc(stack->num, newsize * sizeof(int));
		/*This stuff was debugging ill leave it here for you to look at
		int *array;
		array = (int *) malloc(sizeof(int) * newsize);
		
		free(stack->num);
		stack->num = array;
		free(array);*/
		return 0;
		
}

void initStack() {
	
	stack = (node *) malloc(sizeof(node));
	stack->len = 10;
	stack->num = (int *) malloc(sizeof(int) * stack->len);
	
	top = -1;
	stack->count =0;
}

int size() {
	
	return stack->count;
}

/*this function i though was gonna be the easiest but it killed my program till i changed from top to count*/

int full() {
		
	if(stack->len == stack->count)
		return TRUE;
	else
		return FALSE;
}

int empty() {
if(top == -1)
	return TRUE;
else
	return FALSE;
}

int push(int num) {
	if(full() == TRUE)
	{
		growStack();
		
	}
	//printf("top:%d count:%d\n",top,stack->count);
	stack->count++;
	top++;
	*(stack->num + top) = num;
	return TRUE;
	
	
}

int pop() {
	//TODO: implement this
	int popNum;
	if(empty() == TRUE)
	{
		printf("Nothing to pop!!");
	}
	else
	{
	
	popNum = *(stack->num + top);	
	//printf("popNum:%d\n",popNum);
	*(stack->num + top) = '\0';
	top--;
	stack->count--;
	}
	return popNum;
}

void printNode(node *n) 
{
	
	//if empty
if(top == -1)
	{
	printf("Nothing to print");	
	}
	else
	{
		printf("Number:%d\n",*(stack->num + top));
	//printf("not yet implemented\n");
	}
}

      
