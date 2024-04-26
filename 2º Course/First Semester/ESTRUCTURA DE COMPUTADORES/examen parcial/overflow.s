#### explicación de la relación de CF y OF con el oveflow
#### las instrucciones ADD y SUB hacen la interpretación de SIMULTANEA de operación CON y SIN signo
#### CF es el resultado del overflow de la operación (add,sub,etc) SIN signo
#### OF es el resultado del overflow de la operación (add,sub,etc) CON signo
#### ¿En qué operaración aritmética CF NO es overflow sin signo y  SÍ es un CARRY puro?


	.section .data
oper1:	.byte 0x80 # con signo -128 y sin signo 256
oper2:  .byte 0x7F # con signo +127 y sin signo 255

oper3: 	.byte 0x00 # con signo 0 y sin signo 0
oper4:	.byte 0xFF # con signo -1 y sin signo 511

	.section .text
	.global main
main:	push %ebp
	mov %esp,%ebp

	mov oper1,%al
	mov oper2,%bl
	mov oper3,%cl
	mov oper4,%dl

	subb $0x01,%al # comprobar que sin signo no hay overflow (CF=0) y con signo sí hay overflow (OF=1)
	addb $0x01,%bl # comprobar que sin signo no hay overflow (CF=0) y con signo sí hay overflow (OF=1)

	subb $0x01,%cl # comprobar que sin signo sí hay overflow (CF=1) y con signo no hay overflow (OF=0)
	addb $0x01,%dl # comprobar que sin signo sí hay overflow (CF=1) y con signo no hay overflow (OF=0)

	mov %esp,%ebp
	pop %ebp
	ret
	.end
