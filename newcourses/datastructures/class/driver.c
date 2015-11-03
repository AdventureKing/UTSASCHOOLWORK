/*brandon snow*/
#include <stdio.h>
#include <ctype.h>
#include "set.h"


int main()
{
					
					setADT a=NewSet();
					setADT b=NewSet();
					setADT V;
					int tmp;
					setElementT length;
					int lengthB;

				printf("Welcome today you will be able to enter 2 sets(When you are done please enter -1 to get out of that set)\n");

				printf("Set A:");
				scanf("%d",&tmp);

				while(tmp != -1)
				{	
				  length = setInsertElementSorted(a,tmp);
					printf("count:%d\n",length);
					scanf("%d",&tmp);
				}
				setPrint(a,"a");
				
				printf("\nSet B:");
				scanf("%d",&tmp);	
	
				while(tmp != -1)
				{
					lengthB=setInsertElementSorted(b,tmp);
					printf("count:%d\n",lengthB);
					scanf("%d",&tmp);
				}
					
				setPrint(b,"b");
				V=setDifference(a,b);
				
					printf("\n");
			printf("Please enter a command (U)nion,(D)ifference,(I)ntersection,(Q)uit:\n");
         
										
           

				return 0;
}





