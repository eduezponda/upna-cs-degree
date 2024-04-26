%Problema 1:
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
disp('La solución de la media aritmética es: ')
[~,indiceArit] = max(soluciones(1));
disp(indiceArit);
disp('La solución de la media armónica es: ')
[~,indiceHarm] = max(soluciones(2));
disp(indiceHarm);

ignorancia = 2*min(R,1-R);
w = (1-ignorancia);
denominador = sum(1-ignorancia,2);
for i = 1:numFilas
    w(i,:) = w(i,:)/denominador(i);
end
Rordenado = sort(R,2,'descend');
soluciones(3) = sum(Rordenado.*w,2);
disp('La solución del OWA con función de ignorancia débil es: ')
[~,indiceOWAdebil] = max(soluciones(3));
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
soluciones(4) = sum(Rordenado.*w,2);
disp('La solución del OWA mayor parte de: ')
[~,indiceOWAmayorparte] = max(soluciones(4));
disp(indiceOWAmayorparte);

coefBinomial = nchoosek(4,2);
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
        s = s + (Rordenado(i,j) - anterior) * medidaFuzzy(mat2str(T));
        anterior = Rordenado(i,j);
        T = setdiff(T,indices(i));
    end
end
