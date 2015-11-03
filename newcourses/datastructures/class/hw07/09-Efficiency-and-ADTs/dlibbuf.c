/*Brandon Snow*/


/*What it does: creates nodes and fills them with ch**
**move cursor foward /move cursor backward**
**display my stuff**
didn't do:
split node
delete 
free*/

#include <stdio.h>
#include <stdlib.h>

#include "buffer.h"

typedef struct cellT {
    char ch[Max_Block_Size];
		int count;
		int index;
    struct cellT *prev;
		struct cellT *next;
} cellT;

 struct bufferCDT{
	cellT *start;
	cellT *cursor;
	cellT *end;


};

bufferADT NewBuffer(void)
{

		bufferADT buffer;

    buffer = New(bufferADT);
 		buffer->start = buffer->cursor = buffer->end = New(cellT *);
    buffer->start->next=NULL;
		buffer->start->prev=NULL; 
		buffer->end->next = NULL;
		buffer->end->prev = NULL;
		
    return (buffer);


}
/*


void FreeBuffer(bufferADT buffer)
{
 	bufferADT *cp;
	cellT *next_cell;

    cp = buffer->start;
    while (cp != NULL) {
        next_cell = cp->next;
        FreeBlock(cp);
        cp = next_cell;
    }
    FreeBlock(buffer);

}
*/
/*
******This is come bullshit i was trying*******
void MoveCursorForward( bufferADT buffer)
{
		int i;
	if(buffer->current_block->ch[i+1] != buffer->current_block->ch[Max_Block_Size])
		{
		buffer->current_block->ch[i] = buffer->current_block->ch[i+1];
		}
	else
		{
		exit(1);
		}	

}


void MoveCursorBackward(bufferADT buffer)
{
			int i;
		if(buffer->current_block->ch[i-1] != NULL)
		{
		buffer->current_block->ch[i] = buffer->current_block->ch[i-1];
		}
	else
		{
		exit(1);
		}	

}
*/
/*

void MoveCursorToStart(bufferADT buffer)
{
		while (buffer->cursor_block->prev != NULL) {
        MoveCursorForward(buffer);
    }
}


void MoveCursorToEnd(bufferADT buffer)
{

		while (buffer->cursor_block->next != NULL) {
        MoveCursorForward(buffer);
    }
}
end of bullshit
*/
/*real stuff*/
void InsertCharacter(bufferADT buffer, char ch)
{
			
/*
 	*****if theirs to much***
		if(buffer->cursor->length == Max_Block_Size && buffer->cursor != NULL)
		{
			SplitBlock(buffer);

		}*/
/*if nothing*/
		if(buffer->start == NULL)
		{
			cellT *cp;
			cp = New(cellT *);
			cp->next = NULL;
			cp->prev = NULL;
			cp->ch[0] = ch;
			cp->count = 1;
			cp->index = 1;
			buffer->start = cp;
		
			buffer->cursor = cp;
			buffer->end = cp;
		}
/*If theirs something which is not working*/
		else if(buffer->cursor->count < Max_Block_Size && buffer->cursor != NULL)
		{
			buffer->cursor->ch[buffer->cursor->index]=ch;
			buffer->cursor->count++;
			buffer->cursor->index++;
			buffer->end = buffer->cursor;
			
		} 
		else
/* if all else fails*/
		{
			cellT *cp;
    	cp=New(cellT *);
			cp->next=NULL;
			if(buffer->cursor->next != NULL)
			{
				cp->next = buffer->cursor->next;
			}
				cp->prev = buffer->cursor;
				cp->ch[0] = ch;
				cp->count=1;
				cp->index = 1;
				buffer->cursor->next = cp;
				buffer->cursor=cp;
				buffer->end = cp;
	 }
	

}
/* didn't do*/
void DeleteCharacter(bufferADT buffer)
{



}
/* move cursor back*/
void MoveCursorBackward(bufferADT buffer)
{
			if(buffer->cursor->index != 0)
				{
/* if its still in same block give me item right before it*/
					buffer->cursor->index--;
				}
				else
				 if(buffer->cursor->prev == NULL)
				{
/*check statment*/
					printf("Already at beginning bro!\n");
				}
			else
				{
/*if its 0 go to node right before this one and give me that index*/
					buffer->cursor= buffer->cursor->prev;
					buffer->cursor->index--;
				}
}
/* this moves it to far foward ={*/
void MoveCursorForward( bufferADT buffer)
{
/*find index*/
			if(buffer->cursor->index < buffer->cursor->count)
				{
							/*if its less than count just give me the next element*/
						buffer->cursor->index++;
				}
			else
				 if(buffer->cursor == buffer->end)
				{
/*check statment*/
					printf("Already at end bro!\n");
				}
			else
				{
/*move to next block*/
						buffer->cursor = buffer->cursor->next;
						buffer->cursor->index++;
				}	
		
}
/*this one should work*/
void MoveCursorToEnd(bufferADT buffer)
{
		while(buffer->cursor->next != NULL)
			{
					buffer->cursor->index = buffer->cursor->count;
					buffer->cursor=buffer->cursor->next;
					
			}
		buffer->cursor->index = buffer->cursor->count;
}
/*Should be good*/
void MoveCursorToStart(bufferADT buffer)
{
			while(buffer->cursor != buffer->start)
			{
					buffer->cursor->index = 0;
					buffer->cursor=buffer->cursor->prev;
			}
					buffer->cursor->index = 0;
}
/*Display stuff im pretty sure this is working right*/
void DisplayBuffer(bufferADT buffer)
{
		int i;
		cellT *temp;
		int j;
		/*print line*/
		for(temp = buffer->start; temp != NULL; temp = temp->next)
		{
				for(i=0;i<temp->count;i++)
				{
						printf(" %c",temp->ch[i]);
				}
				
		}
		printf("\n");
/*print space*/
		for(temp=buffer->start;temp!= buffer->cursor; temp=temp->next)
		{
			for(i=0;i<temp->index;i++)
			{
				printf("  ");
			}

		}
/*print cursor*/
		for(j=0;j<buffer->cursor->index;j++)
		{
			printf("  ");
		
		}
			printf("^\n");

}
