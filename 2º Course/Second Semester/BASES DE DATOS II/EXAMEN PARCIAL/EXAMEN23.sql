-- 1

Se desea obtener un listado de pueblos que incluya los pueblos donde viven los
jefes de vendedores que aplican comisiones superiores al 9%, pero deben
excluirse aquellos pueblos en los que vivan clientes que hayan realizado compras
con descuento igual o superior al 8% en las facturas y a�adirse aquellos pueblos en los que vivan clientes los
cuales en las facturas de hace 4 a�os (2019) se les aplic� un IVA del 7%.
Mostrar el nombre de la provincia y su poblaci�n entre par�ntesis, ejemplo:
NAVARRA (PAMPLONA) dando un nombre a la columna resultante.
Ordenar el resultado ascendentemente.
Nota: la resoluci�n de esta consulta debe incluir el uso de al menos dos operadores
conjuntistas.
No deb�is tener en cuenta descuentos o comisiones si estos no tienen ning�n valor.

SELECT DISTINCT PR.NOMBRE || '(' || P.NOMBRE || ')' AS TITULO
FROM PUEBLOS P
JOIN VENDEDORES V USING (CODPUE)
JOIN PROVINCIAS PR USING (CODPRO)
JOIN VENDEDORES V2 ON (V2.CODVEN = V.JEFE)
WHERE V2.COMISION > 9

MINUS 

SELECT DISTINCT PR.NOMBRE || '(' || P.NOMBRE || ')' AS TITULO
FROM FACTURAS F
JOIN CLIENTES C USING (CODCLI)
JOIN PUEBLOS P ON (C.CODPUE = P.CODPUE)
JOIN PROVINCIAS PR ON (PR.CODPRO = P.CODPRO)
WHERE F.DTO >= 8

UNION 

SELECT DISTINCT PR.NOMBRE || '(' || P.NOMBRE || ')' AS TITULO
FROM FACTURAS F
JOIN CLIENTES C USING (CODCLI)
JOIN PUEBLOS P ON (C.CODPUE = P.CODPUE)
JOIN PROVINCIAS PR ON (P.CODPRO = PR.CODPRO)
WHERE F.IVA = 7
AND F.FECHA BETWEEN '01/01/19' AND '31/12/19'

ORDER BY TITULO ASC;


-- 2

Obtener un listado de todos los art�culos de la base de datos, mostrando para 
cada uno de ellos la descripci�n del art�culo, su c�digo, el n�mero de
clientes que los hayan comprado (total_clientes) y el n�mero de unidades que se
hayan vendido (total_unidades_vendidas) contabilizando s�lo las ventas de las
facturas con descuento de 24 o menor.
Ordenar por la descripci�n del art�culo descendentemente.

SELECT A.DESCRIP AS ARTICULO, A.CODART AS CODIGO, COUNT(DISTINCT F.CODCLI) AS TOTAL_CLIENTES, COALESCE(SUM(L.CANT), 0) AS TOTAL_UNIDADES_VENDIDAS
FROM ARTICULOS A
LEFT JOIN LINEAS_FAC L ON (L.CODART = A.CODART)
LEFT JOIN FACTURAS F ON (F.CODFAC = L.CODFAC AND F.DTO <= 24)
GROUP BY A.DESCRIP, A.CODART
ORDER BY A.DESCRIP DESC;


-- 3

La direcci�n de la empresa quiere hacer una provisi�n de art�culos, para ello:
a. Crear la tabla 'articulos_provisiones' como resultado de una consulta que
devuelva el c�digo, la descripci�n y el valor 0 como el atributo provision, de
los diferentes art�culos cuya diferencia entre el stock actual y el stock m�nimo
sea menor que uno y tenga alguna venta con cantidad de una o m�s unidades.
b. Actualizar la provisi�n de cada art�culo de la tabla 'articulos_provisiones'
reci�n creada, con la suma de las unidades vendidas de cada correspondiente
art�culo durante estos cinco a�os de facturaci�n divido entre el factor 4.3,
redondeando hacia arriba usando la funcion CEIL(expresi�n)
Nota: Alg�n art�culo podr�a tener una provisi�n negativa (por alguna devoluci�n).


CREATE TABLE ARTICULOS_PROVISIONES AS
    (SELECT DISTINCT A.CODART AS CODIGO, A.DESCRIP AS DESCRIPCION, 0 AS PROVISION
    FROM ARTICULOS A
    JOIN LINEAS_FAC L ON (A.CODART = L.CODART)
    WHERE (STOCK-STOCK_MIN < 1)
    MINUS
    SELECT DISTINCT A.CODART AS CODIGO, A.DESCRIP AS DESCRIPCION, 0 AS PROVISION
    FROM ARTICULOS A
    JOIN LINEAS_FAC L ON (A.CODART = L.CODART)
    WHERE (L.CANT < 1));

--DROP TABLE ARTICULOS_PROVISIONES CASCADE CONSTRAINT; 
--SELECT * FROM ARTICULOS_PROVISIONES;

UPDATE ARTICULOS_PROVISIONES SET PROVISION =
    (SELECT CEIL(SUM(L.CANT) / 4.3)
    FROM LINEAS_FAC L
    GROUP BY L.CODART)
WHERE CODIGO IN (SELECT DISTINCT CODART FROM LINEAS_FAC);


-- 4

Obtener un listado de pueblos cuya media de IVAs de ventas sea inferior al de
la media de IVAs de entre todas las facturas (cada factura de un vendedor se
considera una venta).
Mostrar el nombre del pueblo y la media de IVAs de ventas ordenado por
media de IVAs de ventas ascendentemente y por nombre del pueblo descendentemente.
    
    SELECT P.NOMBRE AS PUEBLO, AVG(NVL(F.IVA, 0)) AS IVA_MEDIO_VENTAS
    FROM PUEBLOS P, FACTURAS F, CLIENTES C
    WHERE P.CODPUE = C.CODPUE
    AND C.CODCLI = F.CODCLI
    GROUP BY P.NOMBRE
    HAVING (AVG(NVL(F.IVA, 0)) < (SELECT AVG(NVL(IVA,0)) FROM FACTURAS))
    ORDER BY P.NOMBRE DESC, IVA_MEDIO_VENTAS ASC;
    
    
    
    
    
    






