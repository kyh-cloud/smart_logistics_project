#include <Servo.h>
#include <SoftwareSerial.h>
//#include <LiquidCrystal.h>

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
int buffer[2] = {0,0};

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
  String sig;
  int num[2];

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

//  // 앞쪽 초음파 센서 view (f) 
//  Serial.print("앞쪽 초음파 센서 거리측정 : ");
//  Serial.print(distance_f);            
//  Serial.println(" Cm");
//  
//  // 왼쪽 초음파 센서 view
//  Serial.print("왼쪽 초음파 센서 거리측정 : ");
//  Serial.print(distance_l);
//  Serial.println(" Cm");
//
//  // 오른쪽 초음파 센서 view (rf)
//  Serial.print("오른쪽 초음파 센서 거리측정 : ");
//  Serial.print(distance_r);              
//  Serial.println(" Cm");
//
//  // 유량센서
//  Serial.print("유량: ");
//  Serial.print(l_hour, DEC);              // 시간당 리터 출력
//  Serial.println(" L/hour");

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

  // 1초간격마다 출력 값을 비교하기 쉽도록 함
//  Serial.print("\n");
  }
  
  delay(500);

//  if (Serial.available() > 0) {
//    int index;    
//    sig = Serial.readString();
//    
//    index = sig.indexOf(',');
//
//    num[0] = sig.substring(0, index).toInt();
//    num[1] = sig.substring(index+1).toInt();    
//
//    left_motor.write(num[0]);
//    right_motor.write(num[1]);
//    buffer[0] = num[0];
//    buffer[1] = num[1];
//
//    Serial.setTimeout(20);

//    if((num[0] < 20) && (num[1] < 20) && (num[0] < 20)){
//      digitalWrite(r_led, HIGH);
//      digitalWrite(g_led, HIGH);
//      digitalWrite(b_led, HIGH);
//    }
//    else {
//      digitalWrite(r_led, LOW);
//      digitalWrite(g_led, LOW);
//      digitalWrite(b_led, LOW);
//    }
//  }
//  else {
//    left_motor.write(buffer[0]);
//    right_motor.write(buffer[1]);
//  }

  /* 모터 제어부 */
  // 모터 동작 시작!
  if (Serial.available() > 0) {
    if (Serial.read() == 48){
      left_motor_speed = 0;
      right_motor_speed = 0;
//      buffer[0] = left_motor_speed;
//      buffer[1] = right_motor_speed;
      left_motor.write(left_motor_speed);
      right_motor.write(right_motor_speed);
  
      delay(5000);
    }
 }
 else {
  if(distance_f >= 100){
        left_motor_speed = 0;
        right_motor_speed = 0;
        buffer[0] = left_motor_speed;
        buffer[1] = right_motor_speed;
        left_motor.write(left_motor_speed);
        right_motor.write(right_motor_speed);
      }
      else if(distance_f < 100 && distance_f >= 50) {
          left_motor_speed = 40;
          right_motor_speed = 40;
          buffer[0] = left_motor_speed;
          buffer[1] = right_motor_speed;
          left_motor.write(left_motor_speed);
          right_motor.write(right_motor_speed);
      }    
      else if((distance_f < 50) && (distance_f >= 30)) {
          left_motor_speed = 25;
          right_motor_speed = 25;
          buffer[0] = left_motor_speed;
          buffer[1] = right_motor_speed;
          left_motor.write(left_motor_speed);
          right_motor.write(right_motor_speed);
      }
      // 장애물 탐지 시, 오른쪽 모터를 돌려 방향 전환 후
      // 딜레이를 건뒤, 왼쪽 모터를 돌려서 방향을 다시 잡아줌
      else if((distance_f < 30) && (distance_f >= 20)) {
          left_motor_speed = 0;
          right_motor_speed = 25;
          left_motor.write(left_motor_speed);
          right_motor.write(right_motor_speed);
    
          delay(5000);
    
          left_motor_speed = 25;
          right_motor_speed = 0;
          left_motor.write(left_motor_speed);
          right_motor.write(right_motor_speed);
    
          delay(5000);
  
          buffer[0] = 0;
          buffer[1] = 0;
      }
    
      else if(distance_f < 20) {
        left_motor_speed = 25;
        right_motor_speed = 25;
        buffer[0] = left_motor_speed;
        buffer[1] = right_motor_speed;
        left_motor.write(left_motor_speed);
        right_motor.write(right_motor_speed);
      }
 }
// else {
//      left_motor.write(buffer[0]);
//      right_motor.write(buffer[1]);
// }
}

void serialEvent()
{
  while (Serial.available()) {
    left_motor_speed = Serial.parseInt();
    right_motor_speed = Serial.parseInt();
  }
}
