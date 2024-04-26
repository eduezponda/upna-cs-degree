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

#define MAX_INPUT 128

struct msgbuffer{
	long mtype; //prioridad
	pid_t pid; //pid
}msgbuffer; //declaración estructura mensajes

int cambiosContexto, procesosPrior,procesosFCFS, procesosRR, haTerminado, suenaAlarma, idColaMensajes; //declaración variables globales

void salida ();
void terminado();
void nada();
void alarma();
void finalizado();

int main (int argc, char** argv) //argumentos para entrada fichero
{
	char entradaTeclado[MAX_INPUT], **arg;
	key_t clave;
	int pid, pid2, f;
	struct sigaction x; //declaro estructura para modificar sigaction (envía señal tras parar, continuar o morir proceso)
	
	//reinicio contadores y booleanos
	
	procesosPrior = procesosFCFS = procesosRR = 0;
	cambiosContexto = suenaAlarma = 0;
	haTerminado = 0;
	
	clave = ftok ("/etc", 25); //creo la clave
	
	if (clave == (key_t) -1) //error en creación clave
	{
		exit(-1);
	}
	idColaMensajes = msgget (clave, 0600 | IPC_CREAT); // Creo cola mensajes con la clave. IPC_PRIVATE en vez de clave
	
	if (argc == 2) //leer desde descriptor de fichero -> ./procsched .txt 
	{
		f = open (argv[1], O_RDONLY); //abro descriptor fichero con segundo argumento -> argv[0] = procsched; argv[1] = .txt
		dup2(f,STDIN_FILENO); //cambio entrada estándar a descriptor fichero
	}
	
	pid = fork(); // creo proceso hijo
	
	if (pid == 0) //hijo
	{
		
		while(1) //bucle hasta detectar control + d
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
			
			
			arg = fragmenta(entradaTeclado); //fragmento comando
			
			//sigaction (antes fork)
			
			x.sa_handler = finalizado; //redirigir a finalizado() para que envie una señal SIGUSR2 al padre
			x.sa_flags = SA_NOCLDSTOP;
			sigaction(SIGCHLD, &x, NULL);
			
			pid2 = fork(); //creo proceso hijo
			
			if (pid2 == -1)
			{
				printf("Error en la creación del proceso\n");
			}
			else if (pid2 == 0) //hijo
			{
				if (strcmp(arg[0],"1") == 0 || strcmp(arg[0],"2") == 0)
				{
					execvp(arg[1],&arg[1]); //empiezo a ejecutar proceso
				}
				else
				{
					execvp(arg[2],&arg[2]); //empiezo a ejecutar proceso
				}
				 //si quiero que empiece más adelante -> arg[2],&arg[2], depende posición por prioridad
			}
			else //padre
			{
				//sleep(2); ->si lo pongo aqui me ejecuta los procesos nada mas introducirlos si tardan menos de dos segundos
				kill(pid2, SIGSTOP); //paro el proceso para que no se termine de ejecutar con execvp
				if (strcmp(arg[0],"1") == 0) //más facil por el atoi
				{
					msgbuffer.mtype = 1;
					msgbuffer.pid = pid2;
					
				}
				else if (strcmp(arg[0],"2") == 0)
				{	
					msgbuffer.mtype = 2;
					msgbuffer.pid = pid2;
				}
				else
				{
					msgbuffer.mtype = 3 + (int)*arg[1] - 48; //convertir con codigo ascii
					msgbuffer.pid = pid2;
				}
				msgsnd (idColaMensajes, &msgbuffer, sizeof(struct msgbuffer)-sizeof(long), IPC_NOWAIT); //enviar mensaje 
				//printf("%d\n",(int)msgbuffer.mtype);
			}
		}
		
		kill(getppid(), SIGUSR1); //enviar señal SIGUSR1 al padre para que termine pause
		
		while (1) //dejas el proceso vivo para que le llegue la señal sigchild de que se ha terminado de ejecutar pid2
		{
		
		}
	
	}
	else //padre
	{
		signal (SIGINT, salida); //detectar señal control c para finalizar programa -> una vez que se introduce un signal se guarda para el resto del programa
		signal(SIGUSR1, nada);
		signal(SIGUSR2, terminado); //cuando detecta una señal SIGUSR2 es porque el proceso ha terminado y por eso redirije a terminado para cambiar valor variable global haTerminado
		signal(SIGALRM, alarma); //detectar si suena alarma para cambiar variable global suenaAlarma
		pause(); //pausar proceso hasta que le llegue una señal, en este caso o control c o SIGUSR1
		
		//leer mensajes y tratar procesos
		//esperar a que terminen todos los procesos hijos -> waitpid();
		
		while(1)
		{
			 //parado hasta que le llegue cualquier tipo de señal redirigida (SIGUSR1)
			//waitpid(pid, NULL, 0);
			msgrcv (idColaMensajes, &msgbuffer, sizeof(struct msgbuffer) -sizeof(long), -20, IPC_NOWAIT);
			if (msgbuffer.mtype == 1) //tengo q cambiar el programa para que compruebe si solo hay un proceso por ejecutar de tipo 1 y tarda mas de 3 segundos en ejecutar para que no sume más cambios de contexto. Para ello puedes guardar el pid del anterior para luego comprobar si son iguales
			{
				kill(msgbuffer.pid,SIGCONT); //continuar proceso con execvp
				alarm(2); //establecer alarma en 2 segundos
				
				while(suenaAlarma == 0 && haTerminado == 0) //mientras no suena la alarma y el proceso no ha terminado...
				{
				
				}
				
				if (haTerminado == 0) //si no ha terminado -> ha sonado alarma
				{
					kill(msgbuffer.pid, SIGSTOP); //continuar proceso con execvp
					suenaAlarma = 0; //reiniciar alarma
					msgsnd (idColaMensajes, &msgbuffer, sizeof(struct msgbuffer)-sizeof(long), IPC_NOWAIT); //volver a enviar proceso a la cola
					cambiosContexto = cambiosContexto + 1; //proceso no se realiza en 2 segundos -> cambio contexto
				}
				else //si ha terminado proceso antes de que suene la alarma
				{
					alarm(0); //reiniciar alarma
					procesosRR = procesosRR + 1; //sumar contador
					haTerminado = 0; //reiniciar booleano termina proceso rr
				}
				
				
			}
			else if (msgbuffer.mtype == 2)
			{
				kill(msgbuffer.pid,SIGCONT); //continuar proceso con execvp
				waitpid(msgbuffer.pid,NULL,0); //esperar hasta que termine hijo
				procesosFCFS = procesosFCFS + 1; //sumar contador
				
			}
			else if (msgbuffer.mtype == -1) //en el caso de que no haya más mensajes no se meta en ningún if
			{
			
			}
			else //prioridad suma desde 3 hasta ... 20 (tercer argumento msgrcv es -20 ->leer desde 0 hasta valor absoluto -20 = 20 de menor a mayor)
			{
				kill(msgbuffer.pid,SIGCONT); //continuar proceso con execvp
				waitpid(msgbuffer.pid,NULL,0); //esperar hasta que termine hijo
				procesosPrior = procesosPrior + 1; //sumar contador
			}
			msgbuffer.mtype = -1;
			
		}
		
		
	}
	if (argc == 2)
	{
		close(f); //en el caso de que la entrada sea el fichero, cerrar fichero
	}
	borrar(arg); //liberar argumentos


}
void salida ()
{	
	printf("\nProcesos ejecutados con éxito: %d\n",procesosRR + procesosFCFS + procesosPrior);
	printf("	Procesos Round Robin (tipo 1): %d\n",procesosRR);
	printf("	Procesos Cola (tipo 2): %d\n",procesosFCFS);
	printf("	Procesos Prioridad (tipo 3): %d\n",procesosPrior);
	printf("El número de cambios de contexto es: %d\n",procesosRR + procesosFCFS + procesosPrior + cambiosContexto);
	printf("\nSaliendo del planificador\n");
	msgctl(idColaMensajes, IPC_RMID, 0); //liberar cola mensajes
	exit(1);
}
void terminado()
{
	haTerminado = 1; //proceso rr termina ejecución antes de 2 segundos
}
void nada() 
{

}
void alarma()
{
	suenaAlarma = 1; //suena alarma tras dos segundos
}
void finalizado()
{
	kill(getppid(),SIGUSR2); //no se puede cambiar el valor de la variable global directamente porque no reconoce el otro proceso
}


