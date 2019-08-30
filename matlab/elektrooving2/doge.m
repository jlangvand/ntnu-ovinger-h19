clear all;

% Legger på vignett, ramme og andre kunstneriske effekter på et bilde.
%
% Fungerer kun på kvadratiske bilder per nå..

dim_x=800; % Dimensjon på bildet
dim_y=dim_x;

x1 = -pi:pi/((dim_x/4)-1):0;
x3 = 0:pi/((dim_x/4)-1):pi;

y1 = cos(x1);
y2 = ones(1,400);
y3 = cos(x3);

y = [y1 y2 y3];

w1d = (y./4)+3/4;
w2d = w1d'*w1d;

w2d_rgb = w2d;
w2d_rgb(:,:,2) = w2d;
w2d_rgb(:,:,3) = w2d;

ramme = ones(dim_y, dim_x);

for i = 1 : dim_y
  for j = 1 : dim_x
    if i<10 || i>dim_y-10 || j<10 || j>dim_x-10
      ramme(i,j)=0;
    end
  end
end

img_orig = double(imread('puppy.jpg'));
img_weighted = img_orig .* w2d_rgb .* ramme;
img_weighted2(:,:,2) = img_weighted(:,:,3);
img_weighted2(:,:,3) = img_weighted(:,:,2);
img_weighted2(:,:,1) = img_weighted(:,:,1).*1.1;
imshow(uint8(img_weighted2));
%imshow(img_weighted);
