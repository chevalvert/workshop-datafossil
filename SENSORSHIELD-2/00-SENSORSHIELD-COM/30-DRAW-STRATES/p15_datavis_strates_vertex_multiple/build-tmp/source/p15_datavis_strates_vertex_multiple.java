import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import processing.pdf.*; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class p15_datavis_strates_vertex_multiple extends PApplet {



// boolean record;


////// SENSORSHIELD //////  

Serial myPort;
JSONObject json;


////// CONTROL P5 //////


ControlP5 cp5;
Accordion accordion;
int c = color(0, 160, 100);


////// CAPTEURS INPUT //////

float lightValue; // LDR light
float vibrationValue; // piezo vibration
float potValue; // soft pot
float tempValue; // temp sensor
float orientation; // accel orientation
float xValue; // accel X
float yValue; // accel Y
float zValue; // accel Z


//////////  VARIABLES OUTPUT  //////////

float mapLightValue;
float mapVibrationValue;
float mapPotValue;
float mapTempValue;
float mapXValue;
float mapYValue;
float mapZValue;

int [] y1;
int [] y2;
int [] y3;
int [] y4;



public void setup() {
   
  printArray( Serial.list() );
  myPort = new Serial( this, Serial.list()[5], 9600 ); // indicate your arduino port
  myPort.clear();

  background(255);
  strokeWeight(1);
  stroke(0);
  
  fill(0);

  gui();

  y1 = new int[width];
  y2 = new int[width];
  y3 = new int[width];
  y4 = new int[width];
}


public void draw() {

  // if (record) {
  //   // #### will be replaced with the frame number
  //   beginRecord(PDF, "frame-####.pdf");
  // }


  // read sensorShield
  if ( myPort.available() > 0 ) {
    String data = myPort.readStringUntil( '\n' );
    if ( data != null ) {
      println( data ); 
      try {
        json = JSONObject.parse( data );
        // get the values of your sensors from serial (arduino)
        lightValue = json.getInt("capteurLDR");         
        vibrationValue = json.getInt("capteurPIEZO");        
        potValue = json.getInt("capteurPOT");
        tempValue = json.getInt("capteurTEMP");
        orientation = json.getInt("inclinaison");
        xValue = json.getInt("xValue");
        yValue = json.getInt("yValue");
        zValue = json.getInt("zValue");
      } 
      catch ( Exception e ) {
        e.printStackTrace();
      }
      dataVis();
    }
  }

  // if (record) {
  //   endRecord();
  //   record = false;
  // }
}


// save PDF
// void keyPressed() {
//   if (key == 'f') {
//     record = true;
//   }
// }

////// CONTROL P5 //////

public void gui() {

  cp5 = new ControlP5(this);


  // DRAWING 
  Group g1 = cp5.addGroup("Drawing")
    .setBackgroundColor(color(0, 64))
    .setBackgroundHeight(150)
    ;


  cp5.addSlider("strokeWeight")
    .setPosition(10, 20)
    .setSize(100, 20)
    .setRange(0, 20)
    .setValue(1)
    .moveTo(g1)
    ;

  // create accordions
  accordion = cp5.addAccordion("acc")
    .setPosition(40, 40)
    .setWidth(200)
    .addItem(g1)
    ;

  cp5.mapKeyFor(new ControlKey() {
    public void keyEvent() {
      accordion.open(0, 1);
    }
  }
  , 'o');
  cp5.mapKeyFor(new ControlKey() {
    public void keyEvent() {
      accordion.close(0, 1);
    }
  }
  , 'c');

  accordion.open(0, 1);

  // Accordion.MULTI to allow multiple group to be open at a time
  accordion.setCollapseMode(Accordion.MULTI);
}

public void dataVis() {

  background(255);  
  fill(0);

  // values from GUI
  float s1 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s1);

  //////////  LIGHT  //////////
  fill(255, 0, 0);
  mapLightValue =  map(lightValue, 160, 720, height-20, 20); 
  text("Light value : "+ round(lightValue), 30, height-30);

  // We are going to draw a vertex out of the wave points from flex sensor
  beginShape(); 
  noFill();
  stroke(255, 0, 0);

  // Read the array from the end to the beginning to avoid overwriting the data
  for (int a = y2.length-20; a > 0; a--) {
    y1[a] = y1[a-1];
  }

  // Add input value to the beginning
  y1[0] = round(mapLightValue);

  // Display values as vertex
  for (int a = 20; a < y1.length-20; a++) {
    vertex(a, y1[a]);
  }

  // create a rect with the vertex and close it
  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);


  //////////  VIBRATION  //////////
  fill(0, 255, 0);
  mapVibrationValue =  map(vibrationValue, 980, 1023, height-60, 20); 
  text("Vibration value : "+ round(vibrationValue), 30, height-40);

  beginShape(); 
  noFill();
  stroke(0, 255, 0);

  for (int z = y2.length-20; z > 0; z--) {
    y2[z] = y2[z-1];
  }

  y2[0] = round(mapVibrationValue);

  for (int z = 20; z < y2.length-20; z++) {
    vertex(z, y2[z]);
  }

  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);


  //////////  POT  //////////
  fill(0, 0, 255);
  mapPotValue =  map(potValue, 0, 1023, 20, height-20); 
  text("Pot value : "+ round(potValue), 30, height-50);

  beginShape(); 
  noFill();
  stroke(0, 0, 255);

  for (int c = y3.length-20; c > 0; c--) {
    y3[c] = y3[c-1];
  }

  y3[0] = round(mapPotValue);

  for (int c = 20; c < y3.length-20; c++) {
    vertex(c, y3[c]);
  }

  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);


  //////////  TEMP  //////////
  fill(0);
  mapTempValue =  map(tempValue, 20, 25, 20, height-60); 
  text("Distance value : "+ round(tempValue), 30, height-60);

  beginShape(); 
  noFill();
  stroke(0);

  for (int d = y4.length-20; d > 0; d--) {
    y4[d] = y4[d-1];
  }

  y4[0] = round(mapTempValue);

  for (int d = 20; d < y4.length-20; d++) {
    vertex(d, y4[d]);
  }

  vertex(width-20, height-20);
  vertex(20, height-20);
  endShape(CLOSE);
}
  public void settings() {  size(1250, 400);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "p15_datavis_strates_vertex_multiple" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
