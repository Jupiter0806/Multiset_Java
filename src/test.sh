#!/bin/bash
# stop when error
set -e
javac *.java
echo "Completed Compilation, about to run"
# <linkedlist | sortedlinkedlist | bst| hash | baltree>
java MultisetTester bst test.txt