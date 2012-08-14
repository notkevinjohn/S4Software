//File: SolarTracker
//Author: Kevin Zack
//Date Started: 10/28/11
#include <LiquidCrystal.h>
LiquidCrystal lcd(2, 3, 4, 5, 6, 7);
//************ Declare Variables *****************

int SensorRight; // Read the value of the Right Sensor
int SensorLeft;
int goRight; // Used to tell system the sensor is ouputing to go Right
int goLeft; 
int StepPos; // Total Position of the system
int StepCoil; // The coil number active
int StepRight; // Tells the stepper to go right
int StepLeft;
int Error;      // used to have a plus or minus so not constantly moving
int Step1  =  9; // Stepper Pins
int Step2  =  10;
int Step3  =  11;
int Step4  =  12;
int Delay;       // Change the delay of the system
int Manual    = A3;        // Pin delcorations for the overide switches
int Increase  = A4;
int Decrease  = A5;
int ManualOverride;    // Variables for the override
int ManualIncrease;
int ManualDecrease;
int Timer;

//************ Setup *******************************

void setup()
{
  ManualOverride  =  0;  // Starts in Overide position
  StepPos         =  12;  // Start the Stepper Position in the center
  StepCoil        =  1;   // Starts with coil number 1
  StepRight       =  0;  
  StepLeft        =  0;
  goRight         =  0;
  goLeft          =  0;
  Delay           =  50;
  Error           =  35; // Change the Value to give error correction to the system
  
  pinMode(Step1,OUTPUT); // Declare each pin as an output or input
  pinMode(Step2,OUTPUT);
  pinMode(Step3,OUTPUT);
  pinMode(Step4,OUTPUT);
  pinMode(SensorRight, INPUT);
  pinMode(SensorLeft, INPUT);
  pinMode(Manual,INPUT);
  pinMode(Increase,INPUT);
  pinMode(Decrease,INPUT);
  lcd.begin(16, 2);// set up the LCD's number of columns and rows:
}


//************ Sensor *****************************
void loop()
{
    lcd.setCursor(0,0);
    lcd.print("Manually Changing");
    lcd.setCursor(2,1);
    lcd.print("Position...");
    
         digitalWrite(Step4,LOW); // /Turns off coil
         digitalWrite(Step1,HIGH);
         digitalWrite(Step2,HIGH); //Activates coil
         StepCoil  =  2; // Changes StepCoil variable to next coil
         delay(Delay); // stays on for the delay time to allow full transition

         digitalWrite(Step1,LOW);
         digitalWrite(Step2,HIGH);
         digitalWrite(Step3,HIGH);
         StepCoil  =  3;
         delay(Delay);

         digitalWrite(Step2,LOW);
         digitalWrite(Step3,HIGH);
         digitalWrite(Step4,HIGH);
         StepCoil=4;
         delay(Delay);

         digitalWrite(Step3,LOW);
         digitalWrite(Step4,HIGH);
         digitalWrite(Step1,HIGH);
         StepCoil=1;
         delay(Delay);
   

}

