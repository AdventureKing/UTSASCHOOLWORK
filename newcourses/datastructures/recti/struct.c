/*Brandon Snow tfv024 recitation 3*/


#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
int chk_overlap();
/*this is my struct RectT*/
typedef struct Rect
{
double x;
double y;
char color;
double w; /*width;*/
double h; /*height;*/
} RectT;


/*read in and all that in the main*/

int main(int nargs, char *argv[])
{
	RectT a,b,*recs;
	int overlap=0;
	int counta=0;
	int countb=0;
	int i;
	recs = malloc(50 * sizeof(RectT));
	assert(recs);
	
	printf("Please enter a x coordinate for A:\n");
	scanf("%lf",&a.x);
	printf("Please enter a y coordinate for A:\n");
	scanf("%lf",&a.y);	
	printf("Please enter a Width coordinate for A:\n");
	scanf("%lf",&a.w);
	printf("Please enter a Height coordinate for A:\n");
	scanf("%lf",&a.h);
	printf("Please enter a x coordinate for B:\n");
	scanf("%lf",&b.x);
	printf("Please enter a y coordinate for B:\n");
	scanf("%lf",&b.y);	
	printf("Please enter a width coordinate for B:\n");
	scanf("%lf",&b.w);
	printf("Please enter a Height coordinate for B:\n");
	scanf("%lf",&b.h);
	printf("\na[x]=%lf\n",a.x);
	printf("\na[y]=%lf\n",a.y);
	printf("\na[width]=%lf\n",a.w);
	printf("\na[height]=%lf\n",a.h);
	printf("\nb[x]=%lf\n",b.x);
	printf("\nb[y]=%lf\n",b.y);
	printf("\nb[width]=%lf\n",b.w);
	printf("\nb[height]=%lf\n",b.h);
	
	overlap = chk_overlap(&a,&b);
	
	if(overlap == 1)
	{
		printf("\nYour A and B overlap\n");
	}
	
	for(i=0;i<50;i++)
	{
		recs[i].x = rand() % 20;
		recs[i].y = rand() % 20;
		recs[i].h = rand() % 20;
		recs[i].w = rand() % 20;
			
		if(chk_overlap(&a,&recs[i]) == 1)
	{
		counta++;
	}
		if(chk_overlap(&b,&recs[i]) == 1)
	{
		countb++;
	}
	
	}
	
	printf("\nOf the 50 random rectangles %d overlap with A \n", counta);
	printf("\nOf the 50 random rectangles %d overlap with B \n", countb);
	free(recs);
	return 0;
	

}


int chk_overlap(RectT *r1, RectT *r2)
{
	/*
all of this is just condensed down lower in the function just wanted to leave this here to show my progression*/
	/*
	if(r1->x >= r2->x && r1->x <= r2->x +r2->w )
	{
	  if(r1->y >= r2->y && r1->y - r1->h <= r2->y)
	    {
		return 1;
	     }
	  else if(r1->y <= r2->y && r1->y >= r2->y - r2->h && r1->y - r1->h <= r2->y - r2->h)
	     {
		return 1;
	     }
	 }
	  else if(r1->y <= r2->y && r1->y >= r2->y - r2->h)
	     {	
		if(r1->x <= r2->x && r1->x + r1->w >= r2->x)
		  {
		 return 1;
		  }
	      
	  else if(r1->x >= r2->x && r1->x <= r2->x + r2->w && r1->x + r1->w >= r2->x + r2->w)
		  {
		 return 1;
		   }
	     }	  
	*/
	   	return(r1->x + r1 -> w > r2->x&& 
			r1->y + r1->h > r2->y &&
			r1->x < r2->x + r2->w &&
			r1->y < r2->y + r2->h)? 1 : 0; 			



}
