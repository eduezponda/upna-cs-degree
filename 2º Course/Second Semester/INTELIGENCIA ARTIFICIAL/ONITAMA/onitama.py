import sys
import time
import copy

TIEMPO = 1
cartas = []
cartas_ = []
tiem = 0

class nodo:
    def __init__(self, estado, jugador, cartas2, cartaMedio):
        self.estado = estado
        self.jugador = jugador
        self.cartas = cartas2
        self.cartaMedio = cartaMedio

    def eval (self):
        contadorJ = contadorE = 0
        I = J = a = b = 0
        mJ = mE = 0
        posE = posJ = 0
        xJug = xEnem = 0
        c = 0
        xFichasJ = []
        xFichasE = []
        if self.jugador == 0:
            peonJugador = 'w'
            peonEnemigo = 'b'
            maestroJugador = 'W'
            maestroEnemigo = 'B'
            xJug = 0
            xEnem = 4
        else:
            peonJugador = 'b'
            peonEnemigo = 'w'
            maestroJugador = 'B'
            maestroEnemigo = 'W'
            xJug = 4
            xEnem = 0
        for i in range (5):
            for j in range(5):
                if self.estado[i][j] == peonJugador:
                    contadorJ = contadorJ + 1
                    xFichasJ.append([i,j])
                elif self.estado[i][j] == maestroJugador:
                    a = i
                    b = j
                    mJ = 1
                    posJ = i == xJug and j == 2
                elif self.estado[i][j] == maestroEnemigo:
                    I = i
                    J = j
                    mE = 1
                    posE = i == xEnem and j == 2
                elif self.estado[i][j] == peonEnemigo:
                    contadorE = contadorE + 1
                    xFichasE.append([i, j])

        for k in range (len(xFichasE)):
            c += 0.1*(abs(a-xFichasE[k][0]) + abs(b-xFichasE[k][1])) + 0.01*(abs(I-xFichasE[k][0]) + abs(J-xFichasE[k][1]))
        for k in range (len(xFichasJ)):
            c -= 0.1*(abs(I-xFichasJ[k][0]) + abs(J-xFichasJ[k][1])) + 0.01*(abs(a-xFichasJ[k][0]) + abs(b-xFichasJ[k][1]))
        if (len(xFichasE) < 3):
            c += 0.4*(abs(I-xJug) + abs(J-2) - abs(a-xJug) - abs(b-2))
        else:
            c += 0.2*(abs(I-xJug) + abs(J-2) - abs(a-xJug) - abs(b-2))
        return 100*(mJ-mE) + 10*(contadorJ - contadorE) + 100*(posJ - posE) + c


    def esfinal(self):
        if(self.estado[0][2] == 'W' or self.estado[4][2] == 'B'):
            return True
        estaB = False
        estaW = False
        for linea in self.estado:
            for elem in linea:
                if elem == 'B':
                    estaB = True
                elif elem == 'W':
                    estaW = True
        return not estaB or not estaW
    def posiblesMovimientos(self, jug):
        if self.jugador == 0:
            if jug:
                lista = ['w','W']
            else:
                lista = ['b','B']
        else:
            if jug:
                lista = ['b','B']
            else:
                lista = ['w','W']

        posPiezas = [[i, j] for i in range(5) for j in range(5) if self.estado[i][j] in (lista[0], lista[1])]
        piezas = [self.estado[k[0]][k[1]] for k in (posPiezas)]

        posMov=[]

        if self.cartas[0] < 5:
            posMov = [movimiento(posPiezas[j],
             [posPiezas[j][0]+cartas[carta][1+(i*2)],
              posPiezas[j][1]+cartas[carta][2+(i*2)]],
               carta, self.cartaMedio, piezas[j])
                for j in range (len(posPiezas))
                 for carta in (self.cartas) for i in range (4)
                 if (cartas[carta][1+(i*2)] != 0 or cartas[carta][2+(i*2)] != 0)
                 and -1 < posPiezas[j][0]+cartas[carta][1+(i*2)] < 5
                 and -1 < posPiezas[j][1]+cartas[carta][2+(i*2)] < 5
                 and self.estado [posPiezas[j][0]+cartas[carta][1+(i*2)]] [posPiezas[j][1]+cartas[carta][2+(i*2)]]
                 not in (lista[0], lista[1])]

        else:
            posMov = [movimiento(posPiezas[j],
             [posPiezas[j][0]+cartas_[carta-5][1+(i*2)],
              posPiezas[j][1]+cartas_[carta-5][2+(i*2)]],
               carta-5, self.cartaMedio, piezas[j])
                for j in range (len(posPiezas))
                 for carta in (self.cartas) for i in range (4)
                 if (cartas_[carta-5][1+(i*2)] != 0 or cartas_[carta-5][2+(i*2)] != 0)
                 and -1 < posPiezas[j][0]+cartas_[carta-5][1+(i*2)] < 5
                 and -1 < posPiezas[j][1]+cartas_[carta-5][2+(i*2)] < 5
                 and self.estado [posPiezas[j][0]+cartas_[carta-5][1+(i*2)]] [posPiezas[j][1]+cartas_[carta-5][2+(i*2)]]
                 not in (lista[0], lista[1])]
        return posMov


