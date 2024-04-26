##Programa: flags.s

## MACROS
	.equ SYS_EXIT, 1
	.equ SUCCESS, 0
## VARIABLES LOCALES
	.data
## INSTRUCCIONES
	.global main
	.text
main:
## RESET
	xor %eax,%eax
	xor %ebx,%ebx
	
	mov $0xFFFFFFFF, %eax
	shr $1, %eax
	add %eax, %eax
	test $0xFF, %al 
	cmp $0xFFFFFFFF, %eax  ##cmp = cmpl
## SALIDA
	mov $SYS_EXIT, %eax
	mov $SUCCESS, %ebx
	int $0x80
	.end
