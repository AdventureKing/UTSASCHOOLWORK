#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "queue.h"

int insert_count=0;

// print out my list can work back or foward
void printQueue() {
	node *p;
	printf("Queue contains: ");
	//traverse queue from front to rear and print each node
	for(p = front;p != NULL ;p=p->right)
				printf("%d ",p->info);
	printf("\n");
	
}
//free my queue
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
				p = q->right;
				free(q);
		}
}
// fill my node with value read in by file and set other fields to NULL
node * getNode(int val) {
	//alloc the node
		node *p;
		p= (node *) malloc(sizeof(node));
		p->info = val;
		p->left = NULL;
		p->right = NULL;
	//check for NULL
	//initialize and set members
	return p;
}
//INsert node and count= when im comparing n with the current value aka another piece in the linked list
//count when loop through while loop and count when i assign something to n don't count when assigning front or rear because no number exisits there
//account for first numbers
//account for placing at beginning
// need if checks for when n value is smallest number in linked list or greater then current first index  
//account for placing at end
//need if checks to for when n value is largest number in linked list or second largest numbers
//account for placing in middle
//need if chcecks for if it goes to the left of current or to the right of current
void insertNode(node *n,int val) {
	//append node n to rear of queue
		
		check_count=0;
		node *curr = NULL;
		//fill my node 
		n = getNode(val);

		//printf("n val:%d\n",n->info);
		//save me my front if i need to traverse through front else reassign to rear later
		curr = front;

		//check statments
		//if(curr!=NULL)
		//printf("curr=%d\n",curr->info);
		//if(rear != NULL)
		//printf("rear=%d\n",rear->info);
		
		//first number
		//only time rear and front will both equal n
		if(curr == NULL)
			{
			front = n;
			rear = n;
			}
		else if(curr->left == NULL && curr->right == NULL)
		{
			
				//insert behind 
			if(curr->info <= n->info)
				{
					curr->right = n;
					n->left = curr;
					//count
					check_count++;
					//insert_count++;		
				}
				//insert ahead aka front
			else if(curr->info > n ->info)
				{
					curr ->left = n;
					//count
						check_count++;
						//insert_count++;
					n->right = curr;
						//reassign front to lowest value n						
						front = n;
						
				}
			
			
		}
		else if(curr->left == NULL && curr->right != NULL)
		{
				//if val is grater than max / 2 start from rear reassign curr to loop
				if(n->info > rear->info/2)
					{
							curr = rear;
							//find spot right after it needs to go from rear so its in the same spot wether it will traverse from front or back so i dont have to change if statments
							while(curr->left != NULL &&curr->info > n->info)
									{
									curr=curr->left;
									//count
									check_count++;
									//insert_count++;
									}
					}
				else
					{
					//else less than max /2 keep curr at front
					//find spot right after before it needs to go from front
					while(curr->right != NULL && curr->info < n->info)
						{
								curr = curr->right;
								//count	
								check_count++;
								//insert_count++;
						}
					}
						//if inserting at the end
						if(curr->right == NULL)
						{
							//if n is largest number
							if(curr->info < n->info)
							{
							//check statments
						//printf("curr->info:%d\n",curr->info);
						//reassign rear to largest value aka n						
						rear = n;
						n->left = curr;
						curr->right = n;

						//count
						check_count++;
						//insert_count++;
							}

						//if n is second largest
						else if(curr->info > n->info)
						{
							//check statments
							//printf("curr->info:%d\n",curr->info);
							n->left = curr->left;
							curr->left->right = n;
							
							curr->left = n;
							n->right = curr;

							//count
							check_count++;
							//insert_count++;
						}		
					}
					//if inserting at the beginning and n is second smallest number
					else if(curr->left == NULL && n->info > curr->info)
					{	
						//check statments
						//printf("curr->info:%d\n",curr->info);
						
						curr->right->left = n;
						n->right = curr->right;
						n->left = curr;
						curr->right = n;

						//count
						check_count++;
						//insert_count++;
						
					}
									//if inserting at beginning and n is smallest number						
				  else if(curr->left == NULL && n->info < curr->info)
					{
						//check statments
						//printf("curr->info:%d\n",curr->info);
						
						n->left = NULL;
						n->right = curr;
						curr->left = n;

						//count
						check_count++;
						//insert_count++;
						front = n;
						
					}
					//inserting in the middle of two numbers
				else
					{
						//check statments						
						//printf("curr->info:%d\n",curr->info);
						if(n->info > curr->info)
							{
								
								curr->right->left = n;
								n->left = curr;
								n->right = curr->right;
								curr->right = n;
								
								//count
								check_count++;
								//insert_count++;
							}
						else if(n->info < curr -> info)
							{

								n->right = curr;
								curr->left->right = n;
								n->left = curr->left;
								curr->left = n;
								
								//count
								check_count++;
								//insert_count++;
								
							}
						
					}
		}
			insert_count = insert_count + check_count;
			if(check_count == 1)
				printf(" Insert into queue. Checked %d node\n",check_count);
			else
				printf(" Insert into queue. Checked %d node\n",check_count);
		//printf("front info:%d\n",front->info);
	
	//set rear to n
}
//fucking useless or didn't implament
int empty() {
	//empty if front is NULL
		if(front == NULL)
			return TRUE;
		else 
			return FALSE;
}
//delete smallest number in linked list
//reassign front->left to null for if check in insert cause the new node will still be connected to freed memory
node * deleteNode() {
	//remove and return node from front of queue
	//return null if empty
	//move front to front->next
		
	node *p = front;
		if(p == NULL)
			return NULL;
		else
			{
				
			front=front->right;
				front ->left = NULL;
			}	
			
	return p;
}

