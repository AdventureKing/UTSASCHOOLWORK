# Makefile for programs in directory 09-Efficiency-and-ADTs
# ***************************************************************

PROGRAMS = \
		array-editor \
    stack-editor \
    list-editor \
    ddlist-editor \
		dlib-editor \

# ***************************************************************
# Parameters to control Makefile operation
# Note that the gccx command script must be defined

#CC = gccx
#gcc -I..booklib  -o prog   prog.c  ../booklib/cslib.a
CC = gcc -I../cdlibInstall/cslib 
CFLAGS = -I../cslibInstall/cslib -L../cslibInstall/cslib

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



arraybuf.o: arraybuf.c buffer.h
	$(CC) $(CFLAGS) -c arraybuf.c

listbuf.o: listbuf.c buffer.h
	$(CC) $(CFLAGS) -c listbuf.c

ddlistbuf.o: ddlistbuf.c buffer.h
	$(CC) $(CFLAGS) -c ddlistbuf.c

stack.o: stack.c stack.h
	$(CC) $(CFLAGS) -c stack.c

stackbuf.o: stackbuf.c buffer.h stack.h
	$(CC) $(CFLAGS) -c stackbuf.c


dlibbuf.o:dlibbuf.c buffer.h
	$(CC) $(CFLAGS) -c dlibbuf.c

editor.o: editor.c buffer.h
	$(CC) $(CFLAGS) -c editor.c

# ***************************************************************
# Executable programs

array-editor: editor.o arraybuf.o
	$(CC) $(CFLAGS) -o array-editor editor.o arraybuf.o ../cslibInstall/cslib/cslib.a

stack-editor: editor.o stackbuf.o stack.o
	$(CC) $(CFLAGS) -o stack-editor editor.o stackbuf.o stack.o  ../cslibInstall/cslib/cslib.a

list-editor: editor.o listbuf.o
	$(CC) $(CFLAGS) -o list-editor editor.o listbuf.o  ../cslibInstall/cslib/cslib.a

ddlist-editor: editor.o ddlistbuf.o
	$(CC) $(CFLAGS) -o ddlist-editor editor.o ddlistbuf.o  ../cslibInstall/cslib/cslib.a

# /////excutable
dlib-editor: editor.o dlibbuf.o 
	$(CC) $(CFLAGS) -o dlib-editor editor.o dlibbuf.o  ../cslibInstall/cslib/cslib.a
