#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "queue.h"



/*
void initQueue() {
	//what do front and rear point to when queue is empty?
	n= (node *)malloc(sizeof(node));
	n->next = NULL;
	front = n;
}
*/
void printQueue() {
	node *p;
	printf("Queue contains: ");
	//traverse queue from front to rear and print each node
	for(p = front;p != NULL ;p=p->next)
				printf("%d ",p->info);
	
}

void freeQueue() {
	//traverse queue from front to rear and free each node
	//need to preserve control variable to allow movement to next
		node *p;
		node *q;
	//traverse queue from front to rear and free each node
		
	//need to preserve control variable to allow movement to next
		p=front;
		while(p != NULL)
		{
				q = p;
				p = q->next;
				free(q);
		}
}

node * getNode(int val) {
	//alloc the node
		node *p;
		p= (node *) malloc(sizeof(node));
		p->info = val;
		p->next = NULL;
	//check for NULL
	//initialize and set members
	return p;
}

void insertNode(node *n,int val) {
	//append node n to rear of queue
		check_count=0;
		node *prev=NULL;
		node *curr;
		n = getNode(val);
		//printf("n val:%d\n",n->info);
		curr = front;
			while(curr != NULL && curr->info < n->info)
			{
						prev = curr;
						curr = curr->next;
						check_count++;

			}
			if(front != NULL && curr != NULL)
			{
						check_count++;
			}
			
			if(prev != NULL)
			{
							prev->next = n;
			}

			if(prev == NULL)
			{
							
						front = n;
			}
			
			n->next = curr;
		//printf("front info:%d\n",front->info);
	//set rear's next to n if rear != null
	//also if queue is empty then front and rear both point to same node
	//set rear to n
}

int empty() {
	//empty if front is NULL
		if(front == NULL)
			return TRUE;
		else 
			return FALSE;
}

node * deleteNode() {
	//remove and return node from front of queue
	//return null if empty
	//move front to front->next
	node *p = front;
		if(p == NULL)
			return NULL;
		else
			front=front->next;
	return p;
}
