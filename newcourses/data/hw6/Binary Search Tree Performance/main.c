#include <stdio.h>
#include <string.h>
#include	<stdlib.h>
#include <math.h>
#include <time.h>
#include "tree.h"



int main (int argc, char *argv[])
{
	
	int size = 10;
	int y=0;
	//int count_final=0;
	
	srand(time(NULL));
	printf("n              Average # comparisons/visits in my search           log2(n)");
	for(y=0;y < size; y++)
		{
		generateRandomNumSequence();
		}
		
	
		printf("\n");
	
	
	return 0;
}
