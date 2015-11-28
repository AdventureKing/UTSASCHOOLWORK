#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1
#define FALSE 0

/*
escape: true if offset is len / 2 (we are at middle of string and everything matched up to that point)
	false if s[offset] != s[length - offset]
recursive def: return palindrome test with next char
*/
int isPalindrome(char * s, int offset) {
	//we are at middle char then is palindrome
	int len =strlen(s);
	if(offset == (len/2))
			return TRUE;
	
	//test char at offset with char on other end
	//if not equal then return FALSE
	if(*(s+offset) != *(s+len - 1 - offset))
		return FALSE;
	//keep testing next char
	
}

//iterative version
int isPalindrome2(char * s) {
	int i;
	int len=strlen(s);
	
	//iterate from first to middle char
		for(i=0;i<len/2;i++)
		{
			if(*(s+i) != *(s+len - 1 - i))
				return FALSE;
		}
	//if that char does not match char on other end then return FALSE
		return TRUE;
	
}

int main(int nargs, char *args[]) {
	if(nargs < 2) {
		printf("Usage: <executable> <word to scan>\n");
		return(EXIT_FAILURE);
	}
	
	printf("Recursive test:\n");
	int ret = isPalindrome(args[1], 0);
	if(ret == TRUE)
		printf("\t%s is a palindrome\n", args[1]);
	else
		printf("\t%s is NOT a palindrome\n", args[1]);

	printf("Iterative test:\n");
	ret = isPalindrome2(args[1]);
	if(ret == TRUE)
		printf("\t%s is a palindrome\n", args[1]);
	else
		printf("\t%s is NOT a palindrome\n", args[1]);	
	
	return(EXIT_SUCCESS);
}
