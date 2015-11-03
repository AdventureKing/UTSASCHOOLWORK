/*Brandon Snow tfv024
 *Write/call a function that asks user to enter the values for a Sudoku puzzle
 *or solution and saves these values into the data structure you declared in
 *the previous item. User is expected to enter a number between 0 and 9 for
 *each cell in case of a puzzle. Zero is for empty cells, 1-9 are for other
 *cells. In case of a solution, user is expected to enter only the values
 *between 1 and 9. Your program should reject invalid values.*/




#include <stdio.h>
#include <stdlib.h>


void fillPuzzle();/*populate puzzle with solution or 0*/
void test_Puzzle();/*test if correct*/
void puzzleCorrect();/*check if puzzle is correct main call*/
void print_m();/*print function for 2d array*/
void print_n();/*print function for testing 1d array*/
char find_rcg();/*find grid rows and columns call*/
char find_g();/*find the grid and test it*/
char find_values();/*finds and replaces 0*/
void puzzleAnswers();/*main function that traces the answers*/
int checkr();/*checks to see if rows are correct*/
int checkc();/*checks to see if columns are correct*/
int checkG();/*checks to see if grid is correct*/


int main()

{

    int input;
    char  Grid[9][9];


    printf("Press: \n 1 - input data for Sudoku\n 2- check your solution against a known solution\n");

    scanf("%d",&input);

    if(input == 1)

    {
        fillPuzzle(Grid); 

        puzzleAnswers(Grid);


    }

    else if(input == 2)

    {
        fillPuzzle(Grid);    

        puzzleCorrect(Grid);
    }

    else 
    {
        printf("\nPlease enter a 1 or a 2. This program will now exit!! Rerun and enter a 1 or 2 as your selection.\n");

    }

    return 0;
}


/*FILLS MY ARRAY WITH A GIVEN GRID*/

void fillPuzzle(char Grid[][9]) /*have user input numbers 1-9*/
{

    int i,j,k=0;
    int tmp;
    /*Reads in chars*/
    printf("Please enter a number between 1 & 9:");      
    for(i = 0; i<9; i++)  
    {
        for(j=0; j<9; j++)                 
        {
            scanf("%d", &tmp);
            Grid[i][j] = tmp;

            k++;
        }
        if(k== 81)
            printf("\n\n 81 Numbers have been accepted!\n");
    }
    /*check to make sure they enter a 0-9*/
    for(i=0;i<9;i++)
    {
        for(j=0;j<9;j++)
        {
            if(Grid[i][j] > 9 || Grid[i][j] < 0)
            {
                printf("Invalid!!- You entered a number not in the range of the puzzle please began again. ");
                fillPuzzle(Grid);
            }
            if(Grid[i][j] == 0)
            {
                Grid[i][j]=0;
            }
        }
    }

    print_m(Grid);
}


/*CHECK IF A PUZZLE IS CORRECT CALLS ALL CHECK*/

void puzzleCorrect(char x[][9])
{
    int i, j;
    int valid, valid1, valid2;
    for(i=0; i<9; i++)
    {
        for(j=0; j<9; j++)
        {

            valid2=checkr(x,i,j);
            valid1=checkc(x,i,j);
            valid=checkG(x,i,j);
            if(valid ==0)
            {
                printf("Invalid: Your grids are wrong. Please try another solution\n");
                return 0;   
            }
            if(valid1 ==0)
            {

                printf("Invalid: Your columns are wrong. Please try another solution\n");
                return 0;
            }
            if(valid2 ==0)
            {

                printf("Invalid: Your rows are wrong. Please try another solution\n");
                return 0;
            }

        }

    }


}


/*CHECK ROWS*/
int checkr( char x[][9], int i , int j)
{
    char count[10]={0};
    int k=0,v;


    count[x[i][j]]=1;
    /*check rows*/

    for(k=0;k<9;k++)
    {
        if(k!=j)
        {

            count[x[i][k]]++;
        }
    }

    for(v=1; v<10; v++)
    {
        if(count[v]!=1)
        {

            return 0;
        }


    }
    return 1;
}

/*CHECK COLUMNS*/


int checkc(char x[][9], int i, int j)
{
    int b,v;
    char count[10]={0};
    count[x[i][j]]=1;
    /*check col*/

    for(b=0;b<9;b++)
    {
        if(b!=i)
        {
            count[x[b][j]]++;
        }
    }

    for(v=1; v<10; v++)
    {
        if(count[v]!=1)
        {


            return 0;
        }

    }
    return 1;
}

/*CHECK GRIDS*/

