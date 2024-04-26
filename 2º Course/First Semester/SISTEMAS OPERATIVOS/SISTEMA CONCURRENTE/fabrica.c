#include <stdio.h>
#include <stdlib.h> //atoi, rand, exit
#include <signal.h> //libreria señales
#include <sys/shm.h> //libreria memoria
#include <sys/sem.h> //libreria semaforos
#include <sys/msg.h> //libreria cola mensajes

// ./fabrica clave

typedef struct productos
{
	int servidos, pendientes;
	
}Productos; //estructura memoria compartida

struct ordenProduccion{

	long tipoMensaje;
	int a, b, c;
	
}ordenProduccion; //estructura mensajes cola

void salida();
void tiempoPasa();

int memoria, semaforo, idColaMensajes;
Productos *memCompartida;

int main(int argc, char **argv)
{
	struct sembuf buffer; //estructura semaforo
	
	if (argc != 2) //comprobacion argumentos correctos
	{
		printf("El número de argumentos introducidos es incorrecto\n");
		exit(0);	
	}
	
	int clave = atoi (argv[1]);
	
	signal(SIGINT, salida);

	/*if(-1 == (clave = ftok("/etc", clave))) //Creo la clave
    {
        printf("\nLa clave no se ha creado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }*/
    
    if (-1 == (idColaMensajes = msgget (clave, 0600 | IPC_CREAT))) //Creo la cola de mensajes
    {
    	printf("\nLa cola de mensajes no se ha creado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    
    if(-1 == (memoria = shmget(clave, sizeof(Productos), IPC_CREAT|0777))) //Creo la memoria compartida
    {
        printf("\nLa memoria no se ha creado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    
    if ((void *) - 1 == (memCompartida = (Productos *) shmat(memoria, NULL, 0)))
    {
    	printf("\nEl puntero a la dirección de memoria compartida no se ha creado correctamente\n");
    	printf("ERROR\n");
    	exit(0);
    }
        
    if (-1 == (semaforo = semget(clave, 4, 0600 | IPC_CREAT))) //Creo 4 semáforos (permisos escritura y lectura y de creación semáforos si no lo están)
    {
        printf("\nEl semaforo no se ha creado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    
    if (-1 == (semctl(semaforo, 0, SETVAL, 1))) //Inicializo el semáforo en verde (1)
    {
        printf("\nEl semaforo no se ha inicializado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    
    memCompartida->servidos = memCompartida->pendientes = 0;
    
    if (-1 == (semctl(semaforo, 1, SETVAL, 0))) //Inicializo el semáforo en rojo (0)
    {
        printf("\nEl semaforo no se ha inicializado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    if (-1 == (semctl(semaforo, 2, SETVAL, 0))) //Inicializo el semáforo en rojo (0)
    {
        printf("\nEl semaforo no se ha inicializado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    if (-1 == (semctl(semaforo, 3, SETVAL, 0))) //Inicializo el semáforo en rojo (0)
    {
        printf("\nEl semaforo no se ha inicializado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }

    ordenProduccion.a = -1; //se asegura que no entra al bucle si lee mensaje cola
    
    while (1)
    {
    	msgrcv (idColaMensajes, &ordenProduccion, sizeof(struct ordenProduccion) -sizeof(long), 5, IPC_NOWAIT); //5 = long tipoMensaje 
    	if (ordenProduccion.a != -1) //lee mensaje cola
    	{
			//hacer wait a los 3 semaforos (A, B, C)
			buffer.sem_num = 1; //índice array semáforo
			buffer.sem_op = -ordenProduccion.a; 
			buffer.sem_flg = 0; //flags operación
			
			semop(semaforo,&buffer,1); //decrementar semáforo material A
			buffer.sem_num = 2;
			buffer.sem_op = -ordenProduccion.b;
			semop(semaforo,&buffer,1); //decrementar semáforo material B
			buffer.sem_num = 3;
			buffer.sem_op = -ordenProduccion.c; //decrementar semáforo material C
			semop(semaforo,&buffer,1);
			
			printf("Producto %d, %d, %d. Inicio de la producción\n", ordenProduccion.a, ordenProduccion.b, ordenProduccion.c);
			//sleep(2);
			tiempoPasa(); //similar a sleep(2)
			printf("Producto %d, %d, %d finalizado\n",ordenProduccion.a, ordenProduccion.b, ordenProduccion.c);
			
			//bloquear semáforo que controla acceso a memoria principal
			buffer.sem_num = 0; //índice array semáforo
			buffer.sem_op = -1; //decrementar semáforo para bloquear (se bloquea directamente ya que solo puede valer 0 o -1 al ser el semáforo de memoria compartida)
			buffer.sem_flg = 0; //flags operación
			semop(semaforo,&buffer,1);
			
			//acceder memoria compartida en exclusión mutua
			memCompartida->servidos = memCompartida->servidos + 1;
			memCompartida->pendientes = memCompartida->pendientes - 1;
			
			buffer.sem_num = 0; //índice array semáforo
			buffer.sem_op = 1; //incrementar semáforo para desbloquear (se desbloquea directamente ya que pasa a tener valor positivo 1)
			buffer.sem_flg = 0; //flags operación
			semop(semaforo,&buffer,1);
			//desbloquear semáforo
    	}
    	
    	ordenProduccion.a = -1; //se asegura que no entra al bucle si lee mensaje cola
    }
    
}
void salida()
{
	if(-1 == semctl(semaforo, 0, IPC_RMID)) //eliminar semáforos
    {
        printf("\nLos semáforos no se han eliminado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    
    if (-1 == shmdt(memCompartida))
    {
        printf("\nLos semáforos no se han eliminado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    
    if(-1 == shmctl(memoria, IPC_RMID, 0)) //eliminar memoria
    {
        printf("\nLa memoria no se ha eliminado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    if (-1 == (msgctl(idColaMensajes, IPC_RMID, 0))) //eliminar cola de mensajes
    {
        printf("\nLa cola de mensajes no se ha eliminado correctamente\n");
        printf("ERROR\n");
        exit(0);
    }
    
	printf("\nSaliendo del sistema concurrente\n");
	exit(0);
}
void tiempoPasa()
{
  unsigned int i;
  
  int a = 3;
  
  for (i = rand()/4; i > 0; i = i - 1) //valores aleatorios que en mi ordenador se asemejan al tiempo de espera de dos segundos
  {
    a = a % 10 + 1;
  }
}
