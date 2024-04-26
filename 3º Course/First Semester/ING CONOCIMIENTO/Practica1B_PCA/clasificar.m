function porcentajeAciertos = clasificar(nuevaBase, media, prototipos, imagenes, Y, clases)
    numPixeles = 28;
    imagen = reshape(imagenes, numPixeles^2, []);
    w = nuevaBase'*(imagen-media);
    distancia = zeros(length(Y), length(clases));

    for i =1:length(Y)
        for j =1:length(clases)
             vector = (w(:,i)-prototipos(:,j)).^2;
            distancia(i,j) = sum(vector);
        end
    end
    
    [~,k] = min(distancia, [], 2);

    contador = 0;
    for i =1:length(k)
        contador = contador + (clases(k(i)) == Y(i));
    end
    
    porcentajeAciertos = 100 * (contador/length(Y));
end