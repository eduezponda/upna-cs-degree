#include "funcpoll.h"

void init_grupo_de_sockets(grupo_de_sockets *grupo, int servsocket, int max) {
    grupo->serv_socket=servsocket;
    grupo->nclients=0;
    grupo->poll = (struct pollfd*)malloc(sizeof(struct pollfd)*(max+1));
    grupo->client_info = (client*)malloc(sizeof(client)*max);
    grupo->cli = (Cliente*)malloc(sizeof(Cliente)*max);
    int i;
    for (i=0; i<max; i++) {
        grupo->client_info[i].vacio=1;
        grupo->cli[i].id = (char*)malloc(sizeof(char*)*9);
        grupo->cli[i].nombre = (char*)malloc(sizeof(char*)*25);
    }
}


int grupo_de_sockets_guarda_socket(grupo_de_sockets *grupo, int socket, FILE *stream, int max) {
    int i;
    for (i=0; i<max; i++) {
        if (grupo->client_info[i].vacio) {
            grupo->client_info[i].vacio=0;
            grupo->client_info[i].socket=socket;
            grupo->client_info[i].stream=stream;
            strcpy(grupo->cli[i].id, "Invitado");
            strcpy(grupo->cli[i].nombre, "Invitado");
            grupo->cli[i].log = false;
            grupo->cli[i].turno = false;
            grupo->cli[i].nameset = false;
            grupo->nclients+=1;
            return 1;
        }
    }
    return 0;
}


int grupo_de_sockets_borra_socket(grupo_de_sockets *grupo, int socket, int max) {
    int i;
    for (i=0; i<max; i++) {
        if ( (!grupo->client_info[i].vacio) && grupo->client_info[i].socket==socket) {
            grupo->client_info[i].vacio=1;
            fclose(grupo->client_info[i].stream);
            grupo->nclients-=1;
            return 1;
        }
    }
    return 0;
}


int grupo_de_sockets_acepta_nuevo_cliente(grupo_de_sockets *grupo, int max) {
    struct sockaddr_in cli_dir;
    socklen_t cli_dir_len = sizeof(cli_dir);
    int cli_sock = accept(grupo->serv_socket,(struct sockaddr *)&cli_dir,&cli_dir_len);
    FILE *cli_stream=fdopen(cli_sock,"r+");
    setbuf(cli_stream,NULL);
    if ( ! grupo_de_sockets_guarda_socket(grupo,cli_sock,cli_stream, max) ) {
        printf("Nueva conexion denegada, numero maximo de clientes alcanzado\n");
        fprintf(cli_stream, "FULL\n");
        fclose(cli_stream);
        return 0;
    }
    printf("Conexion establecida desde la IP %s puerto %d\n", inet_ntoa(cli_dir.sin_addr), ntohs(cli_dir.sin_port));
    fprintf(cli_stream, "WELCOME\n");

    return cli_sock;
}


void grupo_de_sockets_genera_fd_set(grupo_de_sockets *grupo, fd_set *fdset, int *maxfd, int max) {
    FD_ZERO(fdset);
    FD_SET(grupo->serv_socket,fdset);
    *maxfd=grupo->serv_socket+1;
    int i;
    for (i=0; i<max; i++) {
        client *cli = &(grupo->client_info[i]);
        if (!cli->vacio) {
            FD_SET(cli->socket,fdset);
            if (cli->socket>=*maxfd) *maxfd=cli->socket+1;
        }
    }
}


void grupo_de_sockets_genera_pollinfo(grupo_de_sockets *grupo, int max) {
    grupo->poll[0].fd=grupo->serv_socket;
    grupo->poll[0].events=POLLIN;
    grupo->poll[0].revents=0;
    int i;
    for (i=0; i<max; i++) {
        client *cli = &(grupo->client_info[i]);
        if (!cli->vacio) {
            grupo->poll[i+1].fd=cli->socket;
            grupo->poll[i+1].events=POLLIN;
            grupo->poll[i+1].revents=0;
        } else {
            grupo->poll[i+1].fd=-1;
        }
    }
}


time_t getTime() {
	struct timespec tiempo;
	clock_gettime(CLOCK_REALTIME, &tiempo);
	return tiempo.tv_sec;
}
