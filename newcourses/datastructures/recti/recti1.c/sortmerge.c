/*Brandon Snow*/
/*This program will take a array of numbers that is randomly generated and
 *sorts them and merges them in to a array C.*/
#include <stdio.h>
#include <stdlib.h>


void set_array_rand(int x[], int);
void SELECTION_SORT(int x[], int);
void MERGE(int a[], int ax, int b[], int bx , int c[], int cx );
void PRINT_ARRAY(char *name, int x[], int nx);


int main()
{
    /* 1.   Declare three integer arrays as follows */
    int   a[50], b[70], c[120];
    /* 2. implement a function set_array_rand(int x[], int n)
       and call it to generate the values in array a and b
       randomly. */
    set_array_rand(a, 50);
    set_array_rand(b, 70);
    /* 3. using the SELECTION_SORT(double x[], int n) function
       (see ch02.ppt), sort the elements in a and b arrays. */
    SELECTION_SORT(a, 50);
    SELECTION_SORT(b, 70);
    /* 4. implement a MERGE function and call it as follows to
       merge the values in arrays a and b into array c such that
       the values in c will be sorted after merging */
    MERGE(a, 50, b, 70, c, 120);
    /* 5. print the values in array c */
    PRINT_ARRAY("Array c", c, 120);
    return(0);
}




void set_array_rand(int x[], int n)
{
    int i;
    /* 1. randomly generate elements of x array, e.g, */
    for( i=0; i< n; i++)
    {
        x[i] = rand_int(30, 100);
    }
}
int rand_int(int a,int b)
{
    return rand()%(b-a+1) + a;
}


/* YOUR CODE */
void SELECTION_SORT(int x[], int n)
{
    /*TOOK OUT BRACKETS BETWEEN THE FOR AND THE IF WORKS GETS IT TO SELECT THE RIGHT VALUE*/

    int k,j,m;
    double temp;
    /*ALL HAVE TO START AT 0*/
    k=j=m=temp=0;

    for(k=0; k<=n-2; k++)
    {
        m=k;
        for(j=m+1; j<=n-1; j++)

            if(x[j] < x[m])
                /*if the value j is less than m then the values will swap*/
                m=j;

        temp=x[k];
        x[k] = x[m];
        x[m]=temp;
    } 
}






void MERGE(int a[], int na, int b[], int nb, int c[], int nc)
{
    /* merge the values in a and b into c while keeping the values
       sorted. For example, suppose we have the following two
       Arrays a = { 3, 7, 9, 12} and b = {4, 5, 10}
       When we merge these two arrays, we will get
       c = {3, 4, 5, 7, 9, 10, 12}
       */
    /*ac bc and cc are the count through the matrix initialzing to 0 gets it to work right*/
    int ac, bc ,cc;
    ac=bc=0;

    for(cc=0; cc<nc; cc++)
    {
        /*cc is c count and as long as the elements of c exist its gonna loop*/
        if(ac < na && bc < nb)
        {
            if(a[ac] <=  b[bc])
            {
                c[cc] = a[ac];
                ac++;
            }

            else if(a[ac] > b[bc])
            {
                c[cc] = b[bc];
                bc++;
            }
        }
/*the left over elements get thrown into c array*/
        else if(ac == na)
        {
            c[cc] = b[bc];
            bc++;
        }
        else if(bc == nb)
        {
            c[cc] = a[ac];
            ac++;
        }
    }
}

void PRINT_ARRAY(char *name, int x[], int nx)
{ 
    /*NEEDS TO LOOP THROUGH AND PRINT OUT THE ARRAY*/
    int i;
    printf(" %s:\n", name);
    for(i=0; i < nx; i++)
    {
        if( i== nx-1)
        {
            printf("%d,", x[i]);
        }
        else
        {
            printf("%i, ", x[i]);
        }

    }
            printf("\n");
}
