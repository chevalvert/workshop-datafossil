String[] lines;
int index = 0;
int x=0 ;

void setup() {
  size(1250, 900);
  background(255);
  lines = loadStrings("sensor_value.txt");
  // reading rythm
  frameRate(60);
}

void draw() {
  // check all the lines on the .txt
  if (index < lines.length) {
    // Split this line into pieces at each tab character
    String[] pieces = split(lines[index], '\t');
    // Take action only if there are 7 values on the line
    // (this will avoid blank or incomplete lines)
    if (pieces.length == 7) {

      // light - values on column 0
      int yLight = int(pieces[0]);
      stroke(255, 0, 0);
      point(x, yLight);

      // force - values on column 1
      int yForce = int(pieces[1]);
      stroke(0, 255, 0);
      point(x, yForce);

      // flex  - values on column 2
      int yFlex = int(pieces[2]);
      stroke(0, 0, 255);
      point(x, yFlex);

      // pulse  - values on column 3
      int yPulse = int(pieces[3]);
      stroke(0, 255, 255);
      point(x, yPulse);

      // distance  - values on column 4
      int yDistance = int(pieces[4]);
      stroke(127, 0, 255);
      point(x, yDistance);

      // humidity  - values on column 5
      int yHumidity = int(pieces[5]);
      stroke(51, 151, 255);
      point(x, yHumidity);

      // humidity  - values on column 6
      int yTemp = int(pieces[6]);
      stroke(255, 125, 0);
      point(x, yTemp);

      x += 1;
    }
    // Go to the next line for the next run through draw()
    index = index + 1;
  }
}