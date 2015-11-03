#include <stdio.h>
#include <stdlib.h>
//TO LOWER
int main(int argc, char *argv[])
{
	char letter;
	printf("Enter some fucking words asshole:\n(Enter 1 to leave asshole)\n");
	while(letter != '1')
	{
		scanf(" %c",&letter);
		if(letter >= 'a' && letter <= 'z')
		{
		printf("This is the new letter:%c ",letter - 32);
		
		printf("\n");
		}
		
	}
	return 0;
}
