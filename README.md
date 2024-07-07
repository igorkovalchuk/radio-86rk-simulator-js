
===================

Симулятор ретрокомп'ютера Радіо 86РК

Програмування на JavaScript з додатковими функціями схожими на функції мови Бейсік та псевдографіки ретрокомп'ютера Радіо 86РК.

Radio 86RK retrocomputer simulator (BASIC operators using JavaScript, pseudo-graphics output).

Just for fun ...

Можна просто скопіювати файли на смартфон і запускати, наприклад, за допомогою Opera.

Працює навіть з Firefox для Windows XP.

===================

https://igorkovalchuk.github.io/

===================

https://youtu.be/uPpFo8cquQc - програма "Охота на лис" з журналу "Радио", 1987 рік, переписана на JavaScript (майже 1:1)

===================

Оператори та функції:

* plot(x,y,1) // псевдографіка;
* plot(x,y,0)
* line(x,y)

* cls()

* print(message)
* println(message)
* printtab(x) // аналог print tab(x)

* spc(n)

* result = input(message) // ввести дані, поки що в окремому вікні з виводом в термінал;

* result = await inkey(0) // чекає вводу символа з клавиатурі;
* result = await inkey(1) // не чекає вводу;

(зверніть увагу, що для функцій inkey та pause, використання await обов'язкове, також обов'язковим є використання модифікатора async для function, які містять всередині виклики await)

* cur(x,y)
* result = screen(x,y)

* await pause(0) // чекає поки буде натиснена будь-яка клавіша на клавіатурі;
* await pause(double) // пауза в секундах чи в долях секунди: 1, 10, 0.5, и т.д.

* cos(radians)
* sin(radians)

* n = chr(char)
* char = asc(n)

* poke(addr, n) // ці команди потрібні в деяких программах для виводу на екран (в екранну пам'ять)
* n = peek(addr)

Додаткові ф-ії:

* circle(x, y, r)
* log(message)

* freeze() // зупинка оновлення відображення на екрані (не змінюємо canvas), щоб швидко вивести багато символів в екранну область;
* unfreeze() // відновлення відображення на екрані (оновлюємо canvas);

===================

uk.wikipedia.org/wiki/Радіо_86РК

===================

