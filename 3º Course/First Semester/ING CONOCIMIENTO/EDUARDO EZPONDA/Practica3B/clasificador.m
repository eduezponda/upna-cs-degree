function [acc, MC] = clasificador(XTest, YTest, reglas, conjuntos)
    compatibilidad = zeros(size(XTest, 1), size(reglas, 1));
    asociacion = zeros(size(XTest, 1), size(reglas, 1));
    numeroClases = max(reglas(:, 6));
    MC = zeros(numeroClases, numeroClases);
    acc = 0;   
    numeroEjemplosPorClase = 25;
    for i = 1:size(compatibilidad, 1)
        valorClases = zeros(numeroClases, 1);

        indiceLargoSepalo = find(conjuntos.R_A == XTest(i, 1));
        indiceAnchoSepalo = find(conjuntos.R_B == XTest(i, 2));
        indiceLargoPetalo = find(conjuntos.R_C == XTest(i, 3));
        indiceAnchoPetalo = find(conjuntos.R_D == XTest(i, 4));        
        for j = 1:size(compatibilidad, 2)
            valorLargoSepalo = conjuntos.A(reglas(j, 1), indiceLargoSepalo);
            valorAnchoSepalo = conjuntos.B(reglas(j, 2), indiceAnchoSepalo);
            valorLargoPetalo = conjuntos.C(reglas(j, 3), indiceLargoPetalo);
            valorAnchoPetalo = conjuntos.D(reglas(j, 4), indiceAnchoPetalo);

            compatibilidad(i, j) = valorLargoPetalo * valorAnchoPetalo * valorAnchoSepalo * valorLargoSepalo;
            %compatibilidad(i, j) = max(0,valorLargoPetalo+valorAnchoPetalo-1);
            %compatibilidad(i, j) = max(0,compatibilidad(i, j)+valorAnchoSepalo-1);
            %compatibilidad(i, j) = max(0,compatibilidad(i, j)+valorLargoSepalo-1); 
            asociacion(i, j) = compatibilidad(i, j) * reglas(j, 5);
            %asociacion(i, j) = min(1,compatibilidad(i, j) + reglas(j,4));

            if reglas(j, 6) == 1 
                valorClases(1) = valorClases(1)+asociacion(i, j);

            elseif reglas(j, 6) == 2 
                valorClases(2) = valorClases(2)+asociacion(i, j);

            elseif reglas(j, 6) == 3
                valorClases(3) = valorClases(3)+asociacion(i, j);
            end

            %if reglas(j, 6) == 1 && asociacion(i, j) > valorClases(1)
                %valorClases(1) = asociacion(i, j);

            %elseif reglas(j, 6) == 2 && asociacion(i, j) > valorClases(2)
                %valorClases(2) = asociacion(i, j);

            %elseif reglas(j, 6) == 3 && asociacion(i, j) > valorClases(3)
               % valorClases(3) = asociacion(i, j);

            %end
        end
        valorClases = valorClases/numeroEjemplosPorClase; %esto solo si se
        %hace la media aritmética
        [~, indices] = sort(valorClases, 'descend');        
        if indices(1) == YTest(i)
            acc = acc + 1;
        end
        MC(YTest(i),indices(1)) = MC(YTest(i),indices(1)) + 1;
    end 
    acc = acc / size(XTest, 1);
    disp(['El sistema de clasificación nos da una accuracy de ' num2str(acc) ...
         '(' num2str(acc*100) '%).']);
    disp('La matriz de confusión es:');
    disp(MC);
end
