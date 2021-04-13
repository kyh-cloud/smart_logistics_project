import pymysql
import serial
import re
port = serial.Serial("COM13", "9600")
conn = pymysql.connect(
    host ="34.64.231.31",
    db ="itsea",
    user = "itsea",
    passwd = "itsea123"
    )
curs = conn.cursor()
while True:
    try:
        oridata = port.readline()  #시리얼 포트에서 읽어들인 값    을 data라는 변수에 저장합니다.
        print(oridata)
        light_label  = 'LL' #왼쪽을 판단하기 위해 사용되는 비교문자
        distance_label = 'DD' #오른쪽을 판단하기 위해 사용되는 비교문자
        temp = oridata.decode('utf-8')#바이트를 문자열로 바꾸어주는 역할
        data = temp.split(',') #문자열를 ,를 기준으로 나누어줌
        print(data)
        light = data[0]#list 형식의 data값의 첫번째 list 값인 왼쪽 초음파센서값을 넣어줌
        distance = data[1]#list 형식의 data값의 두번째 list 값인 오른쪽 초음파센서값을 넣어줌
        if((light_label.startswith(light[0:2]))and(distance_label.startswith(distance[0:2]))):
            lit="".join(re.findall("\d+",light))            
            dis="".join(re.findall("\d+",distance))
            curs.execute("""INSERT INTO port (light,dis) VALUES (%s,%s)""", (int(lit),int(dis)))
            print("light ="+ lit + "/" + "distnace ="+ dis)
            # 데이터베이스에 읽어들인 값을 저장합니다.
            # """INSERT INTO 테이블명 (열 이름) VALUES (데이터의 형태)""", (저장할 값) 형태입니다.
            conn.commit()
        else:
            print('i don')
    except KeyboardInterrupt:
        break
port.close()
conn.close()