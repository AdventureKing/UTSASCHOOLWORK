/*Brandon Snow started 8:51am tue april 15th*/

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include "buffer.h"


void ExecuteCommand(nodeT **t,char command,int key);
void HelpCommand(void);
int main()
{
    treeT *t = NULL;
    char command = '\0';
    int key= 0;
    int quit = 0;
    int flag = 1;
    printf("\nPlease enter a command and a number.If no number is needed enter 1.Here is a help command:\n");
    HelpCommand();
    /****create tree and call excute command***/
    while(1)
    {	
        printf("*");
        	fscanf(stdin,"%c %d",&command,&key);
					if( key == '\0' || command == '\0')
						{
									fscanf(stdin,"%c %d",&command,&key);
						}
        ExecuteCommand(&t,command,key);
        printf("\nCOMMAND = %c, KEY=%d\n",command,key);
				key =command = '\0';
				
    }


    return(0);

}

void ExecuteCommand(nodeT **t,char command,int key)
{
   /* printf("\nExcuteCommand Hit here\n");*/
    double i;
		int h;
		int count;

    switch (toupper(command)) {
        case 'I': InsertNode(t, key);break;
        case 'D': printf("\n");DeleteNode(t, key);printf("\n"); break;
        case 'F': printf("\n");FindNode(*t,key);printf("\n"); break;
        case 'O': printf("\n");ListInOrder(*t); printf("\n");break;
        case 'P': printf("\n");PreOrderWalk(*t);printf("\n"); break;
        case 'A': printf("\n");PostOrderWalk(*t);printf("\n"); break;
        case 'L': printf("\n");NodeLevelOrder(*t);printf("\n"); break;
        case 'S': printf("\n");i=add(*t);printf("Sum of tree is:%f\n",i); break;
        case 'N': printf("\n");printf("Min:");Min(*t); printf("\n");break;
        case 'X': printf("\n");printf("Max:");Max(*t); printf("\n");break;
        case 'T': printf("\n");h=height(*t); printf("Height:%d\n",h);break;
        case 'C': printf("\n");count=Count(*t); printf("Number of inputs is:%d\n",count);break;
        case 'H': printf("\n");HelpCommand(); printf("\n");break;
        case 'Q': free_all(*t);exit(0);
        default:  printf("Illegal command\n"); break;
    }
}

void HelpCommand(void)
{
    printf("Use the following commands to edit the buffer:\n");
    printf("  I...   Inserts inputted Integer(Must be positive)\n");
    printf("  D      Deletes inputted Integer(Must be positive)\n");
    printf("  F      Find a Character\n");
    printf("  O      List in order\n");
    printf("  P      List in preorder\n");
    printf("  A      List in postorder\n");
    printf("  l      List Level Order\n");
    printf("  S      Sum of list\n");
    printf("  N      Minimum of list\n");
    printf("  X      Maximum of list\n");
    printf("  T      Height of list\n");	
    printf("  C      Count of list\n");
    printf("  H      Generates a help message\n");
    printf("  Q      Quits the program\n");
}
