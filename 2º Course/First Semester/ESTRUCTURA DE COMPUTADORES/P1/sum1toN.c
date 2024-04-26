/*
Programa: sum1toN.c
Descripción: realiza la suma de la serie 1,2,3,...N
Es el programa en lenguaje C equivalente a sum1toN.ias de la máquina IAS de von
Neumann
y equivalente al programa sum1toN.s en lenguaje ensamblador AT&T
Lenguaje: C99
Descripción: Suma de los primeros 5 números naturales
Entrada: Definida en una variable
Salida: Sin salida periférica<<<<s
Compilación: gcc -m32 -g -o sum1toN sum1toN.c
S.O: GNU/linux 5.4.0-128-generic ubuntu 20.04 x86-64
Librería: /usr/lib/x86_64-linux-gnu/libc.so
PC: ThinkPad L560 product: 20F1S0H400 serial: MP15YSW7
CPU: Intel(R) Core(TM) i5-6300U CPU @ 2.40GHz width:64 bits
Compilador: gcc version 9.4.0
Ensamblador: GNU assembler version 2.34
Linker/Loader: GNU ld (GNU Binutils for Ubuntu) 2.34
Asignatura: Estructura de Computadores
Fecha: 25/09/2022
Autor: Cándido Aramburu
*/

// Módulo Principal
void main (void) {
//Declaración de variables locales enteras n y sum. La suma se inicializa por 0 y en este caso la n con 5 para ir decreciendo el valor de la variable
    int sum = 0, n=5;
//Bucle que genera los sumandos y realiza la suma
    while(n > 0){
        //Condición de salida del bucle cuando el sumando es negativo
        sum+=n; //se añade a sum el valor de n para ir actualizando el sumatorio
        n--; //Decrece el valor de n hasta llegar a 0, que en ese caso ya se habría completado el sumatorio
}
} //Si las instrucciones se han ejecutado sin interrupción ni fallo main() devuelve el valor cero al sistema operativo.
