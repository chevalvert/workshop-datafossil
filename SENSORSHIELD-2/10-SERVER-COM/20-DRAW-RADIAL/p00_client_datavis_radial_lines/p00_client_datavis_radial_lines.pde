/* CLIENT
 * oscP5multicast by andreas schlegel
 * example shows how to send osc via a multicast socket.
 * what is a multicast? http://en.wikipedia.org/wiki/Multicast
 * ip multicast ranges and uses:
 * 224.0.0.0 - 224.0.0.255 Reserved for special well-known multicast addresses.
 * 224.0.0.1 - 238.255.255.255 Globally-scoped (Internet-wide) multicast addresses.
 * 239.0.0.0 - 239.255.255.255 Administratively-scoped (local) multicast addresses.
 * oscP5 website at http://www.sojamo.de/oscP5
 */

import processing.pdf.*;


////// MULTICAST - OSC //////

import oscP5.*;
import netP5.*;
OscP5 oscP5;


////// CONTROL P5 //////

import controlP5.*;
ControlP5 cp5;
Accordion accordion;
color c = color(0, 160, 100);


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

int deg = 0;



void setup() {
  size(1450, 1450);
  background(255);
  strokeWeight(1);
  smooth();

  text("LUMIÈRE", 125, 200); 
  text("VIBRATION", 275, 200);
  text("POTENTIOMÈTRE", 405, 200);
  text("TEMPÉRATURE", 565, 200);
  text("ORIENTATION X", 105, 500); 
  text("ORIENTATION Y", 255, 500);
  text("ORIENTATION Z", 410, 500);

  gui();

  // create a new instance of oscP5 using a multicast socket 
  // the ethernet or wifi IP has to be the same as the one written on the server sketch
  oscP5 = new OscP5(this, "224.0.0.1", 7777);

  beginRecord(PDF, "plancheVis.pdf" );
}


void draw() {
  // draw with values from sensorShield
  dataVis();
}