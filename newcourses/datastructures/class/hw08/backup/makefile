# Makefile for programs in directory HW05
# ***************************************************************

PROGRAMS = hw08
   

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

buffer.o: buffer.c buffer.h
	$(CC) $(CFLAGS) -c buffer.c

driver.o: driver.c buffer.h
	$(CC) $(CFLAGS) -c driver.c


hw08: buffer.o driver.o 
	$(CC) $(CFLAGS) -o hw08 driver.o buffer.o  

