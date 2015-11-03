/*BRANDON Snow*/


#include <stdio.h>
#include <stdlib.h>
int sum();
int product();
int divi();

int main(int nargs, char *argv[])
{
    printf("\n%s\n",argv[2]);
        if(*argv[2] == '+')
        {
            sum(nargs, argv);
        }
        if(*argv[2] == '*')
        {
            product(nargs,argv);
        }
        if(*argv[2] == '/')
        {
            divi(nargs,argv);
        }
    return 0;


}
int product( int nargs, char *argv[])
{
    printf("\nhit\n");
    int i, d=1;
    for(i=1; i<nargs; i++)
    {
        printf("\n argv[%d]: %s nargs:%d d:%d\n",i, argv[i],nargs,d);
        d=atoi(argv[1])* atoi(argv[3]);
    }

    printf("\nproduct:%i\n",d);
    return(0);
}

int sum(int nargs, char *argv[])
{
    int i, c=0;
    for(i=1;i<nargs;i++)
    {
        printf("\n argv[%d]: %s nargs:%d C:%d\n",i,argv[i],nargs,c);
        c+=atoi(argv[i]);
    }

    printf("\nsum:%i\n",c);
    return(0);
}
int divi( int nargs, char *argv[])
{
    printf("\nHit.");
    int i;
   double d=1;
    for(i=1; i<nargs;i++)
    {
    
        printf("\n argv[%d]: %s nargs:%d d:%g", i,argv[i],nargs,d);
         d=  (atoi(argv[1]) /  atoi(argv[3]));
    }
    printf("\nquotient:%g\n",d);
    return 0;
}
