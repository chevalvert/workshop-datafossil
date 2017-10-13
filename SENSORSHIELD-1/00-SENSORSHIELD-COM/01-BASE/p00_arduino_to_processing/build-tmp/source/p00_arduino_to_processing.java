import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class p00_arduino_to_processing extends PApplet {



////// SENSOR SHIELD //////  

Serial myPort;
JSONObject json;


////// CAPTEURS INPUT //////

float lightValue; // LDR light
float forceValue; // FSR force
float flexValue; // FLEX
float pulseValue; // pouls
float distanceValue; // DISTANCE sonar sensor
float humidityValue; // humidity
float temperatureValue; // temp


////// VARIABLES OUTPUT //////

float mapLightValue;
float mapForceValue;
float mapFlexValue;
float mapPulseValue;
float mapDistanceValue;
float mapHumidityValue;
float mapTemperatureValue;


public void setup() {
  
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();
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
  noFill();

  ////// LIGHT //////
  mapLightValue =  map(lightValue, 200, 800, 255, 0);
  ellipse(150, 100, mapLightValue, mapLightValue);

  ////// FORCE //////
  mapForceValue =  map(forceValue, 20, 940, 0, 255);
  ellipse(300, 100, mapForceValue, mapForceValue);

  ////// FLEX ////// 
  mapFlexValue =  map(flexValue, 330, 200, 0, 255);
  ellipse(450, 100, mapFlexValue, mapFlexValue);

  ////// POULS //////  
  // constrain pulseValue to not exceed a maximum and a minimum value 
  float constPulseValue = constrain(pulseValue, 470, 540); 
  mapPulseValue =  map(constPulseValue, 470, 540, 20, 255);
  ellipse(600, 100, mapPulseValue, mapPulseValue);

  ////// DISTANCE //////  
  mapDistanceValue =  map(distanceValue, 10, 160, 255, 10);
  ellipse(150, 400, mapDistanceValue, mapDistanceValue);

  ////// HUMIDITY + TEMPERATURE //////
  mapHumidityValue =  map(humidityValue, 30, 100, 0, 255);
  ellipse(300, 400, mapHumidityValue, mapHumidityValue);

  mapTemperatureValue =  map(temperatureValue, 16, 30, 10, 255);
  ellipse(450, 400, mapTemperatureValue, mapTemperatureValue);
}
  public void settings() {  size( 750, 600 ); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p00_arduino_to_processing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
