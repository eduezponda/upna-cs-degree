SELECT PR.NOMBRE || ' (' || P.NOMBRE || ')' AS PROVINCIA_PUEBLO
FROM PROVINCIAS PR
JOIN PUEBLOS P ON (P.CODPRO = PR.CODPRO)
WHERE P.CODPUE IN
(SELECT DISTINCT V1.CODPUE
FROM VENDEDORES V1
JOIN VENDEDORES V2 ON (V1.CODVEN = V2.JEFE)
WHERE V2.COMISION > 10
MINUS
SELECT DISTINCT C.CODPUE
FROM CLIENTES C
JOIN FACTURAS F ON (F.CODCLI = C.CODCLI)
WHERE F.DTO <= 4)
UNION
(SELECT DISTINCT C.CODPUE
FROM CLIENTES C
JOIN FACTURAS F ON (F.CODCLI = C.CODCLI)
WHERE EXTRACT (YEAR FROM F.FECHA) = '2020'
MINUS
SELECT DISTINCT C.CODPUE
FROM CLIENTES C
JOIN FACTURAS F ON (F.CODCLI = C.CODCLI)
WHERE EXTRACT (YEAR FROM F.FECHA) = '2020' AND F.IVA IS NOT NULL AND F.IVA != 16);

SELECT DISTINCT V1.CODPUE
FROM VENDEDORES V1
JOIN VENDEDORES V2 ON (V1.CODVEN = V2.JEFE)
WHERE V2.COMISION > 10
MINUS
SELECT DISTINCT C.CODPUE
FROM CLIENTES C
JOIN FACTURAS F ON (F.CODCLI = C.CODCLI)
WHERE F.DTO <= 4
UNION
(SELECT DISTINCT C.CODPUE
FROM CLIENTES C
JOIN FACTURAS F ON (F.CODCLI = C.CODCLI)
WHERE EXTRACT (YEAR FROM F.FECHA) = '2020'
MINUS
SELECT DISTINCT C.CODPUE
FROM CLIENTES C
JOIN FACTURAS F ON (F.CODCLI = C.CODCLI)
WHERE EXTRACT (YEAR FROM F.FECHA) = '2020' AND F.IVA IS NOT NULL AND F.IVA != 16);


Obtener un listado de todos los art�culos de la base de datos, mostrando para 
cada uno de ellos el c�digo del art�culo, su descripci�n, el n�mero de unidades 
que se hayan vendido (total_unidades_vendidas) y el n�mero de clientes que los
hayan comprado (total_clientes) contabilizando s�lo las ventas de las facturas
con descuento mayor que 0.
Ordenar por la descripci�n del art�culo ascendentemente.

CODIGO  ARTICULO  TOTAL_UNIDADES_VENDIDAS  TOTAL_CLIENTES
------  --------  -----------------------  --------------

Nota: En el caso de que no tengan unidades vendidas se deber� evitar mostrar NULL
y mostrar 0, usando por ejemplo la funci�n COALESCE(argumento que puede ser NULL,0)
y No deb�is tener en cuenta descuentos si estos no tienen ning�n valor.

SELECT DISTINCT A.CODART "CODIGO", A.DESCRIP "ARTICULO", COALESCE(SUM(L.CANT),0) "TOTAL_UNIDADES_VENDIDAS", COUNT(DISTINCT F.CODCLI) "TOTAL_VENDIDAS"
FROM ARTICULOS A
LEFT JOIN LINEAS_FAC L ON (A.CODART = L.CODART)
LEFT JOIN FACTURAS F ON (L.CODFAC = F.CODFAC AND F.DTO > 0)
GROUP BY A.CODART, A.DESCRIP;


a. Crear la tabla 'articulos_provisiones' como resultado de una consulta que
devuelva el c�digo, la descripci�n y el valor 0 como el atributo provision, de
los diferentes art�culos cuya diferencia entre el stock actual y el stock m�nimo
sea cero o menor y tenga alguna venta con cantidad de cero o m�s unidades. 

CREATE TABLE articulos_provisiones as (
SELECT DISTINCT A.CODART, A.DESCRIP, 0 AS PROVISION
FROM ARTICULOS A
JOIN LINEAS_FAC L ON (A.CODART = L.CODART)
WHERE A.STOCK - A.STOCK_MIN <= 0 AND L.CANT >= 0);

ALTER TABLE articulos_provisiones ADD provisiones number (1) default 0;

b. Actualizar la provisi�n de cada art�culo de la tabla 'articulos_provisiones'
reci�n creada, con la suma de las unidades vendidas de cada correspondiente
art�culo durante estos cinco a�os de facturaci�n divido entre el factor 4.1,
redondeando hacia arriba usando la funcion CEIL(expresi�n)
Nota: Alg�n art�culo podr�a tener una provisi�n negativa (por alguna devoluci�n).

UPDATE ARTICULOS_PROVISIONES SET PROVISIONES = (
SELECT SUM(L.CANT)
FROM ARTICULOS A
LEFT JOIN LINEAS_FAC L ON (A.CODART = L.CODART)
WHERE A.CODART IN (
SELECT ART.CODART 
FROM ARTICULOS_PROVISIONES ART)
GROUP BY A.CODART);

UPDATE ARTICULOS_PROVISIONES SET PROVISIONES = (
SELECT CEIL(SUM(L.CANT)/4.1)
FROM ARTICULOS A
LEFT JOIN LINEAS_FAC L ON (A.CODART = L.CODART)
LEFT JOIN FACTURAS F ON (L.CODFAC = F.CODFAC)
WHERE A.CODART IN (SELECT ART.CODART FROM ARTICULOS_PROVISIONES ART) AND EXTRACT (YEAR FROM F.FECHA) BETWEEN '2018' AND '2023'
GROUP BY A.CODART)
WHERE CODART IN ;

SELECT P.NOMBRE "PUEBLO", NVL(AVG(F.IVA),0) "IVA_MEDIO_COMPRAS"
FROM PUEBLOS P
LEFT JOIN CLIENTES C ON (C.CODPUE = P.CODPUE)
LEFT JOIN FACTURAS F ON (F.CODCLI = C.CODCLI)
GROUP BY P.NOMBRE,P.CODPUE
HAVING NVL(AVG(F.IVA),0) > (SELECT NVL(AVG(F.IVA),0)
FROM FACTURAS F)
ORDER BY PUEBLO DESC, IVA_MEDIO_COMPRAS DESC;



