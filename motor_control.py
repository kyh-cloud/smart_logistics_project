# 유량센서값, 초음파 센서값은 아두이노로부터 수신함
# 모터 스피드는 아두이노로 송신함

int disance_f, distance_r
int left_motor_speed, right_motor_speed
int l_hour
list object_l

if('orange' in object_l){
    if(distance_f >= 100) {
        left_motor_speed = 50
        right_motor_speed = 50
    }
    
    else{
        if((distance_f < 100) & (distance_f >= 50)) {
            left_motor_speed = 40
            right_motor_speed = 40
        }
        
        else if((distance_f < 50) & (distance_f >= 10)) {
            left_motor_speed = 30
            right_motor_speed = 30
        }

        # 본격적으로 접안이 시작됨. 큰선박의 서브모터는 일정한 rpm으로 돌아가고 있다고 전제한다
        else if(distance_f < 10) {
            # 화물선이 항구와 평행한 상태이므로 화물선과 동일한 속도로 움직임으로써 접안에 개입을 안함
            if((dist_diff < 10) & (dist_diff > -10)) {
                if(mb_distance_lf >= 50) {
                    left_motor_speed = 25
                    right_motor_speed = 25
                }
                else if(mb_distance_lf < 50) {
                    left_motor_speed = 0
                    right_motor_speed = 0
                }
            }

            # 화물선이 기운 상태
            else if((dist_diff >= 10) & (dist_diff <= -10)) {
                # 화물선의 머리부분이 항구에서 더 멀게 기울어진 상태이므로 화물선을 밀어주도록 함
                # 기울기만 교정시켜주는 것이 적절하다고 생각하여 화물선과 항구와의 거리는 무시함
                left_motor_speed = 35
                right_motor_speed = 35        
            }            
        }
    }
}
else {
    if(distance_f >= 100) {
        left_motor_speed = 50
        right_motor_speed = 50
    }
    else if((distance_f < 100) & (distance_f >= 50)) {
        left_motor_speed = 40
        right_motor_speed = 40
    }
    # 장애물 탐지 시, 오른쪽 모터를 돌려 방향 전환
    else if(distance_f < 50) {
        left_motor_speend = 0
        right_motor_speed = 30
    }
    # 장애물을 지나 친 후, 오른쪽으로 방향전환하기 위해 왼쪽 모터만 작동
    else if( distance_f >= 50 & distance_r < 30) {
        left_motor_speend = 30;
        right_motor_speed = 0;
    }
}

# 유량센서의 측정값들도 개입을 하기시작. 0~10은 잔잔한 파도량, 10~20은 적당한 파도량, 20부터는 강한 파도량으로 가정
# 파도의 방향은 항구로 향하고 있으므로, 파도가 강할경우 모터의 rpm을 낮춤
if((l_hour > 10) & (l_hour <= 20)) {
    if (left_motor_speed != 0 & right_motor_speed != 0){
        left_motor_speed -= 5;
        right_motor_speed -= 5;
    }
}
else if(l_hour > 20){
    if (left_motor_speed != 0 & right_motor_speed != 0){
        left_motor_speed -= 10;
        right_motor_speed -= 10;
    }
}