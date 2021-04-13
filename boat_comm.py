import pymysql
import serial
import re
port = serial.Serial("/dev/ttyACM0", "9600")
conn = pymysql.connect(
    host ="34.64.231.31",
    db ="itsea",
    user = "itsea",
    passwd = "itsea123"
    )
curs = conn.cursor()
while True:
    try:
        port.write(stre)
        oridata = port.readline()  #시리얼 포트에서 읽어들인 값을 data라는 변수에 저장합니다.
        print(oridata)
        left_label  = 'LL' #왼쪽을 판단하기 위해 사용되는 비교문자
        right_label = 'RR' #오른쪽을 판단하기 위해 사용되는 비교문자
        front_label ='FF'
        temp = oridata.decode('utf-8')#바이트를 문자열로 바꾸어주는 역할
        data = temp.split(',') #문자열를 ,를 기준으로 나누어줌
        print(data)
        front = data[0]#list 형식의 data값의 첫번째 list 값인 왼쪽 초음파센서값을 넣어줌
        left = data[1]#list 형식의 data값의 두번째 list 값인 오른쪽 초음파센서값을 넣어줌
        right = data[2]#list 형식의 data값의 두번째 list 값인 오른쪽 뒤 초음파센서값을 넣어줌
        if((front_label.startswith(front[0:2]))and(right_label.startswith(right[0:2]))and(left_label.startswith(left[0:2]))):
            fro="".join(re.findall("\d+",front))
            lft="".join(re.findall("\d+",left))
            rit="".join(re.findall("\d+",right))
            curs.execute("""INSERT INTO distance (dis_f,dis_l,dis_r) VALUES (%s,%s,%s)""", (int(fro),int(lft), int(rit)))
            print("front ="+ fro + "/" + "left ="+ lft +"/"+"right ="+ rit)
            # 데이터베이스에 읽어들인 값을 저장합니다.
            # """INSERT INTO 테이블명 (열 이름) VALUES (데이터의 형태)""", (저장할 값) 형태입니다.
            conn.commit()
        else:
            print('i don')
    except KeyboardInterrupt:
        break
port.close()
conn.close()