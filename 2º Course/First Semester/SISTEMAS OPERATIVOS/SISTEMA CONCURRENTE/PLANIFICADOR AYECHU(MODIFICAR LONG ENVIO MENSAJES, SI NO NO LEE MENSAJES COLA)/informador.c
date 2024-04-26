// Program: informador

//Librerias
#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <sys/sem.h>
#include <unistd.h>
#include <signal.h>

struct Pedidos {
    int servidos;
    int pendientes;
}Pedidos;

//Constantes
void handler(int);

int main(int argc, char **argv)
{
    //Variables
    int clave, periodo, semaforo, memoria, iteracion = 0;
    struct sembuf ctrlSem;

    //Compruebo si el numero de argumentos introducidos es correcto
    if (argc != 3)
    {
        printf("Formato: ./informador clave periodoI\n");
        exit(1);
    }

    printf("\e[1;1H\e[2J");
    printf("\tProgram: INFORMADOR\n");

    clave = atoi(argv[1]);
    periodo = atoi(argv[2]);

    //Preparo la respuesta para cuando se introduzca ctrl+c
    if(signal(SIGINT, handler) == SIG_ERR) // llamada a accion controlador con argumento SIGINT
    {
    perror("signal");
    exit(2);
    }

    //Creo el semaforo
    if (-1 == (semaforo = semget(clave, 1, 0)))
    {
        perror("semget");
        exit(5);
    }

    if(-1 == (memoria = shmget(clave, sizeof(Pedidos), 0777))) 
    {
        perror("shmget");
        exit(7);
    }

    struct Pedidos * Memoria = (struct Pedidos *)shmat(memoria, 0, 0);

    while (1)
    {
        sleep(periodo);

        //El proceso entra en el semaforo
        ctrlSem.sem_num = 0;
        ctrlSem.sem_op = -1;
        ctrlSem.sem_flg = 0;

        if(-1 == semop(semaforo, &ctrlSem, 1))
        {
            perror("semop");
            exit(8);
        }

       	printf("[%d] Informador %d estado de la producción: [%d, %d]\n", iteracion, getpid(), Memoria->servidos, Memoria->pendientes);

        //El proceso sale del semaforo
        ctrlSem.sem_num = 0;
        ctrlSem.sem_op = 1;
        ctrlSem.sem_flg = 0;

        if(-1 == semop(semaforo, &ctrlSem, 1))
        {
            perror("semop");
            exit(8);
        }

        iteracion = iteracion + 1;
    }
}

void handler(int signum)
{
    printf("saliendo de informador.. \n");
    exit(1);
}
