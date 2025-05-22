/*
 * Simple animation: circles
 */

cls();

var DOUBLE_PI = 3.14159 * 2;

var attempts = 10;

var j, x0, y0, r0, rMax;

for (j = 1; j <= attempts; j++) {

    x0 = Math.random() * 150 + 5;
    y0 = Math.random() * 50 + 5;
    rMax = Math.random() * 30 + 20;

    for (r0 = 10; r0 < rMax; r0 += 5) {
        freeze();
        cls();
        circle1(x0, y0, r0);
        unfreeze();
        await pause(0.1);
    }

}

cls();

function circle1(x0, y0, r0) {
    var delta = 0.01;
    var angle, x, y;
    for (angle = 0; angle < DOUBLE_PI; angle += delta) {
        x = x0 + Math.cos(angle) * r0;
        y = y0 + Math.sin(angle) * r0;
        plot(x, y, 1);
    }
}
