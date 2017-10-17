const int timeBetweenReadings = 4;

void setup() {
  Serial.begin(115200);
}

void loop() {
  Serial.println(analogRead(A0));
  delay(timeBetweenReadings);
}
