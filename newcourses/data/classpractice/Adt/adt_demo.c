/*
Problem 1: ADT

Value Definition

abstract typedef <int small, int big> ORDEREDPAIR
	Conditions
		small <= big

Operators

abstract NUMBERS makeOrderedPair(s, b)
int s, b
precondition
	s <= b
postcondition
	makeOrderedPair.small = s
	makeOrderedPair.big = b


abstract int getSumInRange(n)
NUMBERS n
postcondition
	for all ints i between s and b inclusive
		getSumInRange = getSumInRange + i

*/


//C implementation
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct orderedPair {
	int small;
	int big;
} orderedPair;
orderedPair * pairs;

orderedPair * makeOrderedPair(int s, int b) {
	orderedPair * ret = malloc(sizeof(orderedPair));
	if(ret == NULL)
		return ret;
	ret->small = s;
	ret->big = b;
	return ret;
}

int getSumInRange(orderedPair * p) {
	int i, sum = 0;
	for(i = p->small; i <= p->big; i++) {
		sum += i;
	}
	return sum;
}

void printRecords(int n) {
	int i;
	printf("Printing pairs:\n");
	for(i = 0; i < n; i++) {
		orderedPair * p = pairs + i;
		printf("Pair %d : small %d  big %d\n", (i + 1), p->small, p->big);
	}
}

int main(int nargs, char *args[]) {
	FILE *fp;
	int numRecords = 0, i = 0, n1 = 0, n2 = 0;
	
	printf("Demo 1\n");
	
	//open the file
	fp = fopen("data1.txt", "r");
	if(fp == NULL) {
		printf("Error opening file!");
		return(EXIT_FAILURE);
	}
	
	//count # records in the file
	while(!feof(fp)) {
		fscanf(fp, "%d\n", &n1);
		fscanf(fp, "%d\n", &n2);
		numRecords++;//1 record is 2 fields on separate line
	}
	printf("# of records: %d\n", numRecords);

	//allocate space for all pairs
	pairs = (orderedPair *) malloc(sizeof(orderedPair) * numRecords);
	
	//close and reopen the file to read in each record
	fclose(fp);
	fp = fopen("data1.txt", "r");

	//read in each record and assign the student record at index i using ADT operations
	i = 0;
	while(!feof(fp)) {
		//read the record
		fscanf(fp, "%d\n", &n1);
		fscanf(fp, "%d\n", &n2);
		
		//satisfy precondition and create the pair record
		orderedPair * p = NULL;
		if(n1 > n2) {
			p = makeOrderedPair(n2, n1);
		} else {
			p = makeOrderedPair(n1, n2);
		}
		//check the pointer
		if(p == NULL) {
			printf("Error allocating orderedPair struct!");
			fclose(fp);
			return(EXIT_FAILURE);
		}
		//copy the record in the pairs array
		*(pairs + i) = *p;

		//since struct assignment copies values, we now need to free p
		free(p);
		i++;
	}

	//check if we read everything in
	printRecords(numRecords);
	
	//print sum of ints in each pair's range
	for(i = 0; i < numRecords; i++) {
		printf("Sum of ints in range for pair %d : %d\n", (i + 1), getSumInRange(pairs + i));
	}
	
	//release resources
	free(pairs);
	fclose(fp);
	return(EXIT_SUCCESS);
}
