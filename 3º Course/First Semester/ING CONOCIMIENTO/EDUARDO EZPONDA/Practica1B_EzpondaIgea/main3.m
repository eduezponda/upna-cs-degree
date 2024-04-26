function porcentajeAciertos = main3()
    [XTr,YTr,XTe,YTe] = loadDataEMNIST();
    clases = 1:26;
    [media, A, nuevaBase] = aprendeBase(XTr);
    prototipos = creaPrototipos(nuevaBase, A, YTr);
    porcentajeAciertos = clasificar(nuevaBase, media, prototipos, XTe, YTe, clases);
    disp(['Porcentaje de aciertos de las letras del abecedario: ' num2str(porcentajeAciertos)]);
end

