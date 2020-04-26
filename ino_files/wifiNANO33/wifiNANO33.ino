/*
  Web client
 This sketch connects to a website (http://www.google.com)
 using a WiFi shield.
 This example is written for a network using WPA encryption. For
 WEP or WPA, change the WiFi.begin() call accordingly.
 This example is written for a network using WPA encryption. For
 WEP or WPA, change the WiFi.begin() call accordingly.
 Circuit:
 * WiFi shield attached
 created 13 July 2010
 by dlf (Metodo2 srl)
 modified 31 May 2012
 by Tom Igoe
 */


#include <SPI.h>
#include <WiFi101.h>


// include the library code:
#include <LiquidCrystal.h>

// initialize the library by associating any needed LCD interface pin
// with the arduino pin number it is connected to
const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
String soundOfDuck = "CUAK!";
//const int offset = soundOfDuck.length();
const int offset = 0;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);


///////please enter your sensitive data in the Secret tab/arduino_secrets.h
char ssid[] = "";        // your network SSID (name)
char pass[] = "";    // your network password (use for WPA, or use as key for WEP)

//char ssid[] = "Marcel";        // your network SSID (name)
//char pass[] = "oxxo8701";    // your network password (use for WPA, or use as key for WEP)
int keyIndex = 0;            // your network key Index number (needed only for WEP)
String wholeString;
int chaginShape = 0;
int status = WL_IDLE_STATUS;
int offSetPictures = 0;
int offSetColPictures = 0;
// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
//IPAddress server(74,125,232,128);  // numeric IP for Google (no DNS)
IPAddress server(10,8,12,191);  // numeric IP for Google (no DNS)
//char server[] = "http://10.8.12.191/";    // name address for Google (using DNS)

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):
WiFiClient client;

void setup() {

  // set up the LCD's number of columns and rows:
  lcd.begin(16, 2);
  
  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // check for the presence of the shield:
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue:
    while (true);
  }

  // attempt to connect to WiFi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid, pass);

    // wait 10 seconds for connection:
    delay(10000);
  }
  Serial.println("Connected to wifi");
  printWiFiStatus();

  Serial.println("\nStarting connection to server...");
  // if you get a connection, report back via serial:
  if (client.connect(server, 80)) {
    Serial.println("connected to server");
    
//    // Make a HTTP request:
      client.println("GET /rd HTTP/1.1");
      client.println("Host: 192,168,137,1");
      //client.println("Connection: close");
      client.println();
      client.println();

     
  }
}

void loop() {
  // if there are incoming bytes available
  // from the server, read them and print them:
 
  int xdxd = 0;
  while (client.available()) {
     
    char c = client.read();
 
    Serial.write(c);
    if(xdxd)
      wholeString = wholeString + c;
    if(c == '*')
    {
      chaginShape = 1;
      xdxd = 1;
      wholeString = "";
    }
  }
  xdxd = 0;
  //delay(2000);
  Serial.clear();
  //Serial.write(wholeString.charAt(5));
  
  //char wtf[40];
  //String xd = wholeString + "";
  //wholeString.toCharArray(wtf,wholeString.length());
  //char* xdf = strdup(wholeString);
  //strcpy(wtf,xdf);
  if(chaginShape == 1)
  {
    byte newFigure[8];
    for(int positionRow = 0; positionRow < 8 ; positionRow++)
    {
      byte column = B00000;
      int positionRowXd = positionRow*5;
      int positionColumn = 0;
      for(; positionColumn < 4; positionColumn++)
      {
         if(wholeString.charAt(positionRowXd + positionColumn) == '1')
         {
          //Serial.write("1");
           column = column | 1;
         }
         //if(positionColumn != 4)
         column = column << 1;
      }
      if(wholeString.charAt(positionRowXd + positionColumn) == '1')
         {
          //Serial.write("1");
           column = column | 1;
         }
      
      newFigure[positionRow] = column;
      
    }
    lcd.createChar(0, newFigure);
     
   
    chaginShape = 0;
  }
  //lcd.print("" + wholeString);
  lcd.clear();
  if(offSetPictures >= 16)
  {
    offSetPictures = 0;
    if(offSetColPictures >= 1)
      offSetColPictures = 0;
    else
      offSetColPictures++;
  }
  lcd.setCursor(offSetPictures,offSetColPictures);
  lcd.write(byte(0));
  offSetPictures++;
  //lcd.print("" + wholeString);
  delay(100);
  // if the server's disconnected, stop the client:
  if (!client.connected()) {
    if (client.connect(server, 80)) {
    Serial.println("connected to server");
    
//    // Make a HTTP request:
      client.println("GET /rd HTTP/1.1");
      client.println("Host: 192,168,137,1");
      //client.println("Connection: close");
      client.println();

     
  }
  }
}


void printWiFiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}
