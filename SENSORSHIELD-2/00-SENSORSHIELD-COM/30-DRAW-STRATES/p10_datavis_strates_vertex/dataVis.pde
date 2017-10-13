void dataVis() {

  background(255);

  // values from GUI
  stroke(c);
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

  ////// POT //////
  mapPotValue =  map(potValue, 0, 1023, 20, height-20); 
  fill(0);
  text("Pot value : "+ potValue, 30, height-30);


  // draw a vertex out of the wave points from pot sensor
  beginShape(); 
  noFill();

  // Read the array from the end to the
  // beginning to avoid overwriting the data
  for (int i = y.length-20; i > 0; i--) {
    y[i] = y[i-1];
  }

  // Add input value to the beginning
  y[0] = round(mapPotValue);

  // Display values as vertex
  for (int i = 20; i < y.length-20; i++) {
    vertex(i, y[i]);
  }

  // create a rect with the vertex and close it
  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);
}