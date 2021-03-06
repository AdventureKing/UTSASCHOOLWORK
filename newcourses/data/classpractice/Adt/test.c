#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct orderedPair{
    int small;
    int big;
}orderedPair;

orderedPair *pairs;
orderedPair *makeOrderedPair(int s,int b)
{
			orderedPair *ret=(orderedPair *)malloc(sizeof(orderedPair));
			if(ret == NULL)
			{
					return NULL;
			}
			ret->small= s;
			ret->big=b;
			return ret;
}
void printPairs(int n){
	int i;
		printf("Printing Pairs:\n");
		for(i=0; i<n;i++)
		{
			orderedPair *p= pairs + i;
			printf("pair %d: small %d big %d\n", i+1, p->small, p->big);
		}
}
int main (int nargs, char *args[])
{
    FILE *fp;
    int i=0, n1=0,n2=0 ,numRecords = 0;

    fp=fopen("data1.txt","r");
    while(!feof(fp)){
        fscanf(fp,"d\n",&n1);
        i++;
    }
    numRecords = i/2;
    printf("# of records:%d\n",numRecords);
    fclose(fp);

    pairs=(orderedPair *) malloc(sizeof(orderedPair) * numRecords);
    while(!feof(fp))
    {
        fscanf(fp,"%d", &n1);
        fscanf(fp,"%d", &n2);
       
        orderedPair *temp = NULL;
        if(n2 < n1)
        {
					temp=makeOrderedPair(n2,n1);
        }
        else
        {
						temp=makeOrderedPair(n1,n2);
        }
				if(temp == NULL)
				{
						perror("cannot malloc temp!");
						fclose(fp);
						free(pairs);
						return(EXIT_FAILURE);
				}
				*(pairs +i) = *temp;
					free(temp);
					i++;
    }
			printPairs(numRecords);
    fclose(fp);

    return(0);
}
