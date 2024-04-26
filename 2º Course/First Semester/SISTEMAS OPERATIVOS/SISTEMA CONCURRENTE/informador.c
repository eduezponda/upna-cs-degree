#include <stdio.h>
#include <stdlib.h> //atoi, rand, exit
#include <signal.h> //libreria señales
#include <sys/shm.h> //libreria memoria compartida
#include <sys/sem.h> //libreria semaforos
#include <sys/msg.h> //libreria cola mensajes
#include <unistd.h> //procesos (fork, getpid)

// ./informador clave periodoI

void salida();
void tiempoPasa(); 

typedef struct productos
{
	int servidos, pendientes;
	
}Productos; //estructura memoria compartida

void main(int argc, char **argv)
{
        if (argc != 3)
	{
	        printf("El número de argumentos introducidos es incorrecto\n");
		exit(0);
	}
	key_t clave = atoi (argv[1]); 
	int periodoI = atoi (argv[2]);
	int i = 0;
	int semaforo, memoria;
	Productos *memCompartida;
	struct sembuf buffer;
	
	memoria = shmget(clave, sizeof(Productos), IPC_CREAT|0777); 
	memCompartida = (Productos *) shmat(memoria, NULL, 0);
	semaforo = semget(clave, 4, 0600 | IPC_CREAT);
	
	signal(SIGINT, salida); //detectar CONTROL+C para salir informador
	
	while (1)
	{
	    //sleep(periodoI);
	    for (int j = 1; j <= periodoI; j = j + 1) //bucle para que espere aproximadamente periodoI segundos
	    {
	      tiempoPasa();
	    }

	    buffer.sem_num = 0; //índice array semáforo
	    buffer.sem_op = -1; //decrementar semaforo para bloquear (se bloquea directamente ya que solo puede valer 1 o 0 al ser el semáforo de memoria compartida)
	    buffer.sem_flg = 0;
	    semop(semaforo,&buffer, 1); //bloquear semáforo memoria compartida
	    
	    //leer datos memoria compartida (contadores productos)
	    printf("[%d]: Informador %d estado de la producción: [%d, %d] \n", i, getpid(), memCompartida->servidos, memCompartida->pendientes); //acceder memoria compartida
	    
	    buffer.sem_num = 0; //índice array semáforo
	    buffer.sem_op = 1; //incrementar semaforo para desbloquear (se desbloquea directamente ya que solo puede valer 1 o 0 al ser el semáforo de memoria compartida)
	    buffer.sem_flg = 0;
	    semop(semaforo, &buffer, 1); //desbloquear semáforo memoria compartida
	    
	    i = i + 1; //aumentar contador
	}
}
void salida()
{
	printf("\nSaliendo del informador\n");
	exit(0);
}
void tiempoPasa() //valores para que espere aproximadamente un segundo
{
  unsigned int i;
  
  int a = 3;
  
  for (i = rand()/4; i > 0; i = i - 1) 
  {
    a = a % 7 + 1;
  }
}
