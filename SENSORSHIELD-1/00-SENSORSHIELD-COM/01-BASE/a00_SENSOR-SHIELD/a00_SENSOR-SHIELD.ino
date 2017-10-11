
///// SENSOR SHIELD /////

/*
  Sensor shield library
  Lionel Radisson - @Makio135
  https://github.com/MAKIO135/sensorShieldLib

  Sensors
    - LDR sensor (Light Dependent Resistor) https://www.adafruit.com/product/161
    - FSR sensor (Round Force-Sensitive Resistor) https://www.adafruit.com/product/166
    - FLEX sensor https://www.adafruit.com/product/1070
    - Pulse sensor https://pulsesensor.com/ 
    - Sonar LV-MaxSonar-EZ https://www.adafruit.com/product/982
    - Humidity and temperature sensor break out HTU21D https://www.sparkfun.com/products/retired/12064
*/


///// SENSOR SHIELD LIB /////

#include <sensorShieldLib.h>
SensorShield board;

///// HUMIDITY AND TEMP SENSOR HTU21D /////

#include <Wire.h>
#include <SparkFunHTU21D.h>
HTU21D myHumidity;


///// SENSOR'S PINS /////

int lightPin = A0; // LDR pin
int sonarPin = 10; // sonar pin
int ledPin = 11; // led pin


///// OUTPUTS /////

int lightLevel;
int mappedLightLevel;
float pulse, inches, cm;
float humd, temp;


void setup() {
  // initialize and start Serial with 9600 baudrate
  board.init();
  board.setSensorSensitivity(0);

  // add analog sensors to sensorShield
  board.addSensor("capteurLDR", A0);
  board.addSensor("capteurFSR", A1);
  board.addSensor("capteurFLEX", A2);
  board.addSensor("capteurPOULS", A3);

  // add sonar function as a sensor to sensorShield
  board.addSensor("capteurSONAR", cmValue);

  // add humidity and temp functions as sensors
  myHumidity.begin();
  board.addSensor("humidity", humidityValue);
  board.addSensor("temperature", temperatureValue);

  // LED display
  pinMode(ledPin, OUTPUT);
}


void loop() {
  // read the value, if there are changes update it and send it through serial
  board.update();

  // turn on led with LDR input values
  lightLevel = analogRead(lightPin);
  mappedLightLevel = map(lightLevel, 150, 650, 0, 255);
  analogWrite(ledPin, mappedLightLevel);
}






