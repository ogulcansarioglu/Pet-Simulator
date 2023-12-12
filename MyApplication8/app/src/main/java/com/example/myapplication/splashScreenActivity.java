package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class splashScreenActivity extends AppCompatActivity {

    //this is the splashscreen activity that comes before the first screen to show a loading animation
    private Handler mHandler;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        //create a handler
        final Handler handler = new Handler(Looper.getMainLooper());
        //navigates to the AuthActivity screen after the delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);
            }
        }, 2000);


    }




}
