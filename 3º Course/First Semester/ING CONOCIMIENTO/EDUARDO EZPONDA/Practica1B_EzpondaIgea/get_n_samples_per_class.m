function [X_subset, Y_subset] = get_n_samples_per_class(X, Y, samples_per_class)
    % Sort X and Y according to the labels in Y (in an increasing order)
    % Useful for sampling the values more easily.
    [Y, order_train] = sort(Y);
    X = X(:, :, :, order_train);
    
    all_classes = unique(Y); % Get all possible labels for our dataset
    X_size = size(X);

    X_subset = zeros(X_size(1), X_size(2), X_size(3), samples_per_class * length(all_classes));
    %Y_subset = zeros(samples_per_class * length(all_classes), 1);  % The
    %obtained array will be of type "double" instead of "categorical".
    Y_subset = categorical.empty(0, 1);  % The only way to initialize an array of type "categorical"
    
    % Introduce samples_per_class samples from each distinct class in
    % X_subset, as well as their respective labels:
    current_sample = 1;
    for class_i = 1:length(all_classes)
        current_class = all_classes(class_i);
        subset_idx = 1;
        while (current_sample < length(Y)) && (subset_idx < samples_per_class+1) && (Y(current_sample) == current_class)
            X_subset(:, :, :, (class_i-1) * samples_per_class + subset_idx) = X(:, :, :, current_sample);
            Y_subset((class_i-1) * samples_per_class + subset_idx) = Y(current_sample);
            subset_idx = subset_idx + 1;
            current_sample = current_sample + 1;
        end
        while (current_sample < length(Y)) && (Y(current_sample) == current_class)
            current_sample = current_sample + 1;
        end
    end
end