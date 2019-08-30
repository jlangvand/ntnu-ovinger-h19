clear all

t = 0:0.01:2;

y1 = 2*sin(2*pi*t);
y2 = 1.5*sin(2*pi*t+(pi/4));

plot(t, y1);
hold on
plot(t, y2);
grid on
legend('y1', 'y2');
title('Oppgave 2');
xlabel('Tid');
ylabel('Verdi');
