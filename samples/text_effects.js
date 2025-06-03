/*
 * Simple text effects
 * 
 * Keys:
 * up, down, left, right
 * 1 - recode "forward", 2 - recode "back", 3 - mix, 4 - down
 */

cls();
cur(0, 24);

var url = "https://raw.githubusercontent.com/openjdk/jdk/jdk8-b120/jdk"
        + "/src/share/sample/scripting/scriptpad"
        + "/src/com/sun/sample/scriptpad/Main.java";

var f = await makeRequest(url)
  .then( (text) => { return text; }  )
  .catch( (error) => { alert("Can not load the file, status = " + error.status); return ""; } );

var sourceData = f.split("\n");

if (sourceData.length === 0) {
    println("Can't find this file: " + location + '/' + fileName)
    stop();
}

var data = new Array();
for (var str of sourceData) {
    data.push(str.replace(/	/g, "    "));
}

const lines = 23;

var refresh = true;
var j, k;
var line;
var start = 0;

while (true) {

    if (refresh) {

        cls();
        cur(0, 24);

        j = 0;
        k = 0;

        do {
            line = data[start + k];
            if (line === undefined) {
                line = "";
            }
            println(line);
            j++;
            if (line.length > 65) {
                j++;
            }
            if (line > 130) {
                j++;
            }
            k++;
        } while (j < lines)

    }

    refresh = true;

    k = await inkey(0);

    if (k === "esc") {
        println("EXIT");
        break;
    }

    if (k == "UP") {
        start--;
    }
    if (k == "DOWN") {
        start++;
    }
    if (k == "RIGHT" || k === " ") {
        start += lines;
    }
    if (k == "LEFT") {
        start -= lines;
    }

    if (k === "1") {
        recode(1);
        refresh = false;
    }
    if (k === "2") {
        recode(-1);
        refresh = false;
    }
    if (k === "3") {
        mix();
        refresh = false;
    }
    if (k === "4") {
        down();
        refresh = false;
    }
}

function recode(value) {
    var x, y, c, n;
    freeze();
    for (x = 0; x < 65; x++) {
        for (y = 24; y >= 0; y--) {
            c = screen(x, y);
            n = asc(c)
            if ((n + value) >= 0 && (n + value) <= 255) {
                n = n + value;
            }
            c = chr(n);
            cur(x, y);
            print(c);
        }
    }
    unfreeze();
}

function mix() {
    var x, y, c, x2, y2, c2;
    freeze();
    for (x = 0; x < 65; x += 3) {
        for (y = 24; y >= 0; y -= 3) {
            c = screen(x, y);
            x2 = x + (Math.random() - 0.5) * 1.1;
            y2 = y + (Math.random() - 0.5) * 1.1;
            c2 = screen(x2, y2);
            cur(x, y);
            print(c2);
            cur(x2, y2);
            print(c);
        }
    }
    unfreeze();
}

function down() {
    var x, y, c, x2, y2, c2;
    freeze();
    for (x = 0; x < 65; x += 1) {
        for (y = 0; y <= 24; y += 1) {
            c = screen(x, y);
            x2 = x;
            y2 = y + 1;
            c2 = screen(x2, y2);
            if (c === " " || asc(c) === 0) {
                cur(x2, y2);
                print(c);
                cur(x, y);
                print(c2);
            }
        }
    }
    unfreeze();
}

function makeRequest (url) {

  return new Promise(function (resolve, reject) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url);

    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 300) {
        resolve(xhr.response);
      }
      else {
        reject({
          status: xhr.status,
          statusText: ""
        });
      }
    };

    xhr.onerror = function () {
      reject({
        status: xhr.status,
        statusText: xhr.statusText
      });
    };

    xhr.send();
  });
}
