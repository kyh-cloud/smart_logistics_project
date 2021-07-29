package com.example.itsea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Button realtimeButton = (Button) findViewById(R.id.realtimeButton_alarm);
        Button alarmButton = (Button) findViewById(R.id.alarmButton_alarm);
        Button sensorButton = (Button) findViewById(R.id.sensorButton_alarm);
        Button databaseButton = (Button) findViewById(R.id.databaseButton_alarm);

        // 아래바 알람버튼
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // 아래바 실시간선박관리 버튼
        realtimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RealtimeActivity.class);
                startActivity(intent);
            }
        });

        // 아래바 센서 버튼
        sensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SensorActivity.class);
                startActivity(intent);
            }
        });

        // 아래바 데이터베이스 버튼
        databaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DatabaseActivity.class);
                startActivity(intent);
            }
        });
    }
}