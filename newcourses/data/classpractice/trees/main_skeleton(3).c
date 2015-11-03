#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1
#define FALSE 0
#define NUMNODES 100
#define EMPTY -1
#define ROOT 0
#define NULLNODE -1

typedef struct node {
	int info;
	int left, right;
} node;

node tree[NUMNODES];

void initTree() {
		int i;
	//traverse array and set all node members to EMPTY
		for(i = 0;i < NUMNODES ; i++)
		{
			tree[i].info = EMPTY;
			tree[i].left = EMPTY;
			tree[i].right = EMPTY;
		}
}

int getNode() {
	//traverse array and find first node with EMPTY info member
	//return that index or EMPTY if no empty elements left
		int i;
		for(i=0;i < NUMNODES; i++)
		{
			if(tree[i].info == EMPTY)
					return i;
		}
	return EMPTY;
}

void insertInTree(int x) {
	//if root is empty then set root to x and return
	if(tree[ROOT].info == EMPTY)
	{
				printf("%d was inserted\n",x);
				tree[ROOT].info = x;
				
				return;
	}
	int p,q;
	p = ROOT;
	
	while(p != NULLNODE)
	{
		if(tree[p].info == x)
		{
				printf("%d is a duplicate\n",x);
				return;
		}

		q = p;

		if(x < tree[p].info)
			{
				p=tree[p].left;
			}
		else
			{
				p = tree[p].right;
			}

	}
	
	//walk the tree (start at root) using binary search. go left if x < array[p].info, else right
	//until p = EMPTY
		int n = getNode();
		tree[n].info = x;

		if(x < tree[q].info)
		{
			tree[q].left = n;
		}
		else
		{	
			tree[q].right = n;
			
		}	
		printf("%d was inserted\n",x);
	//q needs to chase p (so we can set q's left or right index to x)
	//NOTE: since we are eliminating duplicates, if array[p].info = x then return

	//after loop, q will be the parent of x's node
	
	//get the next free node index in the array

	//set its info member to x
	
	//set q's left or right member to x's node index
}

void printInorder(int p) {
	//recursive inorder print
	//what is escape condition? Probably should check 2 things
	if(p == NULLNODE)
			return;
		printInorder(tree[p].left);
		printf("%d ",tree[p].info);
		printInorder(tree[p].right);
		
	//left root right
}

void printPreorder(int p) {
	//recursive inorder print
	//what is escape condition?
		if(p == NULLNODE)
			return;
		printf("%d ",tree[p].info);
		printPreorder(tree[p].left);
		printPreorder(tree[p].right);
		
	//root left right
}

void printPostorder(int p) {
	//recursive inorder print
	//what is escape condition?
	if(p == NULLNODE)
			return;
		
		printPostorder(tree[p].left);
		printPostorder(tree[p].right);
		printf("%d ",tree[p].info);
	//left right root
}

int main(int nargs, char *args[]) {
	int data [] = {5, 19, 2, 11, 8, 5};
	int i;

	initTree();
	
	for(i = 0; i < 6; i++) {
		insertInTree(data[i]);
	}

	printf("Printing preorder:\n");
	printPreorder(ROOT);
	printf("\n");

	printf("Printing inorder:\n");
	printInorder(ROOT);
	printf("\n");

	printf("Printing postorder:\n");
	printPostorder(ROOT);
	printf("\n");
	
	return(EXIT_SUCCESS);
}
