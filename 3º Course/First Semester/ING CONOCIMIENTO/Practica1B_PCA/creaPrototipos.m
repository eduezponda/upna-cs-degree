function prototipos = creaPrototipos(nuevaBase, A, Y)
    etiquetasUnicas = unique(Y);
    prototipos = zeros(size(A, 2), length(etiquetasUnicas));
    w = nuevaBase'*A;
    for i = 1:length(etiquetasUnicas)
        indiceClase = (Y == etiquetasUnicas(i));
        prototipos(:,i) = mean(w(:, indiceClase), 2);
    end
end