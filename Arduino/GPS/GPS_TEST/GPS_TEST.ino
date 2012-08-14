/*
Example GPS Parser based off of arduiniana.org TinyGPS examples.
For the SparkFun GPS Shield. Make sure the switch is set to DLINE.
Uses the NewSoftSerial library for serial communication with your GPS,
so connect your GPS TX and RX pin to any digital pin on the Arduino,
just be sure to define which pins you are using on the Arduino to
communicate with the GPS module.
REVISIONS:
1-17-11
changed values to RXPIN = 2 and TXPIN = to correspond with
hardware v14+. Hardware v13 used RXPIN = 3 and TXPIN = 2.*/
    
// In order for this sketch to work, you will need to download
// NewSoftSerial and TinyGPS libraries from arduiniana.org and put them
// into the hardware->libraries folder in your ardiuno directory.

//These are the libraries we must include so we can get data from the GPS
#include <NewSoftSerial.h>
#include <TinyGPS.h>


//These are for the LCD display so we can see where we are when on the move.
#include <LiquidCrystal.h>

// Define our lcd and enter which pins we are using
LiquidCrystal lcd(4,5,6,7,8,9);

// Define which pins you will use on the Arduino to communicate with your
// GPS. In this case, the GPS module's TX pin will connect to the
// Arduino's RXPIN which is pin 3.
#define RXPIN 2
#define TXPIN 3

//Set this value equal to the baud rate of your GPS
#define GPSBAUD 4800

//Create an instance of the TinyGPS object
TinyGPS gps;

//Initialize the NewSoftSerial library to the pins you defined above
NewSoftSerial uart_gps(RXPIN, TXPIN);

// This is where you declare prototypes for the functions that will be
// using the TinyGPS library.
void getgps(TinyGPS &gps);

// In the setup function, you need to initialize two serial ports; the
// standard hardware serial port (Serial()) to communicate with your
// terminal program an another serial port (NewSoftSerial()) for your
// GPS.



//=======================================================================================================\\
// \\
// \\
// Print Messages \\
// In this section all functions print messages to the LCD screen and serial monitor \\
// \\
//=======================================================================================================\\


void printCoordinates(float latitude, float longitude)
{
  //This message will print out the latitude and longitude
  lcd.clear(); //Start off by clearing the LCD of any previous image
  lcd.setCursor(0,0); // Positions starting point on LCD, column 0, row 0 (that is, the top left of our LCD)
  lcd.print("Lat: ");
  lcd.print(latitude);
  lcd.setCursor(0,1); // positions starting point on LCD, column 0, row 1 (that is, the bottom left of our LCD)
  lcd.print("Long: ");
  lcd.print(longitude);
  
  //Prints and identical message to the serial monitor
  Serial.print("Lat/Long: ");
  Serial.print(latitude,5);
  Serial.print(", ");
  Serial.println(longitude,5);
  
  //delay for 5 secons so that the information can be read
  delay(5000);
}


void printSpeedAltitude()
{
  //This functions prints the speed and altitude out to the LCD screen
  int currentSpd = gps.f_speed_kmph();
  
  //The speed is always extremely high or low when inside or close to
  //not moving. These boundries are for no speed and the fastest
  //unmanned vehicle ever to travel on earth
  if(currentSpd < 0 || currentSpd > 10400)
  {
    currentSpd = 0;
  }
  
  lcd.clear();
  // print altitude at the top left of our LCD
  lcd.setCursor(0,0);
  lcd.print("Alt:"); lcd.print(gps.f_altitude()); lcd.print("m");
  // print speed at the bottom left of our LCD
  lcd.setCursor(0,1);
  lcd.print("Spd:"); lcd.print(currentSpd); lcd.print("Kph");
  
  //prints to serial monitor
  Serial.print("Altitude (meters): "); Serial.println(gps.f_altitude());
  // And same goes for speed
  Serial.print("Speed(kmph): "); Serial.println(currentSpd);
  Serial.println();
  
  //delay for 5 secons so that the information can be read
  delay(5000);
}

