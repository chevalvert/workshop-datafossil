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

public class p00_datavis_radial_lines extends PApplet {




////// SENSORSHIELD //////  

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

int deg = 0;



public void setup() {
  
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();

  strokeWeight(1);
  stroke(255, 0, 0);
  
  background(255);

  // textes
  fill(0);
  text("LUMI\u00c8RE", 125, 200); 
  text("VIBRATION", 275, 200);
  text("POTENTIOM\u00c8TRE", 405, 200);
  text("TEMP\u00c9RATURE", 565, 200);
  text("ORIENTATION X", 105, 500); 
  text("ORIENTATION Y", 255, 500);
  text("ORIENTATION Z", 410, 500);
  noFill();
  
  gui();

  beginRecord(PDF, "plancheVis.pdf" );
}


public void draw() {
  // read sensorShield
  while ( myPort.available() > 0 ) {
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
      // draw with values from sensorShield
      dataVis();
    }
  }
}

////// CONTROL P5 //////

public void gui() {

  cp5 = new ControlP5(this);

  // CONTROL COLOURS
  Group g1 = cp5.addGroup("Colours")
    .setBackgroundColor(color(0, 0))
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
    .setBackgroundColor(color(0, 0))
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
public void dataVis() {

  // values from GUI
  stroke(c);
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

  // rotation
  float distAngle = radians(deg);


  ////// LIGHT //////
  mapLightValue =  map(lightValue, 250, 680, 10, 80);
  float x1LightAngle = width/2 + (sin(distAngle) * 10);
  float y1LightAngle = height/2 + (cos(distAngle) * 10);
  float x2LightAngle = width/2 + (sin(distAngle) * mapLightValue);
  float y2LightAngle = height/2 + (cos(distAngle) * mapLightValue);
  line(x1LightAngle, y1LightAngle, x2LightAngle, y2LightAngle);


  ////// VIBRATION //////
  mapVibrationValue =  map(vibrationValue, 0, 1023, 90, 170); 
  float x1VibAngle = width/2 + (sin(distAngle) * 90);
  float y1VibAngle = height/2 + (cos(distAngle) * 90);
  float x2VibAngle = width/2 + (sin(distAngle) * mapVibrationValue);
  float y2VibAngle = height/2 + (cos(distAngle) * mapVibrationValue);
  line(x1VibAngle, y1VibAngle, x2VibAngle, y2VibAngle);


  ////// POT ////// 
  mapPotValue =  map(potValue, 0, 1023, 260, 180);  
  float x1PotAngle = width/2 + (sin(distAngle) * 180);
  float y1PotAngle = height/2 + (cos(distAngle) * 180);
  float x2PotAngle = width/2 + (sin(distAngle) * mapPotValue);
  float y2PotAngle = height/2 + (cos(distAngle) * mapPotValue);
  line(x1PotAngle, y1PotAngle, x2PotAngle, y2PotAngle);


  ////// TEMP\u00c9RATURE ////// 
  mapTempValue =  map(tempValue, 18, 25, 340, 280);  
  float x1TempAngle = width/2 + (sin(distAngle) * 280);
  float y1TempAngle = height/2 + (cos(distAngle) * 280);
  float x2TempAngle = width/2 + (sin(distAngle) * mapTempValue);
  float y2TempAngle = height/2 + (cos(distAngle) * mapTempValue);
  line(x1TempAngle, y1TempAngle, x2TempAngle, y2TempAngle);


  ////// ORIENTATION X //////  
  mapXValue =  map(xValue, -2, 2, 400, 440);  
  float x1XAngle = width/2 + (sin(distAngle) * 360);
  float y1XAngle = height/2 + (cos(distAngle) * 360);
  float x2XAngle = width/2 + (sin(distAngle) * mapXValue);
  float y2XAngle = height/2 + (cos(distAngle) * mapXValue);
  line(x1XAngle, y1XAngle, x2XAngle, y2XAngle);


  ////// ORIENTATION Y //////
  mapYValue =  map(yValue, -2, 2, 460, 500); 
  float x1YAngle = width/2 + (sin(distAngle) * 440);
  float y1YAngle = height/2 + (cos(distAngle) * 440);
  float x2YAngle = width/2 + (sin(distAngle) * mapYValue);
  float y2YAngle = height/2 + (cos(distAngle) * mapYValue);
  line(x1YAngle, y1YAngle, x2YAngle, y2YAngle);


  ////// ORIENTATION Z //////
  mapZValue =  map(zValue, -2, 2, 560, 620);
  float x1ZAngle = width/2 + (sin(distAngle) * 560);
  float y1ZAngle = height/2 + (cos(distAngle) * 560);
  float x2ZAngle = width/2 + (sin(distAngle) * mapZValue);
  float y2ZAngle = height/2 + (cos(distAngle) * mapZValue);
  line(x1ZAngle, y1ZAngle, x2ZAngle, y2ZAngle);


  deg += 5;

  // inclinaison
  if (orientation == 1) {
    fill(0, 5);
    text("Portrait Up", width/4, 50);
  } else if (orientation == 2) {
    fill(0, 5);
    text("Portrait Down", width/2.75f, 50);
  } else if (orientation == 3) {
    fill(0, 5);
    text("Landscape Right", width/2, 50);
  } else if (orientation == 4) {
    fill(0, 5);
    text("Landscape Left", width/1.5f, 50);
  } else {
    fill(0, 5);
    text("Flat", width/1.25f, 50);
  }


  if (deg > 360) {
    endRecord();
    noLoop();
  }
}
  public void settings() {  size(1450, 1450);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p00_datavis_radial_lines" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
