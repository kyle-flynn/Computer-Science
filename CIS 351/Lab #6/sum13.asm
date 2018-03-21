.globl sum13
.text

sum13:
  lw $t1, 0($a0) # $s2 is NEW the base address of the array.
  add $s1, $0, $t0 # This is our SUM variable.
  addi $s0, $0, 0 # This is our I variable.
  addi $t0, $a1, 0 # $a1 stores the LENGTH of the array.
  addi $sp, $sp, -8 # Make room for one word on the stack.
  sw $ra, 4($sp) # Store our return address.
  for:
    beq $s0, $t0, done # If i = length, done!
    sw $s0, 0($sp) # Store our 'i' variable in the stack.
    beq $t1, 13, skip # if nums[i] == 13, skip it!
    add $s1, $s1, $t1 # sum = sum + nums[i]
    lw $s0, 0($sp) # Load our'i' variable from the stack, in case it increased!
    addi $s0, $s0, 1 # i = i + 1
    addi $a0, $a0, 4 # Iterate through array
    lw $t1, 0($a0) # Load our new array value
    j for
  lw $ra 4($sp)
  addi $sp $sp, 8
  jr $ra
  
skip:
   lw $s0, 0($sp) # Get our $s0 from the stack.
   addi $s0, $s0, 1 # Skip counting the nubmer immediately following 13.
   sw $s0, 0($sp) # Store 'i' variable back into the stack.
   jr $ra
done: