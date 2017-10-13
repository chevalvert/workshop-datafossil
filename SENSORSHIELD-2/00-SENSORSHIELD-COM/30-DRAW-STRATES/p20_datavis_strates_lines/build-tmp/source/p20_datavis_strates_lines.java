import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import processing.pdf.*; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class p20_datavis_strates_lines extends PApplet {





////// SENSOR SHIELD //////  

Serial myPort;
JSONObject json;

////// CONTROL P5 //////


ControlP5 cp5;
Accordion accordion;
int c = color(0, 160, 100);


////// CAPTEURS INPUT //////

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

int xPos = 20;



public void setup() {
  
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();

  background(255);
  strokeWeight(1);
  

  gui();

  beginRecord(PDF, "plancheVis.pdf" );

  fill(0);
  text("LUMI\u00c8RE", 20, 120);
  text("VIBRATION", 20, 240);
  text("POTENTIOM\u00c8TRE", 20, 360);
  text("TEMP\u00c9RATURE", 20, 480);
  text("ORIENTATION X", 20, 600);
  text("ORIENTATION Y", 20, 720);
  text("ORIENTATION Z", 20, 840);
}


public void draw() {
  // read sensorShield
  if ( myPort.available() > 0 ) {
    String data = myPort.readStringUntil( '\n' );
    if ( data != null ) {
      println( data ); 
      try {
        json = JSONObject.parse( data );
        // get the values of your sensors from serial (arduino)
        lightValue = json.getInt("capteurLDR");         
        vibrationValue = json.getInt("capteurPIEZO");        
        potValue = json.getInt("capteurPOT");
        tempValue = json.getInt("capteurTEMP");
        orientation = json.getInt("inclinaison");
        xValue = json.getInt("xValue");
        yValue = json.getInt("yValue");
        zValue = json.getInt("zValue");
      } 
      catch ( Exception e ) {
        e.printStackTrace();
      }
      dataVis();
    }
  }
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
      //accordion.close(0, 1);
      accordion.close(0, 1);
    }
  }
  , 'c');

  accordion.open(0, 1);

  // Accordion.MULTI to allow multiple group to be open at a time
  accordion.setCollapseMode(Accordion.MULTI);
}

// avoid closing accordion when mouse pressed
public void mousePressed() {
  accordion.open(0, 1);
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

////// DRAW USING VALUES FROM SERVER //////

public void dataVis() {
  
  // values from GUI
  stroke(c);
  noFill();
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);
  
  ////// LIGHT //////
  mapLightValue =  map(lightValue, 190, 750, 20, 100);
  line(xPos, mapLightValue, xPos, 100); 

  ////// FORCE //////
  mapVibrationValue =  map(vibrationValue, 0, 1023, 220, 140 );  
  line(xPos, mapVibrationValue, xPos, 220); 

  ////// POT ////// 
  mapPotValue =  map(potValue, 0, 1023, 260, 340);  
  line(xPos, mapPotValue, xPos, 340); 

  ////// TEMP\u00c9RATURE ////// 
  mapTempValue =  map(tempValue, 18, 25, 460, 380);  
  line(xPos, mapTempValue, xPos, 460); 

  ////// ORIENTATION X //////
  mapXValue =  map(xValue, -2, 2, 580, 500);  
  line(xPos, mapXValue, xPos, 580);   

  ////// ORIENTATION Y //////
  mapYValue =  map(yValue, -2, 2, 700, 620);  
  line(xPos, mapYValue, xPos, 700);

  ////// ORIENTATION Z //////  
  mapZValue =  map(zValue, -2, 2, 820, 740);
  line(xPos, mapZValue, xPos, 820);

  // avancer x
  xPos= xPos + 10;

  if (xPos > width-20) {
    endRecord();
    noLoop();
  }
}
  public void settings() {  size(1250, 860);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p20_datavis_strates_lines" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
