function [media, A, nuevaBase] = aprendeBase(XTrain)
    [m,N,~,nelem] = size(XTrain);
    XTrain = double(XTrain);
    R = reshape(XTrain,[N*m, nelem]);

    media = mean(R, 2);
    A = R - media;
    numPixeles=(size(XTrain,1)*size(XTrain,2));

    C = A'*A;

    [V,mu]=eig(C); %V son vectores propios y Mu Valores propios
    nuevaBase = zeros(numPixeles,size(V,1)); %quiero cambiar esto
    for i = 1:length(XTrain)
        nuevaBase(:,i) = (1/sqrt(mu(i,i)))*A*V(:,i);
    end
end