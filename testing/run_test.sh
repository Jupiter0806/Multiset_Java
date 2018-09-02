#!/bin/bash

python assign1TestScript.py -v ../src linkedlist ./tests/test*.in
python assign1TestScript.py -v ../src sortedlinkedlist ./tests/test*.in
python assign1TestScript.py -v ../src bst ./tests/test*.in