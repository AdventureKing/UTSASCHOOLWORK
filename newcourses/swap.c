#include <stdio.h>
#include <stdlib.h>

void swapVal(int *, int *);

int  main(){
int v1=10 , v2=25;
int *p1=&v1 , *p2=&v2;

swapVal(p1,p2);

printf("%d, %d\n", *p1, *p2);
return(0);
}

void swapVal( int *p1, int *p2){

int temp;

temp = *p1;
*p1=*p2;
*p2=temp;

}
