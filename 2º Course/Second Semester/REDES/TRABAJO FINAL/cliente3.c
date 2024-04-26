#include "clifunc.h"


void cerrar();


int s;


int main(int argc, char *argv[]) {
    if (argc < 3) {
        printf("di IP y puerto\n");
        exit(-1);
    }

    char *direccion_ip_en_txt = argv[1];
    int puerto = atoi(argv[2]);

    int dirip;
    if (inet_pton(AF_INET,direccion_ip_en_txt,&dirip) != 1 ) {
        printf("Eso no es una direccion IP\n");
        exit(EXIT_FAILURE);
    }

    s = socket(AF_INET,SOCK_STREAM,0);


    struct sockaddr_in remoto;
    remoto.sin_family = AF_INET;
    remoto.sin_addr.s_addr = dirip;
    remoto.sin_port = htons( puerto );


    int r = connect(s,(struct sockaddr *)&remoto,sizeof(remoto));
    if ( r != 0 ) {
        printf("La conexión no se ha establecido\n");
        exit(EXIT_FAILURE);
    }

    FILE *f;
    char lineabuf[BUFFER_SIZE];
    FILE* ser_stream = fdopen(s,"r+");
    setbuf(ser_stream,NULL);

    fgets(lineabuf, BUFFER_SIZE, ser_stream);

    if ((f = fopen("cliente.txt", "r")) == NULL) {

        printf("No existe id almacenado, realizando petición de registro\n");

        fprintf(ser_stream, "REGISTRAR\n");

        fgets(lineabuf, BUFFER_SIZE, ser_stream);
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

			comprobarSetName(argc, argv, ser_stream, true);
            iniciarPartida(ser_stream, true);
        }
        else {
            lineabuf[13] = 'R';
            printf("%s\n", lineabuf);
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
        int len=strlen(lineabuf);
        if (lineabuf[len-1]=='\n') {
            lineabuf[len-1]='\0';
        }

        if (strcmp(lineabuf, "LOGIN OK") == 0) {
            printf("Sesión establecida con id %s\n", id);

			comprobarSetName(argc, argv, ser_stream, false);
            iniciarPartida(ser_stream, true);
        }
        else {
            printf("%s\n", lineabuf);
        }
    }

    close(s);
    return EXIT_SUCCESS;
}


void cerrar() {
	close(s);

	exit(EXIT_SUCCESS);
}
