#include <stdio.h>
#include <stdlib.h> 
#include <math.h>
//aww yiss
#include "hash.h"





int main (int argc, char *argv[])
{
	int i;
	printf("hit here\n");
	printf("Assignment 7 Problem 1 by BRANDON SNOW\n");

	FILE *fp = fopen("data.txt", "r");
	KEYTYPE number;
 	int index;
	printf("hit here\n");
	for(i = 0; i < TABLESIZE; i++)
	{
		hashTable[i].r = '\0';
		hashTable[i].k = '\0';
	}
	
	
	while(!feof(fp))
	{
		
		fscanf(fp,"%d\n",&number);
		//printf("number: %d\n",number);
		RECTYPE number2 = number;
		index = search(number,number2);
		//printf("index: %d",index);
	}
	int j = 0;
	for(i = 0; i < 1000; i++)
	{
		
		printf("j: %d number:%d\n",j,hashTable[i].r);
		j++;
		
	}
	
	fclose(fp);
	return 0;
}
