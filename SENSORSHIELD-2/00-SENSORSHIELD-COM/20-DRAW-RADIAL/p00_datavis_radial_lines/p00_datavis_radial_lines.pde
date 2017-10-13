import processing.serial.*;
import processing.pdf.*;

////// SENSORSHIELD //////  

Serial myPort;
JSONObject json;


////// CONTROL P5 //////

import controlP5.*;
ControlP5 cp5;
Accordion accordion;
color c = color(0, 160, 100);


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



void setup() {
  size(1450, 1450);
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();

  strokeWeight(1);
  stroke(255, 0, 0);
  smooth();
  background(255);

  // textes
  fill(0);
  text("LUMIÈRE", width/2 + 10, height/2); 
  text("VIBRATION", width/2 + 90, height/2);
  text("POTEN", width/2 + 180, height/2);
  text("TEMP", width/2 + 280, height/2);
  text("X", width/2 + 360, height/2); 
  text("Y", width/2 + 440, height/2);
  text("Z", width/2 + 560, height/2);
  noFill();
  
  gui();

  beginRecord(PDF, "plancheVis.pdf" );
}


void draw() {
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
      // draw with values from sensorShield
      dataVis();
    }
  }
}