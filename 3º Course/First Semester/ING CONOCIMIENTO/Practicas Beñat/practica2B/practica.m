% EJERCICION 1-----------------------------------------------------------
U = 1:0.1:10;
A = 1 ./ (1 + ((U-5).^4));
B = 2.^-U;
C = 1 ./ (1 + 10*((U-2).^2));
figure;
plot(U,A,'sr');
title('Funciones de pertenencia');
hold on;
plot(U,B,'+g')
hold on;
plot(U, C, 'pb');
legend('A','B', 'C');

% N(C)
nc = 1 - C;
figure;
plot(U,nc,'sr');
title('N(C)')

% A U B U C
concat = cat(1,A,B,C);
union = max(concat, [], 1);
% A intersect B intersect C
intersect = min(concat, [], 1);
figure;
plot(U,union,'sr');
hold on;
plot(U, intersect, 'pb');
legend('union', 'intersection')
%--------------------------------------------------------------------------
% EJERCICIO 2--------------------------------------------------------------
U = 1:100
adulto = 1 ./ (1 + ((U-50)/10).^4);
joven = 1 - adulto;
joven(50:100) = 0;
anciano = 1 - adulto;
anciano(1:50) = 0;
figure;
plot(U,adulto,'sr');
title('Adulto y joven')
hold on;
plot(U,joven,'pb')
hold on;
plot(U,anciano,'+g')
legend('Adulto', 'Joven', 'Anciano')

% Muy joven
muyJoven = joven.^2;
figure;
plot(U, muyJoven,'sr');
title('Muy joven');
% Bastante anciano
bastanteAnciano = sqrt(anciano);
figure;
plot(U, bastanteAnciano,'sr');
title('Moderadamente anciano');
% Ligeramente adulto
muyAdulto = adulto.^2;
noMuyAdulto = 1 - muyAdulto;
concat = cat(1,muyAdulto,noMuyAdulto);
ligeramenteAdulto = min(concat,[],1);
figure;
plot(U, ligeramenteAdulto,'sr');
title('Ligeramente adulto');
% -------------------------------------------------------------------------