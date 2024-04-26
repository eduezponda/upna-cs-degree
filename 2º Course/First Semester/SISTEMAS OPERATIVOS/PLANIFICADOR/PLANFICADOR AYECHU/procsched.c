#include <stdio.h>
#include <stdbool.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <signal.h>
#include <unistd.h>
#include "fragmenta.h"

#define LON_MAX 100

struct msgbuf {
	long mtype;
	int proc_id;
};

typedef struct Contador {
	int nPrio;
	int nFCFS;
	int nRR;
	int nCC;
}contador;

int crearCola();
int ejecutor (char **cmd);
void controlador(int sig);
void hijo();
void handler_alarma(int sig);
void handler_proceso_terminado(int sig);
void printear(int sig);

struct msgbuf msgBuffer;
int clave_cola;
int pid;
bool turno_agotado;
contador c;

void main(int argc, char **argv)
{
	if(signal(SIGINT, controlador) == SIG_ERR)
	{
		perror("No se puede cambiar la gestión de la señal");
		exit(1);
	}
	
	char entradaTexto[50], **command;
	clave_cola = crearCola();
	c.nPrio = 0; c.nFCFS = 0; c.nRR = 0; c.nCC = 0;
	
	FILE *entrada;

	if (argc > 1)
	{
		printf("\nLeyendo de fichero..\n\n");
		entrada = fopen(argv[1], "r");
		if(entrada == NULL)
		{
			perror("fopen");
		}
	}
	else
	{
		entrada = stdin;
		printf(" Introduce líneas (nivel (1,2,3), prioridad(1,2,3,4,5), nombreprograma, args) : \n");	
	}
	

	if ((pid = fork()) == -1)
	{
		perror("ERROR creación proceso");
	}
	else if (pid == 0)
	{
		
		signal(SIGALRM, handler_alarma);
		signal(SIGUSR1, handler_proceso_terminado);
		signal(SIGUSR2, printear);
		
		raise(SIGSTOP);
		while (1)
		{
			msgrcv (clave_cola, &msgBuffer, sizeof(struct msgbuf)-sizeof(long), -8, 0);
			
			if (msgBuffer.mtype == 1)
			{
				turno_agotado = false;
				alarm(2);
				kill(msgBuffer.proc_id, SIGCONT);
				pause();
				
				if (turno_agotado)
				{
					c.nCC = c.nCC + 1;
					printf("-----| CAMBIO DE CONTEXTO |-----\n");
					kill(msgBuffer.proc_id, SIGSTOP);
					msgsnd(clave_cola, &msgBuffer, sizeof(struct msgbuf)-sizeof(long), IPC_NOWAIT);
				}
				else 
					alarm(0);
					c.nRR = c.nRR + 1;
				
			}
			else
			{
				if (msgBuffer.mtype == 2)
				{
					c.nFCFS = c.nFCFS + 1;
				}
				else
				{
					c.nPrio = c.nPrio + 1;
				}
				kill(msgBuffer.proc_id, SIGCONT);
				pause();
			}
		}
	}
	else
	{
		while (1)
		{
			fscanf(entrada, "%[^\n]", entradaTexto);
			int c = getc(entrada);
			if(c == EOF) 
				break;
			command = fragmenta(entradaTexto);
			
			/*int i = 0;
			while (command[i] != NULL)
			{
				printf("command[%d] = %s \n", i, command[i]);
				i = i + 1;
			}*/
			
			if ((atoi(command[0]) < 1) || (atoi(command[0]) > 3))
			{
				printf("ERROR: nivel no correcto\n");
			}
			else
			{
				if ((atoi(command[1]) < 1) || (atoi(command[1]) > 5))
				{
					printf("ERROR: prioridad no correcta\n");
				}
				else
				{
					msgBuffer.mtype = atoi(command[0]);
					if (msgBuffer.mtype == 3)
					{
						msgBuffer.mtype = msgBuffer.mtype + atoi(command[1]);
					}
					
					int pid_dev = ejecutor(command);
					signal(SIGCHLD, hijo);
					
					struct sigaction proc;
					proc.sa_handler = hijo;
					proc.sa_flags = SA_NOCLDSTOP;
					sigaction(SIGCHLD, &proc, NULL);
					
					msgBuffer.proc_id = pid_dev;
					
					msgsnd(clave_cola, &msgBuffer, sizeof(struct msgbuf)-sizeof(long), IPC_NOWAIT);
				}
			}
		}
		kill(pid, SIGCONT);
		while (1){}
	}
	
	fclose(entrada);
}

int crearCola()
{
	key_t clave;
	
	if ((clave = ftok("/tmp/", clave) == -1))
	{
		perror("No se ha podido crear la clave correctamente");
	}
	
	if ((clave = msgget(clave, 0777 | IPC_CREAT)) == -1)
	{
		perror("No se ha podido crear la cola correctamente");
	}
	
	return clave;
}

void hijo()
{
	kill(pid, SIGUSR1);
}
void handler_proceso_terminado(int sig) 
{
	printf(" ");
}
void handler_alarma(int sig)
{
	turno_agotado = true;
}

int ejecutor (char **cmd)
{
	int pid;
	
	if ((pid = fork()) == -1)
	{
		perror("ERROR creación proceso");
	}
	else if (pid == 0)
	{
		cmd = &cmd[2];
		raise(SIGSTOP);	
		execvp(cmd[0], cmd);
	}
	return pid;
}

void controlador(int sig)
{
	if (pid != 0)
	{	
		printf("\nDetectada señal %d, saliendo de procsched\n", sig);
		msgctl(clave_cola, IPC_RMID, 0);
		printf("\nCola eliminada.\n");
		kill(pid, SIGUSR2);
		exit(0);	
	}
}

void printear(int sig)
{
	printf("Numero de PROCESOS ejecutados en Round Robin = %d\n", c.nRR-c.nCC);
	printf("Numero de PROCESOS ejecutados en FCFS = %d\n", c.nFCFS);
	printf("Numero de PROCESOS ejecutados en Prioridades = %d\n", c.nPrio);
	printf("Numero de Cambios de Contexto = %d\n", c.nCC);
}
