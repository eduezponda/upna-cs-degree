#include <stdbool.h>
#include <time.h>
#include <stdio.h>
#include "servfunc.h"


bool opciones(grupo_de_sockets*, int, int);
void timeIsUp();


time_t inicio_ser;
int periodo, max;
grupo_de_sockets sockets;


int main(int argc, char *argv[]) {

    if (argc < 3) {
        printf("di puerto y numero maximo de clientes\n");
        exit(-1);
    }

    int puerto = atoi(argv[1]);
    max = atoi(argv[2]);

    struct sockaddr_in local;
    local.sin_family = AF_INET;
    local.sin_addr.s_addr = INADDR_ANY;
    local.sin_port = htons( puerto );

    int s = socket(AF_INET,SOCK_STREAM,0);

    int r = bind(s,(struct sockaddr *)&local,sizeof(local));
    if (r < 0) {
        printf("No puedo hacer bind al puerto %d\n",puerto);
        exit(-1);
    }
    FILE *f;
    if ((f = fopen("servidor.txt", "r")) == NULL) {
		if ((f = fopen("servidor.txt", "w")) == NULL) {
			printf("Error al crear el fichero\n");
			exit(1);
		}
    }
	fclose(f);

    listen(s,5);
    
    inicio_ser = getTime();

    printf("Escuchando conexiones en el puerto\n");

    init_grupo_de_sockets(&sockets,s,max);

    periodo = atoi(argv[3]);

    if (argc > 3 && periodo > 0) {
	    signal(SIGALRM, timeIsUp);
	    alarm(periodo);
    }
    
    while (1) {
        grupo_de_sockets_genera_pollinfo(&sockets, max);
        poll(sockets.poll,1+max,-1);

        struct pollfd *pollinfo;
        pollinfo=&(sockets.poll[0]);
        if ( pollinfo->revents & POLLIN ) {
            grupo_de_sockets_acepta_nuevo_cliente(&sockets, max);
        }

        for (int i = 0; i<max; i++) {
            client *cli = &(sockets.client_info[i]);
            pollinfo=&(sockets.poll[i+1]);

            if ( pollinfo->revents ) {
                int r=opciones(&sockets, i, max);
                if ( r == 0 ) {
                    grupo_de_sockets_borra_socket(&sockets,cli->socket, max);
                    printf("Conexion con %s cerrada\n", sockets.cli[i].id);
					if (sockets.nclients > 1)
						printf("%d usuarios conectados\n", sockets.nclients);
					else if (sockets.nclients == 1)
						printf("1 usuario conectado\n");
					else
						printf("No hay usuarios conectados\n");
                }
            }
       }
   }
}


bool opciones (grupo_de_sockets* sockets, int i, int max) {
        FILE* cli_stream = sockets->client_info[i].stream;
	char lineabuf[100];
	Cliente* cliente = &sockets->cli[i];

	if (fgets(lineabuf,100,cli_stream) != NULL) {
		int len=strlen(lineabuf);
		if (lineabuf[len-1]=='\n') {
			lineabuf[len-1]='\0';
		}
		char name[50];
		int c;
		if (!cliente->log) {
			if (strcmp(lineabuf, "REGISTRAR") == 0) {
				cliente->log = registro(cli_stream, cliente);
			}
			else if (sscanf(lineabuf, "LOGIN %s %d", cliente->id, &c)) {
				cliente->log = login(c, cliente, cli_stream);
			}
			else {
				printf("ERROR\n");
				fprintf(cli_stream,"ERROR\n");
				return false;
			}
			if (sockets->nclients > 1 && cliente->log)
				printf("%d usuarios conectados\n", sockets->nclients);
			else if (sockets->nclients == 1)
				printf("1 usuario conectado\n");
			else if (cliente->log)
				printf("No hay usuarios conectados\n");
			return cliente->log;
		}
		else {
			if (sscanf(lineabuf, "SETNAME %s", name)) {
				if (setName(cliente->id, name)) {
					strcpy(cliente->nombre, name);
					printf("Usuario con id %s ha cambiado su nombre a %s\n", cliente->id, name);
					fprintf(cli_stream, "%s\n", "SETNAME OK");
				}
				else {
					printf("Usuario con id %s ha introducido un nombre no valido\n", cliente->id);
					fprintf(cli_stream, "%s\n", "SETNAME ERROR");
				}
			}
			else if (strcmp(lineabuf, "GETNAME") == 0) {
				printf("Enviando nombre del Usuario: %s (%s) ...\n", cliente->id, cliente->nombre);
				fprintf(cli_stream, "GETNAME %s\n", cliente->nombre);
			}
			else if (strcmp(lineabuf, "UPTIME") == 0) {
				fprintf(cli_stream, "UPTIME %ld %ld\n", (time_t)(getTime() - inicio_ser), (time_t)(getTime() - cliente->inicio));

				printf("Enviando informacion de tiempo a %s\n", cliente->id);
			}
			else if (strcmp(lineabuf, "LISTA") == 0) {
				fprintf(cli_stream, "LISTADO\n");

				for (int j = 0; j < max; j++) {
					if (!sockets->client_info[j].vacio) {
						if (j != i) {
							fprintf(cli_stream, "U %s\n", sockets->cli[j].nombre);
						}
						else {
							fprintf(cli_stream, "Y %s\n", sockets->cli[j].nombre);
						}
					}
				}
				fprintf(cli_stream, "E\n");
				printf("Enviando nombres de usuarios conectados a %s\n", cliente->id);
			}
			else {
				printf("ERROR\n");
				fprintf(cli_stream,"ERROR\n");
				return false;
			}
			return true;
		}
	}
	return false;
}


void timeIsUp() {
	info_periodica(sockets, inicio_ser, max);
	alarm(periodo);
}

