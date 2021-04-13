//항구 측에서 측정한 센서 값 아두이노 코드(서버를 위한 태그 추가)
#define cds A1
#define trigPin 9
#define echoPin 8
// 실행시 가장 먼저 호출되는 함수이며, 최초 1회만 실행됩니다.
// 변수를 선언하거나 초기화를 위한 코드를 포함합니다.
void setup() {
  Serial.begin(9600);// 조도센서의 동작 상태를 확인하기 위하여 시리얼 통신을 설정합니다. (전송속도 9600bps)
  pinMode(trigPin,OUTPUT);
  pinMode(echoPin,INPUT);
}
// setup() 함수가 호출된 이후, loop() 함수가 호출되며,
// 블록 안의 코드를 무한히 반복 실행됩니다.
void loop() {
// 조도센서로 부터 측정된 밝기 값을 읽어 cdsValue라는 변수에 저장합니다.
  int cdsValue = analogRead(cds);
  long duration, distance;                   // 각 변수를 선언합니다.
  digitalWrite(trigPin, LOW);                 // trigPin에 LOW를 출력하고
  delayMicroseconds(2);                    // 2 마이크로초가 지나면
  digitalWrite(trigPin, HIGH);                // trigPin에 HIGH를 출력합니다.
  delayMicroseconds(10);                  // trigPin을 10마이크로초 동안 기다렸다가
  digitalWrite(trigPin, LOW);                // trigPin에 LOW를 출력합니다.
  duration = pulseIn(echoPin, HIGH);   // echoPin핀에서 펄스값을 받아옵니다.
  // 측정된 밝기 값를 시리얼 모니터에 출력합니다.
  distance = duration * 17 / 1000;
  Serial.print("LL" +String(cdsValue) + ",");
  Serial.println("DD" + String(distance));
  delay(1000);
}
