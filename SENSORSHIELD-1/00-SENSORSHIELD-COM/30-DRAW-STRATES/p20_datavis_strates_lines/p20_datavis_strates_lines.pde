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
float mapFlexValue = 0;
float mapPulseValue;
float mapDistanceValue;
float mapHumidityValue;
float mapTemperatureValue;

int xPos = 20;


void setup() {
  size(1250, 860);
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();

  background(255);
  strokeWeight(1);
  smooth();

  gui();

  beginRecord(PDF, "plancheVis.pdf" );

  fill(0);
  text("LIGHT", 20, 120);
  text("FORCE", 20, 240);
  text("FLEXION", 20, 360);
  text("DISTANCE", 20, 480);
  text("TEMPÉRATURE", 20, 600);
  text("HUMIDITÉ", 20, 720);
  text("POULS", 20, 840);
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