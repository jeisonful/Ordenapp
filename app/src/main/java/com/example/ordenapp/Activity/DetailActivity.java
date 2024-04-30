package com.example.ordenapp.Activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
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
        binding.backBtn.setOnClickListener(v->{
            finish();
        });

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        binding.priceTxt.setText("$"+object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar()+"");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num*object.getPrice()+"$"));
    }

    private void getIntentExtra() {
        object = (Products) getIntent().getSerializableExtra("object");
    }
}