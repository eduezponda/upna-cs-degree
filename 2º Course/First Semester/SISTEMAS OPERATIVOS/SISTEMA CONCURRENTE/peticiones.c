#include <stdio.h>
#include <stdlib.h> //atoi
#include <unistd.h> //dup2 y close(f)
#include <sys/shm.h> //libreria memoria
#include <sys/sem.h> //libreria semaforos
#include <sys/msg.h> //libreria cola mensajes
#include <fcntl.h> //libreria abrir descriptor fichero (open)

// ./peticiones clave .txt
// fichero opcional para cambiar entrada estándar (teclado) a fichero

struct ordenProduccion{

	long tipoMensaje;
	int a, b, c;
	
}ordenProduccion; //estructura mensajes cola

typedef struct productos
{
	int servidos, pendientes;
	
}Productos; //estructura memoria compartida

int main(int argc, char **argv)
{
	int f, semaforo, memoria, idColaMensajes, clave;
	struct sembuf buffer; //estructura semaforo
	Productos *memCompartida;
	
	if (argc != 2 && argc != 3)
	{
	        printf("El número de argumentos introducidos es incorrecto\n");
		exit(0);
	}

	if (argc == 3) //leer desde fichero 
	{
		f = open (argv[2], O_RDONLY); //abrir descriptor fichero
		dup2(f,STDIN_FILENO); //cambiar entrada estándar
	}
	
	clave = atoi(argv[1]);
	idColaMensajes = msgget (clave, 0777);
	memoria = shmget(clave, sizeof(Productos), 0777);
	memCompartida = (Productos *) shmat(memoria, NULL, 0);
	semaforo = semget(clave, 4, 0); //0=IPC_PRIVATE (obtener clave de un semaforo creado)
	ordenProduccion.tipoMensaje = 5; //inicializar long para que indica como se leen los mensajes
	
	while (1)
	{
		if (argc != 3)
		{
			printf("Introduce la petición (Material A B C): "); //meter dentro de if por si es fichero
		}
		scanf("%d %d %d",&ordenProduccion.a, &ordenProduccion.b, &ordenProduccion.c); //escanear desde teclado o fichero
		if (getchar() == EOF) //control+d
		{
		        if (argc != 3)
		        {
		            printf("\n");
		        }
			break;
		}
		buffer.sem_num = 0; //índice array semáforo
	        buffer.sem_op = -1; //decrementar semáforo para bloquearlo (exclusión mutua)
	        buffer.sem_flg = 0; //flags operación
	        semop(semaforo,&buffer,1);
	        
		//exclusion mutua
		memCompartida->pendientes = memCompartida->pendientes + 1;
		//actualizar semáforo
		
		buffer.sem_num = 0; //índice array semáforo
	        buffer.sem_op = 1; //incrementar semáforo para desbloquearlo 
	        buffer.sem_flg = 0; //flags operación
	        semop(semaforo,&buffer,1);
		msgsnd (idColaMensajes, &ordenProduccion, sizeof(struct ordenProduccion)-sizeof(long), IPC_NOWAIT);
	}
	
	if (argc == 3) //cerrar descriptor fichero
	{
		close(f);
	}
	
}
