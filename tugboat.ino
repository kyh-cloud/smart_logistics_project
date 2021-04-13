//블루투스모듈 HC-06(슬래이브만가능)으로 진행함 
//블루투스모듈 HC-05(슬래이브 마스터둘다가능)는 조금 코드가 다르다  
//HC-06 시리얼창에서 "line ending 없음" 설정할것
/* 시리얼 모니터 클릭 --> 보드레이트 설정, lineending 없음으로 설정
   AT --> OK, AT+NAME이름 --> 이름설정, AT+PIN숫자 --> 블루투스 핀 번호 설정
   AT+ROLE=M : 블루투스 모듈을 Master로 지정
   AT+ROLE=S : 블루투스 모듈을 Slave로 지정 
   AT커멘드는 오로지 슬레이브 모드에서만 인식한다! 주의!! */

// 접안시 예인선은 화물선의 머리부분에 접촉이 돼있는 상태

#include <Servo.h>
#include <SoftwareSerial.h>
#include <LiquidCrystal.h>

#define ECHO_f 50                        // 가장 앞면 초음파 센서
#define TRIG_f 51                        // f == front

#define ECHO_l 4                        // 왼쪽 옆면 초음파 센서
#define TRIG_l 5                        
#define ECHO_r 6                        // 오른쪽 옆면 초음파 센서
#define TRIG_r 7                        

Servo left_motor;                        // 왼쪽 모터
Servo right_motor;                       // 오른쪽 모터

int left_motor_speed;                    // 모터 속도 값!!
int right_motor_speed;                   // 모터로 들어가는 초기값은 0. 최소값.
//int center_motor_speed;

int buzzer = 2;                          // 부저

unsigned long previousMillis = 0;
const long delayTime = 1000;

//volatile int flow_frequency;             // 펄스측정
//unsigned int l_hour;                     // 1시간당 리터량 측정
//unsigned char flowsensor = 23;           // 센서 입력핀
//unsigned long currentTime;
//unsigned long cloopTime;

//void flow (){
//   flow_frequency++;
//}

void setup() {
//  pinMode(flowsensor, INPUT);  
//  digitalWrite(flowsensor, HIGH);         // Optional Internal Pull-Up
  
  Serial.begin(9600);                     // 시리얼 통신(9600레이트)으로 조작한다. 

/* 유량센서 */
//  attachInterrupt(0, flow, RISING); // Setup Interrupt  
//  sei(); // Enable interrupts  
//  currentTime = millis();  
//  cloopTime = currentTime;

/* 모터 */
  left_motor.attach(24, 1000, 2000);      // 이런식으로 서보를 선언해줘야 컨트롤이 가능하다.
  right_motor.attach(26, 1000, 2000);
  
  Serial.setTimeout(50);                  // 아래 parseInt로 값을 받는게 있는데, 이게 기본 딜레이가 1초가 있다.
                                          // 그걸 50ms로 설정해주는 것. 속도를 빠르게 하기 위함
  left_motor.write(0);                    // 초기값은 무조건 0!!! esc 캘리브레이션 할 때도 중요하다.
  right_motor.write(0);

  delay(5000);
  
  pinMode(buzzer,OUTPUT);
  
/* 초음파 센서 핀모드 설정 */  
  pinMode(TRIG_f, OUTPUT);
  pinMode(ECHO_f, INPUT);
  pinMode(TRIG_l, OUTPUT);
  pinMode(ECHO_l, INPUT);
  pinMode(TRIG_r, OUTPUT);
  pinMode(ECHO_r, INPUT);
}

