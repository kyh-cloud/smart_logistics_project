package com.example.itsea;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SensorActivity extends AppCompatActivity {

    Handler laserHandler = new Handler();
    Handler superHandler = new Handler();
    Handler motorHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Button systemButton = (Button) findViewById(R.id.systemButton_security);
        Button alarmButton = (Button) findViewById(R.id.alarmButton_security);
        Button securityButton = (Button) findViewById(R.id.securityButton_security);
        Button settingButton = (Button) findViewById(R.id.settingsButton_security);

        // 아래바 알람버튼
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        // 아래바 시스템관리버튼
        systemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RealtimeActivity.class);
                startActivity(intent);
            }
        });

        // 아래바 보안관리버튼
        securityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // 아래바 설정버튼
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DatabaseActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 데이터 출력 버튼
         **/
        final WebView laserWebView = (WebView)findViewById(R.id.laser_webView);
        final WebView supersoundWebView = (WebView)findViewById(R.id.supersound_webView);
        //final WebView motorWebView = (WebView)findViewById(R.id.motor_webView);

        Button dataButton = (Button)findViewById(R.id.dataButton);
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 레이저센서 출력부분
                laserWebView.setWebViewClient(new WebViewClient());
                WebSettings laserWebSettings = laserWebView.getSettings();
                laserWebSettings.setBuiltInZoomControls(true);

                laserWebView.loadUrl("http://35.216.85.216:80/dock.php");

                /**
                 * 1초마다 실시간으로 데이터 새로고침! (소켓통신 대신 무한loop로 구현함)
                 **/
                laserHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(true){
                            laserHandler.postDelayed(this,1000);
                        }
                        laserWebView.reload();
                    }
                },1000);

                //
                // 초음파센서 출력부분
                supersoundWebView.setWebViewClient(new WebViewClient());
                WebSettings superWebSettings = supersoundWebView.getSettings();
                superWebSettings.setBuiltInZoomControls(true);

                supersoundWebView.loadUrl("http://35.216.85.216:80/shipview.php");

                /**
                 * 1초마다 실시간으로 데이터 새로고침! (소켓통신 대신 무한loop로 구현함)
                 **/
                superHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(true){
                            superHandler.postDelayed(this,1000);
                        }
                        supersoundWebView.reload();
                    }
                },1000);

                // 모터속도 출력부분
                /*motorWebView.setWebViewClient(new WebViewClient());
                WebSettings motorWebSettings = motorWebView.getSettings();
                motorWebSettings.setBuiltInZoomControls(true);

                motorWebView.loadUrl("http://35.216.85.216:80/shipview.php");

                /**
                 * 1초마다 실시간으로 데이터 새로고침! (소켓통신 대신 무한loop로 구현함)
                 **/
                /*motorHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(true){
                            motorHandler.postDelayed(this,1000);
                        }
                        motorWebView.reload();
                    }
                },1000);*/
            }
        });

    }

    // webviewclient 정의
    class webViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){

            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}