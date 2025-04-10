#include <mcp_can.h>
#include <SPI.h>

MCP_CAN CAN0(10);

unsigned long messageId = 2559918081;

void setup() {

  Serial.begin(115200);

  // Initialize MCP2515 running at 8MHz with a baudrate of 500kb/s | masks and filters disabled
  if(CAN0.begin(MCP_ANY, CAN_500KBPS, MCP_8MHZ) == CAN_OK){
    Serial.println("MCP2515 initialized successfully!");
     CAN0.setMode(MCP_NORMAL);
  }else{
    Serial.println("Error Initializing MCP2515 ...");
  }

}

byte data[8] = {0x0A, 0x80, 0x0D, 0x00, 0x0E, 0x00, 0x0E, 0xA0};

void loop() {
 byte sndStat = CAN0.sendMsgBuf(messageId, 8, data);
  if(sndStat == CAN_OK){
    Serial.println("Message Sent Successfully!");
  } else {
    Serial.println("Error Sending Message...");
  }
  delay(710); // in miliseconds!

}