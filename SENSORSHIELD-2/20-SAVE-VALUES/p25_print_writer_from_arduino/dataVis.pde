
////// DRAW USING VALUES FROM ARDUINO SENSORSHIELD //////

void dataVis() {
  background(255);

  // textes
  fill(0);
  text("LUMIÈRE", 125, 200); 
  text("VIBRATION", 275, 200);
  text("POTENTIOMÈTRE", 405, 200);
  text("TEMPÉRATURE", 565, 200);
  text("ORIENTATION X", 105, 500); 
  text("ORIENTATION Y", 255, 500);
  text("ORIENTATION Z", 410, 500);
  noFill();

  ////// LIGHT //////
  mapLightValue =  map(lightValue, 140, 420, 0, 100);
  ellipse(150, 100, mapLightValue, mapLightValue);

  ////// VIBRATION //////
  mapVibrationValue =  map(vibrationValue, 900, 1023, 0, 100);
  ellipse(300, 100, mapVibrationValue, mapVibrationValue);

  ////// POT ////// 
  mapPotValue =  map(potValue, 0, 1023, 0, 100);
  ellipse(450, 100, mapPotValue, mapPotValue);

  ////// TEMPÉRATURE ////// 
  mapTempValue =  map(tempValue, 20, 25, 10, 100);
  ellipse(600, 100, mapTempValue, mapTempValue);

  ////// ORIENTATION X //////  
  mapXValue =  map(xValue, -2, 2, 10, 100);
  ellipse(150, 400, mapXValue, mapXValue);

  ////// ORIENTATION Y //////
  mapYValue =  map(yValue, -2, 2, 10, 100);
  ellipse(300, 400, mapYValue, mapYValue);

  ////// ORIENTATION Z //////
  mapZValue =  map(zValue, -2, 2, 10, 100);
  ellipse(450, 400, mapZValue, mapZValue);

  // inclinaison
  if (orientation == 1) {    
    text("Portrait Up", 575, 500);
  } else if (orientation == 2) {
    text("Portrait Down", 575, 500);
  } else if (orientation == 3) {
    text("Landscape Right", 575, 500);
  } else if (orientation == 4) {
    text("Landscape Left", 575, 500);
  } else {
    text("Flat", 575, 500);
  }
}