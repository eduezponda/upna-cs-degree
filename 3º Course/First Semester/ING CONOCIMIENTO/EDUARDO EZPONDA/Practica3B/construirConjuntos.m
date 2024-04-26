function conjuntos = construirConjuntos(datasetX)
    valoresA = datasetX(:, 1);
    valoresB = datasetX(:, 2);
    valoresC = datasetX(:, 3);
    valoresD = datasetX(:, 4);

    minimoA = min(valoresA);
    minimoB = min(valoresB);
    minimoC = min(valoresC);
    minimoD = min(valoresD);

    maximoA = max(valoresA);
    maximoB = max(valoresB);
    maximoC = max(valoresC);
    maximoD = max(valoresD);

    conjuntos.R_A = minimoA:0.1:maximoA;
    conjuntos.R_A = round(conjuntos.R_A,1);
    conjuntos.R_B = minimoB:0.1:maximoB;
    conjuntos.R_B = round(conjuntos.R_B,1);
    conjuntos.R_C = minimoC:0.1:maximoC;
    conjuntos.R_C = round(conjuntos.R_C,1);
    conjuntos.R_D = minimoD:0.1:maximoD;
    conjuntos.R_D = round(conjuntos.R_D,1);

    conjuntos.A = [trimf(conjuntos.R_A, [minimoA, minimoA, (minimoA + maximoA)/2]);
               trimf(conjuntos.R_A, [minimoA, (minimoA + maximoA)/2, maximoA]);
               trimf(conjuntos.R_A, [(minimoA + maximoA)/2, maximoA, maximoA])];
    %conjuntos.A = [trapmf(conjuntos.R_A, [minimoA, minimoA, (minimoA + maximoA)/2, maximoA]);
           %trapmf(conjuntos.R_A, [minimoA, 5.5, 6.5, maximoA]);
           %trapmf(conjuntos.R_A, [minimoA, (minimoA + maximoA)/2, maximoA, maximoA])];

    figure;
    plot(conjuntos.R_A, conjuntos.A(1, :), 'r'); %Conjunto bajo en rojo
    hold on;  
    plot(conjuntos.R_A, conjuntos.A(2, :), 'g'); %Conjunto medio en verde
    plot(conjuntos.R_A, conjuntos.A(3, :), 'b'); %Conjunto alto en azul    
    title('Largo sepalo');


    conjuntos.B = [trimf(conjuntos.R_B, [minimoB, minimoB, (minimoB + maximoB)/2]);
               trimf(conjuntos.R_B, [minimoB, (minimoB + maximoB)/2, maximoB]);
               trimf(conjuntos.R_B, [(minimoB + maximoB)/2, maximoB, maximoB])];
    %conjuntos.B = [trapmf(conjuntos.R_B, [minimoB, minimoB, (minimoB + maximoB)/2, maximoB]);
           %trapmf(conjuntos.R_B, [minimoB, 3, 3.5, maximoB]);
           %trapmf(conjuntos.R_B, [minimoB, (minimoB + maximoB)/2, maximoB, maximoB])];


    figure;
    plot(conjuntos.R_B, conjuntos.B(1, :), 'r'); %Conjunto bajo en rojo
    hold on;  
    plot(conjuntos.R_B, conjuntos.B(2, :), 'g'); %Conjunto medio en verde
    plot(conjuntos.R_B, conjuntos.B(3, :), 'b'); %Conjunto alto en azul  
    title('Ancho sepalo');

   conjuntos.C = [trimf(conjuntos.R_C, [minimoC, minimoC, (minimoC + maximoC)/2]);
               trimf(conjuntos.R_C, [minimoC, (minimoC + maximoC)/2, maximoC]);
               trimf(conjuntos.R_C, [(minimoC + maximoC)/2, maximoC, maximoC])];
   %conjuntos.C = [trapmf(conjuntos.R_C, [minimoC, minimoC, (minimoC + maximoC)/2, maximoC]);
           %trapmf(conjuntos.R_C, [minimoC, 3.5, 4.5, maximoC]);
           %trapmf(conjuntos.R_C, [minimoC, (minimoC + maximoC)/2, maximoC, maximoC])];


    figure;
    plot(conjuntos.R_C, conjuntos.C(1, :), 'r'); %Conjunto bajo en rojo
    hold on;  
    plot(conjuntos.R_C, conjuntos.C(2, :), 'g'); %Conjunto medio en verde
    plot(conjuntos.R_C, conjuntos.C(3, :), 'b'); %Conjunto alto en azul  
    title('Largo pétalo');

   conjuntos.D = [trimf(conjuntos.R_D, [minimoD, minimoD, (minimoD + maximoD)/2]);
               trimf(conjuntos.R_D, [minimoD, (minimoD + maximoD)/2, maximoD]);
               trimf(conjuntos.R_D, [(minimoD + maximoD)/2, maximoD, maximoD])];
   %conjuntos.D = [trapmf(conjuntos.R_D, [minimoD, minimoD, (minimoD + maximoD)/2, maximoD]);
           %trapmf(conjuntos.R_D, [minimoD, 1, 1.5, maximoD]);
           %trapmf(conjuntos.R_D, [minimoD, (minimoD + maximoD)/2, maximoD, maximoD])];


    figure;
    plot(conjuntos.R_D, conjuntos.D(1, :), 'r'); %Conjunto bajo en rojo
    hold on;  
    plot(conjuntos.R_D, conjuntos.D(2, :), 'g'); %Conjunto medio en verde
    plot(conjuntos.R_D, conjuntos.D(3, :), 'b'); %Conjunto alto en azul  
    title('Ancho pétalo');
end