#!/bin/bash


if [ "$#" -ne 2 ]; then
    echo "Illegal number of parameters"
fi
input=$1;
output=$2;
#!/bin/bash



while read -r line
do
	if [ -f $line ];
	then
    nameOfFile=$line
	echo "Text read from file - $nameOfFile";
	fi
done < $input
