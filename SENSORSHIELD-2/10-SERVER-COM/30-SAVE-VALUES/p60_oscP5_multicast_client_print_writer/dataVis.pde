
////// DRAW USING VALUES FROM SERVER //////

void dataVis() {

  background(255);

  // textes
  fill(0);
  text("LUMIÈRE", 125, 200); 
  text("VIBRATION", 265, 200);
  text("POTENTIOMÈTRE", 400, 200);
  text("TEMPÉRATURE", 560, 200);
  text("X ACCEL", 125, 500); 
  text("Y ACCEL", 270, 500);
  text("Z ACCEL", 420, 500);
  noFill();

  ////// LIGHT //////  
  mapLightValue =  map(lightValue, 280, 600, 0, 150);
  ellipse(150, 100, mapLightValue, mapLightValue); 

  ////// VIBRATION //////  
  mapVibrationValue =  map(vibrationValue, 1023, 0, 10, 150);
  ellipse(300, 100, mapVibrationValue, mapVibrationValue);

  ////// POT //////  
  mapPotValue =  map(potValue, 1023, 0, 10, 150);
  ellipse(450, 100, mapPotValue, mapPotValue);

  ////// TEMP //////  
  mapTempValue =  map(tempValue, 17, 27, 10, 150);
  ellipse(600, 100, mapTempValue, mapTempValue);

  ////// X ////// 
  mapXValue =  map(xValue, -2, 2, 0, 150);
  ellipse(150, 400, mapXValue, mapXValue);

  ////// Y ////// 
  mapYValue =  map(yValue, -2, 2, 0, 150);
  ellipse(300, 400, mapYValue, mapYValue);

  ////// Z ////// 
  mapZValue =  map(zValue, -2, 2, 0, 150);
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