void printDateTime(int year, byte byteMonth, byte byteDay, byte byteHour, byte byteMinute, byte byteSecond)
{
  //Here you can put in the time difference from GMT so we
  //can later convert to local time
  int timeDiff = 4;
  
  //This is converting all the byte date into integers so they
  //can be worked with much more easily within the code
  int month = (int) byteMonth;
  int day = (int) byteDay;
  int hour = (int) byteHour;
  int minute = (int) byteMinute;
  int second = (int) byteSecond;
  
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Date: ");
  
  //If the data has not been acquired yet it will display as 0's
  //so we replace zeros with a calibration message until we have
  //the current date.
  if (day == 0 || month == 0)
  {
    lcd.print("Acquiring.");
  }
  else
  {
  lcd.print(year); //Prints the year
  lcd.print("/");
  //If the month is single digit this puts a zero in front
  //cleaning it up
  if (month < 10)
  {
    lcd.print("0");
  }
  
  lcd.print(month); //Prints the month of the year
  lcd.print("/");
  
  //Print a leading zero if necessary
  if (day < 10)
  {
    lcd.print("0");
  }
  
  lcd.print(day); //Prints the day of the month
  }
  
  lcd.setCursor(0,1); //Move the location of where we write to the LCD
  lcd.print("Time: ");
  
  //Convert the given time into our local time
  int localHour = hour - timeDiff;
  
  //You can get negative hours from the previous subtraction
  //In this case, just add 24 hours to be within a valid range again
  if (localHour < 0)
  {
    localHour = localHour + 24;
  }
  
  //Print a leading zero if necessary
  if (hour < 10)
  {
    lcd.print("0");
  }
  
  lcd.print(localHour); //Prints the hour of the day
  lcd.print(":");
  
  //Print a leading zero if necessary
  if (minute < 10)
  {
    lcd.print("0");
  }
  
  lcd.print(minute); //Prints the minute of the hour
  lcd.print(":");
  
  //Print a leading zero if necessary
  if (second < 10)
  {
    lcd.print("0");
  }
  
  lcd.print(second);
  
  //Print data and time to the serial monitor
  Serial.print("Date: ");
  Serial.print(month);
  Serial.print("/");
  Serial.print(day);
  Serial.print("/");
  Serial.print(year);
  Serial.print(" Time: ");
  Serial.print(hour);
  Serial.print(":");
  Serial.print(minute);
  Serial.print(":");
  Serial.print(second);
  delay(5000); //5 second delay so information can be read
}


//=======================================================================================================\\
// \\
// \\
// Arduino Main Functions \\
// \\
// \\
//=======================================================================================================\\


void setup()
{
  lcd.begin(16, 2); // need to specify how many columns and rows are in the LCD unit
  lcd.clear(); // this clears the LCD. You can use this at any time
  // This is the serial rate for your terminal program. It must be this
  // fast because we need to print everything before a new sentence
  // comes in. If you slow it down, the messages might not be valid and
  // you will likely get checksum errors.
  Serial.begin(115200);
  //Sets baud rate of your GPS
  uart_gps.begin(GPSBAUD);

  //output the waiting message to the LCD
  lcd.setCursor(0,0);
  lcd.print("GPS QuickStart");
  lcd.setCursor(0,1);
  lcd.print("Locking Position");

  //The same message is outputted to the serial monitor instead of the LCD screen.
  Serial.println("");
  Serial.println(" GPS QuickStart");
  Serial.println(" ...waiting for lock... ");
  Serial.println("");
}


void loop()
{
  while (uart_gps.available()) // While there is data on the RX pin...
  {
      int c = uart_gps.read(); // load the data into a variable...
      if (gps.encode(c)) // if there is a new valid sentence...
      {
        getgps(gps); // then grab the data.
      }
  }
}


//=======================================================================================================\\
// \\
// \\
// GPS Functions \\
// \\
// \\
//=======================================================================================================\\


// The getgps function will get and print the values we want.
void getgps(TinyGPS &gps)
{
  // To get all of the data into varialbes that you can use in your code,
  // all you need to do is define variables and query the object for the
  // data. To see the complete list of functions see keywords.txt file in
  // the TinyGPS and NewSoftSerial libs.
  
  // Define the variables that will be used for coordinates
  float latitude, longitude;
  
  // Same goes for date and time
  int year;
  byte month, day, hour, minute, second, hundredths;
  
  // Then call this function to get coordinates from the library
  gps.f_get_position(&latitude, &longitude);
  
  //We grab a cracked date and time from the tinygps library
  gps.crack_datetime(&year,&month,&day,&hour,&minute,&second,&hundredths);
  
  //Print the LCD coordinates to the LCD and serial monitor
  printCoordinates(latitude,longitude);
  
  //print date and time to the LCD
  printDateTime(year, month, day, hour, minute, second);

  //print velocity and elevation to LCD
  printSpeedAltitude();
}
