# Makefile for programs in directory 08-Abstract-Data-Types
# ***************************************************************

PROGRAMS = \
    rpncalc \
    testscan

# ***************************************************************
# Parameters to control Makefile operation
# Note that the gccx command script must be defined

CC = gccx
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

scratch: tidy
	rm -f *.o *.a $(PROGRAMS)

# ***************************************************************
# C compilations

rpncalc.o: rpncalc.c stack.h
	$(CC) $(CFLAGS) -c rpncalc.c

scanadt.o: scanadt.c scanadt.h
	$(CC) $(CFLAGS) -c scanadt.c

stack.o: stack.c stack.h
	$(CC) $(CFLAGS) -c stack.c

testscan.o: testscan.c scanadt.h
	$(CC) $(CFLAGS) -c testscan.c


# ***************************************************************
# Executable programs

rpncalc: rpncalc.o stack.o
	$(CC) $(CFLAGS) -o rpncalc rpncalc.o stack.o

testscan: testscan.o scanadt.o
	$(CC) $(CFLAGS) -o testscan testscan.o scanadt.o


