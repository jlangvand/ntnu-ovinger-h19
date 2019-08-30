e = 1; z = 1;
res = 0; i = 0;
while e > 1e-4
  res = res + (z^i / factorial(i));
  i = i + 1;
  e = exp(1)-res;
end
fprintf('Beregnet verdi: %.6f\nAvvik: %.2g\nAntall iterasjoner: %d\n', res, e, i);
