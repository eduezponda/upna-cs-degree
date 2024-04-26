function reglasNoRedundantes = construirReglas(XTrain, YTrain, conjuntos)   
    numeroReglas = size(XTrain,1); %se incluyen las reglas redundantes
    reglas = zeros(numeroReglas, 6);
    for i = 1:numeroReglas
        indiceLargoSepalo = find(conjuntos.R_A == XTrain(i, 1));
        indiceAnchoSepalo = find(conjuntos.R_B == XTrain(i, 2));
        indiceLargoPetalo = find(conjuntos.R_C == XTrain(i, 3));
        indiceAnchoPetalo = find(conjuntos.R_D == XTrain(i, 4));

        valorBajoLargoSepalo = conjuntos.A (1,indiceLargoSepalo);
        valorBajoAnchoSepalo = conjuntos.B (1,indiceAnchoSepalo);
        valorBajoLargoPetalo = conjuntos.C (1,indiceLargoPetalo);
        valorBajoAnchoPetalo = conjuntos.D (1,indiceAnchoPetalo);

        valorMedioLargoSepalo = conjuntos.A (2,indiceLargoSepalo);
        valorMedioAnchoSepalo = conjuntos.B (2,indiceAnchoSepalo);
        valorMedioLargoPetalo = conjuntos.C (2,indiceLargoPetalo);
        valorMedioAnchoPetalo = conjuntos.D (2,indiceAnchoPetalo);

        valorAltoLargoSepalo = conjuntos.A (3,indiceLargoSepalo);
        valorAltoAnchoSepalo = conjuntos.B (3,indiceAnchoSepalo);
        valorAltoLargoPetalo = conjuntos.C (3,indiceLargoPetalo);
        valorAltoAnchoPetalo = conjuntos.D (3,indiceAnchoPetalo);

        largoSepalo = [valorBajoLargoSepalo, valorMedioLargoSepalo, valorAltoLargoSepalo];
        anchoSepalo = [valorBajoAnchoSepalo, valorMedioAnchoSepalo, valorAltoAnchoSepalo]; 
        largoPetalo = [valorBajoLargoPetalo, valorMedioLargoPetalo, valorAltoLargoPetalo];
        anchoPetalo = [valorBajoAnchoPetalo, valorMedioAnchoPetalo, valorAltoAnchoPetalo];

        [valores1,indices1] = sort(largoSepalo,'descend');
        [valores2,indices2] = sort(anchoSepalo,'descend');
        [valores3,indices3] = sort(largoPetalo,'descend');
        [valores4,indices4] = sort(anchoPetalo,'descend');

        reglas(i,1) = indices1(1);
        reglas(i,2) = indices2(1);
        reglas(i,3) = indices3(1);
        reglas(i,4) = indices4(1);
        reglas(i,5) = valores1(1)*valores2(1)*valores3(1)*valores4(1);
        %vectorGradoCerteza = [valores1(1) valores2(2) valores3(1) valores4(1)];
        %reglas(i,5) = min(vectorGradoCerteza);
        reglas(i,6) = YTrain(i);
    end

    %Elimino reglas redundantes
    [~,~,ic] = unique(reglas(:,1:4),'rows');
    reglasNoRedundantes = [];
    for i = 1:max(ic)
        indice = 0; 
        maxGradoCerteza = 0;
        for j = 1:size(ic,1) %se podría poner también size(reglas,1)
            if i == ic(j) && maxGradoCerteza < reglas(j,5)
                maxGradoCerteza = reglas(j,5);
                indice = j;
            end
        end
        reglasNoRedundantes = cat(1, reglasNoRedundantes, reglas(indice, :));
    end

    diccionario = containers.Map({1, 2, 3}, {'BAJO', 'MEDIO', 'ALTO'});
    disp(['El número de reglas es ' num2str(size(reglasNoRedundantes,1)) ' y son las siguientes:']);
    for i = 1:size(reglasNoRedundantes, 1)
        antecedente1 = diccionario(reglasNoRedundantes(i, 1));
        antecedente2 = diccionario(reglasNoRedundantes(i, 2));
        antecedente3 = diccionario(reglasNoRedundantes(i, 3));
        antecedente4 = diccionario(reglasNoRedundantes(i, 4));
        grado_cert = reglasNoRedundantes(i, 5);
        clase = reglasNoRedundantes(i, 6);

        fprintf('IF Largo Sépalo IS %s AND Ancho Sépalo IS %s AND Largo Pétalo IS %s AND Ancho Pétalo IS %s\n', ...
            antecedente1, antecedente2, antecedente3, antecedente4);
        
        fprintf('THEN Clase %d con Certeza = %.2f\n\n', clase, grado_cert);
    end
end