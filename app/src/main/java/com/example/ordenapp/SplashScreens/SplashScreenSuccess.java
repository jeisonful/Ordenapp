package com.example.ordenapp.SplashScreens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ordenapp.Activity.AdminActivity;
import com.example.ordenapp.Activity.BaseActivity;
import com.example.ordenapp.R;

@SuppressLint("CustomSplashScreen")
public class SplashScreenSuccess extends BaseActivity {
    private static final int SPLASH_SCREEN_DELAY = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_success);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        showSplashScreenSuccess();

    }

    private void showSplashScreenSuccess() {
        findViewById(R.id.splashScreenSuccess).setVisibility(View.VISIBLE);


        new Handler().postDelayed(() -> {
            findViewById(R.id.splashScreenSuccess).setVisibility(View.GONE);

            startActivity(new Intent(SplashScreenSuccess.this, AdminActivity.class));
            finish();
        }, SPLASH_SCREEN_DELAY);
    }
}