
////// RECEIVE OSC VALUES FROM SERVER //////

void oscEvent(OscMessage theOscMessage) {
  // check if theOscMessage has the address pattern we are looking for.   
  if (theOscMessage.checkAddrPattern("/sensorGroup")==true) {
    // check if the typetag is the right one: float 
    if (theOscMessage.checkTypetag("fffffff")) {
      // parse theOscMessage and extract the values from the osc message arguments.      
      float firstValue = theOscMessage.get(0).floatValue();
      float secondValue = theOscMessage.get(1).floatValue();
      float thirdValue = theOscMessage.get(2).floatValue();
      float fourthValue = theOscMessage.get(3).floatValue();
      float fifthValue = theOscMessage.get(4).floatValue();
      float sixthValue = theOscMessage.get(5).floatValue();
      float seventhValue = theOscMessage.get(6).floatValue();


      // create float variables to pic the values from OSC and use it for drawing
      lightValue = firstValue;
      forceValue = secondValue;
      flexValue = thirdValue;
      pulseValue = fourthValue;
      distanceValue = fifthValue;
      humidityValue = sixthValue;
      temperatureValue = seventhValue;


      print("### message from SERVER sensors");
      println(" LIGHT "+firstValue+", FORCE "+secondValue+", FLEX "+thirdValue+", PULSE "+fourthValue+", DIST "+fifthValue+", HUM "+sixthValue+", TEMP "+seventhValue);
      return;
    }
  } 
  println("### received another osc message. with address pattern "+theOscMessage.addrPattern());
}