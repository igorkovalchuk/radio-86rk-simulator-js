/*
 * Simple animation: circles (looks like fireworks)
 */

var DOUBLE_PI = 3.14159 * 2;

cls();

var attempts = 10;
var j, x0, y0, rMax, r0;

for (j = 1; j <= attempts; j++) {

    x0 = Math.random() * 128;
    y0 = Math.random() * 50;
    rMax = Math.random() * 30 + 20;

    for (r0 = 5; r0 < rMax; r0 += 5) {
        freeze();
        cls();
        ball(x0, y0, r0);
        unfreeze();
        await pause(0.1);
    }

}

cls();


function ball(x0, y0, r0) {
    var delta = 0.01;
    var x, y, r;
    for (angle = 0; angle < DOUBLE_PI; angle += delta) {
        r = r0 * Math.random();
        x = x0 + Math.cos(angle) * r;
        y = y0 + Math.sin(angle) * r;
        plot(x, y, 1);
    }
}
