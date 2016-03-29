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
  noFill();

  ////// LIGHT //////
  mapLightValue =  map(lightValue, 200, 800, 255, 0);
  ellipse(150, 100, mapLightValue, mapLightValue);

  ////// FORCE //////
  mapForceValue =  map(forceValue, 20, 940, 0, 255);
  ellipse(300, 100, mapForceValue, mapForceValue);

  ////// FLEX ////// 
  mapFlexValue =  map(flexValue, 330, 200, 0, 255);
  ellipse(450, 100, mapFlexValue, mapFlexValue);

  ////// POULS //////  
  // constrain pulseValue to not exceed a maximum and a minimum value 
  float constPulseValue = constrain(pulseValue, 470, 540); 
  mapPulseValue =  map(constPulseValue, 470, 540, 20, 255);
  ellipse(600, 100, mapPulseValue, mapPulseValue);

  ////// DISTANCE //////  
  mapDistanceValue =  map(distanceValue, 10, 160, 255, 10);
  ellipse(150, 400, mapDistanceValue, mapDistanceValue);

  ////// HUMIDITY + TEMPERATURE //////
  mapHumidityValue =  map(humidityValue, 30, 100, 0, 255);
  ellipse(300, 400, mapHumidityValue, mapHumidityValue);

  mapTemperatureValue =  map(temperatureValue, 16, 30, 10, 255);
  ellipse(450, 400, mapTemperatureValue, mapTemperatureValue);
}