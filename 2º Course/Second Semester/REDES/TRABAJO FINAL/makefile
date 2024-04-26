all: objetivo3

objetivo1: cliente1 servidor1

objetivo2: cliente2 servidor2

objetivo3: cliente3 servidor3

cliente1: clifunc.o cliente1.c
	gcc clifunc.o cliente1.c -o cliente -Wall

cliente2: clifunc.o cliente1.c
	gcc clifunc.o cliente1.c -o cliente -Wall

cliente3: clifunc.o cliente3.c
	gcc clifunc.o cliente3.c -o cliente -Wall

clifunc.o:
	gcc -c clifunc.c -Wall

servidor1: funcpoll.o servfunc.o servidor1.c
	gcc funcpoll.o servfunc.o servidor1.c -o servidor -Wall

servidor2: funcpoll.o servfunc.o servidor2.c
	gcc funcpoll.o servfunc.o servidor2.c -o servidor -Wall

servidor3: funcpoll.o servfunc.o servidor3.c
	gcc funcpoll.o servfunc.o servidor3.c -o servidor -Wall

servfunc.o:
	gcc -c servfunc.c -Wall

funcpoll.o:
	gcc -c funcpoll.c -Wall

clean:
	rm *.o *.txt cliente servidor

