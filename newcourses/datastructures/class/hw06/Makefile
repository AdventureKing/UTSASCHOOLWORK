# Makefile for programs in directory HW05
# ***************************************************************

PROGRAMS = driver_array
   

# ***************************************************************
# Parameters to control Makefile operation
# Note that the gccx command script must be defined

CC = gcc
CFLAGS = 

# ***************************************************************
# Entry to bring the package up to date

all: $(PROGRAMS)

# ***************************************************************
# Standard entries to remove files from the directories
#    tidy    -- eliminate unwanted files
#    scratch -- delete derived files in preparation for rebuild

tidy:
	rm -f ,* .,* *~ core a.out graphics.ps

clean: tidy
	rm -f *.o *.a $(PROGRAMS)

#****************************************************************
#C compile

exception.o: exception.c exception.h genlib.h gcalloc.h 
	gcc -ansi -g -c exception.c 

genlib.o: genlib.c genlib.h gcalloc.h exception.h
	gcc -ansi -g -c genlib.c 

setArrayImp.o: setArrayImp.c set.h genlib.h
	gcc -ansi -g -c setArrayImp.c

setLinkedListImp.o: setLinkedListImp.c set.h genlib.h
	gcc -ansi -g -c setLinkedListImp.c

driver.o: driver.c set.h
	gcc -ansi -g -c driver.c


	
driver_array: driver.o setArrayImp.o genlib.o exception.o
	gcc -ansi -g -o driver_array driver.o setArrayImp.o exception.o genlib.o 

