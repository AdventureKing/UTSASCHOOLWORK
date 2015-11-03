#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void insertion(int *array, int key, int size, FILE *fpout, int index);

int main (int argc, char *argv[])
{
	printf("Assignment 6 Problem 1 by BRANDON SNOW\n");

	FILE *fpin = NULL;
	FILE *fpout = NULL;
	int i = 0;
	int j;
	int size = 10;
	int numbers = 0;
	

	//open the file
	fpin = fopen("data.txt", "r");
	fpout = fopen("output.txt","w");
	int *array = NULL;

	array = calloc(size,sizeof(int) );

	if(fpin == NULL)
   {
		perror("cannot open file!");
		return(EXIT_FAILURE);
	}	
	
	while(!feof(fpin))
	{
		if(i == size)
		{
			size = size + 5;
			array = realloc(array, size * sizeof(int));
			for(j = size -5; j < size; j++)
				*(array + j) = -9999;
			if(array == NULL)
				return printf("No Memory");
		}
		fscanf(fpin, "%d\n", &numbers);
		*(array + i) = numbers;
		insertion(array, numbers, size,fpout, i);
		i++;
	}
	for(i = 0; i < size; i++)
	{
		//printf("%d \n", array[i]);
	}
	fclose(fpin);
	fclose(fpout);
	free(array);
	return 0;
}

void insertion(int *array, int key, int size, FILE *fpout, int index)
{
	int i;
	int count = 0;
	if(*(array + 1) == '\0')
	{
		fprintf(fpout,"INSERTING <%d> at <%d>\n", key, index + 1);
		return;
	}
	for(i = 0; i < size ; i++)
	{
		
		if(*(array + i) == key && i != index)
		{
			//printf("SEARCH RESULT: <%d> is at <%d>\n", key, i + 1);
			fprintf(fpout,"SEARCH RESULT: <%d> is at <%d>\n", key, i + 1);
			count++;
			
				
		}
	}	
	
			if(size % 5 == 0)
				size = size - 5;
			else
				size = size - 10;
			if(count == 0)
			//printf("INSERTING <%d> at <%d>\n", key, index + 1);
			fprintf(fpout,"INSERTING <%d> at <%d>\n", key, index + 1);
			
	return;
}
