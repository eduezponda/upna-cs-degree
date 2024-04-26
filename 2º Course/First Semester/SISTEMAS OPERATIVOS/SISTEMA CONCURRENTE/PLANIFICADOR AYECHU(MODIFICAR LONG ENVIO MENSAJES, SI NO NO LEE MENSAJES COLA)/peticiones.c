// Program: peticiones

//Librerias
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <sys/sem.h> 
#include <unistd.h>
#include <signal.h>
#include <sys/msg.h>

struct ordenProduccion {
    long msgbuf;
    int a;
    int b;
    int c;
}ordenProduccion;

typedef struct Pedidos {
    int servidos;
    int pendientes;
}Pedidos;

//Constantes
void handler(int);
bool bool_fichero = false;

int main(int argc, char **argv)
{
    FILE *fd = NULL;
    int semaforo, clave, colaMensajesID, memoria;
    struct sembuf ctrlSem;

    printf("\e[1;1H\e[2J");
    printf("\tprogram: PETICIONES\n");

    //Compruebo si el numero de argumentos introducidos es correcto
    if ((argc != 2) && (argc != 3))
    {
        printf("Formato: ./peticiones clave [configfileM]\n");
        exit(1);
    }

    if (argc == 2)
    {
        fd = stdin;
    }
    else
    {
        if(NULL == (fd = fopen(argv[2], "r")))
        {
            perror("fopen");
            exit(2);
        }
        bool_fichero = true;
    }

    clave = atoi(argv[1]);

    //Preparo salida
    if(signal(SIGINT, handler) == SIG_ERR)
    {
        perror("No se puede cambiar la gestión de la señal");
        exit(3);
    }
    // --
    //COLA
    if (-1 == (colaMensajesID = msgget(clave, 0777)))
	{
        perror("msgget");
        exit(4);
	}
    //SHARED MEMORY
    if(-1 == (memoria = shmget(clave, sizeof(Pedidos), 0777))) 
    {
        perror("shmget");
        exit(2);
    }
    //GRUPO DE SEMAFOROS
    if (-1 == (semaforo = semget(clave, 1, 0)))
    {
        perror("semget");
        exit(4);
    }
    // --
    Pedidos *Memoria = (Pedidos *)shmat(memoria, NULL, 0);

    while (1)
    {
        fscanf(fd, "%d %d %d", &ordenProduccion.a, &ordenProduccion.b, &ordenProduccion.c); //!= EOF
        int c = getc(stdin);
        if(c == EOF) 
        {
            break;
        }

        //WAIT sobre SHM
        ctrlSem.sem_num = 0;
        ctrlSem.sem_op = -1;
        ctrlSem.sem_flg = 0;

        if(-1 == semop(semaforo, &ctrlSem, 1))
        {
            perror("semop");
            exit(5);
        }

        Memoria->pendientes = Memoria->pendientes + 1;

        //SIGNAL sobre SHM
        ctrlSem.sem_num = 0;
        ctrlSem.sem_op = 1;
        ctrlSem.sem_flg = 0;

        if(-1 == semop(semaforo, &ctrlSem, 1))
        {
            perror("semop");
            exit(5);
        }

        // ENVIAR A COLA DE FABRICA
        msgsnd(colaMensajesID, &ordenProduccion, sizeof(struct ordenProduccion)-sizeof(long), IPC_NOWAIT);
    }
    
    if (bool_fichero)
    {
        fclose(fd);
    }
}

void handler(int signum)
{
    printf("saliendo de peticiones.. \n");
    exit(0);
}
