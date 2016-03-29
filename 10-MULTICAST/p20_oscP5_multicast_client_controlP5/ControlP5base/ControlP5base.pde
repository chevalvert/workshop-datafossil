/**
 * ControlP5 Accordion
 * by Andreas Schlegel, 2012
 * www.sojamo.de/libraries/controlp5
 */

import controlP5.*;

ControlP5 cp5;

Accordion accordion;

color c = color(0, 160, 100);

void setup() {
  size(600, 600);
  strokeWeight(1);
  smooth();
  gui();
}

void gui() {

  cp5 = new ControlP5(this);

  // CONTROL COLOURS
  Group g1 = cp5.addGroup("Colours")
    .setBackgroundColor(color(0, 64))
    .setBackgroundHeight(150)
    ;

  cp5.addRadioButton("colour")
    .setPosition(10, 20)
    .setItemWidth(20)
    .setItemHeight(20)
    .addItem("black", 0)
    .addItem("red", 1)
    .addItem("green", 2)
    .addItem("blue", 3)
    .setColorLabel(color(255))
    .activate(2)
    .moveTo(g1)
    ;

  // CONTROL DRAWING 
  Group g2 = cp5.addGroup("Drawing")
    .setBackgroundColor(color(0, 64))
    .setBackgroundHeight(150)
    ;


  cp5.addSlider("strokeWeight")
    .setPosition(60, 20)
    .setSize(100, 20)
    .setRange(0, 20)
    .setValue(1)
    .moveTo(g2)
    ;

  cp5.addSlider("size")
    .setPosition(60, 50)
    .setSize(100, 20)
    .setRange(0, 500)
    .setValue(100)
    .moveTo(g2)
    ;

  // create accordions
  accordion = cp5.addAccordion("acc")
    .setPosition(40, 40)
    .setWidth(200)
    .addItem(g1)
    .addItem(g2)
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

  // Accordion.MULTI to allow multiple group 
  // to be open at a time.
  accordion.setCollapseMode(Accordion.MULTI);

}

// SET COLOURS
void colour(int theC) {
  switch(theC) {
    case(0):
    c=color(0, 255);
    break;
    case(1):
    c=color(255, 0, 0, 255);
    break;
    case(2):
    c=color(0, 255, 0, 255);
    break;
    case(3):
    c=color(0, 0, 255, 255);
    break;
  }
} 



void draw() {
  background(220);

  fill(c);

  float s2 = cp5.getController("strokeWeight").getValue();
  strokeWeight(s2);
  float s1 = cp5.getController("size").getValue();
  ellipse(width/2, height/2, s1, s1);
}