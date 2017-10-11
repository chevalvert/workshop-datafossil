
////// SEND OSC VALUES FROM SENSORS //////

void mousePressed() {
  // create a new OscMessage with an address pattern, in this case /message from SERVER
  OscMessage myOscMessage = new OscMessage("/message from SERVER");
  // send the OscMessage to the multicast group
  oscP5.send(myOscMessage);
}


void oscEvent(OscMessage theOscMessage) {
  // check if theOscMessage has the address pattern we are looking for.   
  if (theOscMessage.checkAddrPattern("/sensorGroup")==true) {
    // check if the typetag is the right one: 8*float 
    if (theOscMessage.checkTypetag("ffffffff")) {
      // parse theOscMessage and extract the values from the osc message arguments.      
      float firstValue = theOscMessage.get(0).floatValue();
      float secondValue = theOscMessage.get(1).floatValue();
      float thirdValue = theOscMessage.get(2).floatValue();
      float fourthValue = theOscMessage.get(3).floatValue();
      float fifthValue = theOscMessage.get(4).floatValue();
      float sixthValue = theOscMessage.get(5).floatValue();
      float seventhValue = theOscMessage.get(6).floatValue();
      float eigthValue = theOscMessage.get(7).floatValue();
      
      print("### received an osc message from computer 1 /sensorGroup ");
      println(" sensor: LIGHT "+firstValue+", VIBRATION "+secondValue+", POT "+thirdValue+
        ", TEMP "+fourthValue+", ORIENT "+fifthValue+", X "+sixthValue+", Y "+seventhValue+", Z "+eigthValue);
      return;
    }
  } 
  println("### received another osc message: "+theOscMessage.addrPattern());
}