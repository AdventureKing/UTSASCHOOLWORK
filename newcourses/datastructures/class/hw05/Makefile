# Makefile for programs in directory HW05
# ***************************************************************

PROGRAMS =   hw05
   

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


exception.o: exception.c exception.h
	gcc -ansi -Wall -g -c exception.c 

hw05.o: html.c stack.o
	gcc -ansi -Wall -g -c html.c -o hw05.o

stack.o: stack.c stack.h
	gcc -ansi -Wall -g -c stack.c 


hw05: hw05.o stack.o genlib.o exception.o
	gcc -ansi -Wall -g -o hw05 hw05.o stack.o genlib.o exception.o


