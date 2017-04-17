# Method that converts a list into a set.
def make_set(list):
	
	# Here we define our empty set.
	set = []
	
	# Simply loop through each item in our list parameter.
	for item in list:
	
		# Check if the set doesn't already have the item, then append it.
		if not item in set:
			set.append(item)
	
	return set
	
# Method that converts two lists into a set and finds their intersection.
def inter(list1, list2):

	# Making sets of our lists.
	set1 = make_set(list1)
	set2 = make_set(list2)
	inter_set = []
	
	# Looping through each item in the first set, and then
	# looping through the other set's items to find equals.
	for item1 in set1:
		for item2 in set2:
			if item1 == item2:
				inter_set.append(item1)
	
	return inter_set

# Method that converts two lists into a set and finds their union.
def union(list1, list2):

	# Making sets of our lists.
	set1 = make_set(list1)
	set2 = make_set(list2)
	union_set = []
	
	# Adding the items of the first set to the new set.
	for item in set1:
		union_set.append(item)
	
	# Adding the items of the second set to the new set.	
	for item in set2:
		union_set.append(item)
		
	# Simply make a set of the new set to remove duplicates.
	return make_set(union_set)
	
def sym_diff(list1, list2):
	
	# Making sets of our lists.
	set1 = make_set(list1)
	set2 = make_set(list2)
	sym_diff_set = []
	
	# Adding the items of the first set to the new set.
	for item in set1:
		sym_diff_set.append(item)
				
	# Checking if elements already exist, finding the symmetric difference.
	for item in set2:
		if item in sym_diff_set:
			sym_diff_set.remove(item)
		else:
			sym_diff_set.append(item)
			
	return sym_diff_set
	
print (sym_diff([1, 2, 3], [3, 4, 5]))
print (sym_diff([1, 2, 3, 3], [4, 5, 6, 4]))
print (sym_diff(["Tom", "Jerry"], ["Jerry", "Tom"]))

def PIE_two(list1, list2):

	# Making sets of our lists.
	set1 = make_set(list1)
	set2 = make_set(list2)
	pie_set = union(set1, set2)
	
	# Simply return the length of the set.
	return len(pie_set)
	
def PIE_three(list1, list2, list3):

	# Making sets of our lists.
	set1 = make_set(list1)
	set2 = make_set(list2)
	set3 = make_set(list3)
	
	# Make unions of our sets.
	pie_set1 = union(set1, set2)
	pie_set2 = union(pie_set1, set3)
	
	# Simply return the length of the second union set.
	return len(pie_set2)
	
print (PIE_two([1, 3, 3, 5], [1, 5, 7]))
print (PIE_three([1, 3, 5], [1, 5, 1, 7], ["Ben", "Jerry"]))