#ifndef _set_h_
#define _set_h_

#include "genlib.h"

typedef int setElementT;

typedef struct setCDT *setADT;

void setFree(setADT S);



setADT NewSet(void);


setADT setUnion(setADT A, setADT B);

setADT setIntersection(setADT A, setADT B);

setADT setDifference(setADT A, setADT B);

int setCardinality(setADT S);

void setPrint(setADT S, char *name);

setADT setInsertElementSorted(setADT S,setElementT E);






#endif
