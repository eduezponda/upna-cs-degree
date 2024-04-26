#include "servfunc.h"


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
	temp = fopen("temp.txt", "w");

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

bool registro(FILE* cli_stream, Cliente* cliente){
	char lineabuf[100];
	FILE* f;

	int a, b, c;

	srand(time(NULL));
	a = (int)rand() % 10;
	b = (int)rand() % 10;

	c = a + b;

	fprintf(cli_stream,"RESUELVE %d %d\n", a, b);

	if ( fgets(lineabuf,100,cli_stream) != NULL ) {
		int len=strlen(lineabuf);
		if (lineabuf[len-1]=='\n') {
			lineabuf[len-1]='\0';
		}

		if (atoi(&lineabuf[10]) == c) {

			f = fopen("servidor.txt", "a");
			generarID(cliente->id);
			fprintf(f, "%s %d %s\n", cliente->id, c, "Invitado");
			fclose(f);
			printf("Usuario con id %s aceptado\n", cliente->id);
			fprintf(cli_stream,"REGISTRADO OK %s\n", cliente->id);

			return true;
		}
		else {
			printf("REGISTRADO ERROR\n");
			fprintf(cli_stream,"REGISTRADO ERROR\n");
			return false;
		}

	}
	else {
		printf("Nada que leer\n");

		return false;
	}
}


bool login(int c, Cliente* cliente, FILE* cli_stream){
	FILE* f;
	
	bool b = false;
	char linea[50];

	f = fopen("servidor.txt", "r");
	while (( fgets(linea,50,f) != NULL ) && !b) {
		linea[8]='\0';

		b = (strcmp(linea, cliente->id) == 0);
	}
	fclose(f);
	
	int password;
	char nombre[25];
	
	sscanf(&linea[9], "%d %s", &password, nombre);

	if (b && (c == password)) {
		fprintf(cli_stream,"LOGIN OK\n");
		printf("Usuario %s autentificado\n", cliente->id);
		
		strcpy(cliente->nombre, nombre);
		
		return true;
	}
	else {
		printf("LOGIN ERROR\n");
		fprintf(cli_stream,"LOGIN ERROR\n");
		return false;
	}
}

void info_periodica(grupo_de_sockets sockets, time_t inicio, int max) {

	if (sockets.nclients > 0) {
		printf("Enviando informacion periodica a los usuarios conectados\n");
		for (int i = 0; i < max; i++) {
			if (!sockets.client_info[i].vacio) {
				fprintf(sockets.client_info[i].stream, "UPTIME %ld %ld\n", (time_t)(getTime() - inicio), (time_t)(getTime() - sockets.cli[i].inicio));
			}
		}
	}
}