int checkG( char x[][9] , int i , int j)
{
    /*check grid of 3x3*/
    int z,y,v,n,m;
    char count[10]={0};
    count[x[i][j]]=0;



    z=3*(i/3);
    y=3*(j/3);
    for(n=z; n<z+3; n++)
    {
        for(m=y;m<y+3;m++)
        {
            count[x[n][m]]++;                   
        }
    }

    for(v=1; v<10; v++)
    {
        if(count[v]!=1)
        {

            return 0;
        }

    }
    return 1;
}



/*WILL FILL A 0 WITH A CORRECT POSSIBLE ANSWER*/

void puzzleAnswers(char x[][9])
{
    int i,j;
    printf("test "); 
    for(i=0;i<9;i++)
    { 
       printf("test ");
        for(j=0;j<9;j++)
        {
            printf("test ");
            if(x[i][j]== 0)
            {
                find_rcg(i,j,x);
            }
            printf("test ");
        }
    
    }

}



/*THIS PULLS THE 3 ARRAYS ROWS,COLUMNS,GRIDS*/
char find_rcg( int row, int column, char x[][9])
{
    printf("this is function rcg");
    int i;
    char test_ary1[9], test_ary2[9], test_ary3[9];
    for(i=0; i<9 ; i++)
    {
        test_ary1[i]=x[row][i];

    }
    for(i=0;i<9;i++)
    {
        test_ary2[i]=x[i][column];

    }
    *test_ary3=find_g(row, column, test_ary3);
    find_values(test_ary1,test_ary2,test_ary3, row , column, x);
}




/*THIS FUNCTION PULLS A GRID*/
char find_g(int row, int column,char x[][9], char *test_ary3)
{
    int i, j, k;
    if(row<=2)
    {
        if(column <=2)
        {
            k=0;
            for(i=0;i< 3;i++)
            {
            test_ary3[k]=x[i][0];
                k++;
                for(j=1;j <3;j++)
                {
                    test_ary3[k]=x[i][j];
                    k++;
                }
            }
            return(*test_ary3);
        }
        if(column<=2 && column<= 5)
        {
            k=0;
            for(i=0; i<3;i++)
            {
                test_ary3[k]=x[i][3];
                k++;
                for(j=4; j<6;j++)
                {
                    test_ary3[k]=x[i][j];
                    k++;
                }
            }
            return(*test_ary3);
        }
        if(column > 5 && column<= 8)
        {
            k=0;
            for(i=0;i<3;i++)
            {
                test_ary3[k]=x[i][6];
                for(j=4;j<6;j++)
                {
                    test_ary3[k]=x[i][j];
                }
            }
            return(*test_ary3);
        }
    }

return 0;
}




/*finds values for 0 input*/

char find_values( char *ary1, char *ary2, char *ary3, int row, int column, int x[][9])
{
    int i;
    int m_Ary[27];
    int count1=0,count2=0,count3=0,count4=0,count5=0,count6=0,count7=0,count8=0,count9=0;

    for(i=0;i < 27; i++)
    {
        if(i<9)
        {
            m_Ary[i]=ary1[i];
        }
        if(i>=9 && i<18)
        {
            m_Ary[i]=ary2[i-9];
        }
        if(i>=18)
        {
            m_Ary[i]=ary3[i-18];

        }
    }

    /*brute force check my m_Ary if a value comes back then it gets taken off the list of possible values*/
for (i=0;i<27;i++)
{
if(m_Ary==1)
count1++;
if(m_Ary==2)
    count2++;
if(m_Ary==3)
    count3++;
if(m_Ary==4)
    count4++;
if(m_Ary==5)
    count5++;
if(m_Ary==6)
    count6++;
if(m_Ary==7)
    count7++;
if(m_Ary==8)
    count8++;
if(m_Ary==9)
    count9++;

}
    printf("[row][column], [%d],[%d] can be", row+1,column+1);

if(count1=0)
    printf("1 ");
if(count2=0)
    printf("2 ");
if(count3=0)
    printf("3 ");
if(count4=0)
    printf("4 ");
if(count5=0)
    printf("5 ");
if(count6=0)
    printf("6 ");
if(count7=0)
    printf("7 ");
if(count8=0)
    printf("8 ");
if(count9=0)
    printf("9 ");
    return 0;
}

/*print my stuff*/
void print_m(char  m[][9]) 
{       
    int i,j;
    for (i=0; i < 9; i++)
    {
        for (j=0; j < 9; j++)
            printf("%2d ", m[i][j]);
        printf("\n");
    }
    printf("\n"); 
}   
void print_n(char x[])
{
    int i;
    for(i=0; i<9; i++)
    {   
        printf("%d",x[i]);
    }

    printf("\n");
}       

