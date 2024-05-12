package com.example.ordenapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordenapp.Adapter.BestProductsAdapter;
import com.example.ordenapp.Adapter.InventoryAdapter;
import com.example.ordenapp.Domain.Products;
import com.example.ordenapp.R;
import com.example.ordenapp.databinding.ActivityAdminBinding;
import com.example.ordenapp.databinding.ActivityInventoryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InventoryActivity extends BaseActivity {
    private ActivityInventoryBinding binding;
    private String searchText;
    private ArrayList<Products> productList = new ArrayList<>();
    private InventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        setVariable();
        initListProduct();
    }

    private void setVariable() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.buscarTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String text = binding.buscarTxt.getText().toString();
                if (text.isEmpty()) {
                    initListProduct();
                }
            }
        });

        binding.btnBuscar.setOnClickListener(v -> {
            String text = binding.buscarTxt.getText().toString();
            if (!text.isEmpty()) {
                searchText = text;
                initListSearch();
            } else {
                initListProduct();
            }
        });
    }

    private void initListSearch() {
        DatabaseReference myRef = database.getReference("Product");
        binding.progressBarProducts.setVisibility(View.VISIBLE);
        productList.clear();
        Query query = myRef.orderByKey().equalTo(searchText);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        productList.add(issue.getValue(Products.class));
                    }
                    updateRecyclerView();
                } else {
                    Toast.makeText(InventoryActivity.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
                    initListProduct();
                }
                binding.progressBarProducts.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InventoryActivity.this, "Error al recuperar los datos", Toast.LENGTH_SHORT).show();
                binding.progressBarProducts.setVisibility(View.GONE);
            }
        });
    }

    private void initListProduct() {
        DatabaseReference myRef = database.getReference("Product");
        binding.progressBarProducts.setVisibility(View.VISIBLE);
        productList.clear();
        Query query = myRef.orderByChild("Id");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        productList.add(issue.getValue(Products.class));
                    }
                    updateRecyclerView();
                }
                binding.progressBarProducts.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InventoryActivity.this, "Error al recuperar los datos", Toast.LENGTH_SHORT).show();
                binding.progressBarProducts.setVisibility(View.GONE);
            }
        });
    }

    private void updateRecyclerView() {
        if (adapter == null) {
            adapter = new InventoryAdapter(productList);
            binding.ProductsView.setLayoutManager(new LinearLayoutManager(InventoryActivity.this));
            binding.ProductsView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}