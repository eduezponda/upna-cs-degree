    .section .text
    
    .equ TAMANO, 3

    .global pixels_generator_circulo
    .type pixels_generator_circulo, @function
    .type pintar, @function

pixels_generator_circulo:

    #PROLOGO
    pushl %ebp
    mov %esp,%ebp
    
    #SALVAR CONTEXTO
    push %eax
    push %ebx
    push %ecx
    push %edx
    push %edi
    push %esi
    
    #CAPTURAR ARGUMENTOS
    #movl 8(%ebp), %eax   # ORIGEN_X
    #movl 12(%ebp), %ebx  # ORIGEN_Y
    #movl 16(%ebp), %ecx  # RADIO/LADO
    #movl 20(%ebp), %edx  # PROPORCION
    #movl 24(%ebp), %edi  # DIMENSION
    #movl 28(%ebp), %esi  # BUFFER
    
    #DEFINIR VARIABLES LOCALES
    subl $32, %esp  # 8 variables locales DE ARRIBA A ABAJO EN PILA: j, i, yn, xn, en, yc, xc, ec
    
    #INICILIZAR PARAMETROS xc,yc,ec : primera iteracción del bucle
        # ec=3-2*radio;
    movl 16(%ebp), %eax # EAX = radio
    imull $2, %eax   # EAX = 2*r
    movl $3, %ebx   # EBX = 3
    subl %eax, %ebx # EBX = EBX (3) - EAX (2*radio) -> mover EBX a EC
    movl %ebx, -28(%ebp)
        
        # xc=radio;
    movl 16(%ebp), %eax # EAX = radio
    movl %eax, -32(%ebp)
    
        # yc=0;
    movl $0, -36(%ebp)
    
    #BUCLE: yc < xc
  
    mov -36(%ebp), %eax     # EAX = YC
    mov -32(%ebp), %ebx     # EBX = XC
    
        #CONDICION ENTRADA EN EL BUCLE: COORDENADAS PRIMER OCTANTE : yc < xc
    cmpl %ebx, %eax
    jns noCondicion
    
        #OBTENCION DE las coordenadas en el primer octante para la siguiente ITERACCION:
        #xn,yn,en
bucle:        
        #if (ec < 0) {
    cmpl $0, -28(%ebp)
    jns primerElse
    
        #    xn=xc;
    movl -32(%ebp), %eax
    movl %eax, -44(%ebp)
        #    yn=yc+1;
    movl -36(%ebp), %eax
    addl $1, %eax
    mov %eax, -48(%ebp)
        #    en = ec + 2*(3+2*yc);
    movl -36(%ebp), %eax    # YC
    imull $2, %eax          # EAX = 2*yc
    addl $3, %eax           # EAX = 3 + 2*yc
    imull $2, %eax          # EAX = 2 * (3+2*yc)
    addl -28(%ebp), %eax    # EAX = EC + EAX
    movl %eax, -40(%ebp)    # EN <- EAX
    
    jmp saltoElse
    
primerElse:

        # xn=xc-1;
    mov -32(%ebp), %eax # EAX = XC
    subl $1, %eax       # EAX(XC) = XC - 1
    movl %eax, -44(%ebp)
        # yn=yc+1;
    movl -36(%ebp), %eax    # EAX = YC
    addl $1, %eax           # EAX = YC+1
    movl %eax, -48(%ebp)    # mover YC+1 a YN
        # en = ec + 2*(5+2*yc-2*xc);
    movl -32(%ebp), %eax    # EAX = XC
    imull $2, %eax          # EAX = 2*xc
    
    movl -36(%ebp), %ebx    # EBX = yc
    imull $2, %ebx          # EBX = 2*yc
    addl $5, %ebx           # EBX = 5+2*yc
    subl %eax, %ebx         # EBX = 5+2*yc - 2*xc
    addl -28(%ebp), %ebx    # EBX = EC + EBX (5+2*yc - 2*xc)
    movl %ebx, -40(%ebp)    # en = EBX

