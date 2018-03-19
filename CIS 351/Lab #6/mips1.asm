lui $s0, 0x0100 # This is our base address
ori $s0, $s0, 0x0664 # This is also our base address
addi $t1, $0, 10 # This is our limit variable
addi $t0, $0, 0 # This is our maximum counter
add $s1, $s0, $0 # Temporary array

for:
  beq $t0, $t1, done
  lw $t2, 0($s1)
  add $t2, $t2, $t0
  sw $t2, 0($s1)
  addi $t0, $t0, 1
  sll $t3, $t0, 2
  add $s1, $s0, $t3
  j for
done:
  