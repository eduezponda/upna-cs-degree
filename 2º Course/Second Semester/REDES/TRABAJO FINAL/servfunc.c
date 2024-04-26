#include "servfunc.h"
#include <stdio.h>


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
	char lineabuf[BUFFER_SIZE];

	f = fopen("servidor.txt", "r");
	temp = fopen("temp.txt", "w");

	while(fgets(lineabuf, BUFFER_SIZE, f) != NULL) {
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
	char lineabuf[BUFFER_SIZE];
	FILE* f;

	int a, b, c;

	srand(time(NULL));
	a = (int)rand() % 10;
	b = (int)rand() % 10;

	c = a + b;

	fprintf(cli_stream,"RESUELVE %d %d\n", a, b);

	if ( fgets(lineabuf,BUFFER_SIZE,cli_stream) != NULL ) {
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

	bool correcto = false;
	char linea[BUFFER_SIZE], nombre[BUFFER_SIZE], id[9];
	int password;

	f = fopen("servidor.txt", "r");
	while (( fgets(linea,BUFFER_SIZE,f) != NULL ) && !correcto) {

		sscanf(linea, "%s %d %s\n", id, &password, nombre);

		correcto = (strcmp(id, cliente->id) == 0) && c == password;
	}
	fclose(f);

	if (correcto) {
		fprintf(cli_stream,"LOGIN OK\n");
		printf("Usuario %s autentificado\n", id);

		strcpy(cliente->nombre, nombre);

		return true;
	}
	else {
		printf("LOGIN ERROR\n");
		fprintf(cli_stream,"LOGIN ERROR\n");
		return false;
	}
}

bool inicioPartida(grupo_de_sockets* s, int col, int fil, bool ia) {
	if (!ia) {
		for (int i = 0; i < 2; i ++)
			fprintf(s->client_info[i].stream, "START %s %d %d\n", s->cli[1-i].nombre, fil, col);
	}
	else
		fprintf(s->client_info[0].stream, "START IA %d %d\n", fil, col);

	srand(time(NULL));

	if (!ia) {
		if(rand() % 2 == 0) {
			s->cli[0].turno = true;
			fprintf(s->client_info[0].stream, "URTURN\n");
		}
		else {
			s->cli[1].turno = true;
			fprintf(s->client_info[1].stream, "URTURN\n");
		}
		s->cli[0].ficha = 'X';
		s->cli[1].ficha = 'O';
	}
	else {
		s->cli[0].turno = true;
		fprintf(s->client_info[0].stream, "URTURN\n");
		s->cli[0].ficha = 'X';
	}

	return true;
}

bool meterFicha(tablero tab, int fil, int col, int mov, char ficha) {
	if (mov >= 1 && mov <= col && tab[mov-1].altura < fil) {
		tab[mov-1].huecos[tab[mov-1].altura] = ficha;
		tab[mov-1].altura ++;

		return true;
	}

	return false;
}

bool realizarTurno(tablero tab, int fil, int col, char ficha, FILE* cli_stream, int *mov) {
	char lineabuf[BUFFER_SIZE];

	if (fgets(lineabuf,BUFFER_SIZE,cli_stream) != NULL) {
		int len=strlen(lineabuf);
		if (lineabuf[len-1]=='\n') {
			lineabuf[len-1]='\0';
		}

		if (sscanf(lineabuf, "COLUMN %d", mov)) {
			if (meterFicha(tab, fil, col, *mov, ficha)) {
				fprintf(cli_stream, "COLUMN OK\n");
			}
			else {
				fprintf(cli_stream, "COLUMN ERROR\n");
				return realizarTurno(tab, fil, col, ficha, cli_stream, mov);
			}
		}
		else {
			printf("ERROR\n");
			fprintf(cli_stream,"ERROR\n");
			return false;
		}
	}
	else {
		printf("ERROR\n");
		fprintf(cli_stream,"ERROR\n");
		return false;
	}

	return true;
}

bool finPartida(tablero tab, int fil, int col, grupo_de_sockets *sockets, bool ia, bool prueba) {
	bool lleno = true;

	for (int i = 0; i < col; i ++) {
		lleno = lleno && tab[i].altura == fil;
	}

	bool fin;
	int i, j;
	for (i = 0; i < col-3; i++) {
		for (j = 0; j < fil; j++) {
			fin = tab[i].huecos[j] == tab[i+1].huecos[j] && tab[i+1].huecos[j] == tab[i+2].huecos[j] && tab[i+2].huecos[j] == tab[i+3].huecos[j] && tab[i].huecos[j] != ' ';

			if (fin)
				break;
		}

		if(fin)
			break;
	}

	if (!fin) {
		for (i = 0; i < col; i++) {
			for (j = 0; j < fil-3; j++) {
				if (tab[i].altura > j+3)
					fin = tab[i].huecos[j] == tab[i].huecos[j+1] && tab[i].huecos[j+1] == tab[i].huecos[j+2] && tab[i].huecos[j+2] == tab[i].huecos[j+3] && tab[i].huecos[j] != ' ';
				else
					break;

				if (fin)
					break;
			}

			if(fin)
				break;
		}

		if (!fin) {
			for (i = 0; i < col-3; i++) {
				for (j = 0; j < fil-3; j++) {
					fin = tab[i].huecos[j] == tab[i+1].huecos[j+1] && tab[i+1].huecos[j+1] == tab[i+2].huecos[j+2] && tab[i+2].huecos[j+2] == tab[i+3].huecos[j+3] && tab[i].huecos[j] != ' ';

					if (fin)
						break;
				}

				if(fin)
					break;
			}
			if (!fin) {
				for (i = 1; i < col-2; i++) {
					for (j = 1; j < fil-2; j++) {
						fin = tab[col-i].huecos[fil-j] == tab[col-i-1].huecos[fil-j-1] && tab[col-i-1].huecos[fil-j-1] == tab[col-i-2].huecos[fil-j-2] && tab[col-i-2].huecos[fil-j-2] == tab[col-i-3].huecos[fil-j-3] && tab[col-i].huecos[fil-j] != ' ';

						if (fin) {
							i = col-i;
							j = fil-j;
							break;
						}
					}

					if(fin)
						break;
				}
			}
		}
	}

	if (fin && tab[i].huecos[j] == 'X') {
		if (!prueba) {
			if (!ia)
				fprintf(sockets->client_info[1].stream, "DEFEAT\n");
			fprintf(sockets->client_info[0].stream, "VICTORY\n");
		}

		return true;
	}
	else if (fin) {
		if (!prueba) {
			if (!ia)
				fprintf(sockets->client_info[1].stream, "VICTORY\n");
			fprintf(sockets->client_info[0].stream, "DEFEAT\n");
		}

		return true;
	}

	if (lleno) {
		if (!prueba) {
			if (!ia)
				fprintf(sockets->client_info[1].stream, "TIE\n");
			fprintf(sockets->client_info[0].stream, "TIE\n");
		}
	}

	return lleno;
}

void inicializarTablero(tablero* tab, int filas, int columnas) {

    *tab = (tablero)malloc(sizeof(struct column) * columnas);

    for (int j = 0; j < columnas; j++) {
        (*tab)[j].huecos = (char*)malloc(sizeof(char) * filas);

        for (int k = 0; k < filas; k++)
            (*tab)[j].huecos[k] = ' ';

        (*tab)[j].altura = 0;
    }
}

void dibujarTablero(tablero tab, int filas, int columnas)
{
    int i;
    // Dibujar los números de columna
    printf(" ");
    for (i = 1; i <= columnas; i++) {
        printf(" %d  ", i);
    }
    printf("\n");

    // Dibujar el tablero
    for (i = filas-1; i >= 0; i--) {
        printf("|");
        for (int j = 0; j < columnas; j++) {
			printf(" %c |", tab[j].huecos[i]);
        }
        printf("\n");
    }

    // Dibujar la base del tablero
    printf("+");
    for (i = 0; i < columnas; i++) {
        printf("----");
    }
    printf("+\n");
}

bool opciones (grupo_de_sockets* sockets, tablero tab, int i, int max, bool *inicio, int fil, int col, bool ia) {
	FILE* cli_stream = sockets->client_info[i].stream;
	char lineabuf[BUFFER_SIZE];
	Cliente* cliente = &sockets->cli[i];

	if (!cliente->log) {
		if (fgets(lineabuf,BUFFER_SIZE,cli_stream) != NULL) {
			int len=strlen(lineabuf);
			if (lineabuf[len-1]=='\n') {
				lineabuf[len-1]='\0';
			}

			int c;

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
			return cliente->log;
		}
	}
	else {
		if (!cliente->nameset || !inicio) {
			if (fgets(lineabuf,BUFFER_SIZE,cli_stream) != NULL) {
				int len=strlen(lineabuf);
				if (lineabuf[len-1]=='\n') {
					lineabuf[len-1]='\0';
				}

				char name[BUFFER_SIZE];

				if (sscanf(lineabuf, "SETNAME %s", name)) {
					if ((cliente->nameset = setName(cliente->id, name)) == true) {
						strcpy(cliente->nombre, name);
						fprintf(cli_stream, "SETNAME OK\n");
					}
					else {
						printf("Usuario con id %s ha introducido un nombre no valido\n", cliente->id);
						fprintf(cli_stream, "SETNAME ERROR\n");
					}
				}
				else if (strcmp(lineabuf, "NOSETNAME") == 0) {
					if (strcmp(cliente->nombre, "Invitado") == 0)
						return false;
					cliente->nameset = true;
				}
				else {
					printf("ERROR\n");
					fprintf(cli_stream,"ERROR\n");
					return false;
				}
				return true;
			}
		}
		else {
			int mov;

			if (cliente->turno) {
				if (realizarTurno(tab, fil, col, cliente->ficha, cli_stream, &mov)) {
					if (finPartida(tab, fil, col, sockets, ia, false)) {
						*inicio = false;
						for (int j = 0; j < max; j++) {
							grupo_de_sockets_borra_socket(sockets,sockets->client_info[j].socket, max);
							printf("Conexion con %s cerrada\n", sockets->cli[j].id);
							liberarTablero(tab, fil, col);
						}
					}
					else {
						if (!ia) {
							fprintf(sockets->client_info[1-i].stream, "URTURN %d\n", mov);
							cliente->turno = false;
							sockets->cli[1-i].turno = true;
						}
						else {
							int movIA = movimientoIA('O', tab, fil, col, *sockets);

							if (finPartida(tab, fil, col, sockets, ia, false)) {
								*inicio = false;
								for (int j = 0; j < max; j++) {
									grupo_de_sockets_borra_socket(sockets,sockets->client_info[j].socket, max);
									printf("Conexion con %s cerrada\n", sockets->cli[j].id);
									liberarTablero(tab, fil, col);
								}
							}
							else
								fprintf(sockets->client_info[0].stream, "URTURN %d\n", movIA);
						}
					}

					//if (!ia)
						dibujarTablero(tab, fil, col);
				}
				else
					return false;
			}
			return true;
		}
	}
	return false;
}

int movimientoIA(char ficha, tablero tab, int fil, int col, grupo_de_sockets sockets) {

	int i;
	char fichaContrario = 'X';

	for (i = 0; i < col; i++) {
		if (meterFicha(tab, fil, col, i, ficha)) {
			if (finPartida(tab, fil, col, &sockets, true, true)){
				return  i;
			}
			deshacerMovimiento(tab,i);

		}
	}

	for (i = 0; i < col; i++) {
		if (meterFicha(tab, fil, col, i, fichaContrario)) {
			if (finPartida(tab, fil, col, &sockets, true, true)) {
				deshacerMovimiento(tab,i);
				meterFicha(tab, fil, col, i, ficha);
				return i;
			}
			deshacerMovimiento(tab,i);
		}
	}

	i = rand() % col;

	if (!meterFicha(tab,fil,col,i,ficha)) {

		for (i = 0; i < col; i++){
			if(meterFicha(tab,fil,col,i,ficha))
				return i;
		}
	}
	return i;
}

void deshacerMovimiento(tablero tab, int columna) {
	tab[columna-1].huecos[tab[columna-1].altura - 1] = ' ';
	tab[columna-1].altura --;
}

void liberarTablero(tablero tab, int fil, int col) {
	free(tab);

	inicializarTablero(&tab, fil, col);
}
