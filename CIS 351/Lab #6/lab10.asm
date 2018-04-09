.text
.globl nCk

nCk:
	addi $sp, $sp, -4
	sw $ra, 0($sp)

	jal nChoosek 	

	lw $ra, 0($sp)
	addi $sp, $sp, 4	
	jr $ra
	
nChoosek:

	addi $sp, $sp, -16 
	sw $ra, 0($sp)
	sw $s0, 4($sp)
	sw $s1, 8($sp)
	sw $s2, 12($sp)

	add $s0, $a0, $0 # new n
	add $s1, $a1, $0 # new k

	addi $t1, $0, 1
	beq $s1, $0, return1 # if n = 0, return 1
	beq $s1, $t1, returnN # if n = 1, return 1
	beq $s0, $s1, return1 # if n = k, return N 

	addi $a0, $s0, -1 # n - 1
	
	# Do n-1 choose k
	jal nChoosek

	add $s2, $0, $v0 # this is our result of the previous function

	addi $a0, $s0, -1 # n - 1
	addi $a1, $s1, -1 # k - 1

	# Do n-1 choose k-1
	jal nChoosek               

    add $v0, $v0, $s2 # This is the result of n-1 choose k-1 and n-1 choose k
exit:
	lw $ra, 0($sp)       
	lw $s0, 4($sp)
	lw $s1, 8($sp)
	lw $s2, 12($sp)
	addi $sp, $sp, 16
	jr $ra

return1:
	addi $v0, $0, 1
	j exit
returnN:     
	add $v0, $s0, $0
	j exit
