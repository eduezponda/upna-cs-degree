%% Cargar datos
load('iris.mat')
dataset.X = round(iris.features,1);
dataset.Y = (1:3)*[strcmp(iris.label,'Iris-setosa');
strcmp(iris.label,'Iris-versicolor');
strcmp(iris.label, 'Iris-virginica')];
%% Particiones de datos
partition.indexes.train = [1:25 51:75 101:125];
partition.X.train = dataset.X(partition.indexes.train,:);
partition.Y.train = dataset.Y(partition.indexes.train);
partition.indexes.test = [26:50 76:100 126:150];
partition.X.test = dataset.X(partition.indexes.test,:);
partition.Y.test = dataset.Y(partition.indexes.test);
%% Construir conjuntos difusos
conjuntos = construirConjuntos(dataset.X);
%% Construir Reglas
reglas = construirReglas(partition.X.train,partition.Y.train, conjuntos);
%% Clasificar
[acc, MC] = clasificador(partition.X.test,partition.Y.test, reglas, conjuntos);