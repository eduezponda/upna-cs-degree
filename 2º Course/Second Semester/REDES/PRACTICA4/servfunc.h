#ifndef IGL_EEI_2
#define IGL_EEI_2

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <signal.h>
#include "funcpoll.h"

void generarID (char*);
bool setName(char*, char*);
bool esValidoNombre(char*);
time_t getTime();
bool registro(FILE*, Cliente* cliente);
bool login(int, Cliente* cliente, FILE* );
void info_periodica(grupo_de_sockets, time_t, int);

#endif

