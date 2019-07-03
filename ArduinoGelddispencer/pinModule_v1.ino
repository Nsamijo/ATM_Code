/*
   ATM
   Written by Jacob Cuperus and Nathan Samijo
   Version 1.0
   Date: 12th June 2019
*/

/*
   Keypad pins defined
   pins 5-8 are the rows
   pins 1-4 are the columns
*/

/*
   Pin wiring
   RFID       Arduino Uno
   SDA        Digital 10
   SCK        Digital 13
   MOSI       Digital 11
   MISO       Digital 12
   IRQ        Unconnected
   GND        GND
   RST        Digital 9
   3.3V       3.3V

   Keypad     Arduino Uno
   D1         Digital 4
   D2         Digital 3
   D3         Digital 2
   D4         Analog 3
   D5         Digital 8
   D6         Digital 7
   D7         Digital 6
   D8         Digital 5

   Printer    Arduino Uno
   TX
   RX
   GND
   
*/

#include <Keypad.h>
#include <SPI.h>
#include <MFRC522.h>
#include <Wire.h>

/* RFID */
#define SS_PIN 10 //RFID data pin
#define RST_PIN 9 //RFID reset pin

MFRC522 rfid(SS_PIN, RST_PIN);  //Instance of the class
MFRC522::MIFARE_Key rfidKey;

//Change the block where the IBAN will be read
int block = 1;

//Init array that will store new IBAN
byte readBackBlock[14];

/* Keypad */
const byte ROWS = 4;  //Four rows
const byte COLS = 4; //Four columns

char keypadKeys[ROWS][COLS] = {
  {'1','4','7','*'},
  {'2','5','8','0'},
  {'3','6','9','#'},
  {'A','B','C','D'}
};

byte rowPins[ROWS] = {8, 7, 6, 5};      //Connect to the row pinouts of the keypad
byte colPins[COLS] = {4, 3, 2, A3};  //Connect to the columns pinouts of the keypad

Keypad keypad = Keypad(makeKeymap(keypadKeys), rowPins, colPins, ROWS, COLS);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(500000);
  SPI.begin();      //Init SPI bus
  Wire.begin();      //Init wire transmission
  rfid.PCD_Init();  //Init MFRC522
}

void loop() {
  getDataJava();
  RFID();
  keyPad();
}

void RFID() {
  //Look for new cards
  if (!rfid.PICC_IsNewCardPresent())
    return;

  //Verify if the NUID has been readed
  if (!rfid.PICC_ReadCardSerial())
    return;

  // Set authentication key
  for (byte i = 0; i < 6; i++) {
    rfidKey.keyByte[i] = 0xFF;  //Start with a clean rfid key
  }

  // Read the block
  readBlock(block, readBackBlock);
  Serial.print("~~"); //This indicate that the serial print a rfid nuid is

  String content = "";
  for (int index = 0; index < sizeof(readBackBlock); index++) {
    content += char(readBackBlock[index]);
  }
  Serial.println(content);

  // End authentication
  rfid.PICC_HaltA();
  rfid.PCD_StopCrypto1();
}

void readBlock(int blockNumber, byte arrayAddress[]) {
  authenticateBlockAction(block);

  //Read the IBAN from block 1 on the card
  byte buffersize = 18;
  byte status = rfid.MIFARE_Read(blockNumber, arrayAddress, &buffersize);
  if (status != MFRC522::STATUS_OK) {
    Serial.println("Read failed");
    return;
  }
}

void authenticateBlockAction(int blockNumber){
  int largestModulo4Number = blockNumber / 4 * 4;
  int trailerBlock = largestModulo4Number + 3;

  byte status = rfid.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, trailerBlock, &rfidKey, &(rfid.uid));
  if (status != MFRC522::STATUS_OK) {
    Serial.println("Authentication failed");
    return;
  }
}

void keyPad() {
  char keypadKey = keypad.getKey();

  if (keypadKey != NO_KEY) {
    Serial.print("$$"); //This indicate that the serial print are keypad keys
    Serial.println(keypadKey);
  }
}

//writes data to slave
void writeData(String s) {
  Wire.beginTransmission(8);
  Wire.write(s.c_str());
  Wire.endTransmission();
}

//gets data from java
void getDataJava(){
  if (!Serial.available()){
    return;
    }

    String stuff = Serial.readString();
    if (stuff.length() > 0) {
      writeData(stuff);
 }
}
