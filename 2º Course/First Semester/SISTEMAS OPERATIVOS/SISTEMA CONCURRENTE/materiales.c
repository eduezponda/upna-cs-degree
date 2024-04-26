#include <stdio.h>
#include <stdlib.h> //atoi
#include <unistd.h> //dup2 y close(f)
#include <sys/sem.h> //libreria semaforos
#include <sys/msg.h> //libreria cola mensajes
#include <fcntl.h> //libreria abrir descriptor fichero (open)


// ./materiales clave .txt
// fichero opcional ->leer desde entrada estándar (teclado) o fichero

struct ordenProduccion{

	long tipoMensaje;
	int a, b, c;
	
}ordenProduccion; //estructura mensajes cola

int main(int argc, char **argv)
{
	int f, semaforo, clave;
	char entradaTeclado[100];
	struct sembuf buffer;

        if (argc != 2 && argc != 3)
	{
	        printf("El número de argumentos introducidos es incorrecto\n");
		exit(0);
	}

	if (argc == 3) //leer desde teclado
	{
		f = open (argv[2], O_RDONLY);
		dup2(f,STDIN_FILENO); //cambiar entrada estándar a descriptor fichero
		//leer desde fichero 
	}
	
	clave = atoi (argv[1]);
	semaforo = semget(clave, 4, 0); //0=IPC_PRIVATE (obtener clave de un semaforo creado)
	
	while (1)
	{
		if (argc != 3) //si no lee desde fichero
		{
			printf("Introduce los materiales (A B C): "); //meter dentro de if por si es fichero
		}
		scanf("%d %d %d",&ordenProduccion.a, &ordenProduccion.b, &ordenProduccion.c); //escanear elementos desde teclado o fichero
		if (getchar() == EOF) //control+d (final fichero)
		{
		        if (argc != 3)
		        {
		            printf("\n");
		        }
			break;
		}
		buffer.sem_num = 1; //índice array semaforo
		buffer.sem_op = ordenProduccion.a; //aumentar semaforo material A
		buffer.sem_flg = 0;
		semop(semaforo, &buffer, 1);
		
		buffer.sem_num = 2; //índice array semaforo
		buffer.sem_op = ordenProduccion.b; //aumentar semaforo material B
		buffer.sem_flg = 0;
		semop(semaforo, &buffer, 1);
		
		buffer.sem_num = 3; //índice array semaforo
		buffer.sem_op = ordenProduccion.c; //aumentar semaforo material C
		buffer.sem_flg = 0;
		semop(semaforo, &buffer, 1);
	}
	
	if (argc == 3) //si lee desde fichero -> cerrar
	{
		close(f);
	}
	
}
