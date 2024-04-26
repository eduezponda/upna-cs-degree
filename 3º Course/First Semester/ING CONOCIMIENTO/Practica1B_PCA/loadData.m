function [XTrain_subset, YTrain_subset, XTest_subset, YTest_subset] = loadData(classes, samples_per_class_train,samples_per_class_test)
    %%% 1.- Load Train data:
    
    % Process images:
    
    trainImagesFile = 'train-images.idx3-ubyte';
    trainLabelsFile = 'train-labels.idx1-ubyte';
    
    XTrain = processImagesMNIST(trainImagesFile); %Esta funcion ya os la doy, la podeis encontrar como parte de uno de los ejemplos de MATLAB disponibles online.
    YTrain = processLabelsMNIST(trainLabelsFile);
    
    %%% 2.- Load Test data:
    
    testImagesFile = 't10k-images.idx3-ubyte';
    testLabelsFile = 't10k-labels.idx1-ubyte';
    
    XTest = processImagesMNIST(testImagesFile);
    YTest = processLabelsMNIST(testLabelsFile);
    
    %%% 3.- NEW: Get an equal partition of samples with an equal name of
    %%% samples per class:
    [XTrain_subset, YTrain_subset] = get_n_samples_per_class(XTrain, YTrain, samples_per_class_train);
    [XTest_subset, YTest_subset] = get_n_samples_per_class(XTest, YTest, samples_per_class_test);
    
    % Convert "categorical" values in numerical values:
    YTrain_subset = grp2idx(YTrain_subset) - 1;
    YTest_subset = grp2idx(YTest_subset) - 1;
    
    % Filter the desired classes:
    [XTrain_subset, YTrain_subset] = filter_classes(XTrain_subset, YTrain_subset, classes);
    [XTest_subset, YTest_subset] = filter_classes(XTest_subset, YTest_subset, classes);
    
    imshow(XTrain_subset(:, :, :, 1));
end


