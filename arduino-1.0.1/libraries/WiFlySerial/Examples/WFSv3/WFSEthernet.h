/*
 * WFSEthernet.h
 * Arduino Ethernet class for wifi devices
 * Based on Arduino 1.0 Ethernet class
 * 
 * Credits:
 * First to the Arduino Ethernet team for their model upon which this is based.
 * Modifications are
 * Copyright GPL 2.1 Tom Waldock 2012
 Version 1.06
 
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 
 */
#ifndef WFSethernet_h
#define WFSethernet_h

#include <inttypes.h>
#include <WiFlySerial.h>

#include "WFSIPAddress.h"
#include "WFSEthernetClient.h"
#include "WFSEthernetServer.h"


#define MAX_SOCK_NUM 4

class WFSEthernetClass {
private:
  IPAddress _dnsServerAddress;
public:
  static uint8_t _state[MAX_SOCK_NUM];
  static uint16_t _server_port[MAX_SOCK_NUM];


  
  // Devices often appreciate a chance to initialize before commencing operations
  boolean initDevice();
  
  // set up wifi association settings
  boolean configure(uint8_t AuthMode, uint8_t JoinMode, uint8_t DCHPMode);
  boolean credentials( char* pSSID, char* pPassphrase);
  boolean setNTPServer( char* pNTPServer , float fTimeZoneOffsetHrs);

  int begin();
  int begin( IPAddress local_ip);
  int begin( IPAddress local_ip, IPAddress dns_server);
  int begin( IPAddress local_ip, IPAddress dns_server, IPAddress gateway);
  int begin( IPAddress local_ip, IPAddress dns_server, IPAddress gateway, IPAddress subnet);

  IPAddress localIP();
  IPAddress subnetMask();
  IPAddress gatewayIP();
  IPAddress dnsServerIP();
  IPAddress ntpServerIP();

  friend class WFSEthernetClient;
  friend class WFSEthernetServer;
};

extern WFSEthernetClass WFSEthernet;
extern WiFlySerial wifi;



#endif
