/*Brandon snow tfv024*/


#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
/*link in my .h*/

#include "randword.h"


/*in my main i play the game*/


int main (int argc, char *argv[])
{
		
			int i, p;
			int z=8;
			char *word;
			char *guess;
			char letter; 
			
			int right=0;
			int count=0;
			int out=0;
/*counter to see make sure of only 8 guesses*/

			int guessleft=0;

/*link in library*/
			InitDictionary(argv[1]);

/*choose a random word from my library*/
			word = ChooseRandomWord();

					
			

	printf("Hello Welcome to hangman.\nI will guess a secret word. On each turn, you guess a letter.\nIf the letter is in the secret word, I will show you where it appears; if not, a part of your body gets strung up on the scaffold. The object is to guess the word before you are hung.(You may only guess one letter once and ONLY ONCE)\n");
			
			
				guess=malloc(strlen(word) + 1 * sizeof(char));
/*print dashes*/
							
				for(p=0;p<strlen(word);p++)
					{
					guess[p]= '-';
					}

				guess[strlen(word)] = '\0';
	/*the hangman loop*/		
while(out == 0)
			{
	/*first print outs with dashes and then asks you to guess*/

				printf("Here is your word:%s\n", guess);
				printf("You have %d guesses\n", z-guessleft);
				printf("Guess a letter:");
				fgets(&letter,3,stdin);
				
					/*loop to check if letter is in your word*/
			for(i=0;i<strlen(word);i++)
				{
					if(word[i]==letter)
							{
						right++;
					  guess[i]=letter;
         			} 
							
				}
				/*check guess*/
				  if(right == 1)
					{
					count++;
					printf("That guess is correct.\n");
					printf("The word now looks like:%s\n",guess);
					right=0;
					}
				  else if(right == 0)
					{
					 ++guessleft;
					 printf("That guess was incorrect!\n");
					 printf("You now have %i guess left\n",z - guessleft);	
					}	

/*check for if the guessed letter appears twice*/

					else if(right == 2)
							{
					count +=2;
					printf("That guess is correct.\n");
					printf("The word now looks like:%s\n",guess);
					right=0;
								}
					
/*kick out first if statment is checking to see if they guessed the whole word second if only excutes if they runn out of guess's*/

				if(count == (strlen(word)))
				{
				printf("You guessed the word: %s\n",guess);
				out =1;
				
				}	
				else if( count != (strlen(word)) && guessleft == 8 )
						{
				printf("You have failed to guess the word! Better luck next time smalls!\n");
				out =1;
						
				    }
			}
		/*frees */
	   free(guess);
		 free_all();
			
		 return(0);

}



