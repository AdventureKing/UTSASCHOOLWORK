/**********************************************************************
cs3843p1.h
Purpose:
    This defines the typedefs, prototypes, and constants used
    for a program which demonstrates a hex dump function. 
Notes:

**********************************************************************/

/* Inventory is used for one stock item
*/
typedef struct
{
    char        szStockNumber[7];           // Stock Number for a stock item
    long        lStockQty;                  // quantity in stock
    double      dUnitPrice;                 // price per unit of stock
    char        szStockName[31];            // name of the stock item
} Inventory;

/* Customer is used for one customer record
*/
typedef struct 
{
    char        szEmailAddr[51];            // customer's email address is used
                                            // to uniquely define a customer
    char        szFullName[31];             // customer full name
    char        szStreetAddress[31];        // street number, street name, and 
                                            // (optional) apartment number
    char        szCity[21];                 // address city
    char        szStateCd[3];               // address state code
    char        szZipCd[6];                 // address zip code
} Customer;

#define FALSE           0
#define TRUE            1

/*  Error Messages */
#define ERR_MISSING_SWITCH          "missing switch"
#define ERR_EXPECTED_SWITCH         "expected switch, found"
#define ERR_MISSING_ARGUMENT        "missing argument for"

#define ERR_COMMAND_FILENAME        "invalid Command file name, found "
#define ERR_OUTPUT_FILENAME         "invalid Output file name, found "
#define ERR_BAD_INVENTORY_DATA      "invalid Inventory data "
#define ERR_BAD_CUSTOMER_DATA       "invalid Customer Data "
#define ERR_BAD_NUMERIC_COMMAND     "invalid NUMERIC command" 
#define ERR_INVALID_COMMAND         "unknown command, found "
#define ERR_BAD_NR_OF_REC           "invalid number of records "
#define ERR_BAD_SUBSCRIPT           "bad subscript, found "
#define ERR_MISSING_END             "missing END within a major command "

#define ERR_INVALID_HEXDUMP_PARM    "invalid hexDump parameter "

/* program return codes */
#define ERR_COMMAND_LINE_SYNTAX     -1      // invalid command line syntax
#define USAGE_ONLY                  -2      // show usage only
#define ERROR_PROCESSING            -3      // error during processing

int hexDump(char *psbBuffer, int iBufferLength, int iBytesPerLine);
void exitError(char *pszMessage, char *pszDiagnosticInfo);
void exitUsage(int iArg, char *pszMessage, char *pszDiagnosticInfo);

