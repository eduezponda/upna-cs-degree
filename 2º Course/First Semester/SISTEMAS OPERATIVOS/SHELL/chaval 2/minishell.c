#include <stdio.h>
#include <stdlib.h>
#include <string.h> //Para tratar strings (funcion strcmp)

#include <unistd.h> //Para crear procesos
#include <sys/types.h> //Creacion de hijos
#include <sys/wait.h> //Incluyen los fork, waits, pipes... (en Windows no existe)

#include <signal.h> //Detectar las señales (control+c)

#include <sys/stat.h> //Para abir ficheros
#include <fcntl.h> //Para abrir ficheros

#include "fragmenta.h" //Incluyo el fichero fragmenta con sus funciones y acciones

#define MAX_CMD 256 //Constante que define el tamaño de la entrada

int main()
{
    char input[MAX_CMD]; //Variable que guarda la entrada
    signal(SIGINT,salida); //Comando para detectar el control+c, si es introducido se llama a la funcion salida y finaliza el programa
    
    while(1){ //Se ejecuta el bucle hasta que el usuario ponga exit o pulse control+c
        //Introduzca el comando
        printf("minishell""\\""> ");
        //Se escanea en la variable input
        scanf("%[^\n]",input);
        fgetc(stdin);
        
        if (strcmp(input, "exit") == 0) //Si el input es exit el programa finaliza llamando a la funcion salida. Con "strcmp" comparo los strings
            salida();
        
        //signal(SIGINT,salida); //Comando para detectar el control+c, si es introducido se llama a la funcion salida y finaliza el programa

        //Creamos el proceso hijo
        const int pid = fork();
        
        if (pid == -1) //Comprobamos si se crea el proceso
        {
			printf("Error: No se ha podido crear el proceso\n");
		}
		else if (pid == 0){ //Se ha creado el proceso (hijo)
            char** palabras; //crear tabla de strings
            palabras = fragmenta(input); //Fragmento el comando en palabras mediante la funcion fragmenta
            
            //Ahora compruebo cada una de las posibles instrucciones
            int i = 0;
            while (palabras[i] != NULL) //Recorro todas las palabras que se han introducido en el comando
            {
                if (strcmp(palabras[i], "|") == 0){
					palabras[i] = NULL; //Elimino el comando
                    int pipe1[2]; //Creo una tuberia
                    pipe(pipe1); //La inicio
                    int p = fork(); //Creo un proceso
                    if (p == 0){ //Hijo
                        close(pipe1[0]); //Cierro la tuberia
                        dup2(pipe1[1], STDOUT_FILENO); //Cambio el descriptor de fichero
                    }
                    else{ //Padre
                        int estado;
                        close(pipe1[1]); //Cierro la tuberia
                        dup2 (pipe1[0], STDIN_FILENO); //Cambio el descriptor de fichero
                        waitpid (p, &estado, 0); //Espero
                        execvp(palabras[i + 1], &palabras[i + 1]); //El proceso sustituye el programa que se esta ejecutando por otro con la funcion exec
                        borrarg(palabras); //Libero la memoria usada
                    }
				}
				else if (strcmp(palabras[i], ">") == 0){
					int fich;
                    fich = open(palabras[i+1],O_WRONLY | O_TRUNC | O_CREAT, 0666); //Creo un fichero en escritura o, si existe, lo sobreescribo, para listar el contenido del directorio local en dicho fichero
                    if (dup2(fich,STDOUT_FILENO) == -1) //Redirecciona la salida al fichero duplicandola, si es -1 da error
                    {
						printf("ERROR: no se ha podido redireccionar la salida\n");
					}
					palabras[i] = NULL; //Igualo a null
                    close(fich); //Cierro el fichero
                    i = i + 1; //Avanzo a la siguiente palabra ya que he leido 2 (> y el nombre del fichero)
				}
				else if (strcmp(palabras[i], ">>") == 0){
					int fich;
                    fich = open(palabras[i+1],O_RDWR | O_CREAT | O_APPEND, 0700); //Abro un fichero en modo lectura y anexo el contenido del directorio local al final de dicho fichero
                    if (dup2(fich,STDOUT_FILENO) == -1) //Redirecciona la salida al fichero duplicandola, si es -1 da error
                    {
						printf("ERROR: no se ha podido redireccionar la salida\n");
					} 
					palabras[i] = NULL; //Igualo a null
                    close(fich); //Cierro el fichero
					i = i + 1; //Avanzo a la siguiente palabra ya que he leido 2 (>> y el nombre del fichero)
				}
				else if (strcmp(palabras[i], "<") == 0){
                    int fich;
                    fich = open(palabras[i+1],O_RDONLY); //Abro un fichero en modo lectura 
                    if (dup2(fich,STDIN_FILENO) == -1) //Redirecciona la salida al fichero duplicandola, si es -1 da error
                    {
						printf("ERROR: no se ha podido redireccionar la salida\n");
					}
                    palabras[i] = NULL; //Igualo a null
                    close(fich); //Cierro el fichero
                    i = i + 1; //Avanzo a la siguiente palabra ya que he leido 2 (< y el nombre del fichero)
				}
				i = i + 1; //Avanzo a la siguiente palabra
			}
			execvp(palabras[0],palabras); //El proceso sustituye el programa que se esta ejecutando por otro con la funcion exec (ejecutando los comandos de una sola palabra)
			borrarg(palabras); //LLamo a la funcion borrarg para limpiar los espacios de memoria usados
        }
        else  //Padre
        {
			wait(NULL); //Esperamos al hijo
			kill(pid, SIGINT); //Mato al hijo
		}
	}
}
