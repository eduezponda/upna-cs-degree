function [devolver] = practica2B()   
    %EJERCICIO 1:
    % Vector cuyos elementos son los números entre el 1 y el 10 de 1 en 1.
    % Es decir, crear el conjunto referencial.
    U = 1:0.1:10;
    % Vector cuyos elementos son los grados de pertenencia de
    % cada elemento de X a los conjuntos difusos A y B
    muA = 1 ./ (1 + (U - 5).^4);
    muB = 2.^-U;
    muC = 1 ./ (1 + 10.*(U - 2).^2);
    % Pintar los conjuntos difusos A y B:
    figure;
    plot(U, muA, 'sr');
    title('Funciones de pertenencia');
    hold on;
    plot(U, muB, 'pb');
    hold on;
    plot(U, muB, '+g');
    legend('A','B','C');

    %1
    figure
    conjunto1 = 1 - muC; %negacion C
    plot(U, conjunto1, 'sr');
    title('Negación de C');

    %2
    figure
    concatenacion = cat(1, muA, muB, muC);
    union = max(concatenacion, [], 1);
    plot(U, union, 'sr');
    interseccion = min(concatenacion, [] ,1);
    hold on;
    plot(U, interseccion, 'pb');
    legend('Union', 'Interseccion');
    
    %3
    figure
    negacionB = 1 - muB;
    concatenacion = cat(1, negacionB, muC);
    interseccion = min(concatenacion, [], 1);
    negacionInterseccion = 1 - interseccion;
    plot(U, negacionInterseccion,'sr');
    concatenacion = cat(1, muA, muC);
    union = max(concatenacion, [], 1);
    hold on;
    plot(U, union, 'pb');
    legend('Interseccion', 'Union');

    %EJERCICIO 2:
    U = 1:100;
    muAdulto = 1 ./ (1 + ((U - 50)./ 10).^4);
    muJoven = 1 - muAdulto;
    muJoven(50:100) = 0;
    muAnciano = 1 - muAdulto;
    muAnciano(1:50) = 0;

    %MUY JOVEN
    muMuyJoven = muJoven.^2;
    %BASTANTE ANCIANO
    muBastanteAnciano = sqrt(muAnciano);
    %LIGERAMENTE ADULTO
    interseccion = cat(1,muAdulto.^2, 1 - muAdulto.^2);
    muLigeramenteAdulto = min(interseccion, [], 1);

    figure
    plot(U,muMuyJoven, 'sr');
    title('Muy joven');

    figure
    plot(U,muBastanteAnciano, 'pb');
    title('Bastante anciano');

    figure 
    plot(U,muLigeramenteAdulto, '+g');
    title('Ligeramente adulto (muy adulto y no muy adulto)')

    devolver = 0;
end