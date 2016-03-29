
////// DRAW USING VALUES FROM SERVER //////

void dataVis() {

  // values from GUI
  stroke(c);
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

  // draw with the values from the server   
  noFill();

  ////// LIGHT //////
  mapLightValue =  map(lightValue, 190, 560, 20, 100);
  line(xPos, mapLightValue, xPos, 100); 

  ////// FORCE //////
  mapForceValue =  map(forceValue, 10, 1023, 220, 140 );  
  line(xPos, mapForceValue, xPos, 220); 

  ////// FLEX //////
  mapFlexValue =  map(flexValue, 190, 320, 260, 340);  
  line(xPos, mapFlexValue, xPos, 340); 

  ////// DISTANCE //////
  mapDistanceValue =  map(distanceValue, 14, 160, 460, 380);  
  line(xPos, mapDistanceValue, xPos, 460); 

  ////// TEMPERATURE //////
  mapTemperatureValue =  map(temperatureValue, 16, 28, 580, 500);  
  line(xPos, mapTemperatureValue, xPos, 580);   

  ////// HUMIDITÉ //////
  mapHumidityValue =  map(humidityValue, 30, 100, 700, 620);  
  line(xPos, mapHumidityValue, xPos, 700);

  ////// POULS //////  
  float constPulseValue = constrain(pulseValue, 470, 540); // constrain pulseValue to no exceed a maximum and a minimum value 
  mapPulseValue =  map(constPulseValue, 470, 540, 820, 740);
  line(xPos, mapPulseValue, xPos, 820);

  // avancer x
  xPos= xPos + 10;
  delay(250);

  if (xPos > width-20) {
    endRecord();
    noLoop();
  }
}