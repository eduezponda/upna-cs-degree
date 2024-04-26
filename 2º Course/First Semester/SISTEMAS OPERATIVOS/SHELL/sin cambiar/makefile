all: fragmentaprueba minishell

#compilar la prueba de la libreria fragmentaa
fragmentaprueba: fragmenta.o prueba.o
	gcc -o pruebafragmenta fragmenta.o prueba.o

fragmenta.o: fragmenta.c fragmenta.h
	gcc -c fragmenta.c

prueba.o: prueba.c fragmenta.h
	gcc -c prueba.c

#crear la minishell
minishell: minishell.o fragmenta.o
	gcc -o minishell minishell.o fragmenta.o

minishell.o: minishell.c
	gcc -c minishell.c

clean:
	rm -f *.o fragmenta minishell aka
