%Problema 1:
function [devolver] = practica2A()
    R = [0.315 0.161 0.364;
         0.685 0.041 0.062;
         0.840 0.959 0.387;
         0.636 0.938 0.613];
    numFilas = size(R,1);
    numColumnas = size(R,2);
    apartados = 6;
    soluciones = zeros(apartados,numFilas);
    for i = 1:numFilas
        soluciones(1,i) = mean(R(i,:)); 
        soluciones(2,i) = harmmean(R(i,:));
    end
    disp('La alternativa de la media aritmética es: ')
    [~,indiceArit] = max(soluciones(1,:));
    disp(indiceArit);
    disp('La alternativa de la media armónica es: ')
    [~,indiceHarm] = max(soluciones(2,:));
    disp(indiceHarm);
    
    ignorancia = 2*min(R,1-R);
    w = (1-ignorancia);
    denominador = sum(1-ignorancia,2);
    for i = 1:numFilas
        w(i,:) = w(i,:)/denominador(i);
    end
    Rordenado = sort(R,2,'descend');
    soluciones(3,:) = sum(Rordenado.*w,2);
    disp('La alternativa del OWA con función de ignorancia débil es: ')
    [~,indiceOWAdebil] = max(soluciones(3,:));
    disp(indiceOWAdebil);
    
    a = 0.3;
    b = 0.8;
    anterior = 0;
    tramosQ = zeros(numFilas,1);
    w = zeros(numFilas,1);
    for i = 1:numFilas
        r = i/numFilas;
        if r < a
            tramosQ(i) = 0;
        elseif 0.3 <= r  &&  r <= 0.8
            tramosQ(i) = (r - a)/(b - a);
        else 
            tramosQ(i) = 1;
        end
        w = tramosQ(i) - anterior;
        anterior = tramosQ(i);
    end
    Rordenado = sort(R,2,'descend');
    soluciones(4,:) = sum(Rordenado.*w,2);
    disp('La alternativa del OWA mayor parte de: ')
    [~,indiceOWAmayorparte] = max(soluciones(4));
    disp(indiceOWAmayorparte);
    
    medidaFuzzy = dictionary;
    medidaFuzzy(mat2str([1,2,3])) = 1;
    medidaFuzzy(mat2str([])) = 0;
    v11 = (2/numColumnas)^2;
    v12 = (1/numColumnas)^2;
    c1 = nchoosek([1,2,3],2); % [1,2] [1,3] [2,3]
    c2 = nchoosek([1,2,3],1); % [1] [2] [3]
    for i = 1:numColumnas
        medidaFuzzy(mat2str(c1(i,:))) = v11;
        medidaFuzzy(mat2str(c2(i,:))) = v12;
    end
    [Rordenado, indices] = sort(R,2,'ascend');
    T = [1,2,3];
    suma = 0;
    for i = 1:numFilas
        anterior = 0;
        for j = 1:numColumnas
            suma = suma + (Rordenado(i,j) - anterior) * medidaFuzzy(mat2str(T));
            anterior = Rordenado(i,j);
            T = setdiff(T,indices(i));
        end
        soluciones(5,i) = suma;
    end
    [integralChoquet,indiceIntegralChoquet] = max(soluciones(5,:));
    disp('El valor de la integral de Choquet es: ')
    disp(integralChoquet);
    disp('La alternativa de la integral de Choquet es: ');
    disp(indiceIntegralChoquet);

    medidaFuzzy2 = dictionary;
    medidaFuzzy2(mat2str([])) = 0;
    medidaFuzzy2(mat2str([1])) = 0.1;
    medidaFuzzy2(mat2str([2])) = 0.3;
    medidaFuzzy2(mat2str([3])) = 0.2;
    medidaFuzzy2(mat2str([1,2])) = 0.5;
    medidaFuzzy2(mat2str([1,3])) = 0.4;
    medidaFuzzy2(mat2str([2,3])) = 0.7;
    medidaFuzzy2(mat2str([1,2,3])) = 1;
    [Rordenado, indices] = sort(R,2,'ascend');
    for i = 1:numFilas
        T = [1,2,3];
        suma = 0;
        anterior = 0;
        for j = 1:numColumnas
            suma = suma + (Rordenado(i,j) - anterior) * medidaFuzzy2(mat2str(T));
            anterior = Rordenado(i,j);
            T = setdiff(T,indices(j));
        end
        soluciones(6,i) = suma;
    end
    [integralChoquetMedida,indiceIntegralChoquetMedida] = max(soluciones(6,:));
    disp('El valor de la integral de Choquet es: ')
    disp(integralChoquetMedida);
    disp('La alternativa de la integral de Choquet es: ');
    disp(indiceIntegralChoquetMedida);

    %Problema 2
    R1 = [ 0.45, 0.5, 0.2;
       0.65, 0.65, 0.55;
       0.45, 0.55, 0.55;
       0.75, 0.65, 0.35
    ];
    R2 = [ 0.2,  0.55, 0.45;
       0.15, 0.05, 0.95;
       0.65, 0.45, 0.15;
       0.35, 0.6,  0.2
    ];
    R3 = [ 0.35, 0.35, 0.15;
       0.30, 0.75, 0.85;
       0.55, 0.95, 0.55;
       0.65, 0.60, 0.3
    ];
    w = [0.2 0.1 0.7];
    wReshape = reshape(w, 1, 1, 3); %vector tridimensional (1 fila, 1 columna, 3 capas (1 por peso))
    R = cat(3, R1, R2, R3); %1D: filas 2D: columnas 3D: criterios
    R = sort(R,3,'descend'); %ordenar elementos mayor a menor OWA
    R = sum(R.*wReshape, 3);
    disp('La matriz colectiva es: ');
    disp(R);
    integralesChoquet = zeros(4,1);
    [R, indices] = sort(R,3, 'ascend');
    for i = 1: size(integralesChoquet) %numFilas
        ant = 0;
        T = [1, 2, 3];
        for j = 1:size(integralesChoquet,2) %numColumnas
            integralesChoquet(i) = integralesChoquet(i) + (R(i,j) - ant)*medidaFuzzy(mat2str(T));
            ant = R(i,j);
            T = setdiff(T, indices(j));
        end
    end
    disp('La mejor alternativa de las 4 empresas es: ');
    [mejorIntegralEmpresa, indiceEmpresa] = max(integralesChoquet);
    disp(indiceEmpresa);
    disp('La integral de Choquet de dicha empresa es: ');
    disp(mejorIntegralEmpresa);

    %Problema 3
    mediaGeometrica = geomean(R,2);
    [maxMedia, indiceMaxMediaGeo] = max(mediaGeometrica);
    disp('La alternativa de la media geométrica es: ');
    disp(indiceMaxMediaGeo);

    


    devolver = 0;
end