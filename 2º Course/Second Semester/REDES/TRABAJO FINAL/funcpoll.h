#ifndef IGL_EEI
#define IGL_EEI

#include <poll.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

typedef struct cliente{
	char* id;
	char* nombre;
	bool log;
	bool nameset;
	bool turno;
	char ficha;
}Cliente;

typedef struct s_client {
    int vacio;
    int socket;
    FILE *stream;
} client ;

typedef struct s_grupo_de_sockets {
    int serv_socket;
    client* client_info;
    struct pollfd* poll;
    int nclients;
    Cliente* cli;
} grupo_de_sockets ;


void init_grupo_de_sockets(grupo_de_sockets *grupo, int servsocket, int max);
int grupo_de_sockets_guarda_socket(grupo_de_sockets *grupo, int socket, FILE *stream, int max);
int grupo_de_sockets_borra_socket(grupo_de_sockets *grupo, int socket, int max);
int grupo_de_sockets_acepta_nuevo_cliente(grupo_de_sockets *grupo, int max);
void grupo_de_sockets_genera_fd_set(grupo_de_sockets *grupo, fd_set *fdset, int *maxfd, int max);
void grupo_de_sockets_genera_pollinfo(grupo_de_sockets *grupo, int max);
time_t getTime();

#endif
