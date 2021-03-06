#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "queue.h"

extern int insert_count;

int main(int nargs, char *args[]) {
	
	printf("Assignment 3 Problem 1 by Brandon Snow\n");
	FILE *fp;
	int num_lines = 0;
	int number;
	char op[256];
	char cur_line[256];
	//initilize the linked list
	//initQueue();
	fp = fopen("data.txt","r");
	
		if(fp == NULL)
		{
			printf("Error opening file!");
				exit(EXIT_FAILURE);
		}

		while(!feof(fp))
		{
			if(fgets(cur_line,256,fp) != NULL)
			{
				//printf("cur_line:%s\n",cur_line);
				sscanf(cur_line,"%s %d", op, &number);
			//printf("string read in to op:%s\n int read into number:%d\n",op,number);
			
			if(strcmp(op,"INSERT") == 0)
				{
						//printf("INSERT  \n");
						//insert
							node *n=NULL;
							insertNode(n,number);
						//insert_count++;
							
							
				}

			else if(strcmp(op,"REMOVE") == 0)
				{
						node *q=NULL;
						q = deleteNode();
						printf("Remove off top\n");
						//remove 
						free(q);
						
				}
				num_lines++;
				printQueue();
				
				
				//printf("insert count:%d\n",insert_count);
			}	
	}
			printf("Total Checks:%d\n",total_check_count);
			//printf("number of lines:%d\n",num_lines);
	freeQueue();
	fclose(fp);
	return(EXIT_SUCCESS);
}
