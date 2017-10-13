import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import oscP5.*; 
import netP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class p00_oscP5_multicast_server extends PApplet {

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




////// MULTICAST - OSC //////



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



public void setup() {
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // port arduino
  myPort.clear();

  // create a new instance of oscP5 using a multicast socket (ethernet or wifi IP)
  //oscP5 = new OscP5(this, "10.203.1.167", 9999);
  oscP5 = new OscP5(this, "224.0.0.1", 7777);
}


public void draw() {
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
    }
  }
}

////// SEND OSC VALUES FROM SENSORS //////

public void mousePressed() {
  // create a new OscMessage with an address pattern, in this case /message from SERVER
  OscMessage myOscMessage = new OscMessage("/message from SERVER");
  // send the OscMessage to the multicast group
  oscP5.send(myOscMessage);
}


public void oscEvent(OscMessage theOscMessage) {
  // check if theOscMessage has the address pattern we are looking for.   
  if (theOscMessage.checkAddrPattern("/sensorGroup")==true) {
    // check if the typetag is the right one: 7*float 
    if (theOscMessage.checkTypetag("fffffff")) {
      // parse theOscMessage and extract the values from the osc message arguments.      
      float firstValue = theOscMessage.get(0).floatValue();
      float secondValue = theOscMessage.get(1).floatValue();
      float thirdValue = theOscMessage.get(2).floatValue();
      float fourthValue = theOscMessage.get(3).floatValue();
      float fifthValue = theOscMessage.get(4).floatValue();
      float sixthValue = theOscMessage.get(5).floatValue();
      float seventhValue = theOscMessage.get(6).floatValue();
      print("### received an osc message from computer 1 /sensorGroup ");
      println(" sensor value: LIGHT "+firstValue+", FORCE "+secondValue+", FLEX "+thirdValue+
      ", POULS "+fourthValue+", DISTANCE "+fifthValue+", HUM "+sixthValue+", TEMP "+seventhValue);
      return;
    }
  } 
  println("### received another osc message. with address pattern "+theOscMessage.addrPattern());
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p00_oscP5_multicast_server" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
