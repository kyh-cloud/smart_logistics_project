package com.example.data_ITSea;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.itsea.R;

//import static com.example.itsea.RealtimeActivity.url;

public class Data201111Activity extends AppCompatActivity {

    VideoView videoView;
    Handler handler = new Handler();
    public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data201111);

        Toast.makeText(getApplicationContext(),"영상이 준비될때까지 잠시만 기다려주세요.",Toast.LENGTH_SHORT).show();
        // 직접 컨트롤할 수 있도록
        videoView = (VideoView) findViewById(R.id.videoView);
        final WebView webView = (WebView)findViewById(R.id.webView);

        /**
         * 영상데이터 출력
         **/
        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        videoView.setVideoURI(Uri.parse(url));
        videoView.requestFocus();   // 파일의 일부를 가져옴. 준비과정이 끝나야 영상을 가져올 수 있음

        // 준비과정을 알려주는 메소드
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(getApplicationContext(), "동영상 재생준비 완료", Toast.LENGTH_SHORT).show();
            }
        });

        Button startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 처음위치로 돌아갈수 있는거
                videoView.seekTo(0);
                videoView.start();

                webView.setWebViewClient(new WebViewClient());

                WebSettings webSettings = webView.getSettings();
                webSettings.setBuiltInZoomControls(true);

                webView.loadUrl("http://35.216.85.216:80/shipview.php");
                //webView.loadUrl("http://www.naver.com");

                /**
                 * 1초마다 실시간으로 데이터 새로고침! (소켓통신 대신 무한loop로 구현함)
                 **/
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(true){
                            handler.postDelayed(this,1000);
                        }
                        webView.reload();
                    }
                },1000);
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