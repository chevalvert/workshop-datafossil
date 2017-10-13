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

public class p60_oscP5_multicast_client_print_writer extends PApplet {

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


boolean record;


////// MULTICAST - OSC //////



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


public void setup() {
  
  

  // create a new instance of oscP5 using a multicast socket 
  // the ethernet or wifi IP has to be the same as the one written on the server sketch
  oscP5 = new OscP5(this, "239.0.0.1", 7777);

  // Create a new file in the sketch directory to record values
  output = createWriter("sensor_value.txt");
  frameRate(12);
}


public void draw() {

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


public void keyPressed() { 
  if (key == 'r') {
    output.flush(); // Write the remaining data
    output.close(); // Finish the file
    exit(); // Stop the program
  }
  if (key == 'f') {
    record = true; // save a PDF
  }
}

////// RECEIVE OSC VALUES FROM SERVER //////

public void oscEvent(OscMessage theOscMessage) {
  // check if theOscMessage has the address pattern we are looking for.   
  if (theOscMessage.checkAddrPattern("/sensorGroup")==true) {
    // check if the typetag is the right one: 8*float 
    if (theOscMessage.checkTypetag("ffffffff")) {
      // parse theOscMessage and extract the values from the osc message arguments.      
      float firstValue = theOscMessage.get(0).floatValue();
      float secondValue = theOscMessage.get(1).floatValue();
      float thirdValue = theOscMessage.get(2).floatValue();
      float fourthValue = theOscMessage.get(3).floatValue();
      float fifthValue = theOscMessage.get(4).floatValue();
      float sixthValue = theOscMessage.get(5).floatValue();
      float seventhValue = theOscMessage.get(6).floatValue();
      float eigthValue = theOscMessage.get(7).floatValue();

      // create float variables to get the values from OSC and use it for drawing
      lightValue = firstValue;
      vibrationValue = secondValue;
      potValue = thirdValue;
      tempValue = fourthValue;
      orientation = fifthValue;
      xValue = sixthValue;
      yValue = seventhValue;
      zValue = eigthValue;

      print("### received an osc message from computer 1 /sensorGroup ");
      println(" sensor: LIGHT "+firstValue+", VIBRATION "+secondValue+", POT "+thirdValue+
        ", TEMP "+fourthValue+", ORIENT "+fifthValue+", X "+sixthValue+", Y "+seventhValue+", Z "+eigthValue);
      return;
    }
  } 
  println("### received another osc message: "+theOscMessage.addrPattern());
}

////// DRAW USING VALUES FROM SERVER //////

public void dataVis() {

  background(255);

  // textes
  fill(0);
  text("LUMI\u00c8RE", 125, 200); 
  text("VIBRATION", 265, 200);
  text("POTENTIOM\u00c8TRE", 400, 200);
  text("TEMP\u00c9RATURE", 560, 200);
  text("X ACCEL", 125, 500); 
  text("Y ACCEL", 270, 500);
  text("Z ACCEL", 420, 500);
  noFill();

  ////// LIGHT //////  
  mapLightValue =  map(lightValue, 280, 600, 0, 150);
  ellipse(150, 100, mapLightValue, mapLightValue); 

  ////// VIBRATION //////  
  mapVibrationValue =  map(vibrationValue, 1023, 0, 10, 150);
  ellipse(300, 100, mapVibrationValue, mapVibrationValue);

  ////// POT //////  
  mapPotValue =  map(potValue, 1023, 0, 10, 150);
  ellipse(450, 100, mapPotValue, mapPotValue);

  ////// TEMP //////  
  mapTempValue =  map(tempValue, 17, 27, 10, 150);
  ellipse(600, 100, mapTempValue, mapTempValue);

  ////// X ////// 
  mapXValue =  map(xValue, -2, 2, 0, 150);
  ellipse(150, 400, mapXValue, mapXValue);

  ////// Y ////// 
  mapYValue =  map(yValue, -2, 2, 0, 150);
  ellipse(300, 400, mapYValue, mapYValue);

  ////// Z ////// 
  mapZValue =  map(zValue, -2, 2, 0, 150);
  ellipse(450, 400, mapZValue, mapZValue);

  // inclinaison
  if (orientation == 1) {    
    text("Portrait Up", 575, 500);
  } else if (orientation == 2) {
    text("Portrait Down", 575, 500);
  } else if (orientation == 3) {
    text("Landscape Right", 575, 500);
  } else if (orientation == 4) {
    text("Landscape Left", 575, 500);
  } else {
    text("Flat", 575, 500);
  }
}
  public void settings() {  size(750, 600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p60_oscP5_multicast_client_print_writer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
