import oscP5.*;
import netP5.*;
OscP5 oscP5;

// pulse value oscillates between 0 and 1
float tpulseValue = 0;

void setup () {
  size(750, 600);
  smooth();

  // create a new instance of oscP5 using a multicast socket
  // the ethernet or wifi IP has to be the same as the one written on the server sketch
  oscP5 = new OscP5(this, "224.0.0.1", 7777);
  background(255);
}


int x = 0;
float v = 0, pv = 0;
void draw () {
  // clear screen when line go beyond screen
  if (++x > width) {
    x = 0;
    background(255);
  }

  // ease value
  v = (tpulseValue - v) * 0.05;

  // draw line
  line(x, height / 2 - pv * height / 2, x, height / 2 - v * height / 2);

  // store current value as previous value for the next frame
  pv = v;
}


// handle message from server
void oscEvent(OscMessage message) {
  if (message.checkAddrPattern("/vernier")) {
    if (message.checkTypetag("f")) {
      tpulseValue = message.get(0).floatValue();
      return;
    }
  }
}