/* 초음파 센서 제어부 */
void loop() {
//  currentTime = millis();
   
  // 매초, 시간당 리터량을 계산 및 출력함

//  if(currentTime = (cloopTime + 1000)){  
//    cloopTime = currentTime;                // cloopTime 갱신
//    
//    // Pulse frequency (Hz) = 7.5Q, Q는 유량속도(L/min)
//    l_hour = (flow_frequency * 60 / 7.5);   // (Pulse frequency x 60 min) / 7.5Q = flowrate in L/hour
//    flow_frequency = 0;                     // 카운터 리셋    
//  }
   
  // 초음파 센서 타입 정의
  unsigned long currentMillis = millis();
  long duration_f, distance_f; 
  long duration_l, distance_l;
  long duration_r, distance_r;

  // 앞쪽 초음파 센서 (f)
  digitalWrite(TRIG_f, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG_f, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_f, LOW);
  duration_f = pulseIn (ECHO_f, HIGH);
  distance_f = duration_f * 17 / 1000;
  
  // 왼쪽 초음파 센서 (l)
  digitalWrite(TRIG_l, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG_l, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_l, LOW);
  duration_l = pulseIn (ECHO_l, HIGH);
  distance_l = duration_l * 17 / 1000;
  
  // 오른쪽 초음파 센서 (r)
  digitalWrite(TRIG_r, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG_r, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_r, LOW);
  duration_r = pulseIn (ECHO_r, HIGH);
  distance_r = duration_r * 17 / 1000;


/* 측정된 물체로부터 거리값(cm값)을 보여줍니다. */
  // 초음파 부분 
  if(currentMillis - previousMillis >= delayTime){
    previousMillis = currentMillis;

  // 앞쪽 초음파 센서 view (f) 
  Serial.print("FF");
  Serial.print(distance_f);            
  Serial.print(",");
  
  // 왼쪽 초음파 센서 view
  Serial.print("LL");
  Serial.print(distance_l);
  Serial.print(",");

  // 오른쪽 초음파 센서 view (rf)
  Serial.print("RR");
  Serial.print(distance_r);              
  Serial.println(",");

//  // 유량센서
//  Serial.print("유량: ");
//  Serial.print(l_hour, DEC);              // 시간당 리터 출력
//  Serial.println(" L/hour");
  
  // 1초간격마다 출력 값을 비교하기 쉽도록 함
//  Serial.print("\n");
  }
  
  delay(500);

  /* 모터 제어부 */
  // 모터 동작 시작!
  if (Serial.available() > 0) {
    if (Serial.read() == 48) {
      left_motor.write(0);
      right_motor.write(0);
      delay(5000);
    }
  }
  else{
    if(distance_f >= 40){
      left_motor_speed = 40;
      right_motor_speed = 40;    
      left_motor.write(left_motor_speed);    // 시리얼 통신으로 받은 값을 모터에 넣어주자!
      right_motor.write(right_motor_speed);
    }
    
    // 이때부터 부저 동작!!  
  //  else{
  //    int val = map(distance_f,0,100,0,800);
  //    if (val < 800){
  //      digitalWrite(buzzer,HIGH);
  //      delay(200);
  //      digitalWrite(buzzer,LOW);
  //      delay(val);
              
    if((distance_f < 40) && (distance_f >= 25)){
      left_motor_speed = distance_f;
      right_motor_speed = distance_f;
      left_motor.write(left_motor_speed);
      right_motor.write(right_motor_speed);
    }
  
    // 본격적으로 접안이 시작됨. 큰선박의 서브모터는 일정한 rpm으로 돌아가고 있다고 전제한다
    else if(distance_f < 25 && distance_f >= 10){
      // 화물선이 항구와 평행한 상태이므로 화물선과 동일한 속도로 움직임으로써 접안에 개입을 안함  
      left_motor_speed = 25;
      right_motor_speed = 25;
      left_motor.write(left_motor_speed);
      right_motor.write(right_motor_speed);
    }
    else if(distance_f < 10){
      left_motor_speed = 25;
      right_motor_speed = 25;
      left_motor.write(left_motor_speed);
      right_motor.write(right_motor_speed);
   }
    //}
  }
}
      // 유량센서의 측정값들도 개입을 하기시작. 0~10은 잔잔한 파도량, 10~20은 적당한 파도량, 20부터는 강한 파도량으로 가정
      // 파도의 방향은 항구로 향하고 있으므로, 파도가 강할경우 모터의 rpm을 낮춤
//      if((l_hour > 10) && (l_hour <= 20)){
//        left_motor_speed -= 5;
//        right_motor_speed -= 5;
//        left_motor.write(left_motor_speed);    
//        right_motor.write(right_motor_speed);
//      }
//      else if(l_hour > 20){
//        if (left_motor_speed != 0 && right_motor_speed != 0){
//          left_motor_speed -= 10;
//          right_motor_speed -= 10;
//          left_motor.write(left_motor_speed);
//          right_motor.write(right_motor_speed);
//        }

void serialEvent()
{
  while (Serial.available()) {
    left_motor_speed = Serial.parseInt();
    right_motor_speed = Serial.parseInt();
  }
}
