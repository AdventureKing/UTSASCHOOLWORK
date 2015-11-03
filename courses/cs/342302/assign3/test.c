
#include <stdio.h>
#include <ctype.h>
#include "dllist.h"
void printList();
int main (int argc, char *argv[])
{
	char command;
	valueT num;

	printf("hit:\na:insert num\nb:delete specific num\nf:front value\ng:backvalue\nd:delete front\ne:delete back\nh:check list\nj:insert front\np:print\n");


while(scanf(" %c %d" ,&command,&num))
	{
		//printf("%c %d" ,command, num);
		list_t *node = NULL;
		//check with teach on what to do about this
		if(command == 'a')
		{
			
			dl_insert(node,num);
		}
		//works
		else if(command == 'b')
		{
			dl_del(front,num);
		}
		//works
		else if(command == 'f')
		{
			valueT val = dl_get_front(front);
			printf("Front value:%d\n", val);
		}
		//works
		else if(command == 'g')
		{
			valueT val =dl_get_back(rear);
			printf("Back value:%d\n", val);
		}
		//works
		else if(command == 'd')
		{
			 dl_pop_front(front);
			
		}
		//works
		else if(command == 'e')
		{
			dl_pop_back(rear);
			
		}
		//works
		else if(command == 'h')
		{
			valueT val = dl_check(front, num);
			printf("val:%d\n",val);
		}
		//works
		else if(command == 'j')
		{
			
			dl_insert_front(node,num);
		}
		//test print
		else if(command == 'p')
		{
			printList();
		}
        else if(command == 'e')
            exit;
	}
	return 0;
}

void printList()
{
	int i=0;
	list_t *curr = front;
	while(curr != NULL )
	{
		printf("array[%d]:%d\n",i,curr->value);
		i++;	
		if(curr==rear)
			break;	
		curr = curr->next;
			
	}
	printf("front->prev:%d\n",front->prev->value);
	printf("rear->next:%d\n",rear->next->value);
}

	/*while(scanf(" %c %d" ,&command,&num))
	{
		//printf("%c %d" ,command, num);
		list_t *node = NULL;
		//check with teach on what to do about this
		if(command == 'a')
		{
			node = (list_t *)malloc(sizeof(list_t));
			dl_insert(node,num);
		}
		//works
		else if(command == 'b')
		{
			dl_del(front,num);
		}
		//works
		else if(command == 'f')
		{
			valueT val = dl_get_front(front);
			printf("Front value:%d\n", val);
		}
		//works
		else if(command == 'g')
		{
			valueT val =dl_get_back(rear);
			printf("Back value:%d\n", val);
		}
		//works
		else if(command == 'd')
		{
			 dl_pop_front(front);
			
		}
		//works
		else if(command == 'e')
		{
			dl_pop_back(rear);
			
		}
		//works
		else if(command == 'h')
		{
			valueT val = dl_check(front, num);
			printf("val:%d\n",val);
		}
		//works
		else if(command == 'j')
		{
			node = (list_t *)malloc(sizeof(list_t));
			dl_insert_front(node,num);
		}
		//test print
		else
		{
			list_t *curr = front;
			while(curr != NULL)
			{
				printf("val: %d\n", curr->value);
				curr = curr->next;
			}
		}
		
		switch (toupper(command)) 
		{
      case 'A': dl_insert(node,num);break;
      case 'B': dl_del(front,num);break;
      case 'C': dl_insert_front(front, num);break;
      case 'D': dl_get_front(front);break;
      case 'E': dl_get_back(rear);break;
      case 'F': dl_pop_front(front);break;
      case 'G': dl_pop_back(rear); break;
		case 'I': dl_check(front); break;
      case 'H': exit(0);
      default:  printf("Illegal command\n"); 
					break;
		}*/




