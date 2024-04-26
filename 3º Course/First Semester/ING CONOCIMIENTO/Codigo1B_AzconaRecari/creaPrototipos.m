function prototipos = creaPrototipos(nuevaBase, A, Y)
    ims_per_label_local = unique(Y);
    prototipos = zeros(size(A, 2), length(ims_per_label_local));
    w = nuevaBase'*A;
    for i = 1:length(ims_per_label_local)
        clas_index = (Y == ims_per_label_local(i));
        prototipos(:,i) = mean(w(:, clas_index), 2);
    end
    
end