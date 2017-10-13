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

public class p20_client_datavis_strates_lines extends PApplet {

/** CLIENT
 * oscP5multicast by andreas schlegel
 * example shows how to send osc via a multicast socket.
 * what is a multicast? http://en.wikipedia.org/wiki/Multicast
 * ip multicast ranges and uses:
 * 224.0.0.0 - 224.0.0.255 Reserved for special well-known multicast addresses.
 * 224.0.1.0 - 238.255.255.255 Globally-scoped (Internet-wide) multicast addresses.
 * 239.0.0.0 - 239.255.255.255 Administratively-scoped (local) multicast addresses.
 * oscP5 website at http://www.sojamo.de/oscP5
 */




////// MULTICAST - OSC //////



OscP5 oscP5;


////// CONTROL P5 //////


ControlP5 cp5;
Accordion accordion;
int c = color(0, 160, 100);


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
float mapFlexValue = 0;
float mapPulseValue;
float mapDistanceValue;
float mapHumidityValue;
float mapTemperatureValue;

int xPos = 20;




public void setup() {
  
  background(255);
  strokeWeight(1);
  
  gui();

  beginRecord(PDF, "plancheVis.pdf" );

  fill(0);
  text("LIGHT", 20, 120);
  text("FORCE", 20, 240);
  text("FLEXION", 20, 360);
  text("DISTANCE", 20, 480);
  text("TEMP\u00c9RATURE", 20, 600);
  text("HUMIDIT\u00c9", 20, 720);
  text("POULS", 20, 840);

  // create a new instance of oscP5 using a multicast socket 
  // the ethernet or wifi IP has to be the same as the one written on the server sketch
  oscP5 = new OscP5(this, "239.0.0.1", 7777);
}


public void draw() {
  // draw with values from sensorShield
  dataVis();
}

////// CONTROL P5 //////

public void gui() {

  cp5 = new ControlP5(this);

  // CONTROL COLOURS
  Group g1 = cp5.addGroup("Colours")
    .setBackgroundColor(color(0, 255))
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
    .setBackgroundColor(color(0, 255))
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
    // check if the typetag is the right one: float 
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

////// DRAW USING VALUES FROM SERVER //////

public void dataVis() {

  // values from GUI
  stroke(c);
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

  // draw with the values from the server   
  noFill();

  ////// LIGHT //////
  mapLightValue =  map(lightValue, 190, 560, 20, 100);
  line(xPos, mapLightValue, xPos, 100); 

  ////// FORCE //////
  mapForceValue =  map(forceValue, 10, 1023, 220, 140 );  
  line(xPos, mapForceValue, xPos, 220); 

  ////// FLEX //////
  mapFlexValue =  map(flexValue, 190, 320, 260, 340);  
  line(xPos, mapFlexValue, xPos, 340); 

  ////// DISTANCE //////
  mapDistanceValue =  map(distanceValue, 14, 160, 460, 380);  
  line(xPos, mapDistanceValue, xPos, 460); 

  ////// TEMPERATURE //////
  mapTemperatureValue =  map(temperatureValue, 16, 28, 580, 500);  
  line(xPos, mapTemperatureValue, xPos, 580);   

  ////// HUMIDIT\u00c9 //////
  mapHumidityValue =  map(humidityValue, 30, 100, 700, 620);  
  line(xPos, mapHumidityValue, xPos, 700);

  ////// POULS //////  
  float constPulseValue = constrain(pulseValue, 470, 540); // constrain pulseValue to no exceed a maximum and a minimum value 
  mapPulseValue =  map(constPulseValue, 470, 540, 820, 740);
  line(xPos, mapPulseValue, xPos, 820);

  // avancer x
  xPos= xPos + 10;
  delay(250);

  if (xPos > width-20) {
    endRecord();
    noLoop();
  }
}
  public void settings() {  size(1250, 860);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p20_client_datavis_strates_lines" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
