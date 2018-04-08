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
  lw $a0, 4($sp)
  lw $a1, 8($sp)
  lw $a2, 12($sp)

  add $s0, $s0, $a2
  slt $t0, $s0, $a1 # if a < b, t0 = 1
  
  beq $t0, 1, loop
  beq $t1, 0, exit

combineFour:

exit:
  lw $ra, 0($sp)
  addi $sp, $s0, 16
  jr $ra