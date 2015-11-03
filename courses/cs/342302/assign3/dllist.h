#ifndef _dllist_h
#define _dllist_h

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define TRUE 1
#define FALSE 0


typedef int valueT;

typedef int bool;


typedef struct list {
	
	valueT value;

	struct list *prev; // Pointing to previous node
	struct list *next; // Pointing to next node
} list_t;

list_t *front;
list_t *rear;

// Initialize an actual link list (list).
void dl_init(list_t *list);

// Insert a value (val) into the tail of the given list (list).
bool dl_insert(list_t *list, valueT val);

// Delete the node with give value (val) from the given list (list).
bool dl_del(list_t *list, valueT val);

// Insert a value (val) into the front of the given list (list), which becomes the first node o
bool dl_insert_front(list_t *list, valueT val);

// Get the first node’s value from the given list (list).
valueT dl_get_front(list_t *list);

// Get the last node’s value from the given list (list).
valueT dl_get_back(list_t *list);

// Delete the first node from the given list (list).
void dl_pop_front(list_t *list);

// Delete the last node from the given list (list here).
void dl_pop_back(list_t *list);

// Check whether the value (val) is existing or not. If existing, it is return 1. Otherwise,
// this function will return 0.
bool dl_check(list_t *list, valueT val);

#endif
