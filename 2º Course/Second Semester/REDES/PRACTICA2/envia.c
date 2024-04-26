#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>


int main(int argc, char *argv[]) {
  if (argc != 4) {
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

  if (write(s, argv[3], strlen(argv[3])) == -1) {
      printf("Error al enviar el mensaje\n");
      exit(-1);
  }
  write(s, "\n", 1);

  close(s);
}
