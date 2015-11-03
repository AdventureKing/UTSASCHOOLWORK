
#define FALSE 0
#define TRUE 1
#define STACKSIZE 10
typedef struct nodeT{
		
		int *num;
		int count;
		int len;
}node;
/*my pointer to my array*/
node *stack;
/*my spot in the array count-1*/
int top;

node *growStack();
void initStack();
int full();
int empty();
int push(int num);
int pop();
int size();
void printNode(node * n);

