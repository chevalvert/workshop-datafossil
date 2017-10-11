import processing.serial.*;

////// SENSORSHIELD //////  

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


PrintWriter output;


void setup() {
  size( 750, 600 );
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[7], 9600 ); // indicate your arduino port
  myPort.clear();

  // Create a new file in the sketch directory to record values
  output = createWriter("sensor_value.txt");
  frameRate(12);
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
      // record thoses values
      output.println(lightValue + "\t" + forceValue + "\t" + flexValue + "\t" + pulseValue + "\t" + distanceValue + "\t" + humidityValue + "\t" + temperatureValue);
    }
  }
}

void keyPressed() { // Press a key to save the data
  output.flush(); // Write the remaining data
  output.close(); // Finish the file
  exit(); // Stop the program
}