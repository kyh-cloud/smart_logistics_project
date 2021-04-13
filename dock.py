import pymysql
import serial
import re

port = serial.Serial("COM8", "9600")
conn = pymysql.connect(
    host ="34.64.231.31",
    db ="itsea",
    user = "itsea",
    passwd = "itsea123"
    )
curs = conn.cursor()

def two():
    sql = "select * from dock order by(id)DESC"    
    curs.execute(sql)
    data = curs.fetchone()
    x = data                            # 첫번째 row

    distance = int(x[2])
    str_distance = str(distance)
    print(str_distance + "\n")
    str_distance = str_distance.encode('ascii')

    # stre = stre.encode('utf-8')         # stre를 문자열로 인코딩
    # fstre = int(stre)

    if(distance):
        if(distance < 15):
            port.write('0'.encode('ascii'))
        send = 1
        print(send)
        conn.commit()
    else:
        send = 0
        print(send)
        conn.commit()

    # while (True):
    #     port.write(stre)
            
while True:
    try:
        two()
    except KeyboardInterrupt:
        print('종료')        
        break

port.close()
conn.close()