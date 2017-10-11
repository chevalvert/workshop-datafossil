
///// ACCELEROMETER FUNCTION AS SENSOR /////

// X acceleration
float readX() {
  accel.read();
  xValue = accel.cx; // scale range of +/-2g
  return xValue;
}

// Y acceleration
float readY() {
  accel.read();
  yValue = accel.cy;
  return yValue;
}

// Z acceleration
float readZ() {
  accel.read();
  zValue = accel.cz;
  return zValue;
}

// Accel. orientation 
float xOrientationValue() {
  float xOrientation = 0;
  byte pl = accel.readPL();
  switch (pl)
  {
    case PORTRAIT_U:
      xOrientation = 1;
      //Serial.print("Portrait Up");
      break;
    case PORTRAIT_D:
      xOrientation = 2;
      //Serial.print("Portrait Down");
      break;
    case LANDSCAPE_R:
      xOrientation = 3;
      //Serial.print("Landscape Right");
      break;
    case LANDSCAPE_L:
      xOrientation = 4;
      //Serial.print("Landscape Left");
      break;
    case LOCKOUT:
      xOrientation = 5;
      //Serial.print("Flat");
      break;
  }
  return xOrientation;
}

