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