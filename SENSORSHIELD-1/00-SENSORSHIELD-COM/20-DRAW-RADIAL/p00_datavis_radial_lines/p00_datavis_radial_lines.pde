import processing.serial.*;
import processing.pdf.*;


////// SENSOR SHIELD //////  

Serial myPort;
JSONObject json;

////// CONTROL P5 //////

import controlP5.*;
ControlP5 cp5;
Accordion accordion;
color c = color(0, 160, 100);


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