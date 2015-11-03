#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "node.h"
#include "stack.h"

extern node * stack;

char * removeNewline(char * ln) {
	int l = strlen(ln);
	int i;
	for(i = 0; i < l; i++) {
		if(*(ln + i) == '\n') {
			*(ln + i) = '\0';
			return ln;
		}
	}
	return ln;
}

int main(int nargs, char *args[]) {
	FILE *fp;
	char url[255];
	node * n;
	
	initStack();
	if(stack ==NULL)
	{
	printf("Cannot allocate");
	}				
	printf("*** Stack demo ***\n");
	
	//open the file
	fp = fopen("data2.txt", "r");
	if(fp == NULL) {
		perror("cannot open file!");
		return(EXIT_FAILURE);
	}
	
	//read file, 1 line per node
	while(!feof(fp)) {
		if(fgets(url, 255, fp) == NULL)
			continue;
		removeNewline(url);
		n = (node *) malloc(sizeof(node));
		strcpy(n->url, url);
		if(push(n) == TRUE) {
			printf("Push. Stack has %d elements. Pushed node is ", size());
			printNode(n);
		} else 
			printf("Error trying to push node!\n");
		free(n);
	}
	
	while(!empty()) {
		n = pop();
		printf("Pop. Stack has %d elements. Popped node is ", size());
		printNode(n);
	}
	//release resources
	free(stack);
	fclose(fp);
	return(EXIT_SUCCESS);
}
