
////// CONTROL P5 //////

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

  // DRAWING 
  Group g2 = cp5.addGroup("Drawing")
    .setBackgroundColor(color(0, 64))
    .setBackgroundHeight(150)
    ;


  cp5.addSlider("strokeWeight")
    .setPosition(10, 20)
    .setSize(100, 20)
    .setRange(0, 20)
    .setValue(1)
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

  // Accordion.MULTI to allow multiple group to be open at a time
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