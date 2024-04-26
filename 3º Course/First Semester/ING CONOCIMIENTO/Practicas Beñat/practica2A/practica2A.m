%EJERCICIO 1---------------------------------------------------------------
R = [ 0.315, 0.161, 0.364;
      0.685, 0.041, 0.062;
      0.840, 0.959, 0.387;
      0.636, 0.938, 0.613
]
disp('mediaow')
% La media aritmetica
column = mean(R,2);
[maximum, mejor] = max(column);
disp('Alternativa: ')
disp(mejor)
disp('')

% La media armonica
column = size(R,2) ./ (sum(1./R,2));
[maximum, mejor] = max(column);
disp('Alternativa: ')
disp(mejor)
disp('')

% OWA entropia
gx = 2 * min(R, 1 - R);
den = sum((1-gx),2);
pesos = (1-gx) ./ den;
pesos = sort(pesos,2, 'descend');
column = sum(R.*pesos, 2);
[maximum, mejor] = max(column);
disp('Alternativa: ')
disp(mejor)
disp('')

% OWA mayor parte de
pesos = [10/15,4/15,1/15];
column = sum(R.*pesos, 2);
[maximum, mejor] = max(column);
disp('Alternativa: ')
disp(mejor)
disp('')

% Integral de choquet
a = nchoosek(4,2)
n = 3;
medida1 = dictionary;
medida1(mat2str([1,2,3])) = 1;
medida1(mat2str([])) = 0;
v = 1:3;
val2 = (2/n)^2;
val1=(1/n)^2;
C = nchoosek(v,2);
for i = 1:3
    medida1(mat2str(C(i,:))) = val2;
    medida1(mat2str([i])) = val1;
end

medida2 = dictionary;
medida2(mat2str([1,2,3])) = 1;
medida2(mat2str([])) = 0;
v = 1:3;
val2 = (2/n)^0.5;
val1=(1/n)^0.5;
C = nchoosek(v,2);
for i = 1:3
    medida2(mat2str(C(i,:))) = val2;
    medida2(mat2str([i])) = val1;
end
integrals = zeros(4,1);
for j = 1:4
    integrals(j) = integralChoquet(R(j,:),medida1);
end
[maximum, mejor] = max(integrals);
disp('Alternativa: ')
disp(mejor)
disp('')


integrals = zeros(4,1);
for j = 1:4
    integrals(j) = integralChoquet(R(j,:),medida2);
end
[maximum, mejor] = max(integrals);
disp('Alternativa: ')
disp(mejor)
disp('')

medida3 = dictionary
medida3(mat2str([]))=0;
medida3(mat2str([1,2,3]))=1;
medida3(mat2str([1]))=0.1;
medida3(mat2str([2]))=0.3;
medida3(mat2str([3]))=0.2;
medida3(mat2str([1,2]))=0.5;
medida3(mat2str([1,3]))=0.4;
medida3(mat2str([2,3]))=0.7;

integrals = zeros(4,1);
for j = 1:4
    integrals(j) = integralChoquet(R(j,:),medida3);
end
[maximum, mejor] = max(integrals);
disp('Alternativa: ')
disp(mejor)
disp('')
integrals
%--------------------------------------------------------------------------
% EJERCICIO 2: PROBLEMA DE INVERSION---------------------------------------
R1 = [ 0.45, 0.5, 0.2;
       0.65, 0.65, 0.55;
       0.45, 0.55, 0.55;
       0.75, 0.65, 0.35
    ];
R2 = [ 0.2,  0.55, 0.45;
       0.15, 0.05, 0.95;
       0.65, 0.45, 0.15;
       0.35, 0.6,  0.2
    ];
R3 = [ 0.35, 0.35, 0.15;
       0.30, 0.75, 0.85;
       0.55, 0.95, 0.55;
       0.65, 0.60, 0.3
    ];
%Construimos la matriz colectiva
R = sort(cat(3,R1,R2,R3),3,'descend');
weights_reshaped = reshape([0.2,0.1,0.7],1,1,3);
R = sum(R.*weights_reshaped, 3);
R
integrals = zeros(4,1);
for j = 1:4
    integrals(j) = integralChoquet(R(j,:),medida1);
end
[maximum, mejor] = max(integrals);
disp('Alternativa: ')
disp(mejor)
disp('')

integrals
integrals = zeros(4,1);
for j = 1:4
    integrals(j) = integralChoquet(R(j,:),medida2);
end
[maximum, mejor] = max(integrals);
disp('Alternativa: ')
disp(mejor)
disp('')
integrals

%--------------------------------------------------------------------------
% EJERCICIO 3: TOMA DE DECISION II-----------------------------------------
R = [ 0.681, 0.732, 0.513, 0.446, 0.295;
      0.320, 0.596, 0.555, 0.605, 0.284;
      0.355, 0.367, 0.403, 0.556, 0.503;
      0.554, 0.398, 0.822, 0.377, 0.528;
      0.116, 0.687, 0.738, 0.429, 0.620;
];

% Media geometrica
column = geomean(R,2)
column2=nthroot(prod(R,2),size(R,2))
[maximum, mejor] = max(column);
disp('Alternativa: ')
disp(mejor)
disp('')
% Operador OWA con vector de pesos w=(0.75,0,0,...,0,0.25)
weights = zeros(1,size(R,2));
weights(1) = 0.75;
weights(end) = 0.25;
weights
[sorted,indices] = sort(R,2,'descend');
sorted
column = sum(sorted.*weights, 2);
[maximum, mejor] = max(column);
disp('Alternativa: ')
disp(mejor)
disp('')