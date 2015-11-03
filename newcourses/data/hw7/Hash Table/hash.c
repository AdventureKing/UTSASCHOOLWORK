#include <stdio.h>
#include <stdlib.h> 
#include <math.h>
#include "hash.h"

int search(KEYTYPE key, RECTYPE rec)
{	
	//printf("hit search");
	RECTYPE i;
	int j = 0;
	i = hash(key);
	//printf("i: %d", i);
	while(hashTable[i].k != key && hashTable[i].k != '\0')
	{
	
		printf("SEARCH FOR %d REHASHES %d\n", i, j);
		i = rehash(i);
		j++;
		
	}
	if(hashTable[i].k == '\0')
	{
		hashTable[i].k = key;
		hashTable[i].r = rec;
		
	}
	return i;
}
double hash(KEYTYPE key)
{
	RECTYPE rec;

	rec = key % 300;

	return rec;
}
double rehash(KEYTYPE key)
{
	RECTYPE rec;
	
	rec = (key + 1) % 300;
	
	return rec;
}
