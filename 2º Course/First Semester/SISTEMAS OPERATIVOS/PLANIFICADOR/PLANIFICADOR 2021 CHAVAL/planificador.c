#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/wait.h>
#include <sys/msg.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include "fragmenta.h"
#include <sys/shm.h>

//printear al final contadores, liberar cola y procesos, sigaction

struct mensaje
{
    long tipo;
    char comando[50];
    int pid;
}mensaje;

void terminado(int);
void llamado(int);
void nada(int);
void mostrar(int);
int finalizado, num_cambio, num_terminados;
int main(int argc, char ** argv)
{
    int prio, idCola, pid;
    char * cadena;
    struct mensaje m;
    cadena = (char *)malloc(sizeof(char) * 50);
    idCola = msgget(IPC_PRIVATE, 0600 | IPC_CREAT);
    if (argc == 2)
    {
        int fd;
        fd = open (argv[1], O_RDONLY);
        dup2 (fd, STDIN_FILENO);
    }
    pid = fork();
    if (pid == 0)
    {
        int  pid_previo;
        pid_previo = num_cambio = num_terminados = 0;
        struct sigaction x;
        x.sa_handler = terminado;
        x.sa_flags = SA_NOCLDSTOP;
        sigaction(SIGCHLD, &x, NULL);
        signal(SIGUSR1, llamado);
        signal (SIGINT, mostrar);
        while(1)
        {
            if(msgrcv(idCola, &m, sizeof(mensaje)-sizeof(long), 1, IPC_NOWAIT) < 0)
                if(msgrcv(idCola, &m, sizeof(mensaje)-sizeof(long), 2, IPC_NOWAIT) < 0)
                    if(msgrcv(idCola, &m, sizeof(mensaje)-sizeof(long), 3, IPC_NOWAIT) < 0)
                        msgrcv(idCola, &m, sizeof(mensaje)-sizeof(long), 0, 0);
            if(m.pid != pid_previo)
            {
                num_cambio ++;
                pid_previo = m.pid;
            }
            if (m.pid < 0)
            {
                m.pid = fork();
                pid_previo = m.pid;
                if (m.pid == 0)
                {
                    char ** argv;
                    argv = fragmenta(m.comando);
                    execvp(argv[0], argv);
                }
                else
                {
                    if (m.tipo < 4)
                    {
                        wait(NULL);
                        num_terminados ++;
                    }
                    else
                    {
                        finalizado = 0;
                        sleep(3);
                        if (finalizado == 0)
                        {
                            kill(m.pid, SIGSTOP);
                            msgsnd (idCola, &m, sizeof(mensaje)-sizeof(long), 0);
                        }
                        else
                        {
                            num_terminados ++;
                        }
                    }
                }
            }
            else
            {
                finalizado = 0;
                kill(m.pid, SIGCONT);
                sleep(3);
                if (finalizado == 0)
                {
                    kill(m.pid, SIGSTOP);
                    msgsnd (idCola, &m, sizeof(mensaje)-sizeof(long), 0);
                }
                else
                {
                    num_terminados ++;
                }
                
            }
        }
    }
    else
    {
        signal (SIGINT, nada);
        while(scanf ("%d %[^\n]", &prio, cadena) > 0)
        {
            getchar();
            m.tipo = prio; 
            m.pid = -1;
            strcpy (m.comando, cadena);
            msgsnd (idCola, &m, sizeof(mensaje)-sizeof(long), 0);
        }
        pause();
        kill(-pid, SIGINT);
        wait(NULL);
    }
}
void terminado(int i)
{
    finalizado = 1;
}
void llamado(int i)
{
    finalizado = 0;
}
void nada(int i)
{
}
void mostrar(int i)
{
    printf ("Procesos terminados: %d\n", num_terminados);
    printf ("Cambios de contexto: %d\n", num_cambio);

    exit(0);
}
