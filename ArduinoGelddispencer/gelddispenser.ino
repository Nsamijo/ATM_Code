#include "Adafruit_Thermal.h"
#include "SoftwareSerial.h"
#include <Wire.h>
#include <Servo.h>

#define TX_pin 6
#define RX_pin 5
#define servo1 22

Servo servo;
SoftwareSerial serial(RX_pin, TX_pin);
Adafruit_Thermal printer(&serial);

char b10;
char b50;
String iban;
String datum;
String tijd;
String amount;
String take;
boolean check = false;

void setup() {
  servo.attach(servo1);
  Wire.begin(8);
  Wire.onReceive(receiveData);
  //starten printer zodat deze meteen kan printen indien aangeroepen
  printer.begin();
  printer.sleep();
  serial.begin(9600);
  Serial.begin(9600);
}

void loop() {
  if (!check) {
    //Serial.println("mehh");
    return;
  }
  getStrings(take);
//  Serial.println(b10);
//  Serial.println(b50);
//  Serial.println(iban);
//  Serial.println(datum);
//  Serial.println(tijd);
//  Serial.println(amount);
    Serial.println(take);
  if(take.length() > 4){
    printReceipt(datum, tijd, iban, amount);
  }

  b10 = "";
  b50 = "";
  iban = "";
  datum = "";
  tijd = "";
  amount = "";
  check = false;
}

void receiveData() {
  if (check) {
    return;
  }

  take = "";
  while (Wire.available() > 0) {
    char a = Wire.read();
    take += a;
  }
  check = true;
}

void getStrings(String take) {
  for (int i = 0; i < take.length(); i++) {
    if (i == 0) {
       b10 = take.charAt(i);
    }else if(i == 1){
      b50 = take.charAt(i);
    }else if (i > 2 && i < 7) {
       iban += take.charAt(i);
     }else if(i > 7 && i < 18){
       datum += take.charAt(i);
     }else if(i > 18 && i < 23){
      tijd += take.charAt(i);
     }else if(i > 23){
      amount += take.charAt(i);
     }
  }
}

void printReceipt(String date, String Time, String iban, String amount) {
  //maken de printer wakker
  printer.wake();
  //print bank naam, datum en tijd
  printer.justify('L'); //print aan linkerzijde
  printer.boldOn(); //printen bold letters
  printer.setSize('M');//middel lettergrootte
  printer.println("Monopoly Bank");
  printer.println("USSR");
  printer.boldOff();
  printer.setSize('S');//kleine lettergrootte
  //data client
  printer.println("Datum: " + date);
  printer.println("Tijd: " + Time);
  printer.println("IBAN: XXXXXXXXXX" + iban);
  printer.println("--------------------------------");
  printer.println("Amount withdrawn: ");
  printer.println("$ " + amount);
  printer.println("--------------------------------");
  printer.println("Thanking you for using Monopoly Bank");
  printer.println("Have a nice day!");
  printer.println(":)");
  printer.feed(3); //printen blanke regels zodat de bon naar buiten gaat

  printer.sleep(); //zetten de printer weer op slaapstand
  printer.setDefault(); //resetten de printer
}
