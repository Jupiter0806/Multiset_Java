#!/bin/bash
# stop when error
set -e
javac *.java
echo "Completed Compilation, about to run"
java MultisetTester linkedlist test.txt