void dataVis() {

  // values from GUI
  stroke(c);
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

  // rotation
  float distAngle = radians(deg);


  ////// LIGHT //////
  mapLightValue =  map(lightValue, 250, 680, 10, 80);
  float x1LightAngle = width/2 + (sin(distAngle) * 10);
  float y1LightAngle = height/2 + (cos(distAngle) * 10);
  float x2LightAngle = width/2 + (sin(distAngle) * mapLightValue);
  float y2LightAngle = height/2 + (cos(distAngle) * mapLightValue);
  line(x1LightAngle, y1LightAngle, x2LightAngle, y2LightAngle);


  ////// VIBRATION //////
  mapVibrationValue =  map(vibrationValue, 0, 1023, 90, 170); 
  float x1VibAngle = width/2 + (sin(distAngle) * 90);
  float y1VibAngle = height/2 + (cos(distAngle) * 90);
  float x2VibAngle = width/2 + (sin(distAngle) * mapVibrationValue);
  float y2VibAngle = height/2 + (cos(distAngle) * mapVibrationValue);
  line(x1VibAngle, y1VibAngle, x2VibAngle, y2VibAngle);


  ////// POT ////// 
  mapPotValue =  map(potValue, 0, 1023, 260, 180);  
  float x1PotAngle = width/2 + (sin(distAngle) * 180);
  float y1PotAngle = height/2 + (cos(distAngle) * 180);
  float x2PotAngle = width/2 + (sin(distAngle) * mapPotValue);
  float y2PotAngle = height/2 + (cos(distAngle) * mapPotValue);
  line(x1PotAngle, y1PotAngle, x2PotAngle, y2PotAngle);


  ////// TEMPÉRATURE ////// 
  mapTempValue =  map(tempValue, 18, 25, 340, 280);  
  float x1TempAngle = width/2 + (sin(distAngle) * 280);
  float y1TempAngle = height/2 + (cos(distAngle) * 280);
  float x2TempAngle = width/2 + (sin(distAngle) * mapTempValue);
  float y2TempAngle = height/2 + (cos(distAngle) * mapTempValue);
  line(x1TempAngle, y1TempAngle, x2TempAngle, y2TempAngle);


  ////// ORIENTATION X //////  
  mapXValue =  map(xValue, -2, 2, 400, 440);  
  float x1XAngle = width/2 + (sin(distAngle) * 360);
  float y1XAngle = height/2 + (cos(distAngle) * 360);
  float x2XAngle = width/2 + (sin(distAngle) * mapXValue);
  float y2XAngle = height/2 + (cos(distAngle) * mapXValue);
  line(x1XAngle, y1XAngle, x2XAngle, y2XAngle);


  ////// ORIENTATION Y //////
  mapYValue =  map(yValue, -2, 2, 460, 500); 
  float x1YAngle = width/2 + (sin(distAngle) * 440);
  float y1YAngle = height/2 + (cos(distAngle) * 440);
  float x2YAngle = width/2 + (sin(distAngle) * mapYValue);
  float y2YAngle = height/2 + (cos(distAngle) * mapYValue);
  line(x1YAngle, y1YAngle, x2YAngle, y2YAngle);


  ////// ORIENTATION Z //////
  mapZValue =  map(zValue, -2, 2, 560, 620);
  float x1ZAngle = width/2 + (sin(distAngle) * 560);
  float y1ZAngle = height/2 + (cos(distAngle) * 560);
  float x2ZAngle = width/2 + (sin(distAngle) * mapZValue);
  float y2ZAngle = height/2 + (cos(distAngle) * mapZValue);
  line(x1ZAngle, y1ZAngle, x2ZAngle, y2ZAngle);


  deg += 5;

  // inclinaison
  if (orientation == 1) {
    fill(0, 5);
    text("Portrait Up", width/4, 50);
  } else if (orientation == 2) {
    fill(0, 5);
    text("Portrait Down", width/2.75, 50);
  } else if (orientation == 3) {
    fill(0, 5);
    text("Landscape Right", width/2, 50);
  } else if (orientation == 4) {
    fill(0, 5);
    text("Landscape Left", width/1.5, 50);
  } else {
    fill(0, 5);
    text("Flat", width/1.25, 50);
  }


  if (deg > 360) {
    endRecord();
    noLoop();
  }
}