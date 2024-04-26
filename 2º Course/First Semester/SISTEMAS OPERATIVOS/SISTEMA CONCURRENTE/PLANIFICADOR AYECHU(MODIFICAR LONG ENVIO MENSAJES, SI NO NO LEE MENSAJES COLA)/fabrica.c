//Program: fabrica

//Librerias
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/msg.h>

struct ordenProduccion {
    long tipoMensaje;
    int a;
    int b;
    int c;
}ordenProduccion;

typedef struct Pedidos {
    int servidos;
    int pendientes;
}Pedidos;

void handler(int);

int semaforo, memoria, clave, colaMensajesID;

int main(int argc, char **argv)
{
    struct sembuf ctrlSem;

    //Compruebo si el numero de argumentos introducidos es correcto
    if (argc != 2)
    {
        printf("\nERROR; formato: ./fabrica clave\n\n");
        exit(1);
    }
    
    printf("\e[1;1H\e[2J");
    printf("\tProgram: FABRICA\n");

    clave = atoi(argv[1]);

    //Preparo la respuesta para cuando se introduzca ctrl+c
    signal(SIGINT, handler);

    //Creo la cola
	if (-1 == (colaMensajesID = msgget(clave, 0777 | IPC_CREAT)))
	{
        perror("msgget");
        exit(4);
	}

    //Creo la memoria compartida
    if(-1 == (memoria = shmget(clave, sizeof(Pedidos), IPC_CREAT | 0777))) 
    {
        perror("shmget");
        exit(2);
    }

    //Creo el grupo de semaforos
    if (-1 == (semaforo = (semget(clave, 4, IPC_CREAT | 0600))))
    {
        perror("semget");
        exit(3);
    }

    //Inicializo el semaforo 0 a 1
    if (-1 == (semctl(semaforo, 0, SETVAL, 1)))
    {
        perror("semctl");
        exit(5);
    }
    //Inicializo el semaforo 1 a 1
    if (-1 == (semctl(semaforo, 1, SETVAL, 1)))
    {
        perror("semctl");
        exit(5);
    }
    //Inicializo el semaforo 2 a 1
    if (-1 == (semctl(semaforo, 2, SETVAL, 1)))
    {
        perror("semctl");
        exit(5);
    }
    //Inicializo el semaforo 3 a 1
    if (-1 == (semctl(semaforo, 3, SETVAL, 1)))
    {
        perror("semctl");
        exit(5);
    }
    
    Pedidos *Memoria = (Pedidos *)shmat(memoria, NULL, 0);
    
    Memoria->servidos = Memoria->pendientes = 0;

    while (1)
    {
        msgrcv (colaMensajesID, &ordenProduccion, sizeof(struct ordenProduccion)-sizeof(long), 0, 0);

        ctrlSem.sem_num = 1;
        ctrlSem.sem_op = -(ordenProduccion.a);
        ctrlSem.sem_flg = 0;

        if(-1 == (semop(semaforo, &ctrlSem, 1)))
        {
            perror("semop");
            exit(7);
        }

        ctrlSem.sem_num = 2;
        ctrlSem.sem_op = -(ordenProduccion.b);
        ctrlSem.sem_flg = 0;

        if(-1 == (semop(semaforo, &ctrlSem, 1)))
        {
            perror("semop");
            exit(70);
        }

        ctrlSem.sem_num = 3;
        ctrlSem.sem_op = -(ordenProduccion.c);
        ctrlSem.sem_flg = 0;

        if(-1 == (semop(semaforo, &ctrlSem, 1)))
        {
            perror("semop");
            exit(700);
        }

        printf("Producto <%d, %d, %d> Inicio de la producción.. \n", ordenProduccion.a, ordenProduccion.b, ordenProduccion.c);

        sleep(2);

        printf("... producto <%d, %d, %d> finalizado", ordenProduccion.a, ordenProduccion.b, ordenProduccion.c);        

        ctrlSem.sem_num = 0;
        ctrlSem.sem_op = -1;
        ctrlSem.sem_flg = 0;

        if(-1 == (semop(semaforo, &ctrlSem, 1)))
        {
            perror("semop");
            exit(8);
        }

        Memoria->servidos = Memoria->servidos + 1;
        Memoria->pendientes = Memoria->pendientes - 1;

        ctrlSem.sem_num = 0;
        ctrlSem.sem_op = 1;
        ctrlSem.sem_flg = 0;

        if(-1 == (semop(semaforo, &ctrlSem, 1)))
        {
            perror("semop");
            exit(9);
        }
    }    
}

void handler(int num)
{
    printf("\nsaliendo de fabrica..\n");

    //Eliminamos la cola
    if (msgctl(colaMensajesID, IPC_RMID, 0))
    {
        perror("msgctl");
        exit(6);
    }
    //Eliminamos el semaforo
    if(-1 == semctl(semaforo, 0, IPC_RMID))
    {
        perror("semctl");
        exit(7);
    }
    //Eliminamos la memoria
    if(-1 == shmctl(memoria, IPC_RMID, 0))
    {
        perror("shmctl");
        exit(8);
    }
    //Eliminamos la cola
    msgctl(clave, IPC_RMID, 0);
}