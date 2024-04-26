//Programa: memoria.
//Autor: Javier Aranguren Ortiz.
//Fecha: 23/10/2020.
/*Descripcion: Este programa cree una zona de memoria compartida de enteros del
tamaño especificado como argumento (tamano), que tendrá como clave
identificadora la que se pase como argumento (clave) y que estará en ejecución
hasta que se pulse Ctrl+C. Al recibir esta señal, se liberará la memoria compartida
y se finalizará ordenadamente la ejecución del proceso.*/

//Librerias
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>

//Funciones
void salida(int);

void main(int argc, char **argv)
{
    //Variables
    int clave, tamano, memoria, semaforo;

    //Cuerpo
    printf("Programa: memoria.\n");
    printf("Autor: Javier Aranguren Ortiz.\n");
    printf("Fecha: 23/10/2020.\n");
    printf("Descripcion: Este programa cree una zona de memoria compartida de enteros del tamaño especificado como argumento (tamano), que tendrá como clave identificadora la que se pase como argumento (clave) y que estará en ejecución hasta que se pulse Ctrl+C. Al recibir esta señal, se liberará la memoria compartida y se finalizará ordenadamente la ejecución del proceso.\n");

    //Compruebo si el numero de argumentos introducidos es correcto
    if (argc != 2) 
    {
        printf("\nEl numero de argumentos introducido es incorrecto, se deben introducir dos argumentos, el primero indica el tamaño de la memoria y el segundo la clave de esta\n");
        printf("ERROR 1\n\n");
        exit(1);
    }

    //Inicializo las variables
	
    tamano = atoi(argv[1]); //al revés de lo que tenia puesto
    clave = atoi (argv[2]); //se lo he añadido yo
   
    
    //se inicializa a 0 y entonces no crea luego la zona de memoria
	
    //Preparo la respuesta para cuando se introduzca ctrl+c
    signal(SIGINT, &salida);

    //Creo la key
    if(-1 == (clave = ftok("fichero.txt", clave)))
    {
        printf("\nLa clave no se ha creado correctamente, seguramente porque no existe 'fichero.txt' en la misma carpeta que este programa, por favor cree el fichero.\n");
        printf("ERROR 2\n\n");
        exit(2);
    }

    //Creo la memoria compartida
    if(-1 == (memoria = shmget(clave, sizeof(int)*tamano, IPC_CREAT|0777))) //si cambias tamano por un numero cualquiera funciona
    {
        printf("\nLa memoria no se ha creado correctamente\n");
        printf("ERROR 3\n\n");
        exit(3);
    }

    //Creo el semaforo
    if (-1 == (semaforo = semget(clave, 1, IPC_CREAT|0777))) // 1 = número semáforos
    {
        printf("\nEl semaforo no se ha creado correctamente\n");
        printf("ERROR 4\n\n");
        exit(4);
    }

    //Inicializo el semaforo
    if (-1 == (semctl(semaforo, 0, SETVAL, 1)))
    {
        printf("\nEl semaforo no se ha inicializado correctamente\n");
        printf("ERROR 5\n\n");
        exit(5);
    }
    
    //Espero a que el usuario quiera cerrar el programa
    pause();

    //Eliminamos el semaforo y la memoria
    if(-1 == semctl(semaforo, 0, IPC_RMID))
    {
        printf("\nEl semaforo no se ha eliminado correctamente\n");
        printf("ERROR 6\n\n");
        exit(6);
    }
    if(-1 == shmctl(memoria, IPC_RMID, 0))
    {
        printf("\nLa memoria no se ha eliminado correctamente\n");
        printf("ERROR 7\n\n");
        exit(7);
    }
}

//Funciones cuerpo
void salida(int num)
{
    printf("\nSe libera la memoria y se cierra el programa\n");
}
