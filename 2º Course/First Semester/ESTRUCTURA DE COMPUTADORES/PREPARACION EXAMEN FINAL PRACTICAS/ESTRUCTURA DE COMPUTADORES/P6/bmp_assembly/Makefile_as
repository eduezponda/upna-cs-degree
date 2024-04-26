CC=gcc
CFLAGS=-Wall -g -m32
ASM=as
AFLAGS=-gstabs --32

bmp_imagen: bmp_as.c pixels.o
	$(CC) $(CFLAGS) -o bmp_imagen bmp_as.c pixels.o

pixels.o: pixels.s 
	$(ASM) $(AFLAGS) -o pixels.o pixels.s

clean:
	$(RM) test_as.bmp pixels.o bmp_imagen
