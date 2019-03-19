import os, sys

gold_file = sys.argv[1]
student_file = sys.argv[2]

with open(gold_file,"r") as file:
	gold_lines = file.readlines()

with open(student_file,"r") as file:
	student_lines = file.readlines()


student_values = []
object_bins = []
for line in student_lines:
	tokens = line.split()
	if len(tokens)==2:
		object_bins.append([int(tokens[0]), int(tokens[1])])
	if len(tokens)==1:
		if len(object_bins) > 0:
			student_values += (sorted(object_bins))
			object_bins = []
		student_values.append([int(tokens[0])])

if len(object_bins) > 0:
	student_values.append(sorted(object_bins))

if len(gold_lines) == 0:
	print("Empty gold/actual_answers file")
	exit(0)

for i,line in enumerate(gold_lines):
	tokens = line.split()
	if len(tokens) ==1:
		if not student_values[i][0] == int(tokens[0]):
			print("The test case " + gold_file + "failed. Please recheck the program")
			exit(0)
	if len(tokens) == 2:
		if not (student_values[i][0] == int(tokens[0]) and student_values[i][1] == int(tokens[1])):
			print("The test case " + gold_file + "failed. Please recheck the program")
			exit(0)

print ("The test case " + gold_file + " passed")
