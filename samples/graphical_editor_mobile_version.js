// A simple graphical editor for Radio-86RK simulator.

// Mobile version: on-screen keyboard differs today from the normal keyboard;
// disable double-click zoom.

// ABC K M UVW - move the pen
// L - pen down/up mode

// 1 - set pixel, 2 - unset pixel
// 4 - input or select a char and press ENTER, use LEFT/RIGHT arrows to select a char
// 5 - print the previously selected char again

// 0 - save to the binary file, that can be opened in rk86.ru emulator
// To move/transfer this file/picture to the screen area, use the following console command:
// T 0, 92F, 76D0

// cls();

// disable double-click zoom
// https://gist.github.com/andrewvaughan/8f6f892bdbc7d5d973879ed909a4aa1e
document.querySelectorAll("*").forEach(e => {
  e.style["touch-action"] = "manipulation";
});

var max_x = 127;
var max_y = 49;

var x = round(max_x / 2);
var y = round(max_y / 2);

var prev_x = x;
var prev_y = y;

var current = "";

var k = "";

var mode = 0;

var previous_ch = " ";

while(k != "\n") {
  var k = await inkey(0);
  
  if (k == "B") {
    up();
  }
  else if (k == "V") {
    down();
  }
  else if (k == "K") {
    left();
  }
  else if (k == "M") {
    right();
  }
  else if (k == "A") {
    up();
    left();
  }
  else if (k == "C") {
    up();
    right();
  }
  else if (k == "U") {
    down();
    left();
  }
  else if (k == "W") {
    down();
    right();
  }
  else if (k == "1") {
    plot(x, y, 1);
  }
  else if (k == "2") {
    plot(x, y, 0);
  }
  else if (k == "L") {
    mode = 1 - mode;
  }
  else if (k == "4") {
    await enter_char();
    continue;
  }
  else if (k == "5") {
    cur(to_text_coord(x), to_text_coord(y));
    print(previous_ch);
    continue;
  }
  else if (k == "0") {
    save_to_file();
    continue;
  }
  else if (k == "9") {
    // load_from_file();
    continue;
  }
  
  if (mode == 1) {
    plot(x, y, 1);
  }
  
  current = screen(to_text_coord(x), to_text_coord(y));
  plot(x, y, 1);
  await pause(0.2);
  plot(x, y, 0);
  await pause(0.1);
  
  cur(to_text_coord(x), to_text_coord(y));
  print(current);
  
  prev_x = x;
  prev_y = y;
}

function up() {
  if (y < max_y) {
    y = y + 1;
  }
}

function down() {
    if (y > 0) {
      y = y - 1;
    }
}

function left() {
    if (x > 0) {
      x = x - 1;
    }
}

function right() {
    if (x < max_x) {
      x = x + 1;
    }
}

function round(value) {
  return Math.round(value);
}

function to_text_coord(value) {
  return Math.floor(value / 2); 
}

async function enter_char() {
  var current_text = screen(to_text_coord(x), to_text_coord(y));
  cur(to_text_coord(x), to_text_coord(y));
  print("*");
  await pause(0.2);
  
  var ch = chr(0);
  
  while(k != "\n") {
  
    var k = await inkey(0);
    cur(to_text_coord(x), to_text_coord(y));
    if (k == "LEFT" && asc(ch) > 0) {
      ch = chr(asc(ch) - 1);
      print(ch);
    }
    else if (k == "RIGHT" && asc(ch) < 127) {
      ch = chr(asc(ch) + 1);
      print(ch);
    }
    else if (asc(k) >= 32 && asc(k) <= 127 && k.length == 1) {
      ch = k;
      print(ch);
    }
    
  }
  
  if (ch == chr(0)) {
    print(current_text);
  }
  else {
    previous_ch = ch;
  }
}

function save_to_file() {

  var begin = 30416;
  var end = 32767;

  var a = new Uint8Array(end - begin + 1);

  var b = 0;
  for(i = begin; i <= end; i++) {
    b = peek(i);
    a[i - begin] = b;
  }

  var blob = new Blob([a.buffer], {type: "application/octet-stream"});

  var hiddenElement = document.createElement('a');
  const url = window.URL.createObjectURL(blob);
  hiddenElement.href = url;
  hiddenElement.target = '_blank';
  hiddenElement.download = 'test.bin';
  
  hiddenElement.click();
  hiddenElement.remove();

  setTimeout(function() {
    return window.URL.revokeObjectURL(url);
  }, 1000);

}
