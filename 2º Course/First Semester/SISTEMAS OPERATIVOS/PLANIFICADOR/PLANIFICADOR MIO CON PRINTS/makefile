all: fragmenta procsched 

#compilar la libreria fragmenta

fragmenta: fragmenta.c fragmenta.h
	gcc -c fragmenta.c

#crear el planificador
procsched: fragmenta.o
	gcc -o procsched fragmenta.o procsched.c 
	
clean:
	rm -f *.o procsched aka
