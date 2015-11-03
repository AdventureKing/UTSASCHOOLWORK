#include <ctype.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "genlib.h"
#include "set.h"
#define InitialStackSize 100

struct setCDT {
    setElementT elements[InitialStackSize];
		setElementT count;
		int size;
		
};



setADT NewSet(void)
{
    setADT stack;

    stack = New(setADT);
    
    stack->count = 0;
    stack->size = InitialStackSize;
    return (stack);
}

setADT setInsertElementSorted(setADT S,setElementT E)
{
			int k;
			double temp;	
			int len;
			
			S->elements[S->count++]=E;
			len =S->count;
				
			for(k=0; k<=len;k++)
			{
					int j;
					for(j=S->count-1;j>0;j--)
						{
								
								if(S->elements[j-1]>S->elements[j])
									{
								temp=S->elements[j-1];
								S->elements[j-1]=S->elements[j];
								S->elements[j]=temp;
									}
					  }
			}

		return(S->count);
}

void setPrint(setADT S, char *name)
{
			int i;
				printf("{");
	for(i=0;i<S->count;i++)
					{
							if(i==S->count-1)
							{
								printf("%d}",S->elements[i]);
								continue;
							}
						printf("%d,",S->elements[i]);
						
					}
						
}
setADT setDifference(setADT A, setADT B)
{
				int i;
				int j;
				int count;
				setADT C = NewSet();
		for(i=0;i<A->count;i++)
			{
				j=0;
				count=0;
				while(j<B->count)
				{
				if(A->elements[i] == B->elements[j])
					{
									count++;
									j++;
					}
				if(count == 0)
					{
					setInsertElementSorted(C,A->elements[i]);
					}
				}
		 }
			return(C);
}
/*
setADT setUnion(setADT a,setADT b)
{
		int i,m,k,temp,j;

		int c[InitialStackSize];
			
		for(i=0;i<100;i++)
		{
			c[i]=a[i];
			
		}
		




}*/
