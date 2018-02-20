.globl double triple # make the labels "double" and "triple" available to code in other files
.text 				 # signify that the source code starts here

double:				 # labels the line of code where the procedure "double" begins
	add $v0, $a0, $a0
	jr $ra			 # return to the calling procedure

triple: 			 # labels the line of code where the procedure "triple" begins
	add $v0, $a0, $a0
	add $v0, $a0, $a0
	jr $ra			 # return to the calling procedure 	
