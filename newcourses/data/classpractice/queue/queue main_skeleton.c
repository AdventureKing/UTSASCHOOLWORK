#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1
#define FALSE 0

typedef struct node {
	int info;
	struct node * next;
} node;

node * front;
node * rear;

void initQueue() {
	//what do front and rear point to when queue is empty?
	

}

void printQueue() {
	node *p;
	printf("Queue contains: ");
	//traverse queue from front to rear and print each node
		for(p = front;p != NULL ;p=p->next)
				printf("%d ",p->info);
	printf("\n");
}

void freeQueue() {
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
		node *n = (node *) malloc(sizeof(node));
	//check for NULL
		if(n == NULL)
			return NULL;
	//initialize and set members
		n->info = val;
		n->next = NULL;
	return n;
}

void insertNode(node * n) {
	//append node n to rear of queue
	//set rear's next to n if rear != null
	//also if queue is empty then front and rear both point to same node
		if(rear != NULL)
		{
			rear->next = n;
		}
		if(front == NULL)
				front = n;

		rear = n;
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
		if(empty() == TRUE)
				return NULL;

		node *ret = front;
		front = front->next;
		return ret;
	//return null if empty
	//move front to front->next
	return NULL;
}

int main(int nargs, char *args[]) {
	int data [] = {5, 19, -99999, 2, 11, -99999, 8};
	int i;
	
	for(i = 0; i < 7; i++) {
		if(data[i] == -99999) {
			printf("removing node from queue\n");
			node * n = deleteNode();
			if(n != NULL)
				free(n);
		} else {
			printf("inserting node into queue\n");
			node * n = getNode(data[i]);
			if(n == NULL) {
				printf("getnode returned null\n");
			} else {
				insertNode(n);
			}
		}
		printQueue();
	}
	
	freeQueue();
	
	return(EXIT_SUCCESS);
}
