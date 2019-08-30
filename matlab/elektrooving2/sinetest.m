N=1000;
x=0:((2*pi)/N):2*pi;
y=0;
l=zeros(N);
for i=1:N
  e = 1;
  k = 1;
  while e > 1e-50
    fortegn = (-1)^(k-1);
    teller = x(i)^(2*k-1);
    nevner = prod(1:2*k-1);
    ledd = fortegn*teller/nevner;
    e = abs(ledd);
    y = y + ledd;
    k = k + 1;
  end
  l(i)=k;
end
