#include <stdio.h>
#include <stdlib.h>
#include "node.h"

//this is huffman symbol frequency
void printNode(node * n) {
	printf("%d", n->info);
}

node * getNode(int info) {
	node * n = (node *) malloc(sizeof(node));
	if(n == NULL) 
		return NULL;
	n->info = info;
	n->next = n->left = n->right = n->parent = NULL;
	return n;
}

void freeNode(node * n) {
	//dont have to do anything special with members
	free(n);
}