class movimiento:
    def __init__(self, origen, destino, cartaUsada, cartaMedio, pieza):
        self.origen = origen
        self.destino = destino
        self.cartaUsada = cartaUsada
        self.cartaMedio = cartaMedio
        self.pieza = pieza
    def imprimir_mov(self):
        posA = posB = 'a'
        posA = chr(self.origen[1] + 65)
        posB = chr(self.destino[1] + 65)
        sol = str(cartas[self.cartaUsada][0]) + " " + str(posA) + str(4-self.origen[0]+1) + str(posB) + str(4-self.destino[0]+1)

        print(sol)

def aplica (mov, nodo):
    nuevoNodo = copy.deepcopy(nodo)
    nuevoNodo.estado[mov.origen[0]] = nuevoNodo.estado[mov.origen[0]][:mov.origen[1]] + '.' + nuevoNodo.estado[mov.origen[0]][mov.origen[1]+1:]
    nuevoNodo.estado[mov.destino[0]] = nuevoNodo.estado[mov.destino[0]][:mov.destino[1]] + mov.pieza + nuevoNodo.estado[mov.destino[0]][mov.destino[1]+1:]

    nuevoNodo.cartaMedio = mov.cartaUsada
    if nodo.cartas[0] < 5:
        nuevoNodo.cartas = [k+5 for k in range(5) if k not in (mov.cartaMedio, nodo.cartas[0], nodo.cartas[1])]
    else:
        nuevoNodo.cartas = [k for k in range(5) if k not in (mov.cartaMedio, nodo.cartas[0]-5, nodo.cartas[1]-5)]
    return nuevoNodo
def alfa_beta (nodo, profundidad, alfa, beta, jugadorMAX, n):
    if profundidad == 0 or time.time() - tiem > TIEMPO or nodo.esfinal():
        return (nodo.eval(), None, n+1)
    else:
        Mejormov = movimiento([0,0], [0,0], 0, 0, 'a')
        if jugadorMAX:
            value = -sys.maxsize
            posible_movimiento = nodo.posiblesMovimientos(jugadorMAX)
            for movi in (posible_movimiento):
                nuevonodo = aplica(movi, nodo)
                (valnuevonodo, SigMov, n) = alfa_beta(nuevonodo, profundidad-1, alfa, beta, False, n+1)
                if (valnuevonodo > value):
                    value = valnuevonodo
                    Mejormov = movi
                    alfa = value
                if (alfa >= beta):
                    break
            return (value, Mejormov, n)
        else:
            value = sys.maxsize
            posible_movimiento = nodo.posiblesMovimientos(jugadorMAX)
            for movi in (posible_movimiento):
                nuevonodo = aplica(movi, nodo)
                (valnuevonodo, SigMov, n) = alfa_beta(nuevonodo, profundidad-1, alfa, beta, True, n+1)
                if (valnuevonodo < value):
                    value = valnuevonodo
                    Mejormov = movi
                    beta = value
                if (alfa >= beta):
                    break
            return (value, Mejormov, n)

player_id = int(input())
if player_id == 0:
    l = [0, 1]
else:
    l = [2, 3]
prof = 6
while True:
    tablero = []
    cartas = []
    cartas_ = []
    indices = {}
    for i in range(5):
        board = input()
        tablero.append(board)
    for i in range(5):
        owner, card_id, dx_1, dy_1, dx_2, dy_2, dx_3, dy_3, dx_4, dy_4 = [int(j) for j in input().split()]
        if owner == -1:
            carta = [card_id, -dy_1, dx_1, -dy_2, dx_2, -dy_3, dx_3, -dy_4, dx_4]
        elif owner == player_id:
            carta = [card_id, -dy_1, dx_1, -dy_2, dx_2, -dy_3, dx_3, -dy_4, dx_4]
        else:
            carta = [card_id, dy_1, -dx_1, dy_2, -dx_2, dy_3, -dx_3, dy_4, -dx_4]
        cartas.append(carta)

    for k in range (len(cartas)):
        c = [-cartas[k][j] for j in range(len(cartas[k]))]
        c[0] = -c[0]
        cartas_.append(c)
        indices[cartas[k][0]] = k
    action_count = int(input())
    movimientos = []
    tiem = time.time()
    for i in range(action_count):
        inputs = input().split()
        card_id = int(inputs[0])
        move = inputs[1]
        a = b = 0
        a = ord(move[0]) - 65
        b = ord(move[2]) - 65
        mov = movimiento([4-(int(move[1])-1), a],[4-(int(move[3])-1), b], indices[card_id], 4, tablero[4-(int(move[1])-1)][a])
        movimientos.append(mov)
    nodo_ini = nodo(tablero, player_id, l, 4)
    alfa = -sys.maxsize
    beta = sys.maxsize

    value = -sys.maxsize
    nodos = 0
    movimiento_elegido = movimiento([0,0], [0,0], 0, 0, 'a')
    for m in (movimientos):
        nuevonodo = aplica(m, nodo_ini)
        (valnuevonodo, SigMov, nodos) = alfa_beta(nuevonodo, prof-1, alfa, beta, False, nodos+1)
        if (valnuevonodo > value):
            value = valnuevonodo
            movimiento_elegido = m
            alfa = value
    movimiento_elegido.imprimir_mov()
    print(nodos/(time.time()-tiem), file=sys.stderr, flush=True)
    TIEMPO = 0.06
    prof = 3
    # To debug: print("Debug messages...", file=sys.stderr, flush=True)
