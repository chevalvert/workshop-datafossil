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





////// SENSOR SHIELD //////  

Serial myPort;
JSONObject json;

////// CONTROL P5 //////


ControlP5 cp5;
Accordion accordion;
int c = color(0, 160, 100);


////// CAPTEURS INPUT //////

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

public void setup() {
  
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();

  strokeWeight(1);
  stroke(255, 0, 0);
  
  background(255);

  // textes
  fill(0);
  text("LUMI\u00c8RE", width/2 + 10, height/2); 
  text("PRESSION", width/2 + 90, height/2);
  text("FLEXION", width/2 + 180, height/2);
  text("DISTANCE", width/2 + 260, height/2); 
  text("TEMP", width/2 + 360, height/2);
  text("HUMD", width/2 + 440, height/2);
  text("POULS", width/2 + 560, height/2);
  noFill();

  gui();

  beginRecord(PDF, "plancheVis.pdf" );
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
        forceValue = json.getInt("capteurFSR");        
        flexValue = json.getInt("capteurFLEX");
        pulseValue = json.getInt("capteurPOULS");
        distanceValue = json.getInt("capteurSONAR");
        humidityValue = json.getInt("humidity"); 
        temperatureValue = json.getInt("temperature");
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

  // values from the sensor shield

  // rotation
  float distAngle = radians(deg);


  ////// LIGHT //////
  mapLightValue =  map(lightValue, 200, 800, 10, 80);
  float x1LightAngle = width/2 + (sin(distAngle) * 10);
  float y1LightAngle = height/2 + (cos(distAngle) * 10);
  float x2LightAngle = width/2 + (sin(distAngle) * mapLightValue);
  float y2LightAngle = height/2 + (cos(distAngle) * mapLightValue);
  line(x1LightAngle, y1LightAngle, x2LightAngle, y2LightAngle);

  ////// FORCE //////
  mapForceValue =  map(forceValue, 10, 1023, 90, 170 );  
  float x1FrcAngle = width/2 + (sin(distAngle) * 90);
  float y1FrcAngle = height/2 + (cos(distAngle) * 90);
  float x2FrcAngle = width/2 + (sin(distAngle) * mapForceValue);
  float y2FrcAngle = height/2 + (cos(distAngle) * mapForceValue);
  line(x1FrcAngle, y1FrcAngle, x2FrcAngle, y2FrcAngle);

  ////// FLEX //////
  mapFlexValue =  map(flexValue, 190, 350, 260, 180);  
  float x1FlxAngle = width/2 + (sin(distAngle) * 180);
  float y1FlxAngle = height/2 + (cos(distAngle) * 180);
  float x2FlxAngle = width/2 + (sin(distAngle) * mapFlexValue);
  float y2FlxAngle = height/2 + (cos(distAngle) * mapFlexValue);
  line(x1FlxAngle, y1FlxAngle, x2FlxAngle, y2FlxAngle);

  ////// DISTANCE //////
  mapDistanceValue =  map(distanceValue, 14, 40, 340, 280);  
  float x1DistAngle = width/2 + (sin(distAngle) * 280);
  float y1DistAngle = height/2 + (cos(distAngle) * 280);
  float x2DistAngle = width/2 + (sin(distAngle) * mapDistanceValue);
  float y2DistAngle = height/2 + (cos(distAngle) * mapDistanceValue);
  line(x1DistAngle, y1DistAngle, x2DistAngle, y2DistAngle);

  ////// TEMPERATURE //////
  mapTemperatureValue =  map(temperatureValue, 25, 33, 400, 440);  
  float x1TempAngle = width/2 + (sin(distAngle) * 360);
  float y1TempAngle = height/2 + (cos(distAngle) * 360);
  float x2TempAngle = width/2 + (sin(distAngle) * mapTemperatureValue);
  float y2TempAngle = height/2 + (cos(distAngle) * mapTemperatureValue);
  line(x1TempAngle, y1TempAngle, x2TempAngle, y2TempAngle);

  ////// HUMIDIT\u00c9 //////
  mapHumidityValue =  map(humidityValue, 48, 75, 460, 500);  
  float x1HumAngle = width/2 + (sin(distAngle) * 440);
  float y1HumAngle = height/2 + (cos(distAngle) * 440);
  float x2HumAngle = width/2 + (sin(distAngle) * mapHumidityValue);
  float y2HumAngle = height/2 + (cos(distAngle) * mapHumidityValue);
  line(x1HumAngle, y1HumAngle, x2HumAngle, y2HumAngle);

  ////// POULS //////  
  float constPulseValue = constrain(pulseValue, 560, 620); // constrain pulseValue to no exceed a maximum and a minimum value 
  mapPulseValue =  map(constPulseValue, 470, 540, 560, 620);
  float x1PulseAngle = width/2 + (sin(distAngle) * 560);
  float y1PulseAngle = height/2 + (cos(distAngle) * 560);
  float x2PulseAngle = width/2 + (sin(distAngle) * mapPulseValue);
  float y2PulseAngle = height/2 + (cos(distAngle) * mapPulseValue);
  line(x1PulseAngle, y1PulseAngle, x2PulseAngle, y2PulseAngle);

  deg += 5;
  
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
