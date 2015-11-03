#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dllist.h"
#define TRUE 1
#define FALSE 0

bool secondpass(list_t *list, valueT val);
// Initialize an actual link list (list).
void dl_init(list_t *list)
{
	
		list->prev = NULL;
		list->next = NULL;
}
// Insert a value (val) into the tail of the given list (list).
bool dl_insert(list_t *list1, valueT val)
{
	//printf("Hit here");
    
	list_t *curr = front;
    list_t *list;
	list = (list_t *)malloc(sizeof(list_t));
	
	if(list != NULL)
	{
		dl_init(list);
		list->value = val;	
	}
	else
	{
		printf("Error");
		return FALSE;
	}
		
	
	if(curr == NULL)
	{
		front = list;
		rear = list;
		front->prev=rear;
		rear->next=front;
		return TRUE;
	}
	else if(curr != NULL)
	{
		rear->next = list;
		list->prev = rear;
		rear = list;
		rear->next = front;
		front->prev = rear;
		return TRUE;
	}
	return FALSE;
//printf(" Insert %d. node created\n",val);
}


// Delete the node with give value (val) from the given list (list).
bool dl_del(list_t *list, valueT val)
{
    bool secondval = TRUE;
	//user wants to delete first number
	if(front == NULL)
	{
		//printf("ERROR:NOTHING IN LIST BRO");
		return FALSE;
	}
	if(front->value == val)
	{
		list_t *curr = front;
		front = front->next;
		front->prev = rear;
		rear->next = front;
		//list->prev = NULL;
		//list->next = NULL;
		free(curr);
        secondval = secondpass(front,val);
     while(secondval)
         {
            //printf("%d",secondval);
         secondval = secondpass(front,val);
         }
		return TRUE;
	}
	else
	{
		list_t *curr = front;
		//find val using front
		while(curr != NULL )
		{
			
		//if val is inbetween two nodes
		if(curr->value == val && curr != rear)
		{
			curr->prev->next = curr->next;
			curr->next->prev = curr->prev;
			curr->next = NULL;
			curr->prev = NULL;
			free(curr);
           secondval =secondpass(front,val);
            while(secondval)
            {
                secondval = secondpass(front,val);
            }
			return TRUE;
		}
		//if user wants to delete last node
		else if(curr->value == val && curr == rear)
		{
			rear = curr->prev;
			rear->next = front;
			front->prev = rear;
			curr->prev = NULL;
			curr->next = NULL;
			free(curr);
            secondval = secondpass(front,val);
             while(secondval)
             {
               secondval = secondpass(front,val);
             }

			return TRUE;
		}
		else
			{
		
			if(curr->next == front)
			{
				//printf("ERROR:Value not in list\n");
				return FALSE;
			}
			curr = curr->next;
			}
		}
	}
		return FALSE;
}

bool secondpass(list_t *list, valueT val)
{
    list_t *curr = front;
    if(curr->value == val)
    {
        list_t *curr = front;
        front = front->next;
        front->prev = rear;
        rear->next = front;
        list->prev = NULL; 
        list->next = NULL;
        free(curr);
        return TRUE;

    }
    else
    {
        while(curr != NULL)
        {
            if(curr->value == val && curr != rear)
            {
                curr->prev->next = curr->next;
                curr->next->prev = curr->prev;
                curr->next = NULL;
                curr->prev = NULL;
                free(curr);
                return TRUE;
            }
            else if(curr->value == val && curr == rear)
            {
                rear = curr->prev;
                rear->next = front;
                front->prev = rear;
                curr->prev = NULL;
                curr->next = NULL;
                free(curr);
                return TRUE;
            }
            else
            {
                if(curr->next == front)
                {
                    return FALSE;
                }
                curr = curr->next;
            }
        }
    }
    return FALSE;
}
// Insert a value (val) into the front of the given list (list), which becomes the first node o
bool dl_insert_front(list_t *list1, valueT val)
{
	list_t *curr = front;
    list_t *list;
	list = (list_t *)malloc(sizeof(list_t));
	if(list != NULL)
	{
	dl_init(list);
	list->value = val;	
	}
	else
	{
	//printf("ERROR");
	return FALSE;
	}
	if(curr == NULL)
	{
		front = list;
		rear = list;
		front->prev=rear;
		rear->next=front;
		return TRUE;
	}
	else
	{
		list->next = front;
		front->prev = list;
		front = list;
		front->prev = rear;
		rear->next = front;
		return TRUE;
	}
	return FALSE;
	//printf(" Insert %d. node created\n",val);
}


// Get the first node’s value from the given list (list).
valueT dl_get_front(list_t *list)
{
	if(front == NULL)
	{
		//printf("ERROR:There is nothing in the list return 0");
		return FALSE;
	}
	return front->value;
}


// Get the last node’s value from the given list (list).
valueT dl_get_back(list_t *list)
{
	if(rear == NULL)
	{
    	//printf("ERROR:There is nothing in the list return 0");
		return 0;
	}
	return rear->value;
}


// Delete the first node from the given list (list).
void dl_pop_front(list_t *list)
{
    if(front == rear && front != NULL)
    {
        list_t *curr = front;
        front = NULL;
        curr->next = NULL;
        curr->prev = NULL;
        rear = NULL;
        free(curr);
    }
    else if(front != NULL)
    {
	list_t *curr = front;
	front = front->next;
	front->prev = rear;
	rear->next = front;
	free(curr);
    }
}


// Delete the last node from the given list (list here).
void dl_pop_back(list_t *list)
{
    if(front == rear && rear != NULL)
    {
        list_t *curr = rear;
        curr->next = NULL;
        curr->prev = NULL;
        front = NULL;
        rear = NULL;
        free(curr);
    }
    else if(rear != NULL)
    {
	list_t *curr = rear;
	rear = rear->prev;
	rear->next = front;
	front->prev = rear;
	free(curr);
    }
}


// Check whether the value (val) is existing or not. If existing, it is return 1. Otherwise,
// this function will return 0.
bool dl_check(list_t *list, valueT val)
{
	list_t *curr = front;
    if(front == NULL)
        return FALSE;
	//search for my stuff from the beginnning
	while(curr->value != val && curr != rear)
	{
		//printf("Current val: %d Need to find: %d\n",curr->value,val);
		curr=curr->next;
	}
	//if its at the end and didnt find it
	if(curr == rear && curr->value != rear->value)
	{
		return FALSE;
	}
	//it found it
	else if(curr->value == val)
	{
		return TRUE;
	}
	//catch all just in case it has some link error or something and cant parse correctly
	else
	{
		return FALSE;
	}	
}
