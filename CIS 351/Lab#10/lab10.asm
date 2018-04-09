.text
.globl main

main:
	# Initialize the a0 register with a value that we want to see for fibonacci.
	li $a0, 5
	li $a1, 4
	
	addi $sp, $sp, -4
	sw $ra, 0($sp)

	jal nCk 	

	lw $ra, 0($sp)
	addi $sp, $sp, 4	
	jr $ra
	
nCk:

	addi $sp, $sp, -16 
	sw $ra, 0($sp)
	sw $s0, 4($sp)
	sw $s1, 8($sp)
	sw $s2, 12($sp)

	add $s0, $a0, $0 # new n
	add $s1, $a1, $0 # new k

	addi $t1, $0, 1
	beq $s0, $0, return1 # if n = 0, return 1
	beq $s0, $t1, returnN # if n = 1, return n
	beq $s0, $s1, return1 # if n = k, return 1 

	addi $a0, $s0, -1 # n - 1
	
	# Do n-1 choose k
	jal nCk

	add $s2, $0, $v0 # this is our result of the previous function

	# Do n-1 choose k-1
	addi $a1, $s1, -1 # k - 1

	jal nCk               

	add $v0, $v0, $s2 # This is the result of n-1 choose k-1 and n-1 choose k
exit:

	lw $ra, 0($sp)       
	lw $s0, 4($sp)
	lw $s1, 8($sp)
	addi $sp, $sp, 12     
	jr $ra

return1:
	li $v0, 1
	j exit
returnN:     
	li $v0, $s0
	j exit