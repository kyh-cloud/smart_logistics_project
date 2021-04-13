import serial
import re
port = serial.Serial("COM12", "9600")#파이썬으로 시리얼 통신을 조작하기 위한 모듈입니다.
while True:
        try:
            oridata = port.readline()
            fron_label = 'FF'
            left_label  = 'LL'
            left_back_label  = 'SS'
            right_label = 'RR'
            right_back_label = 'BB'
            temp = oridata.decode('utf-8')
            data = temp.split(',')
            print(data)
            front = data[0]
            left = data[1]
            bleft = data[2]
            right = data[3]
            bright = data[4]

            if((right_label.startswith(right[0:2]))and(left_label.startswith(left[0:2]))):
                    lft= "".join(re.findall("\d+",left))
                    rit="".join(re.findall("\d+",right))
                    blft= "".join(re.findall("\d+",bleft))
                    brit="".join(re.findall("\d+",bright))
                    fro="".join(re.findall("\d+",front))
                    print("left =" + lft +"/"+"right =" + rit +"/"+ "left_back =" + blft +"/"+"right_back =" + brit +"/"+"front =" + fro)
            else:
                    print('l`dont no')
        except KeyboardInterrupt:
            break
port.close()