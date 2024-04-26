todos: cliente servidor

cliente: cliente.c
	gcc cliente.c -o cliente -Wall

servidor: funcpoll.o servfunc.o servidor.c
	gcc funcpoll.o servfunc.o servidor.c -o servidor -Wall

servfunc.o:
	gcc -c servfunc.c -Wall
	
funcpoll.o:
	gcc -c funcpoll.c -Wall
	
clean:
	rm *.txt *.o cliente servidor

