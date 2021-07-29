package com.example.itsea;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class RealtimeActivity extends YouTubeBaseActivity {

    private TextView output;
    VideoView videoView;
    //int id = getRawResIdByName("video1.avi");
    //Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + id);
    //public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    //public static String url = "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4";
    Handler handler = new Handler();
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        Button serverStartButton = (Button) findViewById(R.id.server_start_system);
        Button realtimeButton = (Button) findViewById(R.id.realtimeButton_realtime);
        Button alarmButton = (Button) findViewById(R.id.alarmButton_realtime);
        Button sensorButton = (Button) findViewById(R.id.sensorButton_realtime);
        Button databaseButton = (Button) findViewById(R.id.databaseButton_realtime);
        //Button jsonButton = (Button) findViewById(R.id.jsonButton);

        // 아래바 알람버튼
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        // 아래바 실시간 선박관리버튼
        realtimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // 아래바 센서관리버튼
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
                Intent intent = new Intent(getApplicationContext(), DatabaseActivity.class);
                startActivity(intent);
            }
        });

        // JSON으로 파싱한 데이터를 띄우는 버튼
        /*jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JsonActivity.class);
                startActivity(intent);
            }
        });*/

        /**
         * 영상데이터 출력
         **/
        /*Uri url = Uri.parse("android.resource://"+ getPackageName() +"/"+R.raw.smart1);
        Toast.makeText(getApplicationContext(),"영상이 준비될때까지 잠시만 기다려주세요.",Toast.LENGTH_SHORT).show();
        // 직접 컨트롤할 수 있도록
        videoView = (VideoView) findViewById(R.id.videoView);

        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        //videoView.setVideoURI(Uri.parse(url));
        videoView.setVideoURI(url);
        videoView.requestFocus();   // 파일의 일부를 가져옴. 준비과정이 끝나야 영상을 가져올 수 있음

        // 준비과정을 알려주는 메소드
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(getApplicationContext(), "동영상 재생준비 완료", Toast.LENGTH_SHORT).show();
            }
        });*/

        final EditText youtubeText = (EditText)findViewById(R.id.youtubeText);
        youTubePlayerView = findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtubeText.getText().toString()); // 동영상 (실시간 가능)
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        /**
         * web 브라우저 불러오기 & 영상 재생
         **/
        final EditText editText = (EditText)findViewById(R.id.webEditText);
        final WebView webView = (WebView)findViewById(R.id.WebView);

        // web 브라우져 불러오기
        webView.setWebViewClient(new webViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);

        serverStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // URL 설정
                //String url = "http://34.64.76.14/hello.php";
                // AsyncTask를 통해 HttpURLConnection 수행
                //NetworkTask networkTask = new NetworkTask(url, null);
                //networkTask.execute();

                // 영상데이터 재생
                //videoView.seekTo(0);
                //videoView.start();
                youTubePlayerView.initialize("AIzaSyBbFX9UPgdJLeGDmEOEFAhKdgMsjLfsLZM", listener); // api

                webView.loadUrl(editText.getText().toString());
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


        //AsyncTask로 구현
       /* // 위젯에 대한 참조.
        output = (TextView) findViewById(R.id.output);

        // URL 설정
        String url = "http://       .cafe24.com/LoadPat         ";

        // AsyncTask를 통해 HttpURLConnection 수행
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();*/
    }

    // webviewclient 정의
    class webViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){

            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }
        /*public class NetworkTask extends AsyncTask<Void, Void, String> {

            private String url;
            private ContentValues values;

            public NetworkTask(String url, ContentValues values) {
                this.url = url;
                this.values = values;
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result;
                RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
                result = requestHttpURLConnection.request(url, values); // 해당 URL로부터 결과물을 얻어온다

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                // doInBackground()로 부터 리턴된 값이 onPostExecuted의 매개변수로 넘어오므로 s를 출력한다.
                output.setText("출력데이터 : " + s + "\n");
            }
        }*/
}
