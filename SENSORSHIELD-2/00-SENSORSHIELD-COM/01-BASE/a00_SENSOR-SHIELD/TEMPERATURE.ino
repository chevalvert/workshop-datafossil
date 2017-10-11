
///// TEMPERATURE FUNCTION AS SENSOR ///// 

float tempValue() {
  voltage = getVoltage(tempPin);
  degreesC = (voltage - 0.5) * 100.0; // convert the voltage to degrees Celsius
}

// function returns the voltage (0 to 5 Volts) on the analog input pin
float getVoltage(int pin) {
  return (analogRead(pin) * 0.004882814);
}

