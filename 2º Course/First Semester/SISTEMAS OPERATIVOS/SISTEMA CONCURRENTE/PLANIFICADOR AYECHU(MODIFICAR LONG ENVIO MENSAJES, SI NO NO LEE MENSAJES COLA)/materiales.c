// Program: materiales

//Librerias
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <sys/sem.h> 
#include <unistd.h>
#include <signal.h>

struct producto {
    long msgbuf;
    int a;
    int b;
    int c;
}producto;

//Constantes
void handler(int);

int main(int argc, char **argv)
{
    FILE *fd = NULL;
    struct producto buffer[50];
    int semaforo, clave;
    struct sembuf ctrlSem;

    //Compruebo si el numero de argumentos introducidos es correcto
    if ((argc != 2) && (argc != 3))
    {
        printf("Formato: ./materiales clave [configfileM]\n");
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
    }

    clave = atoi(argv[1]);

    //Preparo salida
    if(signal(SIGINT, handler) == SIG_ERR)
    {
        perror("No se puede cambiar la gestión de la señal");
        exit(3);
    }

    if (-1 == (semaforo = semget(clave, 1, 0)))
    {
        perror("semget");
        exit(4);
    }

    printf("\e[1;1H\e[2J");
    printf("\tprogram: MATERIALES\n");

    int i = 0;
    while (1)
    {
        fscanf(fd, "%d %d %d", &buffer[i].a, &buffer[i].b, &buffer[i].c); //!= EOF
        int c = getc(stdin);
        if(c == EOF) 
        {
            break;
        }

        ctrlSem.sem_num = 1;
        ctrlSem.sem_op = buffer[i].a;
        ctrlSem.sem_flg = 0;

        if(-1 == semop(semaforo, &ctrlSem, 1))
        {
            perror("semop");
            exit(5);
        }

        ctrlSem.sem_num = 2;
        ctrlSem.sem_op = buffer[i].b;
        ctrlSem.sem_flg = 0;

        if(-1 == semop(semaforo, &ctrlSem, 1))
        {
            perror("semop");
            exit(6);
        }

        ctrlSem.sem_num = 3;
        ctrlSem.sem_op = buffer[i].c;
        ctrlSem.sem_flg = 0;

        if(-1 == semop(semaforo, &ctrlSem, 1))
        {
            perror("semop");
            exit(7);
        }
    }
}

void handler(int signum)
{
    printf("saliendo de informador.. \n");
    exit(0);
}
