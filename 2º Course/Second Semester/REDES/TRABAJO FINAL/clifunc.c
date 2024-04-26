#include "clifunc.h"


bool partida(FILE* ser_stream, tablero tab, int filas, int columnas, bool imprimir) {

	char lineabuf[BUFFER_SIZE];

    if (fgets(lineabuf, BUFFER_SIZE, ser_stream) != NULL) {
		int len=strlen(lineabuf);
		if (lineabuf[len-1]=='\n') {
			lineabuf[len-1]='\0';
		}

		int movContrario;

		if (strcmp(lineabuf, "URTURN") == 0) {

			return realizarTurno(tab, filas, columnas, ser_stream, imprimir);
		}
		else if (sscanf(lineabuf, "URTURN %d", &movContrario)) {

			if (imprimir)
				meterFicha(tab, filas, columnas, movContrario, 'O');
			return realizarTurno(tab, filas, columnas, ser_stream, imprimir);
		}
		else if (strcmp(lineabuf, "VICTORY") == 0 || strcmp(lineabuf, "DEFEAT") == 0 || strcmp(lineabuf, "TIE") == 0) {
			printf("%s\n", lineabuf);
		}
	}
	return false;
}

bool realizarTurno(tablero tab, int filas, int columnas, FILE* ser_stream, bool imprimir) {

	int eleccion;
	char lineabuf[BUFFER_SIZE];

	if (imprimir)
		dibujarTablero(tab, filas, columnas);

	printf("Columna en la que colocar ficha: ");
	scanf("%d", &eleccion);
	fgetc(stdin);

	fprintf(ser_stream, "COLUMN %d\n", eleccion);

	if (fgets(lineabuf, BUFFER_SIZE, ser_stream) != NULL) {

		while (sscanf(lineabuf, "COLUMN ERROR\n")) {

			printf("Error en la columna\n");
			printf("Columna en la que colocar ficha: ");
			scanf("%d", &eleccion);
			fgetc(stdin);

			fprintf(ser_stream, "COLUMN %d\n", eleccion);
			fgets(lineabuf, BUFFER_SIZE, ser_stream);
		}
		if (strcmp(lineabuf, "ERROR\n") == 0)
			return false;

		if(imprimir)
			meterFicha(tab, filas, columnas, eleccion, 'X');
		return true;
	}
	return false;
}

void iniciarPartida(FILE* ser_stream, bool imprimir) {
	char lineabuf[BUFFER_SIZE];

	if (fgets(lineabuf, BUFFER_SIZE, ser_stream) != NULL) {
		int len = strlen(lineabuf);
		if (lineabuf[len-1]=='\n') {
			lineabuf[len-1]='\0';
		}

		char nomContrario[50];
		int numFilas, numColumnas;

		printf("%s\n", lineabuf);

		if (sscanf(lineabuf, "START %s %d %d", nomContrario, &numFilas, &numColumnas)) {

			tablero tab;

			inicializarTablero(&tab, numFilas, numColumnas);
			printf("Vas a jugar contra %s\n", nomContrario);

			while(partida(ser_stream, tab, numFilas, numColumnas, imprimir));
		}
	}
}

void comprobarSetName(int argc, char** argv, FILE* ser_stream, bool primera) {
	if (argc == 4) {
		char nombre[BUFFER_SIZE];
		strcpy(nombre, argv[3]);

		while (!setname(nombre, ser_stream)) {

			printf("Introduce un nuevo nombre");
			scanf(" %s", nombre);
		}
		return;
	}
	if (primera) {
		printf("Debes indicar el nombre con el que identificarte\n");
		printf("Introduce el nombre\n");

		char nombre[BUFFER_SIZE];
		scanf(" %s", nombre);

		while (!setname(nombre, ser_stream)) {

			printf("Introduce un nuevo nombre");
			scanf(" %s", nombre);
		}
	}
	else
		fprintf(ser_stream, "NOSETNAME\n");  //Ampliación del protocolo para detectar cuando no se quiere hacer SetName
}

bool setname (char* nuevo_nombre, FILE* ser_stream) {
	char linea[BUFFER_SIZE];

	fprintf(ser_stream, "SETNAME %s\n", nuevo_nombre);

	fgets(linea, BUFFER_SIZE, ser_stream);
	int len=strlen(linea);
	if (linea[len-1]=='\n') {
		linea[len-1]='\0';
	}

	if (strcmp(linea, "SETNAME OK") == 0) {
		printf("Nombre cambiado con éxito\n");
	}
	else {
		printf("Nombre no válido\n");
		return false;
	}
	return true;
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

bool meterFicha(tablero tab, int fil, int col, int mov, char ficha) {
	if (mov >= 1 && mov <= col && tab[mov-1].altura < fil) {
		tab[mov-1].huecos[tab[mov-1].altura] = ficha;
		tab[mov-1].altura ++;

		return true;
	}

	return false;
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
