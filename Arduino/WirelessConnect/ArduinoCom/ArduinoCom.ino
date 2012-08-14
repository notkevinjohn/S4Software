/*
  DigitalReadSerial
 Reads a digital input on pin 2, prints the result to the serial monitor 
 
 This example code is in the public domain.
 */
String tempString;
byte tempByte;
char tempChar;
String finalString;
void setup() {
  Serial.begin(9600);
  pinMode(13, OUTPUT);
  Serial.write("Starting Arduino");
  tempChar = ' ';
}

void loop() {
 // if(Serial.available() > 0)
  //Serial.write(Serial.read());
  ///*
  if(Serial.available() > 0)
  {
     tempChar = Serial.read();
     if(tempChar == '!')
      {
        tempChar = Serial.read()'
        if(tempChar = '0')
        {
        digitalWrite(13, HIGH);
        tempChar = ' ';
        Serial.println("HIGH");
      }
      else if(tempChar == '0')
      {
        digitalWrite(13, LOW);
        tempChar = ' '; 
       Serial.println("LOW");     
      }
  }
}




