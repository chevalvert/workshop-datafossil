
////// DRAW USING VALUES FROM SERVER //////

void dataVis() {
  
  // values from GUI
  stroke(c);
  noFill();
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);
  
  ////// LIGHT //////
  mapLightValue =  map(lightValue, 190, 750, 20, 100);
  line(xPos, mapLightValue, xPos, 100); 

  ////// FORCE //////
  mapVibrationValue =  map(vibrationValue, 0, 1023, 220, 140 );  
  line(xPos, mapVibrationValue, xPos, 220); 

  ////// POT ////// 
  mapPotValue =  map(potValue, 0, 1023, 260, 340);  
  line(xPos, mapPotValue, xPos, 340); 

  ////// TEMPÉRATURE ////// 
  mapTempValue =  map(tempValue, 18, 25, 460, 380);  
  line(xPos, mapTempValue, xPos, 460); 

  ////// ORIENTATION X //////
  mapXValue =  map(xValue, -2, 2, 580, 500);  
  line(xPos, mapXValue, xPos, 580);   

  ////// ORIENTATION Y //////
  mapYValue =  map(yValue, -2, 2, 700, 620);  
  line(xPos, mapYValue, xPos, 700);

  ////// ORIENTATION Z //////  
  mapZValue =  map(zValue, -2, 2, 820, 740);
  line(xPos, mapZValue, xPos, 820);

  // avancer x
  xPos= xPos + 10;
  delay(10);

  if (xPos > width-20) {
    endRecord();
    noLoop();
  }
}