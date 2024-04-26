
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int main () {
	
    int i, pid, padre;
    padre = 1;
    
    for (i=0; i < 5; i++)
    {
        pid = fork();
        
        if (pid == -1) {
            printf("Error en la creación del proceso\n");
            exit (-1);
        }
        if (pid == 0) { /* Proceso hijo */
            printf("Este es el proceso hijo %d, cuyo padre es %ld\n", i,(long)getppid());
            exit(999);
        } 
        else { /* Proceso padre */
            printf("Este es el proceso padre con ID %ld\n",(long)getpid());
        }
    }
    return (0);
}

// char buffer[20]
// scanf("%d",&var);
//fscanf(0,"%s",buffer);
