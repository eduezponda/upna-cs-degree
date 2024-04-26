refTemperatura = 0:0.01:40;
muyBaja = trapmf(refTemperatura,[0,0,10,15]);
baja = trapmf(refTemperatura,[10,15,15,20]);
normal = trapmf(refTemperatura,[18,20,20,22]);
alta = trapmf(refTemperatura,[20,25,25,30]);
muyAlta = trapmf(refTemperatura,[25,30,40,40]);
figure;
plot(refTemperatura,muyBaja,"b");
title('Temperatura');
hold on;
plot(refTemperatura,baja,"r");
hold on;
plot(refTemperatura, normal, "y");
hold on;
plot(refTemperatura, alta, "g")
hold on;
plot(refTemperatura, muyAlta, "black");
legend("Muy baja","Baja","Normal","Alta","Muy alta");
pertenenciaTemperatura = cat(1,muyBaja,baja,normal,alta,muyAlta);



refHumedad = 0:0.01:100;
muyBaja = trapmf(refHumedad,[0,0,10,20]);
baja = trapmf(refHumedad,[10,25,25,40]);
normal = trapmf(refHumedad,[30,40,40,50]);
alta = trapmf(refHumedad,[40,55,55,70]);
muyAlta = trapmf(refHumedad,[60,70,100,100]);
figure;
plot(refHumedad,muyBaja,"b");
title('Humedad');
hold on;
plot(refHumedad,baja,"r");
hold on;
plot(refHumedad, normal, "y");
hold on;
plot(refHumedad, alta, "g")
hold on;
plot(refHumedad, muyAlta, "black");
legend("Muy baja","Baja","Normal","Alta","Muy alta");
pertenenciaHumedad = cat(1,muyBaja,baja,normal,alta,muyAlta);



refVariacion = -15:0.5:15;
BG = trapmf(refVariacion,[-15,-10,-10,-7.5]);
BN = trapmf(refVariacion,[-10,-5.5,-5.5,-2.5]);
BP = trapmf(refVariacion,[-7.5,-2.5,-2.5,0]);
M = trapmf(refVariacion,[-1,0,0,1]);
SP = trapmf(refVariacion,[0,2.5,2.5,7.5]);
SN = trapmf(refVariacion,[2.5,7.5,7.5,10]);
SG = trapmf(refVariacion,[7.5,10,10,15]);
figure;
plot(refVariacion,BG,"b");
title('Variacion');
hold on;
plot(refVariacion,BN,"r");
hold on;
plot(refVariacion, BP, "y");
hold on;
plot(refVariacion, M, "g")
hold on;
plot(refVariacion, SP, "black");
hold on;
plot(refVariacion, SN, "m")
hold on;
plot(refVariacion, SG, "b");
legend("Bajada grande","Bajada normal","Bajada pequeña","Mantener","Subida pequeña", "Subida normal","Subida grande");
pertenenciaVariacion = cat(1,BG,BN,BP,M,SP,SN,SG);



reglas = [
    1,1,6;
    1,2,6;
    1,3,7;
    1,4,7;
    1,5,7;
    2,1,4;
    2,2,4;
    2,3,5;
    2,4,5;
    2,5,6;
    3,1,4;
    3,2,4;
    3,3,4;
    3,4,4;
    3,5,3;
    4,1,4;
    4,2,4;
    4,3,3;
    4,4,3;
    4,5,2;
    5,1,3;
    5,2,2;
    5,3,2;
    5,4,1;
    5,5,1;
];


stepTemperatura = 100;
stepHumedad = 100;
pertenenciaTemperatura(2,19.5*stepTemperatura+1)
size(reglas)
temperatura = 19.5;
humedad = 65;
pertenenciaValorTemperatura = pertenenciaTemperatura(:,temperatura*stepTemperatura+1);
pertenenciaValorHumedad = pertenenciaHumedad(:,humedad*stepHumedad+1)
resultante = [];
for index_regla = 1:size(reglas,1)
    pTemperatura = pertenenciaValorTemperatura(reglas(index_regla,1));
    pHumedad = pertenenciaValorHumedad(reglas(index_regla,2));
    if pTemperatura ~= 0 && pHumedad ~= 0
        pMin = min(pTemperatura,pHumedad);
        conjDif = min(pertenenciaVariacion(reglas(index_regla,3),:), pMin);
        resultante = cat(1, resultante, conjDif);
    end
end
agregado = max(resultante,[],1);
crisp = centroide(refVariacion, agregado);
crisp

function centro = centroide(conjunto, pertenencia)
    nominator = sum(conjunto .* pertenencia);
    denominator = sum(pertenencia);
    centro = nominator / denominator;
end