function accuracy = main1(XTrain, YTrain, XTest, YTest)
    im_lim = 100;
    train_lim = int32(im_lim*0.8);
    test_lim = int32(im_lim*0.2);
    classes = [1 7;
                2 7; 
                4 9];
    YTrain = grp2idx(YTrain) - 1;
    YTest = grp2idx(YTest) - 1;
    for i=1:3
        XTrain1 = XTrain;
        XTest1 = XTest;
        YTrain1 = YTrain;
        YTest1 = YTest;
    
        [XTrain1, YTrain1] = filter_classes(XTrain1, YTrain1, classes(i, :));
        [XTest1, YTest1] = filter_classes(XTest1, YTest1, classes(i, :));
        XTrain1 = XTrain1(:,:,:,1:train_lim);
        YTrain1 = YTrain1(1:train_lim);
        XTest1 = XTest1(:,:,:,1:test_lim);
        YTest1 = YTest1(1:test_lim);
        
        [YTrain1, args_train] = sort(YTrain1);
        [YTest1, args_test] = sort(YTest1);
        XTrain1 = XTrain1(:,:,:,args_train);
        XTest1 = XTest1(:,:,:,args_test);
    
        imshow(XTrain1(:,:,:,1));
    
        [media, A, nuevaBase] = aprendeBase(XTrain1);
        prototipos = creaPrototipos(nuevaBase, A,YTrain1);
        accuracy = clasificar(nuevaBase,media,prototipos, XTest1, YTest1, classes(i, :));
end