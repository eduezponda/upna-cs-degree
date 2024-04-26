function porcentajeAciertos = main1()
    %DATOS
    clases = [1, 7; 2, 7; 4, 9];
    samples_per_class_train = 40; % 80 imágenes/2 clases = 40 imágenes/clase
    samples_per_class_test = 10;  % 20 imágenes/20 clases = 10 imágenes/clase
    porcentajeAciertos = zeros(1,3);
    for i = 1:3
        %CARGAR DATOS
        [XTrain_subset, YTrain_subset, XTest_subset, YTest_subset] = loadData(clases(i,:), samples_per_class_train,samples_per_class_test);
        [media, A, nuevaBase] = aprendeBase(XTrain_subset);
        prototipos = creaPrototipos(nuevaBase, A, YTrain_subset);
        porcentajeAciertos(i) = clasificar(nuevaBase, media, prototipos, XTest_subset, YTest_subset, clases(i,:));
        disp(['Porcentaje de aciertos en la iteración ' num2str(i) ': ' num2str(porcentajeAciertos(i))]);
    end
end