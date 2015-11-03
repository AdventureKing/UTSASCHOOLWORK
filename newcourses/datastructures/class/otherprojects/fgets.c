/* fgets example */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main()
{
       FILE * pFile;
          char mystring [100];
            int i;
             pFile = fopen ("input.txt" , "r");
                if (pFile == NULL) perror ("Error opening file");
                   else {
                       for(i=0;i<strlen(pFile);i++)
                            if ( fgets (mystring , 100 , pFile) != NULL )
                                       puts (mystring);
                                 printf("%s",mystring);
                                    }
                   fclose(pFile);
                      return 0;
}



