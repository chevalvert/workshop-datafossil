import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.pdf.*; 
import oscP5.*; 
import netP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class p10_oscP5_multicast_client extends PApplet {

/* CLIENT
 * oscP5multicast by andreas schlegel
 * example shows how to send osc via a multicast socket.
 * what is a multicast? http://en.wikipedia.org/wiki/Multicast
 * ip multicast ranges and uses:
 * 224.0.0.0 - 224.0.0.255 Reserved for special well-known multicast addresses.
 * 224.0.0.1 - 238.255.255.255 Globally-scoped (Internet-wide) multicast addresses.
 * 239.0.0.1 - 239.255.255.255 Administratively-scoped (local) multicast addresses.
 * oscP5 website at http://www.sojamo.de/oscP5
 */


//////////  PDF  //////////  


boolean record;


////// MULTICAST - OSC //////



OscP5 oscP5;


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


public void setup() {
  
  

  // create a new instance of oscP5 using a multicast socket 
  // the ethernet or wifi IP has to be the same as the one written on the server sketch
  oscP5 = new OscP5(this, "224.0.0.1", 7777);
}


public void draw() {

  if (record) {
    // #### will be replaced with the frame number
    beginRecord(PDF, "frame-####.pdf");
  }

  // draw with values from sensorShield
  dataVis();
   
  // save PDF
  if (record) {
    endRecord();
    record = false;
  }
}


public void keyPressed() { 
  if (key == 'f') {
    record = true; // save a PDF frame
  }
}

////// RECEIVE OSC VALUES FROM SERVER //////

public void oscEvent(OscMessage theOscMessage) {
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


      // create float variables to get the values from OSC and use it for drawing
      lightValue = firstValue;
      forceValue = secondValue;
      flexValue = thirdValue;
      pulseValue = fourthValue;
      distanceValue = fifthValue;
      humidityValue = sixthValue;
      temperatureValue = seventhValue;


      print("### message from SERVER sensors");
      println(" LIGHT "+firstValue+", FORCE "+secondValue+", FLEX "+thirdValue+
        ", POULS "+fourthValue+", DIST "+fifthValue+", HUM "+sixthValue+", TEMP "+seventhValue);
      return;
    }
  } 
  println("### received another osc message. with address pattern "+theOscMessage.addrPattern());
}

////// DRAW USING VALUES FROM SERVER //////

public void dataVis() {

  background(255);

  // textes
  fill(0);
  text("LUMI\u00c8RE", 125, 200); 
  text("PRESSION", 275, 200);
  text("FLEXION", 425, 200);
  text("POULS", 575, 200);
  text("DISTANCE", 125, 500); 
  text("HUMIDIT\u00c9", 270, 500);
  text("TEMP\u00c9RATURE", 410, 500);


  // values from the server  

  ////// LIGHT //////
  mapLightValue =  map(lightValue, 140, 420, 0, 100);
  ellipse(150, 100, mapLightValue, mapLightValue);

  ////// FORCE //////
  mapForceValue =  map(forceValue, 20, 940, 0, 100);
  ellipse(300, 100, mapForceValue, mapForceValue);

  ////// FLEX ////// 
  mapFlexValue =  map(flexValue, 330, 200, 0, 100);
  ellipse(450, 100, mapFlexValue, mapFlexValue);

  ////// POULS //////  
  // constrain pulseValue to not exceed a maximum and a minimum value 
  float constPulseValue = constrain(pulseValue, 470, 600); 
  mapPulseValue =  map(constPulseValue, 470, 600, 20, 100);
  ellipse(600, 100, mapPulseValue, mapPulseValue);

  ////// DISTANCE //////  
  mapDistanceValue =  map(distanceValue, 14, 160, 100, 10);
  ellipse(150, 400, mapDistanceValue, mapDistanceValue);

  ////// HUMIDITY + TEMPERATURE //////
  mapHumidityValue =  map(humidityValue, 30, 100, 0, 100);
  ellipse(300, 400, mapHumidityValue, mapHumidityValue);

  mapTemperatureValue =  map(temperatureValue, 16, 30, 10, 100);
  ellipse(450, 400, mapTemperatureValue, mapTemperatureValue);
}
  public void settings() {  size(750, 600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p10_oscP5_multicast_client" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
