#!/usr/bin/perl -w

$input=$ARGV[0];

open(MYFILE,$input) or die "penis ha ha!";
while(<MYFILE>)
{
	chomp;
$word = $_;
print reverse($word);

}
$string = "Hello World";
print "Reversed Value is ", scalar reverse("$string"), "\n";
