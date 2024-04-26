function integral = integralChoquet(fila, medida)
    [sorted, indices] = sort(fila, 'ascend');
    integral = 0;
    set = [1,2,3];
    xant = 0;
    for i = 1:3
       integral = integral + (sorted(i) - xant).*medida(mat2str(set));
       set = setdiff(set, indices(i));
       xant = sorted(i);
    end
end
