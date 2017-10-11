import processing.serial.*;

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



void setup() {
  size( 750, 600 );
  fill(0);
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();
}


void draw() {
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