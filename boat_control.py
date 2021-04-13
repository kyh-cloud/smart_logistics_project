# -*- coding: utf-8 -*-
import pymysql
import serial
import re

port = serial.Serial("COM12", "9600")
conn = pymysql.connect(
    host ="34.64.193.144",
    db ="itsea",
    user = "itsea",
    passwd = "itsea123"
    )

curs = conn.cursor()
sql = "select hum from hum order by(id)DESC"
curs.execute(sql)
data = curs.fetchone()
x = data #첫번째 row
print(x)

stream = str(x)
stre= "".join(re.findall('[\d]+[.\d ]+',stream))
print(stre)

stre=stre.encode('utf-8')
fstre = float(stre)

if(fstre<100):
 send = 1
 print(send)
else:
 send = 0
 print(send)

while (True):
 port.write(stre)

port.close()
conn.close()