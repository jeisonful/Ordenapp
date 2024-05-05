package com.example.ordenapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.ordenapp.databinding.ActivityIntroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class IntroActivity extends BaseActivity {
ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#4682B4"));
    }

    private void setVariable() {
        binding.btnLogin.setOnClickListener(v -> {
            if(mAuth.getCurrentUser()!=null){
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            }else{
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });

        binding.btnRegister.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(IntroActivity.this, RegisterActivity.class));
        });
    }
}