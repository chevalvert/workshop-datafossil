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



////// SENSORSHIELD //////  

Serial myPort;
JSONObject json;


////// CAPTEURS INPUT //////

float lightValue; // LDR light
float vibrationValue; // piezo vibration
float potValue; // soft pot
float tempValue; // temp sensor
float orientation; // accel orientation
float xValue; // accel X
float yValue; // accel Y
float zValue; // accel Z



////// VARIABLES OUTPUT //////

float mapLightValue;
float mapVibrationValue;
float mapPotValue;
float mapTempValue;
float mapXValue;
float mapYValue;
float mapZValue;



public void setup() {
  
  fill(0);
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();
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
