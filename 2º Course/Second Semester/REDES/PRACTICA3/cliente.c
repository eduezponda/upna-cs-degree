#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdbool.h>



bool pregunta();


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
    ser_stream = fdopen(s,"r+");
    setbuf(ser_stream,NULL);

    if ((f = fopen("cliente.txt", "r")) == NULL) {
        char lineabuf[100];

        printf("No existe id almacenado, realizando petición de registro\n");

        fprintf(ser_stream, "REGISTRAR\n");

        fgets(lineabuf,100,ser_stream);
        int len=strlen(lineabuf);
        if (lineabuf[len-1]=='\n') {
            lineabuf[len-1]='\0';
        }

        int i = 9;
        while(lineabuf[i] != ' ') {
            i++;
        }
        lineabuf[i]='\0';

        int sum;
        printf("Resuelve %d + %d = ", atoi(&lineabuf[9]), atoi(&lineabuf[i+1]));
        scanf("%d", &sum);

        fprintf(ser_stream, "RESPUESTA %d\n", sum);

        fgets(lineabuf,100,ser_stream);
        len=strlen(lineabuf);
        if (lineabuf[len-1]=='\n') {
            lineabuf[len-1]='\0';
        }
        lineabuf[13] = '\0';

        if (strcmp(lineabuf, "REGISTRADO OK") == 0) {

            f = fopen("cliente.txt", "w+");

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
        char lineabuf[100];
        char id[9];

        fgets(lineabuf,100,f);
        fprintf(ser_stream, "LOGIN %s", lineabuf);

        lineabuf[8] = '\0';
        strcpy(id, lineabuf);

        printf("Hay datos para el usuario %s, probamos autentificación\n", id);

        fclose(f);

        fgets(lineabuf,100,ser_stream);
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
    char linea[100];
    int eleccion;

    printf("¿Qué quieres hacer?\n");
    printf("1. Cambiar nombre\n");
    printf("2. Ver tu nombre\n");
    printf("3. Salir\n");
    printf("Opción: ");
    scanf("%d", &eleccion);
    fgetc(stdin);

    if (eleccion == 1) {
        char nuevo_nombre[50];

        printf("Elige nombre: ");
        scanf(" %s", nuevo_nombre);

        fprintf(ser_stream, "SETNAME %s\n", nuevo_nombre);

        fgets(linea,100,ser_stream);
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

        fgets(linea,100,ser_stream);
        int len=strlen(linea);
        if (linea[len-1]=='\n') {
            linea[len-1]='\0';
        }
        linea[7] = '\0';

        printf("Tu nombre de usuario es: %s\n\n", &linea[8]);
    }
    else if (eleccion == 3) {
        return false;
    }
    else {
        printf("\nError en la entrada\n\n");
    }
    return true;
}

