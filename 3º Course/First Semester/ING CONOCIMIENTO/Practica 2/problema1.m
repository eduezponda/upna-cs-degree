function [mediaArt, hm, w1, w2, integral1, integral2] = problema1()
    R = [0 0.315 0.161 0.364; 0.685 0 0.041 0.062;
        0.840 0.959 0 0.387; 0.636 0.938 0.613 0];
    %MEDIA ARITMÉTICA
    mediaArt = mean(R,2);
    %MEDIA ARMÓNICA
    mediaArm = harmmean(R,2);
    hm = max(mediaArm);
    %OWA FUNCIÓN IGNORANCIA DÉBIL
    [n, ~] = size(R);
    w1 = zeros(n,n);
    suma = 0;
    for i =1:n
        suma = suma + (1 - ignorancia(i));
    end
    for i=1:n
        w1(i,:) = (1 - ignorancia(i)) / suma;
    end
    %OWA "LA MAYOR PARTE DE"
    w2 = zeros(n,n);
    %INTEGRAL CHOQUET 1
    v1 = (det(R)/n)^2;
    v2 = (det(R)/n)^0.5;
    integral1 = choquet_integral(R, [v1, v2]);

    %INTEGRAL CHOQUET 2
    integral2 = choquet_integral(R,[0, 0.1, 0.3, 0.2, 0.5, 0.4, 0.7, 1]);
end
function gx = ignorancia(x)
    gx = 2*min(x,1-x);
end
function integral = choquet_integral(R, v)
    n = size(R, 1);
    integral = zeros(n, 1);   
    for i = 1:n
        indices = nchoosek(1:n, i);
        for j = 1:size(indices, 1)
            integral(i) = integral(i) + prod(v(indices(j, :)));
        end
    end
end