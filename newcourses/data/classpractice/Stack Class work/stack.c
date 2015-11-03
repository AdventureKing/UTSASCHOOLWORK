#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "node.h"
#include "stack.h"

node * growStack() {
	//TODO: implement this
	return NULL;
}

void initStack() {
	//TODO: implement this
		stack = (node *) malloc(sizeof(node) * STACKSIZE);
		top = -1;
}

int size() {
	//TODO: implement this
		return top + 1;
}

int full() {
	//TODO: implement this (will need more variables than just stack top to determine this)
	return TRUE;
}

int empty() {
	//TODO: implement this
		if(top == -1)
				return TRUE;
		else 
				return FALSE;
	
}

int push(node * val) {
	//TODO: implement this
		top++;
		*(stack+top) = *val;
		/*if(full(stack) == true)
			{
			growStack(stack);
			}*/
	return TRUE;
}

node * pop() {
	node *ret = NULL;
	//TODO: implement this
		if(empty())
			return NULL;
	ret=(stack+top);
		top--;
		return(ret);
	
}
