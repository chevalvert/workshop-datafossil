void dataVis() {
  // values from GUI
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);
  stroke(c);

  // draw with the values from the server   

  // draw rectangles
  noFill();
  rect(30, i, width-60, 30);  
  i += 30; 

  ellipse(width/2, z, 5, 5);
  z += 30;

  ////// FORCE VALUE //////  
  mapFlexValue = map(flexValue, 120, 315, 0, 90);
  fill(0);  
  text(round(mapFlexValue)+" °", 50, yPos); 
  text ("flex = "+round(flexValue), 100, yPos);
  yPos= yPos + 30;

  delay(500);

  // save PDF and stop program
  if (yPos > height-40) {
    endRecord();
    noLoop();
  }
}