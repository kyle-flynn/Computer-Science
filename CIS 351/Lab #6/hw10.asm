main:
  addi $a0, $0, 3
  addi $a1, $0, 7
  addi $a2, $0, 2

  j wackySum

wackySum:
  addi $v0, $0, 0 # sum = 0
  addi $sp, $sp, -16
  addi $s0, $a0, 0 # i = 0
      
  sw $ra, 0($sp)
  sw $a0, 4($sp)
  sw $a1, 8($sp)
  sw $a2, 12($sp)

  jal loop
  
  lw $ra, 0($sp)
  addi $sp, $sp, 16
  jr $ra

loop:
  lw $t0, 4($sp)
  lw $t1, 8($sp)
  lw $t2, 12($sp)

  # combineFour(i, (i+1)/2, (i+2)/2, (i+3));
  addi $a0, $s0, 0
  addi $a1, $s0, 1
  addi $a2, $s0, 2
  addi $a3, $s0, 3
  div $a1, $a1, 2
  div $a2, $a2, 2

  add $s1, $s1, $v0

  jal combineFour

  add $v0, $v0, $s1

  add $s0, $s0, $t2
  slt $t3, $s0, $t1 # if a < b, t0 = 1
  
  beq $s0, $t1, loop
  beq $t3, 1, loop
  beq $t3, 0, exit

combineFour:
  add $v0, $a0, $a1
  add $v0, $v0, $a2
  add $v0, $v0, $a3
  
  addi $sp, $sp, -8
  sw $v0, 0($sp)
  sw $ra, 4($sp)
  
  rem $t0, $v0, 2
  beq $t0, 1, returnHalf
  lw $ra, 4($sp)
  addi $sp, $sp, 8
  jr $ra

returnHalf:
  lw $v0, 0($sp)
  lw $ra, 4($sp)
  addi $sp, $sp, 8
  div $v0, $v0, 2
  jr $ra

exit:
  lw $ra, 0($sp)
  addi $sp, $s0, 16
  jr $ra
