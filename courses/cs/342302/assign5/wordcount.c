#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <dirent.h>
#include "dllist.h"
//resources
//address = (char *)mmap(NULL,length,PROT_READ, MAP_SHARED,fd,offset);
//http://linux.die.net/man/2/mmap
//slide 33 for directory on I/O slides 



void readFILE(FILE *fp);
bool wordfilter(char *word);
void printList(int num,FILE *outPut);
void sortLinkedList();
void searchdir(const char *name, int level);
void freeAll();
char *splitFile(FILE *infp,unsigned int fileSize, int numProc, int fd);


int main(int nargs,char *argv[])
{
	FILE *fp;
	FILE *outPut;
	struct stat fileStat;
	struct stat fileStat1;
	if(nargs != 5)
	{
		printf("ERROR:Not enough parameters. you dun goofed bro!!!!\n");
		exit(1);
	}
	else
	{	
		if(strcmp(argv[1],argv[4]) == 0)
		{
		 printf("ERROR:Can not output to input file\n");
		 exit(1);
		}
		//num of print to console
     int numPrint;
	  //num of process to fork
		 int numProc;
		if(sscanf(argv[2],"%i",&numPrint) != 1 || sscanf(argv[2],"%i",&numPrint) != 1)
		{
				printf("ERROR: parameter 2 and 3 have to be integers!\n");
					exit(1);
		}
    numPrint = atoi(argv[2]);
    numProc = atoi(argv[3]);

    if(stat(argv[1],&fileStat) < 0)
		{
			printf("ERROR:FILE OR DIRECTORY NOT EXIST\n");    
        exit(1);
		}
		if(stat(argv[4],&fileStat1) == 0)
		{
			printf("ERROR:OUTPUT FILE EXIST AND WILL BE OVER WRITTEN\n");    
        exit(1);
		}
		else
		{
			char *ftp = argv[4];
    	outPut = fopen(ftp,"w+"); 
		}

    

    if(outPut == NULL)
    {
     printf("ERROR: couldnt open file for writing\n");
     exit(1);

    }
//if command line read in a file or read in a directory
			if (S_ISREG(fileStat.st_mode) && (fileStat.st_mode & S_IRUSR))
			{
				
				       //get file size
				
          unsigned int fileSize = fileStat.st_size;
          int fd = open(argv[1],O_RDONLY);
					char *file = NULL;
					printf("FileSize: %d\n", fileSize);
				
					int i;
          fp = fopen(argv[1], "r");
						 if(fp == NULL)
					   {
						  printf("ERROR: Couldnt open file might not exist!!!!!\n");
						  exit(1);
					   }
						//mmap the file
					 	file = splitFile(fp,fileSize,numProc,fd);
						int length = fileSize/numProc;
						int j;
						//usable stuff
						int startingIndex[numProc];
						int endingIndex[numProc];
						int tempPosition = 0;

						//moving starting and ending points
						for(i = 0; i < numProc; i++)
						{
							startingIndex[i] = i * length;
							endingIndex[i] = (i * length) + length;
						}

						for(i = 1; i < numProc; i++)
						{
								while(file[startingIndex[i]] != ' ' && file[startingIndex[i]] != '\t' && file[startingIndex[i]] != '\n')
								{
									
									startingIndex[i]++;
									endingIndex[i-1]++;
								}
						}

						for(i = 0; i < numProc; i++)
						{
							printf("block number:%d\n",i);
								//call child stuff 
							for(j = startingIndex[i]; j < endingIndex[i]; j++)
							{
								//filter
								char tempAry[500];
								tempPosition = 0; 
								//cut out the white space caused by having to move the starting and ending indexs
								//printf("file[startingIndex[i]]: %c\n",file[startingIndex[i]]);
								while(file[j] != ' ' && file[j] != '\t' && file[j] != '\n' && j < endingIndex[i])
								{
											tempAry[tempPosition] = file[j];
											tempPosition++;
											j++;
												
								}
								tempAry[tempPosition] = '\0';
								
								if(strlen(tempAry) > 0)	
								{
									if(wordfilter(tempAry) == TRUE)
									{
										printf("%s ",tempAry);
								  }	
									
								}	
										
							}
							printf("\n");			
						}
					
				   readFILE(fp);
	         sortLinkedList();   			
		       printList(numPrint,outPut);
           fclose(fp);
					 freeAll();
			}
			else if (S_ISDIR(fileStat.st_mode) && (fileStat.st_mode & S_IRUSR))
			{
			//printf("This is a directory and you can read noice\n");
           searchdir(argv[1],0);
					 sortLinkedList();
					 printList(numPrint,outPut);
					 freeAll();
   		 }
			else
			{
				printf("ERROR:your input is either not a file, not a directory,or the input could not be read please check permissions on file and re-run if you believe a error has occured"); 
			}
	}
 fclose(outPut);
    
return 0;
}
//call child stuff 
 
