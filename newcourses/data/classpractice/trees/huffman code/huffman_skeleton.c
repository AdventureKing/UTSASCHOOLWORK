#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "node.h"
#include "queue.h"

#define NUMSYMBOLS 26

extern node * queue;

int frequency[NUMSYMBOLS];//frequency of each symbol in symbol string
node * position[NUMSYMBOLS];//link to symbol leaf node in huffman tree
node * root;//huffman tree root. need for when code tree climbing and tree deletion (and silly traversal)
int code[NUMSYMBOLS][NUMSYMBOLS];//huffman code for symbol IN REVERSE

//these are defined below main to get them out of the way
void calcFrequency(char * s);
void printFrequency();
void buildQueue();
void freePostOrder(node * p);
void freeTree();

//START HUFFMAN FUNCTIONS

void buildHuffmanTree() {
	//if pq is empty then nothing to do
		if(empty()){
			return;
		}
	//while queue has more than 1 node
	//NOTE: I wrote a size() function for the pq to make this easy
	while(size() > 1)
	{
	//grab 2 smallest nodes p1 and p2 from pq
		node *p1 = pqmindelete();
		node *p2 = pqmindelete();
	//create new node that is sum of p1 and p2 frequencies
	//create new parent
		node *p = getNode(p1->info + p2->info);
	//set p to be parent of p1 and p2
		p1->parent =p;
		p2 -> parent =p;
	//setleft p1
	p1->left = p1;
	//setright p2
	p2->right = p2;
	//put p in pq
	pqinsert(p);
	//last node in pq is the root so get it from pq and assign to root
	}
	root = pqmindelete();
}

int isleft(node * p) {
			if(p == NULL || p->parent == NULL)
				return NULL;
	//node p is left node if == parents left child
		if(p->parent == p->left)
			return TRUE;
	return FALSE;
}

void calcCodes() {
	int i, j;
	//for each symbol i, start at position and climb the tree recording its code
	//the code will be an int array in the code table for that element i 
	//NOTE: code is IN REVERSE (easier this way)

	//init code array for element i as -1s

	//get the node from the position table

	//initialize a code index for keeping track of which huffman digit is currently being recorded

	//climb the tree

	//build code for the symbol. if child is left child, record a 0, else 1

	//update control variable to parent

	//increment the code index

	//print the code for symbol i + 'a' by traversing it in reverse

}

void printHuffmanCode(char * s) {
	printf("TODO: part of assignment 4\n");
}

int main(int nargs, char * args[]) {	
	if(nargs < 2) {
		fprintf(stderr, "USAGE: %s <symbol_string>\n", args[0]);
		exit(EXIT_FAILURE); 
	}
	//symbol index is symbol char val - 'a'
	//ASSUMES chars are all lowercase
	//determine frequency of symbols
	calcFrequency(args[1]);
	printFrequency();
	
	//build the priority q for each char/frequency in frequency array
	initQueue();
	buildQueue();
	
	//priority queue is done so build the huffman tree
	root = NULL;
	buildHuffmanTree();
	if(root == NULL) {
		freeQueue();//should already be empty, but just in case
		return(EXIT_FAILURE);
	}
	
	calcCodes();
	
	if(nargs > 2) 
		printHuffmanCode(args[2]);
	else
		printHuffmanCode(args[1]);
	
	freeTree();
	return(EXIT_SUCCESS);
}


/****************************************
 ********* SUPPORT FUNCTIONS ************
****************************************/

void calcFrequency(char * s) {
	int i, j, m, c;
	m = strlen(s);
	//for each symbol a to z, look through all of s and count each occurrence
	for(i = 0; i < NUMSYMBOLS; i++) {
		c = 0;
		for(j = 0; j < m; j++) {
			if(*(s + j) - 'a' == i)
				c++;
		}
		frequency[i] = c;
	}
}

void printFrequency() {
	int i;
	printf("Frequency array is:\n");
	for(i = 0; i < NUMSYMBOLS; i++) {
		if(frequency[i] > 0)
			printf("\tChar %c is %d\n", i + 'a', frequency[i]);
	}
}

void buildQueue() {
	int i;
	for(i = 0; i < NUMSYMBOLS; i++) {
		if(frequency[i] > 0) {
			node * n = getNode(frequency[i]);
			position[i] = n;
			pqinsert(n);
		}
	}
}

//free tree nodes by traversing in postorder
void freePostOrder(node * p) {
	if(p == NULL)
		return;
	freePostOrder(p->left);
	freePostOrder(p->right);
	freeNode(p);
}

void freeTree() {
	//traverse postorder
	freePostOrder(root);
}

