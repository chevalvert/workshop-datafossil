int x=0 ;

void setup() {
  size(750, 1000);
  background(255);
}

void draw() {
  String[] lines = loadStrings("sensor_value.txt");

  for (int i = 0; i < lines.length; i++) {
    // Split this line into pieces at each tab character
    String[] pieces = split(lines[i], '\t');
    // Take action only if there are 7 values on the line
    // (this will avoid blank or incomplete lines)
    if (pieces.length == 8) {

      // light - values on column 0
      int yLight = int(pieces[0]);
      stroke(255, 0, 0);
      point(x, yLight);

      // vibration - values on column 1
      int yVib = int(pieces[1]);
      stroke(0, 255, 0);
      point(x, yVib);

      // pot  - values on column 2
      int yPot = int(pieces[2]);
      stroke(0, 0, 255);
      point(x, yPot);

      // temp  - values on column 3
      int yTemp = int(pieces[3]);
      stroke(0, 255, 255);
      point(x, yTemp);

      // orientation  - value on column 4
      int yOrient = int(pieces[4]);
      stroke(50, 155, 255);
      point(x, yOrient);

      // x  - values on column 5
      int xMagnet = int(pieces[5]);
      stroke(127, 0, 255);
      point(x, xMagnet);

      // y  - values on column 6
      int yMagnet = int(pieces[6]);
      stroke(51, 151, 255);
      point(x, yMagnet);

      // z  - values on column 7
      int zMagnet = int(pieces[7]);
      stroke(255, 125, 0);
      point(x, zMagnet);

      x += 1;
    }
  }
  noLoop(); // stop drawing loop if you have achieved the last line of the .txt
}