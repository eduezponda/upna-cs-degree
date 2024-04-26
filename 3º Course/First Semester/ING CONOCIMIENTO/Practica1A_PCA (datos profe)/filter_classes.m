function [X_f, Y_f]= filter_classes(X, Y, classes)
    in = zeros(length(Y), 1);
    for c =1:length(classes)
        cl = classes(c);
        in = max(in, Y==cl);
    end
    
    X_f = X(:,:,:,in==1);
    Y_f = Y(in==1);
end