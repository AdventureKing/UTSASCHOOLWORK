#include <stdlib.h>
#include <stdio.h>

struct Node
{
		int data;
		struct Node* next;
};



struct Node* Insert(struct Node* head,int x);
struct Node* Print();

int main()
{
			struct Node* head;
			head = NULL;
			printf("How many numbers?");
			int n,i,x;
	
		scanf("%d",&n);
	
		for(i=0;i<n;i++)
				{
					printf("Enter the number:");
					scanf("%d",&x);
					head=Insert(head,x);
					Print(head);
				}
			return (0);
}
struct Node* Insert(struct Node* head,int x)
{
			struct Node* temp = (struct Node *)malloc(sizeof(struct Node));
			temp->data=x;
			temp->next=head;
			/*	if(head != Null)
					{
						temp->next = head;
					}*/
			head = temp;
			return (head);
}
struct Node* Print(struct Node* head)
{
			
				printf("List is: ");
			while(head != NULL)
			{
					printf(" %d",head->data);
					head = head->next;
			}
					printf("\n");
				return(0);
}
