#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <time.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdbool.h>

#define BUFFER_SIZE 100

bool pregunta();
void comprobarUPTIME(char*);


FILE *ser_stream;


int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("di IP y puerto\n");
        exit(-1);
    }

    char *direccion_ip_en_txt = argv[1];
    int puerto = atoi(argv[2]);

    int dirip;
    if (inet_pton(AF_INET,direccion_ip_en_txt,&dirip) != 1 ) {
        printf("Eso no es una direccion IP\n");
        exit(-1);
    }

    int s = socket(AF_INET,SOCK_STREAM,0);


    struct sockaddr_in remoto;
    remoto.sin_family = AF_INET;
    remoto.sin_addr.s_addr = dirip;
    remoto.sin_port = htons( puerto );


    int r = connect(s,(struct sockaddr *)&remoto,sizeof(remoto));
    if ( r != 0 ) {
        printf("La conexión no se ha establecido\n");
        exit(-1);
    }

    FILE *f;
    char lineabuf[BUFFER_SIZE];
    ser_stream = fdopen(s,"r+");
    setbuf(ser_stream,NULL);

    if ((f = fopen("cliente.txt", "r")) == NULL) {
       

        printf("No existe id almacenado, realizando petición de registro\n");

        fprintf(ser_stream, "REGISTRAR\n");

        fgets(lineabuf, BUFFER_SIZE, ser_stream);
        comprobarUPTIME(lineabuf);
        int len=strlen(lineabuf);
        if (lineabuf[len-1]=='\n') {
            lineabuf[len-1]='\0';
        }

        int num1, num2;

        if (!sscanf(lineabuf, "RESUELVE %d %d", &num1, &num2)) {
            printf("%s\n", lineabuf);
            close(s);

            return 0;
        }

        int sum;
        printf("Resuelve %d + %d = ", num1, num2);
        scanf("%d", &sum);

        fprintf(ser_stream, "RESPUESTA %d\n", sum);

        fgets(lineabuf, BUFFER_SIZE, ser_stream);
        comprobarUPTIME(lineabuf);
        len=strlen(lineabuf);
        if (lineabuf[len-1]=='\n') {
            lineabuf[len-1]='\0';
        }
        lineabuf[13] = '\0';

        if (strcmp(lineabuf, "REGISTRADO OK") == 0) {

            f = fopen("cliente.txt", "w");

            fprintf(f, "%s %d\n", &lineabuf[14], sum);

            fclose(f);

            printf("Sesión establecida con id %s\n", &lineabuf[14]);
            printf("Conexión abierta\n");

            while(pregunta());
        }
        else {
            lineabuf[13] = 'R';
            printf("%s\n", lineabuf);
            close(s);

            return 0;
        }
    }
    else {
        char id[9];

        fgets(lineabuf, BUFFER_SIZE, f);
        fprintf(ser_stream, "LOGIN %s", lineabuf);

        lineabuf[8] = '\0';
        strcpy(id, lineabuf);

        printf("Hay datos para el usuario %s, probamos autentificación\n", id);

        fclose(f);

        fgets(lineabuf, BUFFER_SIZE, ser_stream);
        comprobarUPTIME(lineabuf);
        int len=strlen(lineabuf);
        if (lineabuf[len-1]=='\n') {
            lineabuf[len-1]='\0';
        }

        if (strcmp(lineabuf, "LOGIN OK") == 0) {
            printf("Sesión establecida con id %s\n", id);

            while(pregunta());
        }
        else {
            printf("%s\n", lineabuf);
            close(s);

            return 0;
        }
    }

    close(s);

}

bool pregunta() {
    char linea[BUFFER_SIZE];
    int eleccion;

    printf("¿Qué quieres hacer?\n");
    printf("1. Cambiar nombre\n");
    printf("2. Ver tu nombre\n");
    printf("3. Pedir el nombre de los clientes conectados\n");
    printf("4. Pedir tiempo en conexion\n");
    printf("5. Salir\n");
    printf("Opción: ");
    scanf("%d", &eleccion);
    fgetc(stdin);

    if (eleccion == 1) {
        char nuevo_nombre[50];

        printf("Elige nombre: ");
        scanf(" %s", nuevo_nombre);

        fprintf(ser_stream, "SETNAME %s\n", nuevo_nombre);

        fgets(linea, BUFFER_SIZE, ser_stream);
        comprobarUPTIME(linea);
        int len=strlen(linea);
        if (linea[len-1]=='\n') {
            linea[len-1]='\0';
        }

        if (strcmp(linea, "SETNAME OK") == 0) {
            printf("Nombre cambiado con éxito\n\n");
        }
        else {
            printf("Nombre no válido\n\n");
        }
    }
    else if (eleccion == 2) {
        fprintf(ser_stream, "GETNAME\n");

        fgets(linea, BUFFER_SIZE, ser_stream);
        comprobarUPTIME(linea);
        int len=strlen(linea);
        if (linea[len-1]=='\n') {
            linea[len-1]='\0';
        }
        linea[7] = '\0';

        printf("Tu nombre de usuario es: %s\n\n", &linea[8]);
    }
    else if (eleccion == 3) {
        fprintf(ser_stream, "LISTA\n");

        fgets(linea, BUFFER_SIZE, ser_stream);
        comprobarUPTIME(linea);
        int len=strlen(linea);
        if (linea[len-1]=='\n') {
            linea[len-1]='\0';
        }

        while(strcmp(linea, "E") != 0) {

            printf("%s\n", linea);

            if (fgets(linea, BUFFER_SIZE, ser_stream) == NULL) {
                printf("Conexion perdida\n");
                return false;
            }
            comprobarUPTIME(linea);
            len=strlen(linea);
            if (linea[len-1]=='\n') {
                linea[len-1]='\0';
            }
        }
        printf("%s\n\n", linea);
	}
	else if (eleccion == 4) {
        fprintf(ser_stream, "UPTIME\n");

        fgets(linea, BUFFER_SIZE, ser_stream);
        time_t ser, cli;
        sscanf(linea, "UPTIME %ld %ld", &ser, &cli);

        printf("\nEl tiempo de vida del servidor es de %ld\n", ser);
        printf("El tiempo de conexión es de %ld\n\n", cli);
	}
    else if (eleccion == 5) {
        return false;
    }
    else {
        printf("\nError en la entrada\n\n");
    }
    return true;
}

void comprobarUPTIME(char* l) {
    time_t a, b;
    while (sscanf(l, "UPTIME %ld %ld", &a, &b)){
        printf("\nEl tiempo de vida del servidor es de %ld\n", a);
        printf("El tiempo de conexión es de %ld\n\n", b);
        fgets(l,BUFFER_SIZE,ser_stream);
    }
}

