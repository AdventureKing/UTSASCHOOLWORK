#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
fi

input=$1;
output=$2;
#array containing words
wordArray=;
#array containing the count of words
wordCount=;
#index to inserted words
wordArrayIndex=0;
#index to word count array
wordCountIndex=0;


function createArray {
#rename for help

wordInput=$1;

#master for loop that will find my words and if they exist already in my array just increament that index

for ((i=0; i < $wordArrayIndex; ++i)); do
    if [ "$wordInput" = "${wordArray[$i]}" ]; then
				        
				#echo "Found hostname at index $i"
				((wordCount[$i]++))
					return
    fi
done
	wordArray[${wordArrayIndex}]=$wordInput;
	
		wordCount[$wordCountIndex]=1;
		wordArrayIndex=$wordArrayIndex+1;
		wordCountIndex=$wordCountIndex+1;
}


while read -r line1; 
 do 
	if [ -f "$line1" ]; then
		STR=$line1;
		while read line2
			do
    			name=$line2
						for singleWord in $name; do
							#echo "Text read from file - $singleWord"
							 createArray $singleWord
						done 			
			done < $STR       
	 fi	
 done < $(find $input);


for ((j=0; j < $wordArrayIndex; ++j)); do
		echo "${wordArray[$j]} ${wordCount[$j]}"
done

#printf "%s %s\n" "${wordArray[@]} ${wordCount[@]}" > $output;
