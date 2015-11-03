#ifndef _hash_h
#define _hash_h
#include <stdio.h>
#include <stdlib.h> 
#include <math.h>
//aww yiss

#define TABLESIZE 300

typedef int KEYTYPE;
typedef int RECTYPE;

typedef struct record{
	KEYTYPE k;
	RECTYPE r;
}recordTable;

recordTable hashTable[TABLESIZE];



double hash(KEYTYPE key);
double rehash(KEYTYPE key);
int search(KEYTYPE key, RECTYPE rec);
#endif

