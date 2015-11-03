#include <stdio.h>
#include <stdlib.h>
#include "dllist.h"
void printList();
int main(int args, char *argv[])
{
    
    FILE *input = fopen(argv[1],"r");
    int num;
    char command;

    

     while(fscanf(input,"%c %d\n",&command,&num) != EOF)
    {
        
        
        if(command == 'a')
        dl_insert(front,num);
        else if(command == 'b')
        dl_del(front,num);
        else if(command == 'f')
        { 
        valueT val = dl_get_front(front);
        printf("front value:%d\n",val);
        }
        else if(command == 'g')
        {
        valueT val = dl_get_back(front);
        printf("Back value:%d\n",val);
        }
        else if(command == 'd')
        dl_pop_front(front);
        else if(command == 'e')
        dl_pop_back(front);
        else if(command == 'h')
        {
        valueT val = dl_check(front, num);
        printf("val exists:%d\n",val);
        }
        else if(command == 'j')
        {
            dl_insert_front(front,num);
        }
        else if(command == 'p')
        {
            printList();
        }
        
    }
    fclose(input);
    return 0;
}

void printList(){
    list_t *curr = front;
    int i = 0;
    while( curr != NULL)
    {
        printf("array[%d]: %d\n", i ,curr->value);
        i++;
        if(curr == rear)
            break;
        curr = curr->next;
    }
    printf("front->prev: %d\n",front->prev->value);
    printf("rear->next: %d\n",rear->next->value);

}
