#!/usr/local/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
fi

PASSED=$1

 
if [[ -d $PASSED ]]; then
    echo "$PASSED is a directory"
	 echo `find "$1" -type f` > $2 
elif [[ -f $PASSED ]]; then
    echo "$PASSED is a file"
			echo `find -name $1` > "$2"
else
    echo "$PASSED is not valid"
	  exit 1
fi


