#include "arbolBin.h"
#include <stdio.h>

tipoArbolBin construirrecursivo(int a[],int b[],int x);
int main(){
		int el;
		tipoArbolBin a;
		printf("Introduce el numero de elemenots: \n");
		scanf("%d",&el);
		int pre[el],in[el];
		
		printf("Introduce el preorden : ");
		for(int i=0;i<el;i++){
			scanf("%d",&pre[i]);
		}
		printf("Introduce el inorden: ");
		for(int i=0;i<el;i++){
			scanf("%d",&in[i]);
		}
		a = construirrecursivo(pre,in,el);
		printf("El arbol en postorden es: ");
		postorden(a);
		printf("\n");
	
}
tipoArbolBin construirrecursivo(int a[],int b[],int x){
	if(x==1)
		construir(x,NULL,NULL);
	else{
		
		}
	tipoArbolBin ar;
	int i= 0;
	int c[x],d[x],e[x],f[x];
	while(b[i]!=a[0]){
		c[i]=b[i];
		i++;
	}
	for(int j=1;j<=i;j++)
		e[j]=a[j];
	i++;
	while(i<x){
		d[i]=b[i];
		i++;
	}
	ar = construirrecursivo(c,d,x/2);
	ar = construirrecursivo(e,f,x/2);
	
	return ar;
}
