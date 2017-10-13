import processing.serial.*;
import processing.pdf.*;
// boolean record;


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

int [] y1;
int [] y2;
int [] y3;
int [] y4;



void setup() {
  size(1250, 400); 
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();

  background(255);
  strokeWeight(1);
  stroke(0);
  smooth();
  fill(0);

  gui();

  y1 = new int[width];
  y2 = new int[width];
  y3 = new int[width];
  y4 = new int[width];
}


void draw() {

  // if (record) {
  //   // #### will be replaced with the frame number
  //   beginRecord(PDF, "frame-####.pdf");
  // }


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

  // if (record) {
  //   endRecord();
  //   record = false;
  // }
}


// save PDF
// void keyPressed() {
//   if (key == 'f') {
//     record = true;
//   }
// }