#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int *insertion_sort( int x[],int size);
void shiftLeft(int *masterArray, int min, int cursor, int x);
void shiftRight(int *masterArray, int max, int cursor, int x);
//Not quite right dont know whats going on.
int main(int nargs, char *argv[])
{
	FILE *fp;
	int number;
	int i=0;
	int j;
	int k=0;
	int *finalArray;
	
	printf("Assignment 5 Problem 3 by BRANDON SNOW\n");
	//open the file
	fp = fopen("data.txt", "r");
	if(fp == NULL) 
	{
		perror("cannot open file!");
		return(EXIT_FAILURE);
	}
	while(!feof(fp)) 
	{
		fscanf(fp,"%d\n",&number);
		
		i++;
	}
	fclose(fp);
	fp = fopen("data.txt", "r");

	//read file, 1 line per array intry
 
	int array[i];

	while(!feof(fp)) 
	{
		fscanf(fp,"%d\n",&number);
		array[k] = number;
	//	printf("Array[%d]: %d\n",k,array[k]);
		k++;
	}
	
	finalArray = insertion_sort(array, i);	
	printf("Sorted Array\n");
	for(j = 0 ; j < i; j++)
	{
		printf("array[%d]: %d\n", j + 1,finalArray[j]);
	}
	fclose(fp);
	
	free(finalArray);
	return(0);
}

int *insertion_sort( int x[],int size)
{
	int i;

	int *masterArray;

	masterArray = malloc(sizeof(int) * size);

	int init = size/2;

	int numsRight = 0;
	int numsLeft = 0;
	for(i = 0; i < size; i++)
	{
		*(masterArray + i) = -1;
	}

	*(masterArray + init) = x[0];
	for(i = 1; i < size ; i++)
	{
		int min = init - numsLeft;
		int max = init + numsRight;
	
		int cursor = min; //Start scan on min value
		
		/*Tracer Start
		int n = 0;
		for(n = min; n <= max; n++)
			printf("%d(%d) ", *(masterArray + n), n);
		printf("- cursor:%d\n", cursor);*/
		
		//If lower than min, decrease and continue
		if(x[i] < *(masterArray + cursor)) {
			cursor--;
		} else {
			//Otherwise increment cursor until on top of the last number that is less than x[i]
			while(x[i] > *(masterArray + cursor) && *(masterArray + cursor) != -1)
				cursor++;
		}
		
		if(*(masterArray + cursor) == -1)
		{
			*(masterArray + cursor) = x[i];
				if( cursor > init)
						numsRight++;
				else
						numsLeft++;
		}
		else
		{
			int diff = cursor - min; //Difference in position of cursor from initial
			int rightOffSet = 0;
			int leftOffSet = 0;
			leftOffSet = diff; //Diff represents number of values left of cursor
			rightOffSet = max - min - diff + 1; //Total count (max - min) minus numbers on left plus one for the number at "cursor", gives us numbers on right
			
			//printf("diff:%d, rightOffSet:%d, leftOffSet:%d\n",diff,rightOffSet,leftOffSet);
			
			//Check to see which side has more numbers on each side of the cursor
			if(rightOffSet > leftOffSet)
			{
				//Shift left, unless we've already reached lowerbound
					if(min > 0) {
						shiftLeft(masterArray, min, cursor, x[i]);
						numsLeft++;
					}	else {
						shiftRight(masterArray, max, cursor, x[i]);
						numsRight++;
					}
			}
			else
			{	
				//Shift right, unless we've already reached upperbound
					if(max <= size) {
						shiftRight(masterArray, max, cursor, x[i]);
						numsRight++;
					}	else {
						shiftLeft(masterArray, min, cursor, x[i]);
						numsLeft++;
					}
			}
		}
		
		/*Tracer End
		for(n = init - numsLeft; n <= init + numsRight; n++)
			printf("%d(%d) ", *(masterArray + n), n);
		printf("- cursor:%d\n\n", cursor);*/
		//if(i == 12) break;
	}		
		return(masterArray);
}

void shiftLeft(int *masterArray, int min, int cursor, int x) {
	int k = 0;
	//printf("left - min:%d, x:%d\n", min, x);	
	for(k = min; k <= cursor - 1; k++)
	{
		*(masterArray + (k - 1)) = *(masterArray + k);
	}

	*(masterArray + cursor - 1) = x;
}


void shiftRight(int *masterArray, int max, int cursor, int x) {
	int k = 0;
	//printf("right - max:%d, x:%d\n", max, x);
	for(k = max; k >= cursor; k--)
	{
		*(masterArray + (k + 1)) = *(masterArray + k);
	}
	
	*(masterArray + cursor) = x;
}











