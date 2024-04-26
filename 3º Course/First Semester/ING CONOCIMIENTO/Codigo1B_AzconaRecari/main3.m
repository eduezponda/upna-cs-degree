function accuracy = main3(XTrain, YTrain, XTest, YTest)
    XTest = double(XTest);
    XTrain = double(XTrain);
    classes = 1:26;
    XTest = XTest/255;
    XTrain = XTrain/255;
    [media, A, nuevaBase] = aprendeBase(XTrain);
    prototipos = creaPrototipos(nuevaBase,A,YTrain);
    accuracy = clasificar(nuevaBase,media,prototipos,XTest,YTest,classes);
    accuracy
end