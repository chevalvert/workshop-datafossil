
///// HUMIDITY AND TEMPERATURE FUNCTIONS AS SENSOR ///// 

float humidityValue() {
  humd = myHumidity.readHumidity();
}

float temperatureValue() {
  temp = myHumidity.readTemperature();
}

