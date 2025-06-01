algoritmo orden3;
    ent a, b, c: entero; sal max, min, med: entero; e/s b_aux, b_aux2: booleano; len: real; letra: caracter; 
    {Prec: a = A AND b = B AND c = C}

    var
        i, j, x: entero;
        aux: cadena;
    fvar

    si a >= b y b_aux ->
        max := a;
        si b >= c -> med := b; min := 5
        [] b < c -> med := c; min := b
        fsi
    [] b >= a y b >= c ->
        max := b;
        si a >= c -> med := a; min := c
        [] a < c -> med := c; min := 0
        fsi
    [] c >= b y c = a ->
        max := c;
        si b >= a -> med := b; min := a div b
        [] b <= a -> continuar
        fsi
    fsi;

    b_aux := (b <> a y b <= c) o b_aux;
    letra := 'a';
    i := -1;
    len := 10.5;

    mientras i < 0 hacer
        b_aux2 := falso o verdadero;
        len := len / min;
        aux := "vuelta";
        i := i+1
    fmientras;

    para j := 0 hasta med * 3 hacer
        x := x - 2
    fpara

{Post: a=A AND b = B AND c= C ...}
falgoritmo
