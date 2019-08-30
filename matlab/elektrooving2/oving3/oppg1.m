x = randi(100,1,50);
big = int16.empty;
small = int16.empty;
for i=1:50
    if x(i) > 50
      big=[big x(i)];
    else
      small=[small x(i)];
    end
end
sprintf('big   - antall elementer: %i, middelverdi: %.2f\nsmall - antall elementer: %i, middelverdi: %.2f', size(big,2), sum(big)/size(big,2), size(small,2), sum(small)/size(small,2) )
