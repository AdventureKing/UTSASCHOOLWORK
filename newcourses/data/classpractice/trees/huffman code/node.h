//next is just used for priority queue
typedef struct node {
	int info;
	struct node * next;
	struct node * left;
	struct node * right;
	struct node * parent;
} node;

void printNode(node * n);
node * getNode(int info);
void freeNode(node * n);