char *splitFile(FILE *infp,unsigned int fileSize, int numProc,int fd)
{
	
 	char *address;
	
	size_t length;
	
	off_t offset=0;
	length = fileSize/numProc;		
	printf("length:%d\n",length);
	
		//printf("run number:%d\n",i);


		address  = (char *)mmap(NULL,fileSize,PROT_READ,MAP_SHARED,fd,offset);
		if(address  == MAP_FAILED)
		{
			printf("ERROR: you dun goofed!\n");
				exit(1);		
		}
		//printf("random letter: %c",address[length]);
	  
		printf("\n");
		//printf("Offset:%d\n",offset);
		//offset = offset + length;
		
		
	return address;
}







void readFILE(FILE *fp)
{
		char *x;	
		
    /* assumes no word exceeds length of 1023 */
		//printf("Contents of the file:\n");
    while (!feof(fp)) {
				x =(char *) malloc(sizeof(char)*500);
				fscanf(fp, "%s ", x);
				//printf("%s\n",x);
				if(wordfilter(x) == TRUE)
                {
       	        dl_insert(front,x);
				  // printf("SECOND CALL IN READFILE word being read in: %s\n",x);
                }
				else
				{
					free(x);
				}
	
    }

		//printf("\n");
        

}
//filter the words out that are unneeded
bool wordfilter(char *word)
{
		unsigned int i;
	
	for(i = 0; i< strlen(word); i++)
	{
			if(word[i] != '-')
			{
			word[i]=tolower(word[i]);
			}
		if(word[i] == '-' ||(word[i] >= 97 && word[i] <= 122) || word[i] == ' ')
		{
			continue;
		}
		else if(word[i] == '-' && i == (strlen(word) - 1))
		{
			//printf("this word was filitered out %s\n",word);
			return FALSE;
		}
		else
			return FALSE;
	}
		return TRUE;

}
//print list starting from greatest to least
void printList(int num,FILE *outPut)
{
    
	int i=0;
	list_t *curr = rear;
	while(curr != NULL )
	{
        if(i < num)
		printf("%d\t%s \n",curr->count,curr->word);
		i++;
        fprintf(outPut, "%d\t%s\n",curr->count,curr->word);
		if(curr==front)
			break;	
		
		curr = curr->prev;
			
	}
	//printf("front->prev:%s\n",front->word);
	//printf("rear->next:%s\n",rear->word);
}
//bubble sort my linked list
void sortLinkedList(){
    int i=1;


    list_t *curr=front;
    //get total number of nodes to run bubble sort for loops on
    while(curr != NULL)
    {
        i++;
        if(curr == rear)
            break;
        curr = curr->next;

    }

    //printf("%d\n",i);
    int x,y;

    for(x = 0; x < i;x++)
    {
     curr = front;
      for(y=0; y<i-1-x;y++)
      {
        //swap just the word and value to next node if more occurences of current word
       if(curr->count > curr->next->count)
       {
        char *tempNextWord = curr->next->word;
        int tempNextNum = curr->next->count;
        char *tempWord = curr->word;
        int tempNum = curr->count;
        curr->next->count = tempNum;
        curr->next->word = tempWord;
        curr->word = tempNextWord;
        curr->count = tempNextNum;

       }
       curr = curr->next;

      }
    }

}
void searchdir(const char *name, int level)
{
    DIR *dir;
    struct dirent *entry;
		char *symlinkpath = (char *)name;
    if (!(dir = opendir(name)))
        return;
    if (!(entry = readdir(dir)))
        return;

    do {
        if (entry->d_type == DT_DIR)
				{
            char path[1024];
            int len = snprintf(path, sizeof(path)-1, "%s/%s", name, entry->d_name);
            path[len] = 0;
            if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0)
                continue;
            searchdir(path, level + 1);
        }
        else
				{
					//this logic sucked so 
					//buf has the full real file path
					//then i copy the systemlink part of the real file path in buff
					//then since that deletes the d_name i pin a / and the d_name to the end
					//of result so result now has full file path
						FILE *fp;
						char *ptr;
						char buf[PATH_MAX + 10]; 
						char result[1000];
						realpath(entry->d_name, buf);
							//printf("systemlinkpath %s\n",symlinkpath);
							ptr = realpath(symlinkpath,buf);
							strcpy(result,ptr);
							strcat(result,"/");
							strcat(result,entry->d_name);
    					
						//	printf("result %s\n",result);
							if(strcmp(result, "~") == 0)
							{
								continue;
							}
							else
							{
								fp = fopen(result,"r");
								//printf("opened %s\n",result);
							}						
						//if you cant read one file skip it and continue
							if(fp == NULL)
					   	{
						  	printf("ERROR: You cant open this file in this directory\n");
						  	continue;
					   	}	
							else
							{
								
								readFILE(fp);
		            fclose(fp); 
								
							}
				     
		          
				}
    } while ((entry = readdir(dir)));
    closedir(dir);
}

//free all memory
void freeAll()
{
		list_t *curr = front;
			while(curr != NULL && curr->next != NULL)
			{
				list_t *temp = curr;
				curr = curr->next;
				free(temp->word);
				free(temp);
				
				if(curr == rear)
					break;
			}
			free(curr->word);
			free(curr);
			
}
