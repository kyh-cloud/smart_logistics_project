package com.example.itsea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSearchButton(View view){
        Toast.makeText(getApplicationContext(), "검색", Toast.LENGTH_SHORT).show();
    }

    public void onRealtimeButton(View view){
        Toast.makeText(getApplicationContext(), "실시간선박정보", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), RealtimeActivity.class);
        startActivity(intent);
    }

    public void onSensorButton(View view){
        Toast.makeText(getApplicationContext(), "센서데이터관리", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), SensorActivity.class);
        startActivity(intent);
    }

    public void onAlarmButton(View view){
        Toast.makeText(getApplicationContext(), " 알람", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        startActivity(intent);
    }

    public void onDatabaseButton(View view){
        Toast.makeText(getApplicationContext(), "데이터베이스", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), DatabaseActivity.class);
        startActivity(intent);
    }
}
