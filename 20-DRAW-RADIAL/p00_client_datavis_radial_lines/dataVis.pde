
////// DRAW USING VALUES FROM SERVER //////

void dataVis() {

  // values from GUI
  stroke(c);
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);


  // values from the server   

  // rotation
  float distAngle = radians(deg);


  ////// LIGHT //////
  mapLightValue =  map(lightValue, 200, 520, 10, 80);
  float x1LightAngle = width/2 + (sin(distAngle) * 10);
  float y1LightAngle = height/2 + (cos(distAngle) * 10);
  float x2LightAngle = width/2 + (sin(distAngle) * mapLightValue);
  float y2LightAngle = height/2 + (cos(distAngle) * mapLightValue);
  line(x1LightAngle, y1LightAngle, x2LightAngle, y2LightAngle);


  ////// FORCE //////
  mapForceValue =  map(forceValue, 10, 1023, 90, 170 );  
  float x1FrcAngle = width/2 + (sin(distAngle) * 90);
  float y1FrcAngle = height/2 + (cos(distAngle) * 90);
  float x2FrcAngle = width/2 + (sin(distAngle) * mapForceValue);
  float y2FrcAngle = height/2 + (cos(distAngle) * mapForceValue);
  line(x1FrcAngle, y1FrcAngle, x2FrcAngle, y2FrcAngle);


  ////// FLEX //////
  mapFlexValue =  map(flexValue, 190, 350, 260, 180);  
  float x1FlxAngle = width/2 + (sin(distAngle) * 180);
  float y1FlxAngle = height/2 + (cos(distAngle) * 180);
  float x2FlxAngle = width/2 + (sin(distAngle) * mapFlexValue);
  float y2FlxAngle = height/2 + (cos(distAngle) * mapFlexValue);
  line(x1FlxAngle, y1FlxAngle, x2FlxAngle, y2FlxAngle);


  ////// DISTANCE //////
  mapDistanceValue =  map(distanceValue, 14, 162, 340, 280);  
  float x1DistAngle = width/2 + (sin(distAngle) * 280);
  float y1DistAngle = height/2 + (cos(distAngle) * 280);
  float x2DistAngle = width/2 + (sin(distAngle) * mapDistanceValue);
  float y2DistAngle = height/2 + (cos(distAngle) * mapDistanceValue);
  line(x1DistAngle, y1DistAngle, x2DistAngle, y2DistAngle);


  ////// TEMPERATURE //////
  mapTemperatureValue =  map(temperatureValue, 18, 25, 400, 440);  
  float x1TempAngle = width/2 + (sin(distAngle) * 360);
  float y1TempAngle = height/2 + (cos(distAngle) * 360);
  float x2TempAngle = width/2 + (sin(distAngle) * mapTemperatureValue);
  float y2TempAngle = height/2 + (cos(distAngle) * mapTemperatureValue);
  line(x1TempAngle, y1TempAngle, x2TempAngle, y2TempAngle);


  ////// HUMIDITÉ //////
  mapHumidityValue =  map(humidityValue, 48, 75, 460, 500);  
  float x1HumAngle = width/2 + (sin(distAngle) * 440);
  float y1HumAngle = height/2 + (cos(distAngle) * 440);
  float x2HumAngle = width/2 + (sin(distAngle) * mapHumidityValue);
  float y2HumAngle = height/2 + (cos(distAngle) * mapHumidityValue);
  line(x1HumAngle, y1HumAngle, x2HumAngle, y2HumAngle);


  ////// POULS //////  
  float constPulseValue = constrain(pulseValue, 560, 620); // constrain pulseValue to no exceed a maximum and a minimum value 
  mapPulseValue =  map(constPulseValue, 470, 540, 560, 620);
  float x1PulseAngle = width/2 + (sin(distAngle) * 560);
  float y1PulseAngle = height/2 + (cos(distAngle) * 560);
  float x2PulseAngle = width/2 + (sin(distAngle) * mapPulseValue);
  float y2PulseAngle = height/2 + (cos(distAngle) * mapPulseValue);
  line(x1PulseAngle, y1PulseAngle, x2PulseAngle, y2PulseAngle);


  deg += 5;
  delay(250);

  if (deg > 360) {
    endRecord();
    noLoop();
  }
}