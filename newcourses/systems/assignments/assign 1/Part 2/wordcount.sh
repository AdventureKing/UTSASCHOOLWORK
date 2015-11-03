#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
fi

input=$1;
output=$2;

 while read fileext; do 
	if [[ -f $input ]]; then	
		
		while read line2; do
		echo "word = '$line2'"
		a=($line2);
		
		
		done < $line
	fi	
done < $1
  #find . -name "myfile*" -print | xargs grep "myword" $1
