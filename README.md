# iot-protocol-transfer-core

This's an iot a framework.
At first the devices are based on RS485 communication interface and the core will provide other ways like 433mhz,zigbee,gprs and more.
The devices use modbus as protocol.To link the RS485 Devices we need a converter to change the protocol from RS485 modbos to socket traditional protocol.
We use a TCP socket server based on netty to ask the devices for data cyclicaly.Every server channel links one converter,and storage the devices' basic data which link this converter.While the channel getting the deviece's data,publishing it to mqtt's topic.Our mqtt broker is based on emqx.
Persistence layer is based on infuluxDB.Thanks to influx's component -- telegraf,influxDB can easily subscribe the mqtt topic.
Finally we transfer the data from devices to influxDB,and we can get the data from influxDB for application.
