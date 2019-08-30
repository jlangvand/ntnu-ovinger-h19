n = 0:6;
y = factorial(n);
subplot(2,1,1);
stem(n,y);
title("n!");
z = 1;
res = 0;
iterations = 7;
for i=0:iterations-1
  res = res + (z^i / factorial(i));
end
fprintf('Feil 1 (e^1): %.4f\n', exp(z)-res);
z = 2;
res = zeros(1, iterations);
for i=1:iterations
  %j=i-1.0;
  if i == 1
    res(i) = z^(i-1.0) / factorial(i-1.0);
  else
    res(i) = res(i-1) + (z^(i-1.0) / factorial(i-1.0));
  end
end
subplot(2,1,2);
plot(n, res);
title("Tilnærmet verdi for e^2");
xlabel("Iterasjoner");
fprintf('Feil 2 (e^2): %.4f\n', exp(z)-res(i));
