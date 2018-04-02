.globl nCk
.text

# https://bitbucket.org/trebsirk/algorithms/src/9728989fdff75481cc419593f4189e6e07132287/nchoosek.py?at=master&fileviewer=file-view-default
# https://stackoverflow.com/questions/20381190/mips-assembly-trying-to-write-a-recursive-program-to-calculate-ncr-combinatio
# a0 is param 1 == "nCk"
# a1 is param 2  == n
# a2 is param 3 == k

j main

nCk:
	addi $sp, $sp, -12 # Make stack space for 2 registers
	sw $ra, 0($sp)     # Save the return address
	sw $a1, 4($sp)    # Store a1 on the stack
	sw $a2, 8($sp)    # Store a2 on the stack

check:
	beq $a2, 0, end   # if k == 0 end
	beq $a2, $a1, end # if k == n end
	jal recurse
	jr $ra
	

main:
	
	
	
	j recurse
	
recurse:
	addi $a0, 
end: