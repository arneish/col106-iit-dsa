import os, sys
import random


text_size = 100
query_count = 100
query_size = 9

character_set = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ .0123456789"
len_charset  = len(character_set)

text_list = []
for i in range(text_size):
	ind = random.randint(0,len_charset-1)
	text_list.append(character_set[ind])

text  = ''.join(text_list)
print(text)

print(query_count)

def generate_pattern():
	query_lim = random.randint(8,50)
	start_ind = random.randint(0,(text_size - query_lim-1))
	pattern = ''
	for j in range(query_size):
		if query_size/2 == j:
			pattern +='*'
			continue
		if j%2==0:
			pattern +='?'
			continue
		if j < query_size/2:
			pattern += text[start_ind+j]
		else:
			k = query_size - j
			pattern += text[start_ind+query_lim - k - 1]
		# pattern += '?'
		# pattern += text[start_ind+2]
		# pattern += '*'
		# pattern += '?'
		# pattern += text[start_ind+query_lim-2]
		# pattern += '?'
		# pattern += text[start_ind+query_lim]
	return pattern

for q in range(query_count):
	pattern = generate_pattern()
	while (' ' in pattern or '.' in pattern):
		pattern = generate_pattern()
	print(pattern)



