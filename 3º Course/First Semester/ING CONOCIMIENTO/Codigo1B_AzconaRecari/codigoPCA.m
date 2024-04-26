trainImagesFile = 'train-images.idx3-ubyte';
trainLabelsFile = 'train-labels.idx1-ubyte';
testImagesFile = 't10k-images.idx3-ubyte';
testLabelsFile = 't10k-labels.idx1-ubyte';

XTrain = processImagesMNIST(trainImagesFile); %Esta funcion ya os la doy, la podeis encontrar como parte de uno de los ejemplos de MATLAB disponibles online.

YTrain = processLabelsMNIST(trainLabelsFile);
size(XTrain) %28 x 28 (tamano imagen) x 1 (solo hay escala de grises, no pixeles rgb) x 60000 (numero de ejemplos)
XTrain = XTrain(:,:,:, mod(1:60000, 60000/10) <50);
YTrain = YTrain(mod(1:60000, 60000/10) <50); %Cogemos solo una submuestra

XTest = processImagesMNIST(testImagesFile);
YTest = processLabelsMNIST(testLabelsFile);




%ETAPA 1
%[m,N,dim,nelem] = size(XTrain);
%R = reshape(XTrain,[N*m, nelem]);
%media = mean(R, 2);
%A = R-media;
%C = A'*A;
% [V,mu]=eig(C); %V son vectores propios y Mu Valores propios
%nuevaBase = zeros(784,80);
%for i = 1:80
   % nuevaBase(:,i) = (1/sqrt(mu(i,i)))*A*V(:,i);
%end
%nuevaBase;


%Etapa 2
%prototipos = creaPrototipos(nuevaBase, A, YTrain);


%Etapa 3
%porcentajeAciertos = clasificar(nuevaBase, media, prototipos, XTrain, YTrain, classes);
%porcentajeAciertos

%ACCURACY DE LA CLASE [0,5];
accuracy = main(XTrain, YTrain, XTest, YTest);

%ACCURACY DE LA CLASE [1,7; 2,7; 4,9];
accuracy = main1(XTrain, YTrain, XTest, YTest);

%ACCURACY CON MUESTRA DE 100 Y 200 
accuracy = main2(XTrain, YTrain, XTest, YTest, 100);
accuracy = main2(XTrain, YTrain, XTest, YTest, 200);

%Main 3
clear
load('emnist-letters.mat')
N_EX_TRAIN = 1000;
XTr = reshape(dataset.train.images.', 28, 28, 1, []);
YTr = dataset.train.labels;
XTr = XTr(:,:,:,1:N_EX_TRAIN);
YTr = YTr(1:N_EX_TRAIN);
XTe = reshape(dataset.test.images.', 28, 28, 1, []);
YTe = dataset.test.labels;
XTe = XTe(:,:,:,1:100:20800);
YTe = YTe(1:100:20800);

%ACCURACY CON EMINST
accuracy = main3(XTr, YTr, XTe, YTe);










