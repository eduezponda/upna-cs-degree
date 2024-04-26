#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>



void generarID (char*);
bool setName(char*, char*);
void getName(char*, char*);
bool esValidoNombre(char*);
bool opciones(char*);


FILE *cli_stream;


int main(int argc, char *argv[]) {
    if (argc < 2) {
        printf("di puerto\n");
        exit(-1);
    }

    int puerto = atoi( argv[1]);

    struct sockaddr_in local;
    local.sin_family = AF_INET;
    local.sin_addr.s_addr = INADDR_ANY;
    local.sin_port = htons( puerto );

    int s = socket(AF_INET,SOCK_STREAM,0);

    int r = bind(s,(struct sockaddr *)&local,sizeof(local));
    if ( r < 0 ) {
        printf("No puedo hacer bind al puerto %d\n",puerto);
        exit(-1);
    }
    FILE *f;
    if ((f = fopen("servidor.txt", "r")) == NULL) {
		if ((f = fopen("servidor.txt", "w+")) == NULL) {
			printf("Error al crear el fichero\n");
			exit(1);
		}
    }
    fclose(f);

    listen(s,5);

    printf("Escuchando conexiones en el puerto\n");

    struct sockaddr_in remoto;
    socklen_t remotolen = sizeof(remoto);
    int cli_sock;
    while (1) {
		cli_sock=accept(s,(struct sockaddr *)&remoto,&remotolen);
		cli_stream = fdopen(cli_sock,"r+");
		setbuf(cli_stream,NULL);
		char id[9];
		strcpy(id, "Invitado");
		while(1) {
			printf("Conexión establecida desde la IP %s puerto %d\n", inet_ntoa(remoto.sin_addr), ntohs(remoto.sin_port));

			char lineabuf[101];

			if ( fgets(lineabuf,100,cli_stream) != NULL ) {
				int len=strlen(lineabuf);
				if (lineabuf[len-1]=='\n') {
					lineabuf[len-1]='\0';
				}

				if (strcmp(lineabuf, "REGISTRAR") == 0) {
					printf("Recibida petición de registro.\n");

					int a, b, c;

					srand(time(NULL));
					a = (int)rand() % 10;
					b = (int)rand() % 10;

					c = a + b;

					printf("Estableciendo prueba %d + %d. ", a, b);
					fprintf(cli_stream,"RESUELVE %d %d\n", a, b);

					if ( fgets(lineabuf,100,cli_stream) != NULL ) {
						int len=strlen(lineabuf);
						if (lineabuf[len-1]=='\n') {
							lineabuf[len-1]='\0';
						}

						if (atoi(&lineabuf[10]) == c) {

							printf("Recibido %d, prueba superada.\n", c);
							f = fopen("servidor.txt", "r+");
                                                        generarID(id);
							printf("Asignando id %s.\n", id);
							fprintf(f, "%s %d %s\n", id, c, "Invitado");
							fclose(f);
                                                        fprintf(cli_stream,"REGISTRADO OK %s\n", id);

							while (opciones(id));
							break;
						}
						else {
							printf("REGISTRADO ERROR\n");
							fprintf(cli_stream,"REGISTRADO ERROR\n");
							break;
						}

					}
					else {
						printf("Nada que leer\n");

						break;
					}
				}
				lineabuf[5] = '\0';
				if (strcmp(lineabuf, "LOGIN") == 0) {
					lineabuf[14]='\0';

					bool b = false;
					char linea[50];

					strcpy(id, &lineabuf[6]);
					f = fopen("servidor.txt", "r");
					while (( fgets(linea,50,f) != NULL ) && !b) {
						linea[8]='\0';

						b = (strcmp(linea, id) == 0);
					}
					fclose(f);

					int i = 9;
					while(linea[i] != ' ') {
						i++;
					}
					linea[i]='\0';

					if (b && (strcmp(&linea[9], &lineabuf[15]) == 0)) {
						fprintf(cli_stream,"LOGIN OK\n");
						printf("Usuario con id %s aceptado\n", linea);

						while(opciones(id));
						break;
					}
					else {
						printf("LOGIN ERROR\n");
						fprintf(cli_stream,"LOGIN ERROR\n");
						break;
					}
				}
				else {
					printf("ERROR\n");
					fprintf(cli_stream,"ERROR\n");
					break;
				}
			}
			else {
				printf("Nada que leer\n");

				break;
			}
		}
		fclose(cli_stream);
		printf("Conexión con %s cerrada\n", id);
    }
}


