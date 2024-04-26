#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include "fragmenta.h"

void main(){
    char cadena[100], **arg;
    int i;
    
    while(1){
        scanf("%[^\n]", cadena);
        getchar();
        printf("%s\n", cadena);
        arg = fragmenta(cadena);
        printf("AQUI\n");
        i = 0;
        
        while (arg[i]!=NULL) {
            printf("%s\n", arg[i]);
            i++;
        }
        borrarg(arg);
    }
}