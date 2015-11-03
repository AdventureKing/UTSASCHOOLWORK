#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>


int *sort_array(int **array_groups, int i);



int main(int nargs, char *argv[]) {
	FILE *fp;
	int number;
	int i=0;
	int p,q;
	int total=0;
	int index = 0;
	
	printf("Assignment 5 Problem 2 by BRANDON SNOW\n");
	//open the file
	fp = fopen("data.txt", "r");
	if(fp == NULL) {
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
 
	int size = (int) sqrt(i);
		
	int **array_groups = malloc(sizeof(int *) * size);
	//printf("Big peen\n");

	

	while(!feof(fp)) 
	{
		//printf("wide peen: %d \n", index);
		if( total % size == 0)
		{
			*(array_groups + index) = malloc(sizeof(int) * size);
			index++;
		}
		//printf("wide peen: %d \n", index);
		//printf("small peen: %d \n", total);

		fscanf(fp,"%d\n",&number);

		
		*(*(array_groups + index - 1) +(total % size)) = (int) number;
		//printf("Med peen\n");
		total++;
		
	}
	
	int *final = sort_array(array_groups,i);

	for(p = 0; p < i; p++)
	{
		printf("Array[%d]: %d\n", p , *(final + i - p - 1));
	}
	
	/*
	for(j=0; j< size ; j++)
		for(h=0; h< size ; h++)
			printf("%d: %d\n",j * i/size + h ,array_groups[j % size][h % size]);
*/
	
	
/*	
	for(j=0; j<i; j++)
	{
		printf("%d: %d\n",j + 1,final[j]);
	}
*/
	free(final);
	for(q = 0 ; q < size; q++)
	{
		free(*(array_groups +q));
	}
	
	fclose(fp);
	
	free(array_groups);
	return 0;
}

//largest index in size 10 array 
int getLargestInt(int *array, int size)
{
	int i;
	int max = 0;
 	
	for(i = 0; i < size; i++)
	{
		if(*(array + max) < *(array + i))
			max = i;
	}
	/*i = *(array + max);
	if(isArray == 1) 
		*(array + max) = 0;
	 */
	return max;
}

//get me largest elements
//pull largest out and put at index 0
//continue until file is done
int *sort_array(int **array_groups, int i)
{
	int *final; 
	int p,v;
	int size = sqrt(i);
	final = malloc(sizeof(int) * i);
	int *auxArray;
	auxArray = malloc(sizeof(int) * size);
	//largest number
	for(v = 0; v < i; v++)
	{
		//auxarray is place holder for largest elements this 
		// loop fills them
		// id1 is the array that catalogs the index of the largest of each group
		
		int id1[size];	
		for(p = 0; p < size; p++)
		{
			id1[p] = getLargestInt(*(array_groups + p), size);
			*(auxArray + p)= *(*(array_groups + p) + id1[p]);
			
		}
		//id2 gets largest of largest
		int id2 = getLargestInt(auxArray,size);
		//put into my final
		*(final + v) = *(auxArray + id2);
		//set the largest current number to 0 so i dont get fucking 997 every fucking time
		*(*(array_groups + id2) + id1[id2]) = 0; 
		
		
	}
	free(auxArray);
	//return array 
	return(final);
}






















