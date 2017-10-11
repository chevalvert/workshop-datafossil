
///// SENSOR SHIELD /////

/*
  Sensor shield library
  Lionel Radisson - @Makio135
  https://github.com/MAKIO135/sensorShieldLib

  Sensors
    - LDR sensor (Light Dependent Resistor) https://www.adafruit.com/product/161
    - Piezo Vibration Sensor with Mass https://www.sparkfun.com/products/9197
    - Linear Soft Pot https://www.sparkfun.com/products/8680
    - Temperature sensor TMP36 https://www.adafruit.com/product/165
    - Accelerometer sensor Triple Axis MMA8452Q https://www.sparkfun.com/products/12756
*/


///// SENSOR SHIELD LIB /////

#include <sensorShieldLib.h>
SensorShield board;


///// SENSOR'S PINS /////

int lightPin = A0; // LDR pin
int piezoPin = A1; // piezo vibration pin
int potPin = A2; // soft pot pin
int tempPin = A3; // temperature pin
int ledPin = 11; // led pin


///// ACCELEROMETER SENSOR /////

#include <Wire.h> // Wire library for I2C 
#include <SFE_MMA8452Q.h> // SFE_MMA8452Q library for MMA8452Q

// create an instance of the MMA8452Q class:  "accel".
MMA8452Q accel;


///// OUTPUTS /////

float voltage, degreesC; // temp variables
float xOrientation, xValue, yValue, zValue; // accel variables
int lightLevel, mappedLightLevel; // led output variables



void setup() {
  // initialises and start Serial with 9600 baudrate
  board.init();

  // initialize the accelerometer
  accel.init();

  // add analog sensors to sensorShield
  board.addSensor("capteurLDR", A0);
  board.addSensor("capteurPIEZO", A1);
  board.addSensor("capteurPOT", A2);

  // add functions as sensors to sensorShield
  board.addSensor("capteurTEMP", tempValue);
  board.addSensor("inclinaison", xOrientationValue);
  board.addSensor("xValue", readX);
  board.addSensor("yValue", readY);
  board.addSensor("zValue", readZ);

  // LED display
  pinMode(ledPin, OUTPUT);
}


void loop() {
  // read the value, if there are changes update it and send it through serial
  board.update();

  // turn on led with LDR input values
  lightLevel = analogRead(lightPin);
  mappedLightLevel = map(lightLevel, 280, 600, 0, 255);
  analogWrite(ledPin, mappedLightLevel);
}






