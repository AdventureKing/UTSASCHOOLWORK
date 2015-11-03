#!/bin/bash

HELLO=Hello;
function funcHello {
   local HELLO=world;
   echo “$HELLO”;
 }
 echo “hello”;
 echo “$HELLO”;
 funcHello;
 echo “$HELLO”;

