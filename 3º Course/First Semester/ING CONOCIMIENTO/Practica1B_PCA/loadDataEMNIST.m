function [XTr,YTr,XTe,YTe] = loadDataEMNIST()
    N_EX_TRAIN = 1000;
    dataset = load('emnist-letters.mat');
    XTr = reshape(dataset.dataset.train.images.', 28, 28, 1, []);
    YTr = dataset.dataset.train.labels;
    XTr = XTr(:,:,:,1:N_EX_TRAIN);
    YTr = YTr(1:N_EX_TRAIN);

    XTe = reshape(dataset.dataset.test.images.', 28, 28, 1, []);
    YTe = dataset.dataset.test.labels;
    XTe = XTe(:,:,:,1:100:20800);
    YTe = YTe(1:100:20800);
    XTr = cast(XTr, 'double');
    YTr = cast(YTr, 'double');
    XTe = cast(XTe, 'double');
    YTe = cast(YTe, 'double');
end

