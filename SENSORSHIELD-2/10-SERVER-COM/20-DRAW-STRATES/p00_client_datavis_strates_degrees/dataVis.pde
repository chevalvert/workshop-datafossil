
////// DRAW USING VALUES FROM SERVER //////

void dataVis() {

  // values from GUI
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);
  stroke(c);

  noFill();

  rect(30, i, width-60, 30);  
  i += 30; 

  ellipse(width/2, z, 5, 5);
  z += 30;

  ////// POT VALUE //////  
  mapPotValue = map(potValue, 0, 1023, 0, 90);
  fill(0);  
  text(round(mapPotValue)+" °", 50, yPos); 
  text ("pot = "+round(potValue), 100, yPos);
  yPos= yPos + 30;

  delay(500);

  // save PDF and stop program
  if (yPos > height-40) {
    endRecord();
    noLoop();
  }
}