package com.example.ordenapp.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordenapp.Adapter.CartAdapter;
import com.example.ordenapp.Helper.ChangeNumberItemsListener;
import com.example.ordenapp.Helper.ManagmentCart;
import com.example.ordenapp.R;
import com.example.ordenapp.databinding.ActivityCartBinding;

import java.text.DecimalFormat;

public class CartActivity extends BaseActivity {
private ActivityCartBinding binding;
private RecyclerView.Adapter adapter;
private ManagmentCart managmentCart;

    private double tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        managmentCart = new ManagmentCart(this);
        setVariable();
        calculateCart();
        initList();
    }

    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        }else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, this::calculateCart);
        binding.cardView.setAdapter(adapter);
    }

    private void calculateCart() {
        double percentTax = 0.18; // percent 18% tax itbis
        int delivery = 25; // pesos DOP
        tax = (double) Math.round(managmentCart.getTotalFee() * percentTax * 100.0) /100;
        double total = (double) Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) /100;
        double itemTotal = (double) Math.round(managmentCart.getTotalFee() * 100) /100;
        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}