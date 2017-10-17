import processing.serial.*;
import oscP5.*;
import netP5.*;

OscP5 oscP5;
Serial arduinoPort;
float value = 0;

void setup() {
  String[] serials = Serial.list();
  printArray(serials);
  try {
    arduinoPort = new Serial(this, serials[1], 115200);
    arduinoPort.clear();
    arduinoPort.bufferUntil(byte('\n'));
  } catch (ArrayIndexOutOfBoundsException e) {
    println(e);
    exit();
  }

  // create a new instance of oscP5 using a multicast socket
  oscP5 = new OscP5(this, "224.0.0.1", 7777);
}

void draw() {
  background(255);
  noStroke();
  fill(0);
  rect(0, value * height, width, height);
}

void serialEvent(Serial p) {
  try {
    String data = p.readStringUntil('\n').replaceAll("[^0-9]+", "");
    if (data != null && data.length() > 0) {
      int raw = -1;
      raw = Integer.parseInt(data);
      if (raw > 0) {
        value = norm(raw, 0, 1024);

        OscMessage message = new OscMessage("/vernier");
        message.add(value);
        oscP5.send(message);
      }
    }
  } catch (Exception e) {
    println(e);
  }
}
