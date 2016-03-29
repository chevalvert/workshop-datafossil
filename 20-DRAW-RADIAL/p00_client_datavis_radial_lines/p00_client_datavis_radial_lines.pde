/* CLIENT
 * oscP5multicast by andreas schlegel
 * example shows how to send osc via a multicast socket.
 * what is a multicast? http://en.wikipedia.org/wiki/Multicast
 * ip multicast ranges and uses:
 * 224.0.0.1 - 224.0.0.255 Reserved for special well-known multicast addresses.
 * 224.0.1.0 - 238.255.255.255 Globally-scoped (Internet-wide) multicast addresses.
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
float forceValue; // FSR force
float flexValue; // FLEX
float pulseValue; // pouls
float distanceValue; // DISTANCE sonar sensor
float humidityValue; // humidity
float temperatureValue; // temp


//////////  VARIABLES OUTPUT  //////////

float mapLightValue;
float mapForceValue;
float mapFlexValue;
float mapPulseValue;
float mapDistanceValue;
float mapHumidityValue;
float mapTemperatureValue;

int deg = 0;



void setup() {
  size(1450, 1450);
  strokeWeight(1);
  smooth();
  background(255);

  text("LIGHT", 50, 1250);
  text("FORCE", 50, 1275);
  text("FLEXION", 50, 1300);
  text("DISTANCE", 50, 1325);
  text("TEMPÉRATURE", 50, 1350);
  text("HUMIDITÉ", 50, 1375);
  text("POULS", 50, 1400);

  gui();

  // create a new instance of oscP5 using a multicast socket (ethernet or wifi IP)
  oscP5 = new OscP5(this, "224.0.0.1", 7777);

  beginRecord(PDF, "plancheVis.pdf" );
}


void draw() {
  // draw with values from sensorShield
  dataVis();
}


void oscEvent(OscMessage theOscMessage) {
  // check if theOscMessage has the address pattern we are looking for.   
  if (theOscMessage.checkAddrPattern("/sensorGroup")==true) {
    // check if the typetag is the right one: 7*float 
    if (theOscMessage.checkTypetag("fffffff")) {
      // parse theOscMessage and extract the values from the osc message arguments.      
      float firstValue = theOscMessage.get(0).floatValue();
      float secondValue = theOscMessage.get(1).floatValue();
      float thirdValue = theOscMessage.get(2).floatValue();
      float fourthValue = theOscMessage.get(3).floatValue();
      float fifthValue = theOscMessage.get(4).floatValue();
      float sixthValue = theOscMessage.get(5).floatValue();
      float seventhValue = theOscMessage.get(6).floatValue();


      // create float variables to pic the values from OSC and use it for drawing
      lightValue = firstValue;
      forceValue = secondValue;
      flexValue = thirdValue;
      pulseValue = fourthValue;
      distanceValue = fifthValue;
      humidityValue = sixthValue;
      temperatureValue = seventhValue;


      print("### message from SERVER sensors");
      println(" LIGHT "+firstValue+", FORCE "+secondValue+", FLEX "+thirdValue+", PULSE "+fourthValue+", DIST "+fifthValue+", HUM "+sixthValue+", TEMP "+seventhValue);
      return;
    }
  } 
  println("### received another osc message. with address pattern "+theOscMessage.addrPattern());
}