saltoElse:

    #j=origen_x+xc;   // la abscisa x son las columnas , por lo tanto j
    movl 8(%ebp), %eax   # EAX = ORIGEN_X
    addl -32(%ebp), %eax    # EAX = ORIGEN_X + XC
    movl %eax, -56(%ebp)    # j = EAX
    
    #i=origen_y+yc; // la ordenada y son las filas, por lo tanto la i 
    movl 12(%ebp), %eax # EAX = ORIGEN_Y
    addl -36(%ebp), %eax    # EAX = ORIGEN_Y + YC
    movl %eax, -52(%ebp)    # i = EAX
    
        #LLAMAR para cada OCTANTE a la SUBRUTINA PINTAR. Función pintar(i,j,azul,verde,rojo)
        #1º OCTANTE: coordenadas obtenidas según Bresenham (i,j), pintar de color rojo
    pushl $0xFF
    pushl $0x00
    pushl $0x00
    
    movl -56(%ebp), %eax # eax = j
    pushl %eax
    
    movl -52(%ebp), %ebx # ebx = i
    pushl %ebx
    call pintar
        #2º OCTANTE: coordenadas obtenidas por simetría (j,i) , pintar de color verde
    pushl $0x00
    pushl $0xFF
    pushl $0x00
    
    movl -52(%ebp), %ebx # ebx = i
    pushl %ebx
    
    movl -56(%ebp), %eax # eax = j
    pushl %eax
    call pintar
        #3º OCTANTE: coordenadas obtenidas por simetría (-j,i) , pintar de color azul
    pushl $0x00
    pushl $0x00
    pushl $0xFF
    
    movl -52(%ebp), %ebx # ebx = i
    pushl %ebx
    
    movl -56(%ebp), %eax # eax = j
    imull $-1, %eax     # eax = -j
    pushl %eax
    call pintar
        #4º OCTANTE: coordenadas obtenidas por simetría (-i,j) , pintar de color blanco
    pushl $0xFF
    pushl $0xFF
    pushl $0xFF
    
    movl -56(%ebp), %eax # eax = j
    pushl %eax
    
    movl -52(%ebp), %ebx # ebx = i
    imull $-1, %ebx # ebx = -i
    pushl %ebx
    call pintar
        #5º OCTANTE: coordenadas obtenidas por simetría (-i,-j) , pintar de color rojo
    pushl $0xFF
    pushl $0x00
    pushl $0x00
    
    movl -56(%ebp), %eax # eax = j
    imull $-1, %eax # eax = -j
    pushl %eax
    
    movl -52(%ebp), %ebx # ebx = i
    imull $-1, %ebx # ebx = -i
    pushl %ebx
    call pintar
        #6º OCTANTE: coordenadas obtenidas por simetría (-j,-i) , pintar de color verde
    pushl $0x00
    pushl $0xFF
    pushl $0x00
    
    movl -52(%ebp), %ebx # ebx = i
    imull $-1, %ebx #ebx = -i
    pushl %ebx
    
    movl -56(%ebp), %eax # eax = j
    imull $-1, %eax # eax = -j
    pushl %eax
    call pintar
        #7º OCTANTE: coordenadas obtenidas por simetría (j,-i) , pintar de color azul
    pushl $0x00
    pushl $0x00
    pushl $0xFF
    
    movl -52(%ebp), %ebx # ebx = i
    imull $-1, %ebx #ebx = -i
    pushl %ebx
    
    movl -56(%ebp), %eax # eax = j
    pushl %eax
    
    call pintar
        #8º OCTANTE: coordenadas obtenidas por simetría (i,-j) , pintar de color blanco
    pushl $0xFF
    pushl $0xFF
    pushl $0xFF
    
    movl -56(%ebp), %eax # eax = j
    imull $-1, %eax # eax = -j
    pushl %eax
    
    movl -52(%ebp), %ebx # ebx = i
    pushl %ebx
    call pintar
    
        #--
        
        # ec=en;
    movl -40(%ebp), %eax
    movl %eax, -28(%ebp)
        # yc=yn;
    movl -48(%ebp), %eax
    movl %eax, -36(%ebp)
        # xc=xn;
    movl -44(%ebp), %eax
    movl %eax, -32(%ebp)
    
    # yc < xc
    mov -36(%ebp), %eax     # EAX = YC
    mov -32(%ebp), %ebx     # EBX = XC
    
    cmpl %ebx, %eax
    js bucle

    #FIN DEL BUCLE
    
    # EPILOGO
noCondicion:

    addl $32, %esp
    popl %esi
    popl %edi
    popl %edx
    popl %ecx
    popl %ebx
    popl %eax
    
    mov %ebp, %esp
    popl %ebp
    
    ret
    
pintar: # pintar(i, j, azul, verde, rojo)
    # pintar(i,j,azul, verde, rojo, reg_mem);
    #PROLOGO -> Actualizar EBP, Nuevo FRAME
    push    %ebp
    mov     %esp, %ebp
    
    # CUERPO
    mov     112(%ebp), %eax   
    mull    32(%ebp)
    add     12(%ebp), %eax
    mov     $TAMANO, %ecx
    mull    %ecx
    mov     28(%ebp), %ecx
    add     %eax, %ecx
    movb    16(%ebp), %al
    movb    %al, (%ecx)
    movb    20(%ebp), %al
    movb    %al, 1(%ecx)
    movb    24(%ebp), %al
    movb    %al, 2(%ecx)
    
    # EPILOGO
    pop     %ebp
    ret

    #PILA:
    # i
    # j
    # azul
    # verde
    # rojo

    
    .end
