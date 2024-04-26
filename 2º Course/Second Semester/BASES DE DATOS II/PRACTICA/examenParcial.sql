/*La dirección de la empresa decide aplicar las siguientes normas y políticas:
a. Si se elimina un vendedor, las facturas de la responsabilidad de ese vendedor 
serán consecuentemente eliminados. Al mismo tiempo, las líneas de facturas 
de esas facturas eliminadas serán conservadas en la base de datos.*/

ALTER TABLE FACTURAS DROP CONSTRAINT fk_fac_ven;
ALTER TABLE FACTURAS ADD (CONSTRAINT fk_fac_ven foreign key (codven) references vendedores(codven) on delete cascade);

ALTER TABLE LINEAS_FAC DROP CONSTRAINT fk_lin_fac;
ALTER TABLE LINEAS_FAC ADD (CONSTRAINT fk_lin_fac foreign key (codfac) references facturas on delete set null);

/*b. El descuento de las facturas NO deberá ser mayor del 75%. En ningún caso podrá ser nulo. 
Los valores antiguos de descuento de facturas se actualizarán de la siguiente forma:
• El valor nulo, y el 0% pasan a ser 1% de descuento en las facturas.
• Los descuentos de facturas entre el 3% y el 10% inclusive se incrementan en un 70% (*1.7).
• Y los descuentos de facturas del 11% en adelante se incrementan en un 100% (se doblan *2).*/

ALTER TABLE FACTURAS DROP CONSTRAINT ch_dto;
ALTER TABLE FACTURAS ADD (constraint ch_dto check (dto between 0 and 75));

UPDATE FACTURAS SET dto = 1 WHERE dto = 0 OR dto IS NULL;
UPDATE FACTURAS SET dto = dto*1.7 WHERE dto between 3 and 10;
UPDATE FACTURAS SET dto = dto*2 WHERE dto > 10;

ALTER TABLE FACTURAS MODIFY (dto NOT NULL);

--c. Subir un 7% el descuento a todas las líneas de factura que tengan 11 o más cantidad de unidades.
UPDATE lineas_fac SET dto = dto*1.07 WHERE cant > 10;

/*d. Crear la tabla 'vendedores_vip' como resultado de una consulta que devuelva los vendedores de 2 clase (codven, clase), 
que son aquellos que tengan algún artículo con precio > 6000 vendido alguna vez.*/

create table  vendedores_vip
( codven    number(5),
  clase  number(1) default 2,
constraint pk_vendedores_vip primary key (codven));

INSERT INTO vendedores_vip (codven) SELECT unique v.codven 
FROM vendedores v, lineas_fac l WHERE v.codven = l.codven AND l.precio > 6000;

/*e. Actualizar la tabla para obtener cuales de estos son finalmente de 1 clase, 
que son aquellos de 2 clase que tengan también algún artículo con precio > 10000 vendido alguna vez.*/

UPDATE vendedores_vip SET clase = 1 WHERE codven in (SELECT unique v.codven 
FROM vendedores v, lineas_fac l WHERE v.codven = l.codven AND l.precio > 10000);

-----------------------------2
/*Obtener un listado de todos los artículos con descripciones repetidas 
(no debemos mostrar ninguna descripción de artículo que sea única) 
mostrando para cada uno de ellos el número de unidades que se hayan vendido 
(total_unidades_vendidas) y el número de vendedores de líneas de facturas 
que los hayan vendido (total_vendedores).
Mostrar el código del artículo y su descripción entre paréntesis 
junto a los totales anteriores. Ordenar por el código del artículo descendente.
Nota: En el caso de que no tengan unidades vendidas se 
deberá evitar mostrar NULL y mostrar 0, usando por ejemplo la función COALESCE.

ARTICULO  TOTAL_UNIDADES_VENDIDAS  TOTAL_VENDEDORES*/

