#ifndef _tree_h
#define _tree_h

#define NUMBERS 100

typedef struct node{
    int key;
    struct node *left, *right;
} node;

float Log2( float n );
node* FindNode(node **t, int key);

int level_cal(int min, int max);
void free_all(node *p);
int generateRandom();
int generateRandomNumSequence();
void ListInOrder();
void InsertNode(node **tp, int key);

#endif
