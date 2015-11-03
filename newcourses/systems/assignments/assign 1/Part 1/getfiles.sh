#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
fi

PASSED=$1
OUTPUT=$2
 
if [[ -d $PASSED ]]; then
    echo "$PASSED is a directory"
	 find "$PASSED" -type f > $OUTPUT  
elif [[ -f $PASSED ]]; then
    echo "$PASSED is a file"
		find "$PASSED" -type f > $OUTPUT  
else
    echo "$PASSED is not valid"
	  exit 1
fi


