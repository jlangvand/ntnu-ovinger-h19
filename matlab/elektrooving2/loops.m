clear all

tabell = zeros(10);

for x=1:10
  for y=1:10
    tabell(x,y)=x*y;
  end
end

for n=1:10
  tabell2(:,n) = n;n;10*n;
end

% Definisjon av sinusfunksjon
% Høyere N for større nøyaktighet
% x = pi/4;
% N = 10;
% y = 0;
% for k=1:N
%   fortegn = (-1)^(k-1);
%   teller = x^(2*k-1);
%   nevner = prod(1:2*k-1);
%   ledd = fortegn*teller/nevner;
%   y = y + ledd;
% end

x = pi/4;
y = 0;
e = 1;
k = 1;
while e > 0.000000001
  fortegn = (-1)^(k-1);
  teller = x^(2*k-1);
  nevner = prod(1:2*k-1);
  ledd = fortegn*teller/nevner;
  e = abs(ledd);
  y = y + ledd;
  k = k + 1;
end
