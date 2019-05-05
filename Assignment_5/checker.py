# VVIP READ THIS!!!!
# this is just to help you automate the process. please go through this and modify it as per you need

import os,sys

test_case_dir = 'test_cases/'
solution_dir = 'solutions/'
student_dir = 'student/'
student_executable = 'SuffixTree'
# memory_size = '1024m'

os.system("javac *.java")
os.system("rm -rf " + student_dir)


if not os.path.exists(student_dir):
	os.makedirs(student_dir)


for filename in os.listdir(test_case_dir):
	test_case = test_case_dir + filename
	solution = solution_dir + "out_" + filename
	student_answer = student_dir + 'student_' + filename
	os.system(' '.join(['java', student_executable, test_case, student_answer]))
# 	os.system(' '.join(['java', '-Xmx' + memory_size,student_executable, test_case, student_answer]))
	# os.system(' '.join(['./a.out', '<', test_case, '>', student_answer]))
	if os.path.isfile(student_answer):
		diff = os.popen(' '.join(['diff', '-b', student_answer, solution])).read()
		if diff == '':
			print ("Test case " + test_case + " passed. ")
		else:
			print ("Test case " + test_case + " failed. Please recheck. Wrong Answer")
	else:
		print ("Test case " + test_case + " failed. Please recheck. File was not created due to some error in code")

