/*Brandon Snow tfv024*/
/*using pointer arithmetic, re-write the string functions discussed in class
 * a function that counts the number of characters in a string s12
 * a function that reverses the characters in a string s1
 * a function that compares 2 strings s1 and s2 using lexicographic order, returns 0 if they are the same, -1 if s1 comes before s2 or 1 is s1 comes after s2
 * a function that searches string s1 in s2 and returns 1 if s1 is in s2 ortherwise returns 0*/


#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

char count(char *s);
void reverse(char *s);
int compare(char *s1, char *s2);
int search();

/*call all my functions and get then running COOLRUNNINGS*/


int main()
{

    printf("\nThis program will now excute\n");
    char S1[100];
    char S2[100];
    printf("Please enter 100 numbers for S1:\n");
    fgets(S1,100,stdin);
    printf("Please enter 100 numbers for S2:\n");
    fgets(S2,100,stdin);
    printf("\nThis is the count of s1:%d\n",count(S1));
    printf("\nThis is the count of S2:%d\n",count(S2));
    reverse(S1);
    printf("S1 reversed is:%s\n",S1);
    reverse(S2);
    printf("S2 reversed is:%s\n",S2 );
    reverse(S1);
    reverse(S2);
    printf("S1:%s\nS2:%s\nWhen you compare the two if you return 0 then the strings were equal,if you return -1 S1 comes before S2,if you return 1 S2 comes before S1) : %d\n",S1,S2, compare(S1,S2));
    printf("Is S1 in S2?(If you return a 1 it is , 0 it isn't):%d\n",search(S1,S2));

    return 0;
}

/*count the ammount of characters in a array*/
char count(char x[])
{

    int i=0;
    while(*(x+i) != '\0')
    {
        i=i+1;

    }

    return(i-1);
    printf("\n");
    return 0;
}
/*reverse the two strings for me*/

void reverse(char x[])
{
    int size,position;
    int beg=0;
    char temp;
    size=count(x);
    for(beg=0, position=size-1; beg<position;beg++,position--)
    {
        temp=*(x+beg);
        *(x+beg)=*(x+position);
        *(x+position)=temp;


    }


    printf("\n");

}
/*compare the two strings*/
int compare(char S1[], char S2[])
{
    int i=0;

    while(*(S1+i) == *(S2+i)&& *(S1+i) !='\0' && *(S2+i) !='\0') 
    {

        i=i+1;

    }
    if(tolower(*(S1+i))<tolower(*(S2+i)))
    {
        return -1;
    }
    if(tolower(*(S2+i))<tolower(*(S1+i)))
    {
        return 1;
    }  
    if(S1==S2)
    {
        return 0;
    }

    printf("\n");

    return 0;

}
/*takes the count function counts then finds where i=j and counts those hits if nothing hits return 0 if everything hits then return 1*/
/*j counts the number of hits in a row*/
int search(char a[], char b[])
{
    int i=0,j=0, ca, cb;
    ca=count(a);
    cb=count(b);
    if(ca>cb)
    {
        return 0;
    }
    for(i=0; i<cb ; i++)
    {
        if(tolower(*(a+j)) == tolower(*(b+i)) && j<ca)
        {
        j++;
        }
            else if(tolower(*(a+j)) != tolower(*(b+i)) && j>0 && j<ca)
            {
            j=0;
            i--;
            }
    }
    if(j >= ca)
        return 1;
    else
        return 0;
}


