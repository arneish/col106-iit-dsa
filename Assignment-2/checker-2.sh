javac *.java
java $1 test3.txt > test3_o.txt
java $1 test4.txt > test4_o.txt
java $1 test6.txt > test6_o.txt
java $1 test10.txt > test10_o.txt
java $1 test12.txt > test12_o.txt

java Checker test3.txt test3_o.txt
java Checker test4.txt test4_o.txt
java Checker test6.txt test6_o.txt
java Checker test10.txt test10_o.txt
java Checker test12.txt test12_o.txt
