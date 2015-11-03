#ifndef _queue_h
#define _queue_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define TRUE 1
#define FALSE 0
//my struct node that will hold 1 int and a next field

typedef struct node {
	int info;
	struct node * next;
} node;

//gobal front of link
node *front;
//node *rear;
//counter for printf of number of nodes checked when insterting a new node
//counter for number of nodes inserted
//int insert_count;
int check_count;
int total_check_count;
//functions

node * getNode(int val);

void printQueue();
void initQueue();
void freeQueue();
void insertNode(node *n,int val);

int empty();

node *deleteNode();

#endif
