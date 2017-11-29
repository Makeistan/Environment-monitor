#include <ESP8266WiFi.h>
const char* ssid     = "umar phone";      // SSID
const char* password = "useyourownwifi";
const char* host = "192.168.1.104";  // IP serveur - Server IP
const int   port = 1234;            // Port serveur - Server Port
const int   watchdog = 5000;        // Fréquence du watchdog - Watchdog frequency
unsigned long previousMillis = millis();
WiFiClient client;
String data;
#include<string.h>
byte buff[2];
int pin = D8;//DSM501A input D8
unsigned long duration;
unsigned long starttime;
unsigned long endtime;
unsigned long sampletime_ms = 30000;
float lowpulseoccupancy = 0;
float lowpulseinsec = 0;
float ratio = 0;
float concentration = 0;

int i = 0;


void setup() {
  Serial.begin(9600);
  Serial.print("Connecting to ");
  Serial.println(ssid);

  pinMode(D8, INPUT);
  starttime = millis();


  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  if (!client.connect(host, port)) {
    Serial.println("connection failed");
    return;
  }
  else
  {
    Serial.println("connection sucessful");
  }

}
void loop()
{

  duration = pulseIn(pin, LOW);
  lowpulseoccupancy += duration;
  endtime = millis();
  //Serial.println("Batmeez1 . . . . .");
  if ((endtime - starttime) > sampletime_ms)
  {
    lowpulseinsec = (lowpulseoccupancy - endtime + starttime + sampletime_ms) / 1000;
    ratio = lowpulseinsec / (30 * 10); // Integer percentage 0=>100
    concentration = 1.1 * pow(ratio, 3) - 3.8 * pow(ratio, 2) + 520 * ratio + 0.62; // using spec sheet curve
    //Serial.print("lowpulseoccupancy:");
    //Serial.print(lowpulseoccupancy);
    //Serial.print("    ratio:");
    //Serial.print(ratio);
    //Serial.print("    DSM501A:");
    Serial.println(concentration);
    data = (String)concentration;
    // client.println(data);
    //Serial.println(data);
    starttime = millis();
    lowpulseinsec = 0;
    ratio = 0;
    lowpulseoccupancy = 0;
  }


  //Serial.println(concentration);
  //Serial.println("value:");
}
