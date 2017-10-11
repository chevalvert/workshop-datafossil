
///// SONAR FUNCTION AS SENSOR /////

float cmValue() {
  pinMode(sonarPin, INPUT);
  pulse = pulseIn(sonarPin, HIGH);
  inches = pulse / 147; // 147 uS per inch
  cm = inches * 2.54; // 2,54 cm per inch
}
