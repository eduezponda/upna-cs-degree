function porcentajeAciertos = clasificar(nuevaBase, media, prototipos, imagenes, Y, classes)
    imagen = reshape(imagenes, 784, []);
    w = nuevaBase'*(imagen-media);
    %w = nuevaBase'*(double(imagen)-media);
    distancia = zeros(length(Y), length(classes));

    for i =1:length(Y)
        for j =1:length(classes)
             vector = (w(:,i)-prototipos(:,j)).^2;
            distancia(i,j) = sum(vector);
        end
    end
    
    [~,k] = min(distancia, [], 2);
    contador = 0;
    for i =1:length(k)
        if classes(k(i))== Y(i)
            contador = contador + 1;
        end
    end
    porcentajeAciertos = 100*contador/length(Y);
end


%imagen = reshape(imagenes, [], size(imagenes, 4))