void generarID (char* id) {

    srand(time(NULL));

    for (int i = 0; i < 8; i ++) {
        if (i == 0 || i == 1 || i == 3 || i == 7) {
            id[i] = (char)((rand() % 25) + 97);
        }
        else {
            id[i] = (char)((rand() % 10) + '0');
        }
    }
    id[8] = '\0';
}

bool setName(char* id, char* name) {
	if (!esValidoNombre(name)) {
		return false;
	}
	FILE *f, *temp;
	char lineabuf[101];

	f = fopen("servidor.txt", "r");
	temp = fopen("temp.txt", "w+");

	while(fgets(lineabuf,100,f) != NULL) {
		int len=strlen(lineabuf);
		if (lineabuf[len-1]=='\n') {
			lineabuf[len-1]='\0';
		}
		lineabuf[8] = '\0';

		if(strcmp(lineabuf, id) == 0) {
			lineabuf[8] = ' ';

			int i = 9;
			while(lineabuf[i] != ' ') {
				i += 1;
			}
			lineabuf[i] = '\0';

			fprintf(temp, "%s %s\n", lineabuf, name);
		}
		else {
			lineabuf[8] = ' ';
			fprintf(temp, "%s\n", lineabuf);
		}
	}

	rename("temp.txt", "servidor.txt");

	fclose(f);
	fclose(temp);

	return true;
}

void getName(char* id, char* name) {
	char lineabuf[100];

	FILE *f = fopen("servidor.txt", "r");

	while(fgets(lineabuf,100,f) != NULL) {
		int len=strlen(lineabuf);
		if (lineabuf[len-1]=='\n') {
			lineabuf[len-1]='\0';
		}
		lineabuf[8] = '\0';

		if (strcmp(lineabuf, id) == 0) {
			int i = 9;
			while(lineabuf[i] != ' ') {
				i++;
			}
			i++;

			strcpy(name, &lineabuf[i]);
		}
	}
	fclose(f);

	printf("Enviando nombre del Usuario: %s (%s) ...\n", id, name);
}

bool esValidoNombre(char* name) {
	if (strlen(name) > 16) {
		return false;
	}
	for (int i = 0; i < strlen(name); i++) {
		if (name[i] < '0' || (name[i] > '9' && name[i] < 'A') || (name[i] > 'Z' && name[i] < 'a') || name[i] > 'z') {
			return false;
		}
	}

	return true;
}

bool opciones (char* id) {
	char lineabuf[100];

	if(fgets(lineabuf,100,cli_stream) != NULL){
	  int len=strlen(lineabuf);
	  if (lineabuf[len-1]=='\n') {
		  lineabuf[len-1]='\0';
	  }
	  lineabuf[7]='\0';

	  if (strcmp(lineabuf, "SETNAME") == 0) {
		  if (setName(id, &lineabuf[8])) {
			  printf("Usuario con id %s ha cambiado su nombre a %s\n", id, &lineabuf[8]);
			  fprintf(cli_stream, "%s\n", "SETNAME OK");
		  }
		  else {
			  printf("Usuario con id %s ha introducido un nombre no valido\n", id);
			  fprintf(cli_stream, "%s\n", "SETNAME ERROR");
		  }
	  }
	  else if (strcmp(lineabuf, "GETNAME") == 0) {
		  char name[25];

		  getName(id, name);
		  fprintf(cli_stream, "GETNAME %s\n", name);
	  }
	  else {
		  printf("ERROR\n");
		  fprintf(cli_stream,"ERROR\n");
		  return false;
	  }
	  return true;
	}
	return false;
}

