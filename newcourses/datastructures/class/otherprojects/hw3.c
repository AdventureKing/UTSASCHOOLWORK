/*Brandon Snow -hw3 
 *
 *
 * Design a new type called payrollT that is capable of holding the data for a list of
 * employees, each of which is represented using the employeeT type introduced in the
 * section on "Dynamic records" at the end of the chapter. The type payrollT should
 * be a pointer type whose underlying value is a record containing the number of
 * employees and a dynamic array of the actual employeeT values, as illustrated by the
 * following data diagram.After writing the types that define this data structure, write a function GetPayroll
 * that reads in a list of employees, as shown in the following sample run:Write a program that generates the weekly payroll for a company whose
 * employment records are stored using the type payrollT, as defined in the preceding
 * exercise. Each employee is paid the salary given in the employee record, after
 * deducting taxes. Your program should compute taxes as follows:
 * o Deduct $1 from the salary for each withholding exemption. This figure is the
 * adjusted income. (If the result of the calculation is less than 0, use 0 as the adjusted
 * income.)
 * o Multiply the adjusted income by the tax rate, which you should assume is a
 * flat 25 percent.
 * For example, Bob Cratchit has a weekly income of $15. Because he has seven
 * dependents, his adjusted income is $15 - (7 x $1), or $8. Twenty-five percent of $8
 * is $2, so Mr. Cratchit's net pay is $15 - $2, or $13.
 * The payroll listing should consist of a series of lines, one per employee, each of
 * which contains the employee's name, gross pay, tax, and net pay. The output should
 * be formatted in columns, as shown in the following sample run:
 * Hint:Hint: First download the program provided by the textbook and Makefile that I put under hw-
 * 03 directory or attached here. Compile and execute it. Once you understand what it does, then
 * implement the necessary functions, data structures etc. to do the things in the above questions.
 * In this program, you will find some already implemented code which can help to complete this
 * hw. (Also, I changed all the textbook specific memory allocation things with malloc in that
 * program. So you can simply use gcc to compile it, but you need to check if memory is allocated or not after each malloc. Please make these corrections, too!).*/




#include <string.h>
#include <stdlib.h>
#include <stdio.h>




struct employeeT{
char name;
char job[20];
char ss[11];
double weekly_p;
int withholdings;
};

struct GetPayroll{




};



