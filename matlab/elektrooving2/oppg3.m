clear all
datafil = load('sinusdata');
y1 = datafil(:,2);
y2 = datafil(:,3);
t = datafil(:,1);
plot(t,y1);
hold on
plot(t,y2);
title('Oppgave 3');
grid on
xlabel('Tid');
ylabel('Verdi');
legend('En kurve', 'En annen kurve');
