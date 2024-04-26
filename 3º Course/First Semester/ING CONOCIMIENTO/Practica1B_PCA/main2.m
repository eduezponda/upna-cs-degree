function porcentajeAciertos = main2()
    %DATOS
    clases = 0:9;
    samples_per_class_train = 8; % 80 imágenes/10 clases = 8 imágenes/clase
    samples_per_class_test = 2;  % 20 imágenes/10 clases = 2 imágenes/clase
    porcentajeAciertos = zeros(1,2);

    %CARGAR DATOS primer caso
    [XTrain_subset, YTrain_subset, XTest_subset, YTest_subset] = loadData(clases, samples_per_class_train,samples_per_class_test);
    [media, A, nuevaBase] = aprendeBase(XTrain_subset);
    prototipos = creaPrototipos(nuevaBase, A, YTrain_subset);
    porcentajeAciertos(1) = clasificar(nuevaBase, media, prototipos, XTest_subset, YTest_subset, clases);
    disp(['Porcentaje de aciertos con distribución 80:20: ' num2str(porcentajeAciertos(1))]);

    %CARGAR DATOS segundo caso
    samples_per_class_test = 16; % 160 imágenes/10 clases = 16 imágenes/clase
    samples_per_class_train = 4; % 40 imágenes/10 clases = 4 imágenes/clase
    [XTrain_subset, YTrain_subset, XTest_subset, YTest_subset] = loadData(clases, samples_per_class_train,samples_per_class_test);
    [media, A, nuevaBase] = aprendeBase(XTrain_subset);
    prototipos = creaPrototipos(nuevaBase, A, YTrain_subset);
    porcentajeAciertos(2) = clasificar(nuevaBase, media, prototipos, XTest_subset, YTest_subset, clases);
    disp(['Porcentaje de aciertos con distribución 160:40: ' num2str(porcentajeAciertos(2))]);
end