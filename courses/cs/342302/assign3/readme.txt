Brandon Snow
tfv024
ReadMe for dllist lib



• Function Implementation Descrption:
    -dl_init:Takes in a pointer and sets its left and right to NULL
    -dl_insert:Takes in a value and a pointer. I malloc for a node and have 
        a temp pointer set to null. I use dl_init to init the node. I have a 
        check to make sure the pointer is created else returns a error.if the 
        temp pointer is set to null then its the first node and its updated and
        returns true. Else it goes into a else if and updates the rear and front
        to have their correct connections and then return true. If neither one 
        of those conditions are met then it returns false.
    -dl_del:This delete takes in a value and a list.
        I have a gobal bool for secondval set to true for later use. I 
        check at the beginning if front is == null then return false cause
        there is nothing in the list.Then if the value that was entered is the 
        front node I update everything and then free the old front node.I then 
        use a function called secondpass on secondval to make sure that no more
        of that number exist in the list.Then i return true.
            I have a else to my if that goes into a while loop that 
        loops through my list then have two if checks saying if its a value
        inbetween two other numbers its updates the correct pointers then frees
        that memory i then use the same tatic as before and check for any more
        of that number. If secondval comes back as false i know that all of
        that number have been deleted. Then i return true. If the value is at 
        the end of the list. All approiate pointers are updated. I then check 
        to see if that value exist any where else. I then return true. Lastly
        if the value is not found and the temp pointer = rear then i return 
        false.
    -secondpass:Takes a list and a value. Its basically a mini version of my
        delete function. It just runs though my list and each time that it finds
        another val that matches it deletes it and returns true.It returns false
        if it doesn't find any thing which breaks the while loop in the delete 
        function.
    -dl_insert_front: Takes in a list and a value. The insert function checks 
        front == null if it does then it just updates front and rear to be the
        inputted value and returns ture. Else it updates rear and front pointers
        and inserts the numbers into the front.
    -dl_get_front:This function takes in a list.If the front == null then it 
        returns false. This function returns the fronts value(aka the first in
        the list).
    -dl_get_back:This function reads in the list as a parameter.If the rear == 
        NULL then it returns false cause the list will be empty or some run time
        error happenend.This function returns the rear value  if its not empty.
    -dl_pop_front:This function will delete the frist node in the list that is 
        read in.First I check if their is just one node with front == rear &&
        front != null so that way it doesn't segfault.If those conditions are
        met then it will assign a temp pointer to the front. Then sets Front
        and rear == NULL.Then frees the old front.The else if just checks if
        fronts != null if its not then it moves the front and frees the old
        front.
    -dl_pop_back:This function is the opposite of the dl_pop_front. This time 
        on the list that it takes in it sets a temp variable to the rear of the
        list and moves the rear back one node and realests the memory in the 
        node. I check the same thing first that being if front == rear and rear
        != null if not then delete the only node. Else just move rear if its not
        = to null.
    -dl_check: This last function takes in two parameters a list and a value.
        If front is == to null it returns false cause nothings in the list.
        Then while the list has stuff in it a temp pointer goes through all the
        items to see if its there. It breaks when curr->next = Front. That way
        i know it went through every item in the list. If it returns true it 
        found it if it returns false it wasn't there.

• Building and Using as a shared library:
    I complied the dllist.c and .h into a shared library called dllib.so. You compile that with our main.c to get it working. The line you need is 
    gcc -Labsolutepathtolibrary -Wall main.c -o excutable -lshared
    After that you should be good. It didnt seem like i needed anything special. I couldnt get it to work on the school terminal but i checked it on mine and it ran fine.

• Building a static library:
Ok so building my library was fairly easy for me since my first programming language was in c. When your compling my code you need to have the library in   the same directory as the file your trying to compile it with. The compile line should be
gcc -Wall -g -o xtest dllist.c main.c
    Then you just run your program with a ./test and you should be good to go.


• Comparing between dynamic library and static Libraries:
    The dynamic is a pain but really easy to update cause all you have to do is update the .o for the library object with a command line argument. The shared library seems like more of a hassle for me as a programmer but easier for the user cause i dont have to re compile the whole library into the program. The static is just easier for me cause i just throw it all into one giant file.

• Designed Test Cases:
    With both my different test files i should be hitting every possible combiniation of inputs as possible. I can input pretty much any combonation of numbers and data to get everything pretty much sorted out. I spent along time doing different test cases with these two specific types of main functions.
    
2 test cases: test.c-Where a user enters a command and it does the corresponding command.
    compile line:gcc -Wall -g -o xhw3 test.c dllist.c

    -For this test case I entered many different numbers and sysbols to try to throw it off. The check for if its not a int value needs to be done in main as my library will convert non-characters to their ascii int value and insert it.
User gets a prompt at the beginning for what to press to get the desired results.After that its just plug and play. All functions worked as designed. I spent a good amount of time checking and re-checking things. Found running time errors of my delete front and back. It would confuse the pointers for the front and rear if i deleted to much so i had to fix that and a few other small issues with the circular part.

compile:gcc -Wall -g -o xhw3-1 test2.c dllist.c




test2.c-where a file is read in with commands entered to corresponding functions to test if the program can handle any test case. I put in a file a bunch of letters and numbers and do all operations and then print it out. I also do small cases as to figure out whats working and what isn't.I send in pretty much 20 different forms of input files the one includded does like 3 test cases of each letter to make sure over flow and under flow aren't issues. Most of the testing here was to make sure nothing broke. If it broke i looked at it and changed my statements in the library to make sure it didn't happen again.







