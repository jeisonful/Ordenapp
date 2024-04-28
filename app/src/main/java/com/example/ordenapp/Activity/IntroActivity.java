package com.example.ordenapp.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ordenapp.R;
import com.example.ordenapp.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#4682B4"));
    }

    private void setVariable() {
        binding.btnLogin.setOnClickListener(v -> {

        });

        binding.btnRegister.setOnClickListener(v ->{

        });
    }
}