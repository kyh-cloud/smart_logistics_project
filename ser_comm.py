# -*- coding: utf-8 -*-
import pymysql
import serial
import re
import time

port2 = serial.Serial("COM8", "9600")
conn = pymysql.connect(
    host ="34.64.231.31",
    db ="itsea",
    user = "itsea",
    passwd = "itsea123"
    )

curs = conn.cursor()

# 서버에서 불러온 값을 아두이노로 전송
def two():
    sql = "select * from distance order by(id)DESC"
    curs.execute(sql)
    data = curs.fetchone()
    x = data                            # 첫번째 row

    # stream = str(x)
    distance_l = int(x[1])
    distance_r = int(x[2])
    distance_f = int(x[3])
    str_distance = str(distance_f) + "," + str(distance_l) + "," + str(distance_r)
    print(str_distance + "\n")
    str_distance = str_distance.encode('ascii')

    # stre = stre.encode('utf-8')         # stre를 문자열로 인코딩
    # fstre = int(stre)

    if(distance_f):
        # if(distance_f >= 100):
        #     left_motor_speed = 50
        #     right_motor_speed = 50
        #     str_speed = str(lefteed)            

        # elif((distance_f < 100) and (distance_f >= 50)):
        #     left_motor_speed = 40
        #     right_motor_speed _motor_speed) + "," + str(right_motor_speed)
        #     str_speed = str_speed.encode('ascii')
        #     port2.write(str_sp= 40
        #     str_speed = str(left_motor_speed) + "," + str(right_motor_speed)
        #     str_speed = str_speed.encode('ascii')
        #     port2.write(str_speed)

        # elif((distance_f < 50) and (distance_f >= 15)):
        #     left_motor_speed = 25
        #     right_motor_speed = 25
        #     str_speed = str(left_motor_speed) + "," + str(right_motor_speed)
        #     str_speed = str_speed.encode('ascii')
        #     port2.write(str_speed)

        # 장애물 탐지 시, 오른쪽 모터를 돌려 방향 전환 후
        # 딜레이를 걸고, 왼쪽 모터를 돌려서 방향을 다시 잡아줌
        if(distance_f < 15):
            port2.write('0'.encode('ascii'))

        # else:
        #     port2.write('1'.encode('ascii'))
        
        # if(distance_f >= 100):
        #     left_motor_speed = 50
        #     right_motor_speed = 50
        
        # else if((distance_f < 100) and (distance_f >= 40)):
        #     left_motor_speed = 40
        #     right_motor_speed = 40        
        
        # else if((distance_f < 40) and (distance_f >= 25)):
        #     left_motor_speed = distance_f
        #     right_motor_speed = distance_f    

        # 본격적으로 접안이 시작됨. 큰선박의 서브모터는 일정한 rpm으로 돌아가고 있다고 전제한다
        # else if(distance_f < 25 and distance_f >= 10):
        #     # 화물선이 항구와 평행한 상태이므로 화물선과 동일한 속도로 움직임으로써 접안에 개입을 안함
        #     left_motor_speed = 25
        #     right_motor_speed = 25
        
        # else if(distance_f < 10):
        #     left_motor_speed = 0
        #     right_motor_speed = 0
        # str_speed = (str)left_motor_speed + ',' + (str)right_motor_speed
        # str_speed = 1234

    # port2.write(str_speed)
        conn.commit()
        send = 1
        print(send)
    else:
        send = 0
        print(send)
        conn.commit()

    # while (True):
    #     port.write(stre)

# 아두이노에서 값을 불러와서 서버로 전송
def one():
    oridata = port2.readline()       #시리얼 포트에서 읽어들인 값을 data라는 변수에 저장합니다.
    print(oridata)

    left_label  = 'LL'              #왼쪽을 판단하기 위해 사용되는 비교문자
    right_label = 'RR'              #오른쪽을 판단하기 위해 사용되는 비교문자
    front_label = 'FF'              #전방을 판단하기 위해 사용되는 비교문자
    temp = oridata.decode('utf-8')  #바이트를 문자열로 바꾸어주는 역할
    data = temp.split(',')          #문자열를 ','를 기준으로 나누어줌
    print(data)

    front = data[0]                 #list 형식의 data값의 첫번째 list 값인 왼쪽 초음파센서값을 넣어줌
    left = data[1]                  #list 형식의 data값의 두번째 list 값인 오른쪽 초음파센서값을 넣어줌
    right = data[2]                 #list 형식의 data값의 두번째 list 값인 오른쪽 뒤 초음파센서값을 넣어줌

    if((front_label.startswith(front[0:2]))and(right_label.startswith(right[0:2]))and(left_label.startswith(left[0:2]))):
        fro="".join(re.findall("\d+",front))
        lft="".join(re.findall("\d+",left))
        rit="".join(re.findall("\d+",right))
        curs.execute("""INSERT INTO distance (dis_f,dis_l,dis_r) VALUES (%s,%s,%s)""", (int(fro),int(lft), int(rit)))
        print("front = "+ fro + " / " + "left = "+ lft +" / "+"right = "+ rit)
        # 데이터베이스에 읽어들인 값을 저장합니다.
        # """INSERT INTO 테이블명 (열 이름) VALUES (데이터의 형태)""", (저장할 값) 형태입니다.

        conn.commit()
    else:
        print('??????\n')
            
while True:
    try:
        one()
        two()
    except KeyboardInterrupt:
        print('종료')        
        break

port2.close()
conn.close()