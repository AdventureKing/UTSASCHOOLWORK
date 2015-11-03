#ifndef _tree_h
#define _tree_h

#define NUMBERS 100

typedef struct node{
    int key;
    struct node *left, *right;
} node;
int ary[NUMBERS];
int level_cal(int min, int max);
void free_all(node *p);
int generateRandom();
int generateRandomNumSequence();
void ListInOrder();
void InsertNode(node **tp, int key);
int Max(node *t);
int Min(node *t);
int leaf(node *t);
#endif
