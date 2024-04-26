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

	mov $0x00AA, %ax
	mov $0xFF00, %bx
	cmp %bx, %ax
	ja salto1
	jg salto2
salto1:
	mov $0xFF, %ebx
salto2:
	mov $1, %eax
## SALIDA
	int $0x80
	.end
