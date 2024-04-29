package com.example.ordenapp.Activity;

import android.os.Bundle;

import com.example.ordenapp.Domain.Products;
import com.example.ordenapp.R;
import com.example.ordenapp.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Products object;
    private int num = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {

    }

    private void getIntentExtra() {
        object = (Products) getIntent().getSerializableExtra("object");
    }
}