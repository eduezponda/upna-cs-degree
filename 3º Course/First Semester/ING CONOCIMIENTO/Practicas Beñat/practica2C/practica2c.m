% 2 FUNCIONES DE PERTENENCIA    
edad = 0:100;
adulto = trimf(edad,[25,50,75]);
figure;
plot(edad,adulto,'r');
legend('Adulto');
joven = trapmf(edad,[0,0,15,30]);
figure;
plot(edad,joven,'r');
legend('Joven');
anciano = gaussmf(edad,[20,100]);
figure;
plot(edad,anciano,'r');
legend('Anciano');

% VARIABLES LINGUISTICAS
nota = linspace(0,10,100);
S = trapmf(nota,[0,0,4,5]);
AP = trapmf(nota,[4,5,6,7]);
N = trapmf(nota,[6,7,8,9]);
SB = trapmf(nota,[8,9,10,10]);
figure;
plot(nota,S,'r');
title('Notas')
hold on;
plot(nota,AP,'black')
hold on;
plot(nota,N,'g')
hold on;
plot(nota,SB,'b')
legend('Suspenso', 'Aprobado', 'Notable','Sobresaliente')

%2)
res = min(1, trapmf(8.6,[6,7,8,9])+trapmf(8.6,[8,9,10,10]))

% 4 SELECCION DE CANDIDATOS
altura = 0:220;
alto = @(x) trapmf(x,[170,180,220,220]);
exito = linspace(0,1,100);
bueno = @(x) trapmf(x, [0.625,0.875,1,1]);
tabla = [
    167, 0.75;
    169, 0.375;
    175, 0.9375;
    179, 0.75;
    183, 1;
    186, 0.8125;
    187, 0.75;
    190, 0.6250;
    200, 0.8125;
]
esAlto = alto(tabla(:,1));
esBueno = bueno(tabla(:,2));
reglaEsAltoYesBueno = min(esAlto, esBueno)

% 5 Centroide
centro = centroide(edad, adulto);
centro
function centro = centroide(conjunto, pertenencia)
    nominator = sum(conjunto .* pertenencia);
    denominator = sum(pertenencia);
    centro = nominator / denominator;
end