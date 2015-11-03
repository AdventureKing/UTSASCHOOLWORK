#!/usr/bin/perl -w
use warnings;

$input=$ARGV[0];

open(MYFILE,$input) or die "ERROR: the INPUT is not existing";
while(<MYFILE>)
{
	$myLine = $_;

	print $myLine;

}
