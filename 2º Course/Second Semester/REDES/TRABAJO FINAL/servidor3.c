#include "servfunc.h"
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>


void cerrar();


int s;


int main(int argc, char *argv[]) {

    if (argc < 4) {
        printf("di puerto, numero de filas y columnas del tablero\n");
        exit(EXIT_FAILURE);
    }

    int puerto = atoi(argv[1]);
    int filas = atoi(argv[2]);
	int columnas = atoi(argv[3]);

	if (filas < 6 || columnas < 7) {
		printf("Tamaño del tablero incorrecto\n");
		exit(EXIT_FAILURE);
	}

    struct sockaddr_in local;
    local.sin_family = AF_INET;
    local.sin_addr.s_addr = INADDR_ANY;
    local.sin_port = htons( puerto );

    s = socket(AF_INET,SOCK_STREAM,0);

	signal(SIGINT, cerrar);

    int r = bind(s,(struct sockaddr *)&local,sizeof(local));
    if (r < 0) {
        printf("No puedo hacer bind al puerto %d\n",puerto);
        exit(EXIT_FAILURE);
    }

    FILE *f;
    if ((f = fopen("servidor.txt", "r")) == NULL) {
		if ((f = fopen("servidor.txt", "w")) == NULL) {
			printf("Error al crear el fichero\n");
			exit(EXIT_FAILURE);
		}
    }
	fclose(f);

    listen(s,5);

    printf("Escuchando conexiones en el puerto\n");

	int max = 2;
	bool partidaEnCurso = false;
	tablero tab;
	grupo_de_sockets sockets;

	inicializarTablero(&tab, filas, columnas);

    init_grupo_de_sockets(&sockets,s,max);

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
                int res = opciones(&sockets, tab, i, max, &partidaEnCurso, filas, columnas, false);
                if ( res == 0 ) {
                    grupo_de_sockets_borra_socket(&sockets,cli->socket, max);
                    printf("Conexion con %s cerrada\n", sockets.cli[i].id);
                }
            }
       }

       if (!partidaEnCurso && sockets.nclients == 2 && sockets.cli[0].nameset && sockets.cli[1].nameset) {
			partidaEnCurso = inicioPartida(&sockets, columnas, filas, false);
	   }
   }
}


void cerrar() {
	close(s);

	exit(EXIT_SUCCESS);
}
