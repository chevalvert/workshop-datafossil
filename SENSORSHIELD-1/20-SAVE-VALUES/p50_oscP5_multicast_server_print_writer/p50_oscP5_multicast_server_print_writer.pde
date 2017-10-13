/* SERVER
 * based on oscP5multicast by andreas schlegel
 * example shows how to send osc via a multicast socket.
 * what is a multicast? http://en.wikipedia.org/wiki/Multicast
 * ip multicast ranges and uses:
 * 224.0.0.1 - 224.0.0.255 Reserved for special well-known multicast addresses.
 * 224.0.1.0 - 238.255.255.255 Globally-scoped (Internet-wide) multicast addresses. (wifi)
 * 239.0.0.0 - 239.255.255.255 Administratively-scoped (local) multicast addresses. (ethernet)
 * oscP5 website at http://www.sojamo.de/oscP5
 */

import processing.serial.*;


////// MULTICAST - OSC //////

import oscP5.*;
import netP5.*;
OscP5 oscP5;
NetAddress myRemoteLocation;


////// SENSORSHIELD //////

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
  myPort = new Serial( this, Serial.list()[7], 9600 ); // port arduino
  myPort.clear();

  // create a new instance of oscP5 using a multicast socket (ethernet or wifi IP)
  oscP5 = new OscP5(this, "224.0.0.1", 7777);

  // Create a new file in the sketch directory to record values
  output = createWriter("sensor_value.txt");
  frameRate(12);
}


void draw() {
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
      
      ////// SEND OSC VALUES FROM SENSORS ////// 

      // create a new osc message object
      OscMessage myMessage = new OscMessage("/sensorGroup");  

      // add floats to each osc message
      myMessage.add(lightValue); 
      myMessage.add(forceValue); 
      myMessage.add(flexValue);    
      myMessage.add(pulseValue); 
      myMessage.add(distanceValue); 
      myMessage.add(humidityValue); 
      myMessage.add(temperatureValue); 

      // send the message
      oscP5.send(myMessage);

      // record thoses values
      output.println(lightValue + "\t" + forceValue + "\t" + flexValue + "\t" + pulseValue + "\t" + distanceValue + "\t" + humidityValue + "\t" + temperatureValue);
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