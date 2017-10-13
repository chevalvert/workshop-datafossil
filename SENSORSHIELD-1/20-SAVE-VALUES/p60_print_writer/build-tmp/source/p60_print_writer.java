import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class p60_print_writer extends PApplet {



//////////  PDF  //////////  


boolean record;


////// SENSOR SHIELD //////  

Serial myPort;
JSONObject json;


////// VARIABLES INPUT //////

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

PrintWriter output;


public void setup() {
  
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // port arduino
  myPort.clear();

  

  // Create a new file in the sketch directory to record values
  output = createWriter("sensor_value.txt");
  frameRate(12);
}


public void draw() {

  if (record) {
    // #### will be replaced with the frame number
    beginRecord(PDF, "frame-####.pdf");
  }

  // read sensorShield
  if ( myPort.available() > 0 ) {
    String data = myPort.readStringUntil( '\n' );
    if ( data != null ) {
      //println( data ); 
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
      // draw with values from sensorShield
      dataVis();

      // record thoses values
      output.println(lightValue + "\t" + forceValue + "\t" + flexValue + "\t" + pulseValue + "\t" + distanceValue + "\t" + humidityValue + "\t" + temperatureValue);
    }
  }
  // save PDF
  if (record) {
    endRecord();
    record = false;
  }
}

public void keyPressed() { 
  if (key == 'r') {
    output.flush(); // Write the remaining data
    output.close(); // Finish the file
    exit(); // Stop the program
  }
  if (key == 'f') {
    record = true; // save a PDF
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


  // values from the server  

  ////// LIGHT //////
  mapLightValue =  map(lightValue, 140, 420, 0, 100);
  ellipse(150, 100, mapLightValue, mapLightValue);

  ////// FORCE //////
  mapForceValue =  map(forceValue, 20, 940, 0, 100);
  ellipse(300, 100, mapForceValue, mapForceValue);

  ////// FLEX ////// 
  mapFlexValue =  map(flexValue, 330, 200, 0, 100);
  ellipse(450, 100, mapFlexValue, mapFlexValue);

  ////// POULS //////  
  // constrain pulseValue to not exceed a maximum and a minimum value 
  float constPulseValue = constrain(pulseValue, 470, 600); 
  mapPulseValue =  map(constPulseValue, 470, 600, 20, 100);
  ellipse(600, 100, mapPulseValue, mapPulseValue);

  ////// DISTANCE //////  
  mapDistanceValue =  map(distanceValue, 14, 160, 100, 10);
  ellipse(150, 400, mapDistanceValue, mapDistanceValue);

  ////// HUMIDITY + TEMPERATURE //////
  mapHumidityValue =  map(humidityValue, 30, 100, 0, 100);
  ellipse(300, 400, mapHumidityValue, mapHumidityValue);

  mapTemperatureValue =  map(temperatureValue, 16, 30, 10, 100);
  ellipse(450, 400, mapTemperatureValue, mapTemperatureValue);
}
  public void settings() {  size(750, 600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p60_print_writer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
