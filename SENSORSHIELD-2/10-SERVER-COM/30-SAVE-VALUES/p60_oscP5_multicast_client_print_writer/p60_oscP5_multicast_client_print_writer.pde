/* CLIENT
 * oscP5multicast by andreas schlegel
 * example shows how to send osc via a multicast socket.
 * what is a multicast? http://en.wikipedia.org/wiki/Multicast
 * ip multicast ranges and uses:
 * 224.0.0.0 - 224.0.0.255 Reserved for special well-known multicast addresses.
 * 224.0.1.0 - 238.255.255.255 Globally-scoped (Internet-wide) multicast addresses.
 * 239.0.0.0 - 239.255.255.255 Administratively-scoped (local) multicast addresses.
 * oscP5 website at http://www.sojamo.de/oscP5
 */


//////////  PDF  //////////  

import processing.pdf.*;
boolean record;


////// MULTICAST - OSC //////

import oscP5.*;
import netP5.*;
OscP5 oscP5;


//////////  VALUES FROM SERVER  //////////   

float lightValue; // LDR light
float vibrationValue; // piezo vibration
float potValue; // soft pot
float tempValue; // temp sensor
float orientation; // accel orientation
float xValue; // accel X
float yValue; // accel Y
float zValue; // accel Z


//////////  VARIABLES OUTPUT  //////////

float mapLightValue;
float mapVibrationValue;
float mapPotValue;
float mapTempValue;
float mapXValue;
float mapYValue;
float mapZValue;

PrintWriter output;


void setup() {
  size(750, 600);
  smooth();

  // create a new instance of oscP5 using a multicast socket 
  // the ethernet or wifi IP has to be the same as the one written on the server sketch
  oscP5 = new OscP5(this, "239.0.0.1", 7777);

  // Create a new file in the sketch directory to record values
  output = createWriter("sensor_value.txt");
  frameRate(12);
}


void draw() {

  if (record) {
    // #### will be replaced with the frame number
    beginRecord(PDF, "frame-####.pdf");
  }

  // draw with values from sensorShield
  dataVis();

  // record thoses values
  output.println(lightValue + "\t" + vibrationValue + "\t" + potValue + "\t" + tempValue + "\t" + orientation + "\t" + xValue + "\t" + yValue + "\t" + zValue);

  // save PDF
  if (record) {
    endRecord();
    record = false;
  }
}


void keyPressed() { 
  if (key == 'r') {
    output.flush(); // Write the remaining data
    output.close(); // Finish the file
    exit(); // Stop the program
  }
  if (key == 'f') {
    record = true; // save a PDF
  }
}