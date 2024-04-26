function [devolver] = practica2C()
    %ADULTO TRIANGULAR:
    edad = 0:100;
    muAdulto = zeros(1, length(edad));
    for i = 1:length(edad)
        muAdulto(i) = trimf(edad(i), [25 50 75]);
    end
    figure;
    plot(edad,muAdulto,'r');
    legend('Adulto');

    %JOVEN TRAPEZOIDAL:
    edad = 0:100;
    muJoven = zeros(1, length(edad));
    for i = 1:length(edad)
        muJoven(i) = trapmf(edad(i), [0 0 15 30]); %antes de 0, 0-15, 15-30, 30 en adelante
    end
    figure;
    plot(edad,muJoven,'r');
    legend('Joven');

    %ANCIANO
    edad = 0:100;
    muAnciano = zeros(1, length(edad));
    for i = 1:length(edad)
        muAnciano(i) = gaussmf(edad(i), [100 20]); 
    end
    figure;
    plot(edad,muAnciano,'r');
    legend('Anciano');

    %VARIABLES LINGUÍSTICAS:
    nota = linspace(0,10,100); %notas desde 0 a 10 con precisión de decimales
    S = zeros(1, length(nota));
    AP = zeros(1, length(nota));
    N = zeros(1, length(nota));
    SB = zeros(1, length(nota));

    for i = 1:length(nota)
        S(i) = trapmf(nota(i), [0 0 4 5]);
        AP(i) = trapmf(nota(i), [4 5 6 7]);
        N(i) = trapmf(nota(i), [6 7 8 9]);
        SB(i) = trapmf(nota(i), [8 9 10 10]);
    end
    figure;
    plot(nota, S, 'r');
    hold on;
    plot(nota, AP, 'g');
    hold on;
    plot(nota, N, 'black');
    hold on;
    plot(nota, SB, 'b');
    title('Notas');
    legend('Suspenso','Aprobado', 'Notable','Sobresaliente');

    %Grado verdad Sup(notable, sobresaliente)
    gradoVerdad = min(1, trapmf(8.6, [6 7 8 9]) + trapmf(8.6, [8 9 10 10]));
    disp('El grado de verdad de 8.6 para la expresión 8.6 es notable o sobresaliente es: ');
    disp(gradoVerdad);

    %SELECCIÓN DE CANDIDATOS:
    numCandidatos = 9;
    candidatos = [167, 0.75;
                169, 0.375;
                175, 0.9375;
                179, 0.75;
                183, 1;
                186, 0.8125;
                187, 0.75;
                190, 0.6250;
                200, 0.8125;];
    gradoVerdadRegla = zeros(1, numCandidatos);
    disp('Los candidatos que tienen un grado de verdad mayor que 0.5 son: ');
    for i = 1:numCandidatos
        gradoVerdadRegla(i) = min(trapmf(candidatos(i,1), [170 180 220 220]), trapmf(candidatos(i,2), [0.625 0.875 1 1]));
        if (gradoVerdadRegla(i) > 0.5)
            disp(['Candidato número ' num2str(i)]);
        end
    end
    %Otra forma: 
    
    alto = @(x) trapmf(x,[170,180,220,220]);
    bueno = @(x) trapmf(x,[0.675,0.875,1,1]);
    esAlto = alto(candidatos(:,1));
    esBueno = bueno(candidatos(:,2));
    esAltoYBueno = min(esAlto, esBueno);

    %Centroide (Escogemos como x el conjunto edad que varía de 0 a 100
    %años):

    numerador = sum(edad .* muAdulto);
    denominador = sum(muAdulto);
    centroDeMasas = sum(numerador / denominador);
    disp(['El centro de masas del conjunto difuso Adulto es: ' num2str(centroDeMasas)]);

    devolver = 0;
end