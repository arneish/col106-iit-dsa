#!/bin/sh
javac *.java

declare -a arr=("small_test_case_without_print.txt" "small_test_case_without_delete.txt" "medium_test_case.txt" "large_test_case_without_print.txt" "large_test_case.txt")

for i in "${arr[@]}"
do
    java BestFit $i > student_$i
    python checker.py ans_$i student_$i
done