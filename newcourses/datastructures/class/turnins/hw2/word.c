/*Brandon Snow tfv024
 * this program will take a command line argument and search through a word
 * grid and find where those words appear in the grid. It will then print out 
 * where the word starts on the grid*/
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

char count();
#define ROW 3
#define COL 4
int main(int nargs , char *argv[])
{


/*my counters for words found that match*/
    int found_r=0,found_c=0,found_d=0;
    /*rows,columns,current word read in,counter of letter in current word*/
    int i,j,c,z;
    /*first [row][column] of letter in word that had complete hit in array*/
    
    int ix,ij;
    /*given array*/
    char g[ROW][COL] = {
        {'a','b','c','d'},
        {'d','c','b','a'},
        {'x','y','z','d'}};

    /*loop till end of words on command line*/
    for(c=1;c<nargs;c++)
    {
    if(count(argv[c]) <= 1)
        {
            printf("\nPlease be sure to enter a string not a single letter!The letter [%s] can't be checked\n",argv[c]);
                break;
        }
    /*check for if you didn't enter a string but just one word*/
    printf("\nYour word [%s] will now be checked against the word grid\n",argv[c]);
    found_r=0,found_c=0,found_d=0;
 /*just a place holder for current [i][j]*/
        ix=-1;
        ij=-1;
        /*traverse array*/
        for(i=0;i<ROW;i++)
        {
/*if ix or ij are ++ exit loops and print for me*/
        if(ix != -1 && ij != -1)
            break;
            for(j=0;j<COL;j++)
            {
           
            if(ix != -1 && ij != -1)
                break;
                /*z is my current count of input*/

                z=0;
                /* if my point in my array == the first letter of input word then increment z to check second letter*/
                if(tolower(g[i][j]) == tolower(argv[c][z]))
                {
                
                    z=1;
                    /*as long as their is letter */
                    while(z>0 && z<count(argv[c]))
                    {

                        /*this one goes down col*/

                        if(i+z < ROW && tolower(argv[c][z]) ==tolower(g[i+z][j]))
                        {

                            found_c++;
                            
                        }
                        /*this one down rows*/
                        if(j+z < COL && tolower(argv[c][z]) ==tolower(g[i][j+z]))
                        {
                           
                            found_r++;
                            
                        }

                        /*checks first hit then checks the diagonals and then all diagonals*/
                        if( i+z < ROW && j+z < COL && tolower(argv[c][z]) == tolower(g[i+z][j+z]))
                        {

                            found_d++;
                            
                            
                        }
                         
 
                            /*if found hits is more than the total count of that word than that means the whole word was found*/
                        if(found_r >=count(argv[c])-1 || found_c >=count(argv[c])-1 || found_d >=count(argv[c])-1)
                        {
                        /*these get incremented to tell me where the start of the word is so if its at [0][0] then ix will go to 0 and ij will go to 0. Also if they say that it was found at [4][1] ix will get the 4 and ij will get the 1.*/
                        ix=i;
                        ij=j;

                        
                        }
                        z++;
                    }
                }
            }
        }
/*print statments*/
        if(found_r >=count(*(argv+c))-1)
        {

            printf("\nYour word [%s] was found in a row starting at[%d][%d]\n",argv[c],ix,ij);
        }
        else if(found_c>=count(*(argv+c))-1 )
        {

            printf("\nYour word [%s] was found on a column starting at[%d][%d]\n",argv[c],ix,ij);
        }
        else if(found_d >=count(*(argv+c))-1)
        {

            printf("\nYour word [%s] was found on a diagonal starting at[%d][%d]\n",*(argv+c),ix,ij);
        }
        else
        {

            printf("\nYour word [%s] was not found in the grid\n", *(argv+c));
        }
    }
    return 0;
}


/*count function to count total number of letters in input*/
char count(char x[])
{

    int i=0;
    while(*(x+i) != '\0')
    {
        i=i+1;

    }

    return(i);
    printf("\n");
    return 0;
}
