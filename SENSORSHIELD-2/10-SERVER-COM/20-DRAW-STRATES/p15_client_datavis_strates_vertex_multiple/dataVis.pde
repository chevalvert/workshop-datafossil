
////// DRAW USING VALUES FROM SERVER //////

void dataVis() {

  background(255);  

  // values from GUI
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

  //////////  LIGHT  //////////
  fill(255, 0, 0);
  mapLightValue =  map(lightValue, 160, 720, height-20, 20); 
  text("Light value : "+ round(lightValue), 30, height-30);

  // We are going to draw a vertex out of the wave points from flex sensor
  beginShape(); 
  noFill();
  stroke(255, 0, 0);

  // Read the array from the end to the beginning to avoid overwriting the data
  for (int a = y2.length-20; a > 0; a--) {
    y1[a] = y1[a-1];
  }

  // Add input value to the beginning
  y1[0] = round(mapLightValue);

  // Display values as vertex
  for (int a = 20; a < y1.length-20; a++) {
    vertex(a, y1[a]);
  }

  // create a rect with the vertex and close it
  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);


  //////////  VIBRATION  //////////
  fill(0, 255, 0);
  mapVibrationValue =  map(vibrationValue, 980, 1023, height-60, 20); 
  text("Vibration value : "+ round(vibrationValue), 30, height-40);

  beginShape(); 
  noFill();
  stroke(0, 255, 0);

  for (int z = y2.length-20; z > 0; z--) {
    y2[z] = y2[z-1];
  }

  y2[0] = round(mapVibrationValue);

  for (int z = 20; z < y2.length-20; z++) {
    vertex(z, y2[z]);
  }

  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);


  //////////  POT  //////////
  fill(0, 0, 255);
  mapPotValue =  map(potValue, 0, 1023, 20, height-20); 
  text("Pot value : "+ round(potValue), 30, height-50);

  beginShape(); 
  noFill();
  stroke(0, 0, 255);

  for (int c = y3.length-20; c > 0; c--) {
    y3[c] = y3[c-1];
  }

  y3[0] = round(mapPotValue);

  for (int c = 20; c < y3.length-20; c++) {
    vertex(c, y3[c]);
  }

  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);


  //////////  TEMP  //////////
  fill(0);
  mapTempValue =  map(tempValue, 20, 25, 20, height-60); 
  text("Distance value : "+ round(tempValue), 30, height-60);

  beginShape(); 
  noFill();
  stroke(0);

  for (int d = y4.length-20; d > 0; d--) {
    y4[d] = y4[d-1];
  }

  y4[0] = round(mapTempValue);

  for (int d = 20; d < y4.length-20; d++) {
    vertex(d, y4[d]);
  }

  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);
}