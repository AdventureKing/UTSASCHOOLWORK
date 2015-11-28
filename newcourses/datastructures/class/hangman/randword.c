/*brandon snow tfv024*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
/* #include "genlib.h" */
#include "randword.h"


static char **list;
static int arraylength;




/*open up the list of words you want and link it back to the main*/
void  InitDictionary(char *file)		
{
    
    int i = 0;
	
		char temp[1024];
    /*open file*/
    FILE *fp = fopen(file, "r");
/*make sure stuff in file*/
    if (fp == 0)
    {
        fprintf(stderr, "Failed to open your file. Fail less bro!!!\n");
        exit(1);
    }
/*malloc list of pointers*/
		list = malloc(sizeof(double *) * 1024);
    
		while(fscanf(fp,"%s",temp) != EOF)
		{
				list[i]= malloc(strlen(temp)+1 * sizeof(char));
				/*trying to get rid of segfault*/
					list[i][strlen(temp)]='\0';
				strcpy(list[i],temp);
				
				i++;

		}
		
		arraylength=i;
		list[i]=NULL;
    fclose(fp);
                       
}
	
/*choose a random word*/
char *ChooseRandomWord(void)
{
		srand(time(NULL));
		int rword = rand() % arraylength;
		return list[rword];
}

void free_all(void)
{
	int i;
for(i=0;list[i];i++)
			{
			free(list[i]);
			}
		free(list);
}
