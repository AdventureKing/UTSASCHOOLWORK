#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "stack.h"



int main(int nargs, char *argv[]) {
	FILE *fp;
	int number;

	int popNum;
	initStack();
	//open the file
	fp = fopen("data.txt", "r");
	if(fp == NULL) {
		perror("cannot open file!");
		return(EXIT_FAILURE);
	}
	
	//read file, 1 line per array intry 

	while(!feof(fp)) 
	{
		fscanf(fp,"%d\n",&number);
		if(number > -99999)
		{
			if(push(number) == TRUE)
		      {
				//printf("Push. Stack has %d elements. Pushed node is ", size());
				//printNode(stack);
			} else 
				printf("Error trying to push node!\n");

			
		}
		else if(number == -99999)
		{
				//printf("Hit here\n");
			popNum = pop(stack);
				printf("# elements after popping: %d, integer popped: %d\n",stack->count,popNum);
			
		}
		
	}
	
	//printf("hit here\n");
	free(stack->num);
	free(stack);
	//printf("hit here\n");
	fclose(fp);
	
	
	return(0);
}
