#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <signal.h>
#include "fragmenta.h"

//printear al final contadores, liberar cola y procesos, sigaction

#define MAX_INPUT 128

struct msgbuffer{
	long mtype;
	pid_t pid;
}msgbuffer;

int cambiosContexto, procesosPrior,procesosFCFS, procesosRR, haTerminado, suenaAlarma;

void salida ();
void terminado();
void nada();
void alarma();
void finalizado();

int main (int argc, char** argv)
{
	char entradaTeclado[MAX_INPUT], **arg;
	key_t clave;
	int idColaMensajes, pid, pid2, f;
	struct sigaction x;
	
	procesosPrior = procesosFCFS = procesosRR = 0;
	cambiosContexto = suenaAlarma = 0;
	haTerminado = 0;
	
	clave = ftok ("/etc", 25);
	
	if (clave == (key_t) -1) 
	{
		exit(-1);
	}
	idColaMensajes = msgget (clave, 0600 | IPC_CREAT); //ipc_private por si se buguea
	if (argc == 2)
	{
		f = open (argv[1], O_RDONLY);
		dup2(f,STDIN_FILENO);
		//leer desde fichero -> ./procsched .txt 
	}
	
	pid = fork();
	
	if (pid == 0)
	{
		
		while(1)
		{
			
			if (argc != 2)
			{
				printf("planificador""\\""> "); //meter dentro de if por si es fichero
			}
			scanf("%[^\n]",entradaTeclado);
			if (getchar() == EOF) //control+d
			{
				break;
			}
			//fgetc(stdin) = getchar();
			
			
			arg = fragmenta(entradaTeclado);
			
			//sigaction (antes fork)
			
			x.sa_handler = finalizado;
			x.sa_flags = SA_NOCLDSTOP;
			sigaction(SIGCHLD, &x, NULL);
			
			pid2 = fork();
			
			if (pid2 == -1)
			{
				printf("Error en la creación del proceso\n");
			}
			else if (pid2 == 0)
			{
				if (strcmp(arg[0],"1") == 0 || strcmp(arg[0],"2") == 0)
				{
					execvp(arg[1],&arg[1]);
				}
				else
				{
					execvp(arg[2],&arg[2]);
				}
				 //si quiero que empiece más adelante -> arg[2],&arg[2], depende posición por prioridad
			}
			else
			{
				//sleep(2); ->si lo pongo aqui me ejecuta los procesos nada mas introducirlos si tardan menos de dos segundos
				kill(pid2, SIGSTOP);
				if (strcmp(arg[0],"1") == 0) //más facil por el atoi
				{
					//printf("RR: %d\n",procesosRR);
					//procesosRR = procesosRR + 1;
					msgbuffer.mtype = 1;
					msgbuffer.pid = pid2;
					//printf("RR: %d\n",procesosRR);
					
				}
				else if (strcmp(arg[0],"2") == 0)
				{	
					//procesosFCFS = procesosFCFS + 1;
					msgbuffer.mtype = 2;
					msgbuffer.pid = pid2;
				}
				else
				{
					//procesosPrior = procesosPrior + 1;
					msgbuffer.mtype = 3 + (int)*arg[1] - 48;
					msgbuffer.pid = pid2;
				}
				printf(" %d\n",pid2);
				msgsnd (idColaMensajes, &msgbuffer, sizeof(struct msgbuffer)-sizeof(long), IPC_NOWAIT);
				//printf("%d\n",(int)msgbuffer.mtype);
			}
		}
		
		kill(getppid(), SIGUSR1);
		
		while (1) //dejas el proceso vivo para que le llegue sigchild
		{
		
		}
	
	}
	else
	{
		signal (SIGINT, salida); //antes de scanf
		signal(SIGUSR1, nada);
		signal(SIGUSR2, terminado);
		signal(SIGALRM, alarma);
		pause();
		//leer mensajes y tratar procesos
		//esperar a que terminen todos los procesos hijos -> waitpid();
		
		
		while(1)
		{
			 //parado hasta que le llegue cualquier tipo de señal redirigida (SIGUSR1)
			//waitpid(pid, NULL, 0);
			msgrcv (idColaMensajes, &msgbuffer, sizeof(struct msgbuffer) -sizeof(long), -20, IPC_NOWAIT);
			if (msgbuffer.mtype == 1)
			{
				printf("%d\n",msgbuffer.pid);
				kill(msgbuffer.pid,SIGCONT);
				alarm(2);
				
				while(suenaAlarma == 0 && haTerminado == 0) {
				
				}
				
				if (haTerminado == 0)
				{
					kill(msgbuffer.pid, SIGSTOP);
					suenaAlarma = 0;
					msgsnd (idColaMensajes, &msgbuffer, sizeof(struct msgbuffer)-sizeof(long), IPC_NOWAIT);
					cambiosContexto = cambiosContexto + 1;
				}
				else
				{
					alarm(0);
					procesosRR = procesosRR + 1;
					haTerminado = 0;
				}
				
				
			}
			else if (msgbuffer.mtype == 2)
			{
				printf("%d\n",msgbuffer.pid);
				kill(msgbuffer.pid,SIGCONT);
				waitpid(msgbuffer.pid,NULL,0);
				procesosFCFS = procesosFCFS + 1;
				
			}
			else if (msgbuffer.mtype == -1)
			{
			
			}
			else
			{
				printf("%d\n",msgbuffer.pid);
				kill(msgbuffer.pid,SIGCONT);
				waitpid(msgbuffer.pid,NULL,0);
				procesosPrior = procesosPrior + 1;
			}
			msgbuffer.mtype = -1;
			
		}
		msgctl(idColaMensajes, IPC_RMID, 0);
		
	}
	if (argc == 2)
	{
		close(f);
	}
	borrar(arg);


}
void salida ()
{	
	printf("\nProcesos ejecutados con éxito: %d\n",procesosRR + procesosFCFS + procesosPrior);
	printf("	Procesos Round Robin (tipo 1): %d\n",procesosRR);
	printf("	Procesos Cola (tipo 2): %d\n",procesosFCFS);
	printf("	Procesos Prioridad (tipo 3): %d\n",procesosPrior);
	printf("El número de cambios de contexto es: %d\n",procesosRR + procesosFCFS + procesosPrior + cambiosContexto);
	printf("\nSaliendo del planificador\n");
	exit(1);
}
void terminado()
{
	haTerminado = 1; //proceso rr termina ejecución en 2 segundos
}

void nada() {

}
void alarma()
{
	suenaAlarma = 1;
}
void finalizado()
{
	printf("Muere proceso\n");
	kill(getppid(),SIGUSR2);
	
}


