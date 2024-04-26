function accuracy = main(XTrain, YTrain, XTest, YTest)
    disp('ACCURACY DE LA CLASE [0,5]');
    im_lim = 100;
    train_lim = int32(im_lim*0.8);
    test_lim = int32(im_lim*0.2);
    classes = [0, 5];
    YTrain = grp2idx(YTrain) - 1;
    YTest = grp2idx(YTest) - 1;
    [XTrain, YTrain] = filter_classes(XTrain, YTrain, classes);
    [XTest, YTest] = filter_classes(XTest, YTest, classes);
    XTrain = XTrain(:,:,:,1:train_lim);
    YTrain = YTrain(1:train_lim);
    XTest = XTest(:,:,:,1:test_lim);
    YTest = YTest(1:test_lim);
    
    [YTrain, args_train] = sort(YTrain);
    [YTest, args_test] = sort(YTest);
    XTrain = XTrain(:,:,:,args_train);
    XTest = XTest(:,:,:,args_test);
    
    [media, A, nuevaBase] = aprendeBase(XTrain);
    prototipos = creaPrototipos(nuevaBase, A,YTrain);
    accuracy = clasificar(nuevaBase,media,prototipos, XTest, YTest, classes);
end