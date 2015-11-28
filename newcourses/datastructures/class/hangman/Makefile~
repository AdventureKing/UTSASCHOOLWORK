# Makefile for programs in directory HW04
# ***************************************************************

PROGRAMS =   hw04 
   

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

# ***************************************************************
# C compilations

randword.o: randword.c randword.h
	gcc -ansi -Wall -g -c randword.c 



hw04.o: hangman.c randword.h
	gcc -ansi -Wall -g -c hangman.c -o hw04.o


# ***************************************************************
# Executable programs



hw04: hw04.o randword.o
	gcc -ansi -Wall -g -o hw04 hw04.o randword.o

