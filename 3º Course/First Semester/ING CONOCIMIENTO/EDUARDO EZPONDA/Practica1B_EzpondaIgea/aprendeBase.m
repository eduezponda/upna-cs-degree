function [media, A, nuevaBase] = aprendeBase(X)
    XSize = size(X);
    XSize = reshape(X, XSize(1) * XSize(2), XSize(4));  
    media = mean(XSize, 2);
    A = XSize - media;
    C = A'*A;
    [v,mu] = eig(C);
    nuevaBase = zeros(XSize(2), XSize(4));
    for i = 1:size(v,2)
        nuevaBase(:,i) = (1/sqrt(mu(i,i)))* A * v(:,i);
    end   
end