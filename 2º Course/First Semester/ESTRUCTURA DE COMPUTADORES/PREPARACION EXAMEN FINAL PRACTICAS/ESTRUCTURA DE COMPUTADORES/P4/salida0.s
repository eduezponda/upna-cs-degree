#	.global _start
#	.section .text
#_start:
#	push $0xFF #return code
#	call exit #libc library
#	.end

	.global _start
	.section .text
_start:
	push $0xFF #return code
	push $1 # exit syscall code
	call syscall #libc library
	.end
