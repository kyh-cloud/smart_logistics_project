package com.example.itsea;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.data_ITSea.Data201111Activity;

import androidx.appcompat.app.AppCompatActivity;

public class DatabaseActivity extends AppCompatActivity {

    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Button realtimeButton = (Button) findViewById(R.id.realtimeButton_database);
        Button alarmButton = (Button) findViewById(R.id.alarmButton_database);
        Button sensorButton = (Button) findViewById(R.id.sensorButton_database);
        Button databaseButton = (Button) findViewById(R.id.databaseButton_database);

        // 아래바 알람버튼
       alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        // 아래바 실시간 선박 버튼
        realtimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RealtimeActivity.class);
                startActivity(intent);
            }
        });

        // 아래바 센서관리 버튼
        sensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SensorActivity.class);
                startActivity(intent);
            }
        });

        // 아래바 database버튼
        databaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        /** search 버튼 **/

        searchText = (EditText)findViewById(R.id.searchText);
        Button searchButton = (Button)findViewById(R.id.searchbutton_database);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = searchText.getText().toString();

                if(date.length() != 0){
                    if (date.equals("1111")){
                        Intent intent = new Intent(getApplicationContext(), Data201111Activity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"검색완료", Toast.LENGTH_SHORT).show();
                    }
                } else if(date.length() == 0){
                    Toast.makeText(getApplicationContext(),"날짜를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /** data 버튼 **/
        Button button201111 = (Button)findViewById(R.id.button201111);
        button201111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"2020.11.11 data",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Data201111Activity.class);
                startActivity(intent);
            }
        });

       /*Button button200929 = (Button)findViewById(R.id.button200929);
        button200929.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"2020.09.29 data",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Data200929Activity.class);
                startActivity(intent);
            }
        });*/
    }
}