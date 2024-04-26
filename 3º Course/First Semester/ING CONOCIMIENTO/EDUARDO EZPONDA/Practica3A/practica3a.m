%Variables de entrada: 
temperatura = 0:0.01:40;

tempMuyBaja = trapmf(temperatura, [0 0 10 15]);
tempBaja = trapmf(temperatura, [10 15 15 20]);
tempNormal = trapmf(temperatura, [18 20 20 22]);
tempAlta = trapmf(temperatura, [20 25 25 30]);
tempMuyAlta = trapmf(temperatura, [25 30 40 40]);

humedad = 0:0.01:100;

humMuyBaja = trapmf(humedad, [0 0 10 20]);
humBaja = trapmf(humedad, [10 25 25 40]);
humNormal = trapmf(humedad, [30 40 40 50]);
humAlta = trapmf(humedad, [40 55 55 70]);
humMuyAlta = trapmf(humedad, [60 70 100 100]);

%Mostrar gráficas temperatura y humedad:
figure;
plot(temperatura, tempMuyBaja, 'r');
hold on;
plot(temperatura, tempBaja, 'g');
hold on;
plot(temperatura, tempNormal, 'b');
hold on;
plot(temperatura, tempAlta, 'c');
hold on;
plot(temperatura, tempMuyAlta, 'y');
title('Temperatura');
legend('Muy baja', 'Baja', 'Normal', 'Alta', 'Muy alta');

figure;
plot(humedad, humMuyBaja, 'r');
hold on;
plot(humedad, humBaja, 'g');
hold on;
plot(humedad, humNormal, 'b');
hold on;
plot(humedad, humAlta, 'c');
hold on;
plot(humedad, humMuyAlta, 'y');
title('Humedad');
legend('Muy baja', 'Baja', 'Normal', 'Alta', 'Muy alta');

%Variables de salida
variacionTemperatura = -15:0.5:15;
bajadaGrande = trapmf(variacionTemperatura, [-15 -10 -10 -7.5]);
bajadaNormal = trapmf(variacionTemperatura, [-10 -5.5 -5.5 -2.5]);
bajadaPequena = trapmf(variacionTemperatura, [-7.5 -2.5 -2.5 0]);
mantener = trapmf(variacionTemperatura, [-1 0 0 1]);
subidaPequena = trapmf(variacionTemperatura, [0 2.5 2.5 7.5]);
subidaNormal = trapmf(variacionTemperatura, [2.5 7.5 7.5 10]);
subidaGrande = trapmf(variacionTemperatura, [7.5 10 10 15]);

figure;
plot(variacionTemperatura, bajadaGrande, 'r');
hold on;
plot(variacionTemperatura, bajadaNormal, 'g');
hold on;
plot(variacionTemperatura, bajadaPequena, 'b');
hold on;
plot(variacionTemperatura, mantener, 'c');
hold on;
plot(variacionTemperatura, subidaPequena, 'y');
hold on;
plot(variacionTemperatura, subidaNormal, 'm');
hold on;
plot(variacionTemperatura, subidaGrande, 'k');
title('Variación de temperatura');
legend('Bajada grande', 'Bajada normal', 'Bajada pequeña', 'Mantener', 'Subida pequeña', ...
    'Subida normal', 'Subida grande');

%Modus Ponens Difuso:

separacion = 100; %como la precisión es en centesimas, para hallar el valor de temperatura alta
                  %19.5ºC necesitas una conversión. Se multiplica por 100
                  %por las centesimas, y se suma 1 por empezar la
                  %temperatura en el rango de 0.
tempEjemplo = 19.5;
humEjemplo = 65;
pertenenciaTempBaja = tempBaja(tempEjemplo*separacion + 1);
pertenenciaTempNormal = tempNormal(tempEjemplo*separacion + 1);
pertenenciaHumAlta = humAlta(humEjemplo*separacion + 1);
pertenenciaHumMuyAlta = humMuyAlta(humEjemplo*separacion + 1);

numeroReglas = 4; %si temp baja y hum alta then variacion es subida pequeña
                  %si temp baja y hum muy alta then variacion es subida
                  %normal
                  % si temp Normal y hum alta then variacion es mantener
                  % si temp normal y hum muy alta then variacion es bajada
                  % pequeña
%Si quisiera añadir más reglas puedo escribir los índices en una matriz
%[1 1 3; 1 2 4;.....] Ademas puedo hacer temp = cat(1,Alta, Baja,...) y lo
%mismo con la humedad

k1 = min(pertenenciaTempBaja, pertenenciaHumAlta);
k2 = min(pertenenciaTempBaja, pertenenciaHumMuyAlta);
k3 = min(pertenenciaTempNormal, pertenenciaHumAlta);
k4 = min(pertenenciaTempNormal, pertenenciaHumMuyAlta);

%Conjuntos difusos:

variacion1 = min(k1, subidaPequena);
variacion2 = min(k2, subidaNormal);
variacion3 = min(k3, mantener);
variacion4 = min(k4, bajadaPequena);

%Agregación salidas reglas:
variaciones = cat(1, variacion1, variacion2, variacion3, variacion4);
conjuntoSalida = max(variaciones, [], 1);

figure;
plot(variacionTemperatura, conjuntoSalida, 'LineWidth', 2);
title('Conjunto de Salida');
xlabel('Variación de Temperatura');
ylabel('Pertenencia');

%Defuzzificación
numerador = sum(variacionTemperatura .* conjuntoSalida);
denominador = sum(conjuntoSalida);
centroDeMasas = numerador/denominador;

disp(['La temperatura de la calefacción tiene que variar un ' num2str(centroDeMasas) '%']);

