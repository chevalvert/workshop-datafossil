import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.pdf.*; 
import oscP5.*; 
import netP5.*; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class p20_oscP5_multicast_client_controlP5 extends PApplet {

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


////// CONTROL P5 //////


ControlP5 cp5;
Accordion accordion;
int c = color(0, 160, 100);


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



public void setup() {
  
  strokeWeight(1);
  
  gui();

  // create a new instance of oscP5 using a multicast socket 
  // the ethernet or wifi IP has to be the same as the one written on the server sketch
  oscP5 = new OscP5(this, "239.0.0.1", 7777);
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


// save a PDF
public void keyPressed() {
  if (key == 'f') {
    record = true;
  }
}

////// CONTROL P5 //////

public void gui() {

  cp5 = new ControlP5(this);

  // CONTROL COLOURS
  Group g1 = cp5.addGroup("Colours")
    .setBackgroundColor(color(0, 64))
    .setBackgroundHeight(150)
    ;

  cp5.addRadioButton("colour")
    .setPosition(10, 20)
    .setItemWidth(20)
    .setItemHeight(20)
    .addItem("black", 0)
    .addItem("red", 1)
    .addItem("green", 2)
    .addItem("blue", 3)
    .setColorLabel(color(255))
    .activate(2)
    .moveTo(g1)
    ;

  // DRAWING 
  Group g2 = cp5.addGroup("Drawing")
    .setBackgroundColor(color(0, 64))
    .setBackgroundHeight(150)
    ;


  cp5.addSlider("strokeWeight")
    .setPosition(10, 20)
    .setSize(100, 20)
    .setRange(0, 20)
    .setValue(1)
    .moveTo(g2)
    ;


  // create accordions
  accordion = cp5.addAccordion("acc")
    .setPosition(40, 40)
    .setWidth(200)
    .addItem(g1)
    .addItem(g2)
    ;

  cp5.mapKeyFor(new ControlKey() {
    public void keyEvent() {
      accordion.open(0, 1);
    }
  }
  , 'o');
  cp5.mapKeyFor(new ControlKey() {
    public void keyEvent() {
      accordion.close(0, 1);
    }
  }
  , 'c');

  accordion.open(0, 1);

  // Accordion.MULTI to allow multiple group to be open at a time
  accordion.setCollapseMode(Accordion.MULTI);
}


// SET COLOURS
public void colour(int theC) {
  switch(theC) {
    case(0):
    c=color(0, 255);
    break;
    case(1):
    c=color(255, 0, 0, 255);
    break;
    case(2):
    c=color(0, 255, 0, 255);
    break;
    case(3):
    c=color(0, 0, 255, 255);
    break;
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

  // texts
  fill(0);
  text("LUMI\u00c8RE", 125, 200); 
  text("VIBRATION", 275, 200);
  text("POTENTIOM\u00c8TRE", 405, 200);
  text("TEMP\u00c9RATURE", 565, 200);
  text("ORIENTATION X", 105, 500); 
  text("ORIENTATION Y", 255, 500);
  text("ORIENTATION Z", 410, 500);

  // values from GUI
  fill(c);
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

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
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p20_oscP5_multicast_client_controlP5" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
