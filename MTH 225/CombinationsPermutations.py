def factorial(number):
	if number <= 0:
		return 1
	else:
		return number * factorial(number-1)
		
print (factorial(3))
print (factorial(0))
print (factorial(5))

def choose(n, k):
	return factorial(n) / (factorial(k) * factorial(n-k))

print (choose(5,3))
print (choose(5,5))
	
def perm(n, k):
	return factorial(n) / factorial(n-k)

print (perm(5, 3))
print (perm(5, 5))
print (perm(5, 0))

def lat_two(dict):
	x_vals = []
	y_vals = []
	for x, y in dict.items():
		x_vals.append(x)
		y_vals.append(y)
	
	steps = (x_vals[1] - x_vals[0]) + (y_vals[1] - y_vals[0])
	lefts = (x_vals[1] - x_vals[0])
	
	return choose(steps, lefts)
		
print (lat_two({0:0, 5:3}))
print (lat_two({2 : 5, 3 : 5}))

def lat_three(dict):
	x_vals = []
	y_vals = []
	for x, y in dict.items():
		x_vals.append(x)
		y_vals.append(y)
	
	steps_1 = (x_vals[1] - x_vals[0]) + (y_vals[1] - y_vals[0])
	lefts_1 = (x_vals[1] - x_vals[0])
	
	steps_2 = (x_vals[2] - x_vals[1]) + (y_vals[2] - y_vals[1])
	lefts_2 = (x_vals[2] - x_vals[1])
	
	return choose(steps_1, lefts_1) * choose(steps_2, lefts_2)	
	
print (lat_three({0 : 0, 2 : 1, 5 : 3}))
print (lat_three({2 : 5, 3 : 5, 5 : 5}))

def list_perm(list):

# If lst is empty then there are no permutations
	if len(list) == 0:
		return []
 
    # If there is only one element in lst then, only
    # one permuatation is possible
	if len(list) == 1:
		return [list]
 
    # Find the permutations for lst if there are
    # more than 1 characters
 
	perm_list = [] # empty list that will store current permutation
 
    # Iterate the input(lst) and calculate the permutation
	for i in range(len(list)):
		print ("Current: " + str(list))
		first = list[i]
		   # Extract lst[i] or m from the list.  remLst is
		   # remaining list
		remaining = list[:i] + list[i+1:]
		   # Generating all permutations where m is first
		   # element
		for p in list_perm(remaining):
			print ("Appended: " + str([first]) + str(p))
			perm_list.append([first] + p)
	return perm_list

print (list_perm([1, 2, 3]))