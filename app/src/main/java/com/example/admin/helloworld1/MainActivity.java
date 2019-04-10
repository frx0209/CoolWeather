package com.example.admin.helloworld1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView = (TextView) findViewById(R.id.abc);
        this.button=(Button)findViewById(R.id.Btn);
        this.button.setOnClickListener((v)->{
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        });

        String weatherId = "CN101040200";
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=d8bd0f362c38447799474b5ac38ce2ea";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                    }
                });

            }


        });
    }
}