# Variables
BISON = bison
FLEX = flex
GCC = gcc
CCFLAGS = -lfl
SRC = parser.y scanner.l lista_nombres.c tabla_simbolos.c cuadruplas.c lista_etiquetas.c lista_valores.c
OBJ = parser.tab.c lex.yy.c lista_nombres.o tabla_simbolos.o cuadruplas.o lista_etiquetas.o lista_valores.o
EXEC = compilador
INPUT = archivo.alg
OUTPUT = output.txt

# Regla principal
all: $(EXEC)

# Regla para generar el compilador
$(EXEC): $(OBJ)
	$(GCC) -o $(EXEC) $(OBJ) $(CCFLAGS)

# Regla para procesar Bison
parser.tab.c parser.tab.h: parser.y
	$(BISON) -d -v parser.y

# Regla para procesar Flex
lex.yy.c: scanner.l
	$(FLEX) scanner.l

# Regla para compilar los archivos C
%.o: %.c
	$(GCC) -c $<

# Regla para ejecutar el compilador
run: $(EXEC)
	./$(EXEC) < $(INPUT)

# Regla para ejecutar el compilador y guardar
run_save: $(EXEC)
	./$(EXEC) < $(INPUT) > $(OUTPUT)

# Limpiar los archivos generados
clean:
	rm -f $(EXEC) $(OBJ) parser.tab.c parser.tab.h lex.yy.c
