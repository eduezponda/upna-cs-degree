#include "fragmenta.h"  //implementar modulo fragmenta
#include <stdio.h>
#include <stdlib.h>   //para el exit
#include <string.h>  //funcion strcmp ->comparar strings, si devuelve 0 es que coinciden

#include <sys/wait.h> //implementar funciones para los procesos (pipes, fork)
#include <sys/types.h> //creacion procesos 
#include <sys/stat.h> //abrir descriptores ficheros

#include <unistd.h>  //creacion procesos
#include <fcntl.h> //abrir descriptores ficheros

#define MAX 150 //tamaño entrada estándar (teclado)
#define TRUE 1

int main (void)
{
	char entradaTeclado[MAX];
	
	while(TRUE) //el bucle no termina hasta que ejecutes exit o control+c (envio señal SIGINT para salir del programa). TRUE = 1
	{		 
		printf("minishell""\\""> "); //inicializar
		scanf("%[^\n]",entradaTeclado); //introducir comando por teclado
		fgetc(stdin);
		
		if(strcmp(entradaTeclado, "exit") == 0) //si el comando es exit ->programa finaliza 
		{
			exit(-1);
		}
		else
		{
		 	//continuar
		}
		
		int pid;
		pid = fork(); //creamos proceso hijo
		
		if (pid == -1) 
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
				if (strcmp(arg[i], "<") == 0)
				{
					int f; 
					
					f = open (arg[i + 1],O_RDONLY); //abrir descriptor fichero en lectura 
					if (dup2(f, STDIN_FILENO) == -1) 
					{
						printf("Error en el redireccionamiento de la salida\n");
					}
					else
					{
					 	//continuar
					}
					close(f); //cerrar descriptor fichero
					arg[i] = NULL; //elimino la parte del comando correspondiente
					i = i + 1; //avanzar siguiente parte comando después de haber leido dos partes ("<" y el nombre del descriptor de fichero)
				}
				else if (strcmp(arg[i], ">") == 0)
				{
					int f; 
					
					f = open (arg[i + 1], O_WRONLY | O_TRUNC | O_CREAT, 0666); //creacion descriptor fichero f en escritura. Si ya existía, se sobreescribe para agregar el contenido del directorio local en f
					if (dup2(f, STDOUT_FILENO) == -1) 
					{
						printf("Error en el redireccionamiento de la salida\n");
					}
					else
					{
						//continuar
					}
					close(f); //cerrar descriptor fichero
					arg[i] = NULL; //elimino la parte del comando correspondiente
					i = i + 1; //avanzar siguiente parte comando después de haber leido dos partes (">" y el nombre del descriptor de fichero)
					
				}
				
				else if (strcmp(arg[i], ">>") == 0)
				{
					int f; 
					
					f = open (arg[i + 1], O_RDWR | O_CREAT | O_APPEND, 0700); //abrir descriptor fichero en lectura y le agrego el contenido del directorio local al final de f
					if (dup2(f, STDOUT_FILENO) == -1) 
					{
						printf("Error en el redireccionamiento de la salida\n");
					}
					else
					{
					 	//continuar
					}
					close(f); //cerrar descriptor fichero
					arg[i] = NULL; //elimino la parte del comando correspondiente
					i = i + 1; //avanzar siguiente parte comando después de haber leido dos partes (">>" y el nombre del descriptor de fichero)
				}
				else if (strcmp(arg[i], "|") == 0)
				{
					int tuberia[2]; 
					int pid2;
					
					arg[i] = NULL; //elimino la parte del comando correspondiente
					pipe(tuberia); //inicializacion tuberia 
					pid2 = fork(); //creacion proceso hijo
					
					if (pid2 == -1) 
					{
						printf("Error en la creación del proceso hijo\n");
					}
					else if (pid2 == 0) //proceso hijo
					{
						close(tuberia[0]); //cerrar tuberia ->parte que no utilizas -> evitar inconsistencias
						dup2(tuberia[1], STDOUT_FILENO); //cambiar descriptor fichero
					}
					else //proceso padre
					{
						int status;
						
						close(tuberia[1]); //cerrar tuberia ->parte que no utilizas -> evitar inconsistencias
						dup2(tuberia[0], STDIN_FILENO); //cambiar descriptor fichero
						waitpid(pid2, &status, 0);
						execvp(arg[i + 1], &arg[i + 1]); 
						borrarg(arg); // se libera la parte del argumento que ha sido usada
						
					}
				}
				else
				{
					//continuar
				}
					
				i = i + 1; //avanzar a la siguiente parte del fragmenta
			}
			execvp(arg[0], arg);
			borrarg(arg); // se libera la parte del argumento que ha sido usada
		}
		else //proceso padre
		{
			wait(NULL); //se espera a que termine el proceso hijo
			kill(pid, SIGINT); //envio de la señal SIGINT para eliminar el proceso hijo
		}
		
	}
}