select '(' || a.codart || ',' || a.descrip || ')' as articulo, sum(l.cant) as total_unidades_vendidas, count(l.codven) as total_vendedores
from lineas_fac l, (select unique a1.codart, a1.descrip
                    from articulos a1, articulos a2
                    where a1.descrip = a2.descrip and a1.codart != a2.codart) a
where l.codart = a.codart
group by a.codart, a.descrip
order by a.codart desc;


----------------------3
/*Mostrar los compradores (clientes) que han comprado menos de 80000 euros en total 
y que cumpla la condición de que el nombre del comprador debe comenzar con la letra 'A'.
NOTA: para calcular la compra tendremos en cuenta solamente la cantidad 
y el precio indicados en las líneas de factura, sin considerar descuentos ni impuestos (IVA).

COMPRADOR  */

select c.codcli 
from clientes c, facturas f 
where nombre like 'A%' and c.codcli = f.codcli and f.codcli in (select codfac
from lineas_fac 
group by codfac 
having sum(cant*precio)<80000);

select unique clientes.codcli
from clientes join (facturas join lineas_fac on facturas.codfac = lineas_fac.codfac and lineas_fac.cant*lineas_fac.precio<80000) 
on clientes.codcli = facturas.codcli and clientes.nombre like 'A%';

---------------4
/*Se desea obtener un listado de pueblos que incluya los pueblos donde 
viven los jefes de vendedores que aplican comisiones superiores al 8%, 
pero deben excluirse aquellos pueblos en los que vivan clientes que 
hayan realizado compras con descuento inferior al 12% en las facturas 
y añadirse aquellos pueblos en los que vivan clientes los cuales en las 
facturas de hace 5 años (2015) se les aplicó un IVA del 16%.
Nota: la resolución de esta consulta debe incluir el uso de dos operadores conjuntistas.
Mostrar el nombre de la provincia y su población entre paréntesis, ejemplo:
NAVARRA (PAMPLONA).
Ordenar por el nombre de la población ascendente.*/

(select p.nombre from vendedores v, pueblos p where  v.codpue = p.codpue and v.codven in(
select unique jefe from vendedores where comision > 8 and jefe > 0)
minus
select p.nombre from pueblos p where p.codpue in (select unique c.codpue 
    from clientes c, facturas f 
    where c.codcli = f.codcli and dto < 12))
union
select nombre from pueblos where codpue in (select unique c.codpue 
from clientes c, facturas f
where c.codcli = f.codcli and f.iva = 16 and f.fecha between '01/01/2015' and '31/12/2015');



(select p1.nombre
from pueblos p1, (select v.codpue from vendedores v, (select unique jefe from vendedores where comision>8 and jefe is not null) j where v.codven = j.jefe) v1
where p1.codpue = v1.codpue
minus
select p2.nombre
from pueblos p2, (select unique c.codpue from clientes c, facturas f where c.codcli = f.codcli and f.dto<12) c2
where p2.codpue = c2.codpue)
union
select p3.nombre
from pueblos p3, (select unique c.codpue from clientes c, facturas f where c.codcli = f.codcli and f.fecha between '01/01/2015' and '31/12/2015' and f.iva = 16) c3
where p3.codpue = c3.codpue;


-------------------------------------------5
/*Obtener un listado de provincias cuya media de descuentos de compras sea superior 
al de la media de descuentos de entre todas las facturas 
(cada factura de un cliente se considera una compra). 
Mostrar el nombre de la provincia y la media de descuentos de compras 
ordenado por nombre de la provincia alfabeticamente al contrario y 
por media de descuentos de compras ascendentemente. PROVINCIA DTO_MEDIO_COMPRAS*/

select unique a.nombre
from provincias a, pueblos p
where p.codpro = a.codpro and
p.codpue in (select unique c.codpue 
from clientes c, facturas f
where c.codcli = f.codcli
group by c.codpue having avg(dto) > (select avg(dto) from facturas));
