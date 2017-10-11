import processing.serial.*;

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

PrintWriter output;



void setup() {
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // port arduino
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
    }
  }
}


void keyPressed() { 
  if (key == 'r') {
    output.flush(); // Write the remaining data
    output.close(); // Finish the file
    exit(); // Stop the program
  }
}