#ifndef _queue_h
#define _queue_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define TRUE 1
#define FALSE 0


typedef struct node {
	int info;
	struct node * next;
} node;


node *front;
//node * rear;

int insert_count;
int check_count;

node * getNode(int val);
void printQueue();
void initQueue();
void freeQueue();
void insertNode(node *n,int val);
int empty();
node * deleteNode();

#endif
