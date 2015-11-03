#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "queue.h"



/*
 *Dont need this

void initQueue() {
	//what do front and rear point to when queue is empty?
	n= (node *)malloc(sizeof(node));
	n->next = NULL;
	front = n;
}
*/
//print my stuff
void printQueue() {
	node *p;
	printf("Queue contains: ");
	//traverse queue from front to rear and print each node
	for(p = front;p != NULL ;p=p->next)
				printf("%d ",p->info);
	printf("\n");
	
}
//free all my stuff
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
//allocate for node and return to insertNode
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

//insert in order fuckkk
void insertNode(node *n,int val) {
	//append node n to rear of queue
		
		check_count=0;
		node *prev=NULL;
		node *curr;
		n = getNode(val);
		//printf("n val:%d\n",n->info);
		curr = front;
			//traverse through the 
			while(curr != NULL && curr->info < n->info)
			{
						prev = curr;
						
						//printf("curr->info:%d\n",curr->info);
						curr = curr->next;
						check_count++;
						total_check_count++;

			}
			
			//still checking 
			if(front != NULL && curr != NULL)
			{
						check_count++;
						total_check_count++;
			}
			//if prev value is less than n then it links to n at the end
			if(prev != NULL)
			{
							prev->next = n;
			}
			//if no previous nodes make the node i just created the front aka first number inserted
			if(prev == NULL)
			{
							
						front = n;
			}
			//create link if middle else sets n->next to null if at the end
			n->next = curr;
			if(check_count == 1)
				printf(" Insert into queue. Checked %d node.\n",check_count);
			else
				printf(" Insert into queue. Checked %d nodes.\n",check_count);
		//printf("front info:%d\n",front->info);
	
	
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
