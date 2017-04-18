# Simple method that calculates a factorial through recursion.
def factorial(number):
	if number <= 0:
		return 1
	else:
		return number * factorial(number-1)
		
print (factorial(3))
print (factorial(0))
print (factorial(5))

# Simple method that calculates n choose k.
def choose(n, k):
	return factorial(n) / (factorial(k) * factorial(n-k))

print (choose(5,3))
print (choose(5,5))
	
# Simple method that calculates n permute k.
def perm(n, k):
	return factorial(n) / factorial(n-k)

print (perm(5, 3))
print (perm(5, 5))
print (perm(5, 0))

# Method that calculates the number of possible lattice paths given a dictionary.
def lat_two(dict):

	# Since reading dictionaries is hard, let's turn everything into a list.
	x_vals = []
	y_vals = []
	
	# Looping through the key and values of a dictionary.
	for x, y in dict.items():
		x_vals.append(x)
		y_vals.append(y)
	
	# Formula for number of possible lattice paths is steps choose lefts.
	steps = (x_vals[1] - x_vals[0]) + (y_vals[1] - y_vals[0])
	lefts = (x_vals[1] - x_vals[0])
	
	return choose(steps, lefts)
		
print (lat_two({0:0, 5:3}))
print (lat_two({2 : 5, 3 : 5}))

# Method that calculates the number of possible lattice paths given a dictionary.
def lat_three(dict):

	# Since reading dictionaries is hard, let's turn everything into a list.
	x_vals = []
	y_vals = []
	
	# Looping through the key and values of a dictionary.
	for x, y in dict.items():
		x_vals.append(x)
		y_vals.append(y)
	
	# Calculate total steps and left steps from our first 2 points.
	steps_1 = (x_vals[1] - x_vals[0]) + (y_vals[1] - y_vals[0])
	lefts_1 = (x_vals[1] - x_vals[0])
	
	# Calculate total steps and left steps from our last 2 points.
	steps_2 = (x_vals[2] - x_vals[1]) + (y_vals[2] - y_vals[1])
	lefts_2 = (x_vals[2] - x_vals[1])
	
	return choose(steps_1, lefts_1) * choose(steps_2, lefts_2)	
	
print (lat_three({0 : 0, 2 : 1, 5 : 3}))
print (lat_three({2 : 5, 3 : 5, 5 : 5}))

# This method is a recursion nightmare. However, it works (to my surprise). 
def list_perm(list):

	# If empty list, return empty list.
	if len(list) == 0:
		return []
 
    # if one item in the list, then one possible permutation. 
	if len(list) == 1:
		return [list]
 
	# Empty list that will store all permutations
	perm_list = []
 
    # For every item in the list, loop through each index
	for i in range(len(list)):
				
		# Make the first item the one we are permuting around.
		first = list[i]
		
		# The remaining items in the list by slicing indices.
		remaining = list[:i] + list[i+1:]
		
		# Using recursion, we find all possible permutations of our
		# first element. Eventually, the first element will be the
		# next element, and the process will repeat. 
		for p in list_perm(remaining):
			perm_list.append([first] + p)
	
	# After our permutations are made, make one final check
	# to see if there are duplicates. Basically, we are making
	# a set of the list of permutations. 
	new_perm_list = []
	
	for perm in perm_list:
		if not perm in new_perm_list:
			new_perm_list.append(perm)
			
	return new_perm_list

print (list_perm([1, 2, 3]))
print (list_perm([1, 1, 3]))
print (list_perm([1, 2, 3, 4]))