/*Brandon snow
 * 
 * File: employee.c
 * ----------------
 * This program tests the functions defined for records of type
 * employeeRecordT and employeeT.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * Type: string
 * ------------
 * The type string is identical to the type char *, which is
 * traditionally used in C programs.  The main point of defining a
 * new type is to improve program readability.   At the abstraction
 * levels at which the type string is used, it is usually not
 * important to take the string apart into its component characters.
 * Declaring it as a string emphasizes this atomicity.
 */

typedef char *string;



/*
 * Constants
 * ---------
 * MaxEmployees -- Maximum number of employees
 */

#define MaxEmployees 100

/*
 * Type: employeeRecordT
 * ---------------------
 * This structure defines a type for an employee record.
 */

typedef struct {
    string name;
    string title;
    string ssnum;
    double salary;
    int withholding;
} employeeRecordT;

/*
 * Type: employeeT
 * ---------------
 * This type definition introduces an employeeT as a
 * pointer to the same record type as before.
 */

typedef struct {
    string name;
    string title;
    string ssnum;
    double salary;
    int withholding;
} *employeeT;

/*
 * Type: payrollT
 * --------------
 * This type represents an entire collection of employees.  The
 * type definition uses a dynamic array of employeeT values to
 * ensure that there is no maximum bound imposed by the type.
 * The cost of this design is that the programmer must explicitly
 * allocate the storage for the array using NewArray.
 */

typedef struct {
    employeeT *employees;
    int nEmployees;
} *payrollT;

/*
 * Global variables
 * ----------------
 * staff       -- Array of employees
 * nEmployees  -- Number of employees
 * manager     -- Used to produce a figure for the code
 */

static employeeRecordT staff[MaxEmployees];
static int nEmployees;

/* Private function declarations */

static void InitEmployeeTable(void);
static payrollT CreatePayroll(employeeRecordT staff[], int nEmployees);
static void ListEmployees(payrollT payroll);
static double AverageSalary(payrollT payroll);
static void getpayroll();
static double Income_Tax(payrollT payroll, int i);

/* Main program */

int main()
{	
	int i;
    payrollT payroll; 
    getpayroll();
    payroll = (CreatePayroll(staff, nEmployees));
    ListEmployees(payroll);
	
	for(i=0;i<nEmployees;i++){
		
		free(payroll->employees[i]);
		free(staff[i].name);
		free(staff[i].title);
		free(staff[i].ssnum);
		
		
		
				  }
		
		
		free(payroll->employees);
		free(payroll);
	return 0;
}
/*not needed but left in*/
/*static void InitEmployeeTable(void)
{
    employeeRecordT empRec;

    empRec.name = "Ebenezer Scrooge";
    empRec.title = "Partner";
    empRec.ssnum = "271-82-8183";
    empRec.salary = 250.00;
    empRec.withholding = 1;
    staff[0] = empRec;
    empRec.name = "Bob Cratchit";
    empRec.title = "Clerk";
    empRec.ssnum = "314-15-9265";
    empRec.salary = 15.00;
    empRec.withholding = 7;
    staff[1] = empRec;
    nEmployees = 2;
}
*/
/*create payroll with the 5 fields*/

static payrollT CreatePayroll(employeeRecordT staff[], int nEmployees)
{
    payrollT payroll;
    int i;

    payroll = (payrollT) malloc(sizeof *payroll); /* New(payrollT);*/
    payroll->employees = (employeeT *) malloc(nEmployees*sizeof(employeeT)); /* NewArray(nEmployees, employeeT);*/
    payroll->nEmployees = nEmployees;
    for (i = 0; i < nEmployees; i++)
   {
        payroll->employees[i] = (employeeT)malloc(sizeof *((employeeT) NULL));   /* (New(employeeT);*/
        payroll->employees[i]->name = staff[i].name;
        payroll->employees[i]->title = staff[i].title;
        payroll->employees[i]->ssnum = staff[i].ssnum;
        payroll->employees[i]->salary = staff[i].salary;
        payroll->employees[i]->withholding = staff[i].withholding;
    }
	
    return (payroll);
}
/*fill my payroll*/
static void getpayroll(void)
{
	employeeRecordT empRec;
	int z;
	int i;
	char buff[1024];
	
	int lenmax=100;
	string temp2,temp1,temp3;
	printf("How many employees do you have to enter?:");
	fgets(buff,1023,stdin);
	z=atoi(buff);
		
		
		
	
	for(i=0;i<z;i++)
		{

			if(z > MaxEmployees)
			{
			printf("\nThis program can only accept 100 employees for your payroll. To calculate the payroll for a business with more employees please upgrade to Pro.\n");
				break;
	
			}
			printf("Employee Name (%d):",i+1);
			fgets(buff,1023,stdin);
			temp1=malloc(sizeof(char)* lenmax);
			buff[strlen(buff)-1] = '\0';
			strcpy(temp1,buff);
			empRec.name=temp1;
		
			
			printf("Title:");
			fgets(buff,1023,stdin);
			temp2=malloc(sizeof(char)* lenmax);
			buff[strlen(buff)-1] = '\0';
			strcpy(temp2,buff);
			empRec.title=temp2;
			
			printf("Employee Social Security Number:");
			fgets(buff,1023,stdin);
			buff[strlen(buff)-1] = '\0';
			temp3=malloc(sizeof(char)* lenmax);
			strcpy(temp3,buff);
			empRec.ssnum=temp3;

			printf("Employee's Salary:");
			scanf("%lf%*c",&empRec.salary);

			printf("Witholding exemptions:");
			scanf("%d%*c",&empRec.withholding);
			
			
			
			memcpy(&staff[i],&empRec,sizeof(employeeRecordT));	
			
			

		}
		nEmployees=z;
			
		
}
/*print out your table of employees entered*/
static void ListEmployees(payrollT payroll)
{
    int i;
    double tax, net;
	printf("Name                     Gross      Tax     Net\n");
    	printf("--------------------------------------------------\n");
    for (i = 0; i < payroll->nEmployees; i++)
    {	
	tax=Income_Tax(payroll,i);
	net= payroll->employees[i]->salary - tax;
        printf("%-22s %7.2lf - %5.2lf = %6.2lf\n", payroll->employees[i]->name, payroll->employees[i]->salary,tax,net);
    
    }
}
/*compute the income tax*/
static double Income_Tax(payrollT payroll, int i)
{
    double salary, adjustedincome;
    double final_pay;
    
        salary = payroll->employees[i]->salary;
	adjustedincome = salary -(payroll->employees[i]->withholding * 1);    
	final_pay = adjustedincome * .25;    
	return(final_pay);
}

