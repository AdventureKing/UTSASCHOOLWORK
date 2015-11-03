/*Brandon Snow hw05 .c file*/



#include <ctype.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#include "stack.h"

/*
   typedef double stackElementT;

   struct stackCDT {
   stackElementT *elements;
   int count;
   int size;
   };*/
static stackADT stack;


void Displaystack();


char* read_tag(FILE *fp);




int main(int nargs, char *argv[])
{
    FILE *fp;

    stack=NewStack();
    int j=0;
    
    char *htmlfile;
    char *tmp2;

    /*open file*/
    fp = fopen(argv[1], "r");

    if (fp == NULL)
    {
        fprintf(stderr, "Can't open!\n");
        exit(1);
    }
    /*read everything into a file and push tag */
	while((htmlfile = read_tag(fp)))
    {
        if(htmlfile[j] != '/' )
        {	
            Push(stack,htmlfile);
        }
        else {


            tmp2 = (char *) Pop(stack);
            if(strcmp(tmp2, htmlfile+1) == 0) 

                continue;
            else{ 
                printf("Your file wasn't correctly nested.These tags Do not match :%s %s \n",tmp2, htmlfile);
								
                break;
            }
        }

    }

    if(StackDepth(stack) == 0)
    {
        printf("Your file is correctly nested");

    }



		
    free(tmp2);
    printf("\n");
    
    FreeStack(stack);
		fclose(fp);
    return (0);
}


/*this function will read in */
char* read_tag(FILE *fp)
{
  
    int i=0;
    int ch;
    char tmp[1024];
    char *tag;
    int flag1=0, flag2=0;

    /*read tag in to string*/
   
    while((ch = fgetc(fp)) != EOF )
    {

        if(ch == '<')
        {
            flag1 = 1;

        }
        if(ch == '>')
        {
            if (tmp[i-1] != '/') {
                break;
            }
            tmp[i+2]='\0';
         
            i=0;flag1=0;flag2=0;
            continue;
        }

        if(flag1 == 1 ) /* && flag2 != 1) */
            tmp[i++]=ch;

        if(	tmp[i-1] == '<')
            i--;													

    }

    if(ch == EOF)
    {
        return NULL;
    }
    tmp[i] = '\0';
    tag = malloc(strlen(tmp) + 1);
    strcpy(tag,tmp);
		
    return(tag);	
    
}



/*return true if <title></title> match*/


/*print what i got*/
void Displaystack()
{
    int i, depth;
    depth = StackDepth(stack);
    printf("STACK: ");
    for(i=depth-1;i>=0;i--)
    {
        if(i<depth-1) printf(" ,");
        printf("%s",GetStackElement(stack,i));
    }
    printf("\n");

}

