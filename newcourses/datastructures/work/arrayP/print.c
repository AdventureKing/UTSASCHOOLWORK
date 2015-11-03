#include <stdio.h>
void print_array();
int main()
{
    int x;
    int y;
    int array[8][8]; /* Declares an array like a chessboard */

    for ( x = 0; x < 8; x++ ) 
    {
        for ( y = 0; y < 8; y++ )
        {
            array[x][y] = x * y; /* Set each element to a value */
    
        }
    }
    
print_array(array);
    printf( "Array Indices:\n" );
    for ( x = 0; x < 8;x++ ) {
        for ( y = 0; y < 8; y++ )
        {
            
            printf( "[%d]" "*" "[%d]" "=" "%d ",x ,y ,  array[x][y] );
        }

        printf( "\n" );
    }
    
    getchar();
return(0);
}
void print_array( int x[][8])
{
   int i,j;
   for(i=0; i<8; i++)
   {
       for(j=0;j<8;j++)
    
           printf("%d ",x[i][j]);
       
    

     printf("\n");

   }
   
     printf("\n");
}
