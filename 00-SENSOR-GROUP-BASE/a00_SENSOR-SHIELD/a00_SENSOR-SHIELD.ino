
///// SENSOR SHIELD /////
///// SensorShieldlib Lionel Radisson - @Makio135 /////

#include <sensorShieldLib.h>
SensorShield board;

///// HUMIDITY SENSOR /////

#include <Wire.h>
#include <SparkFunHTU21D.h> // HTU21D Humidity Sensor
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
  // initialises and start Serial with 9600 baudrate
  board.init();

  // add analog sensors
  board.addSensor("capteurLDR", A0);
  board.addSensor("capteurFSR", A1);
  board.addSensor("capteurFLEX", A2);
  board.addSensor("capteurPOULS", A3);

  // add sonar function as a sensor
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






