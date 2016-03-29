
////// DRAW USING VALUES FROM SERVER //////

void dataVis() {

  background(255);

  // textes
  fill(0);
  text("LUMIÈRE", 125, 200); 
  text("PRESSION", 275, 200);
  text("FLEXION", 425, 200);
  text("POULS", 575, 200);
  text("DISTANCE", 125, 500); 
  text("HUMIDITÉ", 270, 500);
  text("TEMPÉRATURE", 410, 500);


  // values from GUI
  fill(c);
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

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
  mapDistanceValue =  map(distanceValue, 10, 160, 100, 10);
  ellipse(150, 400, mapDistanceValue, mapDistanceValue);

  ////// HUMIDITY + TEMPERATURE //////
  mapHumidityValue =  map(humidityValue, 30, 100, 0, 100);
  ellipse(300, 400, mapHumidityValue, mapHumidityValue);

  mapTemperatureValue =  map(temperatureValue, 16, 30, 10, 100);
  ellipse(450, 400, mapTemperatureValue, mapTemperatureValue);
}