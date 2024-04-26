#include "fragmenta.h"  //implementar modulo fragmenta
#include <stdio.h>
#include <stdlib.h>   //crear memoria y punteros
#include <string.h>  //funcion strcmp, implementar funciones de las cadenas

#include <sys/wait.h> //implementar funciones para los procesos (pipes, fork)
#include <sys/types.h> //creacion procesos hijos
#include <sys/stat.h> //abrir descriptores ficheros

#include <unistd.h>  //creacion procesos
#include <fcntl.h> //abrir descriptores ficheros
#include <signal.h> //implementar señales

#define MAX_INPUT 128 //tamaño entrada por teclado del usuario

int main (void)
{
	char entradaTeclado[MAX_INPUT];
	
	while(1) //el bucle no termina hasta que ejecutes exit o control+c (envio señal SIGINT para salir del programa). 1 = TRUE
	{		 
		printf("minishell""\\""> "); //inicializar
		scanf("%[^\n]",entradaTeclado); //introducir comando por teclado
		fgetc(stdin);
		
		if(strcmp(entradaTeclado, "exit") == 0) //si el comando es exit ->programa finaliza con salida(). Se compara la string del input con "exit" con la funcion strcmp
		{
			salida();
		}
		else
		{
		 	//continuar
		}
		signal(SIGINT, salida); // si se envia la señal SIGINT a través del comando control + c, se llama a la funcion salida para salir del programa
		
		const int pid = fork(); //creamos proceso hijo
		
		if (pid == -1) //error en la creacion del proceso hijo
		{
			printf("Error en la creación del proceso hijo\n");
		}
		
		else if (pid == 0) //proceso hijo
		{
			char** arg;
			int i;
			
			arg = fragmenta(entradaTeclado); //divides el comando en distintas palabras
			
			i = 0;
			
			while (arg[i] != NULL) //recorres las palabras para comprobar el comando introducido por el usuario
			{
				if (strcmp(arg[i], "|") == 0)
				{
					arg[i] = NULL; //elimino la parte del comando correspondiente
					int tuberia1[2]; //declaración tuberia 1
					pipe(tuberia1); //inicializacion tuberia 1
					int pid2;
					pid2 = fork(); //creacion proceso hijo
					
					if (pid2 == -1)
					{
						printf("Error en la creación del proceso hijo\n");
					}
					else if (pid2 == 0) //proceso hijo
					{
						close(tuberia1[0]); //cerrar tuberia1 (leer) ->parte que no utilizas ->evitar inconsistencias
						dup2(tuberia1[1], STDOUT_FILENO); //cambiar descriptor fichero
					}
					else //proceso padre
					{
						int status;
						
						close(tuberia1[1]); //cerrar tuberia1 (escribir) ->parte que no utilizas ->evitar inconsistencias
						dup2(tuberia1[0], STDIN_FILENO); //cambiar descriptor fichero
						waitpid(pid2, &status, 0);
						execvp(arg[i + 1], &arg[i + 1]); // el proceso cambia el programa que se esta ejecutando por otro
						borrarg(arg); // se libera la parte de la memoria que ha sido usada
						
					}
				}
				else if (strcmp(arg[i], ">") == 0)
				{
					int f; //declaración descriptor fichero
					
					f = open (arg[i + 1], O_WRONLY | O_TRUNC | O_CREAT, 0666); //creacion descriptor fichero f en escritura. Si ya existía, se sobreescribe para listar el contenido del directorio local en f
					if (dup2(f, STDOUT_FILENO) == -1) //error al redireccionar salida duplicando la salida del descriptor
					{
						printf("Error en el redireccionamiento de la salida\n");
					}
					else
					{
						//continuar
					}
					arg[i] = NULL; //elimino la parte del comando correspondiente
					close(f); //cerrar descriptor fichero
					i = i + 1; //avanzar siguiente parte comando después de haber leido dos partes (">" y el nombre del descriptor de fichero)
					
				}
				else if (strcmp(arg[i], ">>") == 0)
				{
					int f; //declaración descriptor fichero
					
					f = open (arg[i + 1], O_RDWR | O_CREAT | O_APPEND, 0700); //abrir descriptor fichero en lectura y le agrego el contenido del directorio local de f
					if (dup2(f, STDOUT_FILENO) == -1) //error al redireccionar salida duplicando la salida del descriptor
					{
						printf("Error en el redireccionamiento de la salida\n");
					}
					else
					{
					 	//continuar
					}
					arg[i] = NULL; //elimino la parte del comando correspondiente
					close(f); //cerrar descriptor fichero
					i = i + 1; //avanzar siguiente parte comando después de haber leido dos partes (">>" y el nombre del descriptor de fichero)
				}
				else if (strcmp(arg[i], "<") == 0)
				{
					int f; //declaración descriptor fichero
					
					f = open (arg[i + 1],O_RDONLY); //abrir descriptor fichero en lectura 
					if (dup2(f, STDIN_FILENO) == -1) //error al redireccionar salida duplicando la salida del descriptor
					{
						printf("Error en el redireccionamiento de la salida\n");
					}
					else
					{
					 	//continuar
					}
					arg[i] = NULL; //elimino la parte del comando correspondiente
					close(f); //cerrar descriptor fichero
					i = i + 1; //avanzar siguiente parte comando después de haber leido dos partes (">>" y el nombre del descriptor de fichero)
				}	
				i = i + 1; //avanzar a la siguiente parte del fragmenta
			}
			
			execvp(arg[0], arg);
			borrarg(arg); // se libera la parte de la memoria que ha sido usada
		}
		else //proceso padre
		{
			wait(NULL); //se espera a que termine el proceso hijo
			kill(pid, SIGINT); //envio de la señal SIGINT para terminar con el proceso hijo
		}
		
	}
}

