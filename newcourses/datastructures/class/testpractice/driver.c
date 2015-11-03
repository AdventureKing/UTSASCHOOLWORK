#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include "ran.h"
int main()
{
	int i;
	int j[10];
		srand(time(NULL));
		for(i=0;i<10;i++)
		{	
		j[i]=RandomNumber(1000,0);
		printf("%d\n",j[i]);
		}
		
		return(0);
}
