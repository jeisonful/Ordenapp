package com.example.ordenapp.Activity;

import static android.text.TextUtils.lastIndexOf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordenapp.Adapter.BestProductsAdapter;
import com.example.ordenapp.Adapter.CategoryAdapter;
import com.example.ordenapp.Domain.Category;
import com.example.ordenapp.Domain.Products;
import com.example.ordenapp.Domain.User;
import com.example.ordenapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends BaseActivity {
private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserData();
        initBestProduct();
        initCategory();
        setVariable();
    }

    private void getUserData() {
        String name = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        DatabaseReference myRef = database.getReference("User");

        Query query = myRef.orderByChild("Email").equalTo(userEmail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String caracter = String.valueOf(dataSnapshot.getValue());
                    int position = caracter.lastIndexOf("Rank=")+5;
                    String rankValue = String.valueOf(caracter.charAt(position));
                    if(rankValue.equals("0")){
                        binding.btnAdmin.setVisibility(View.GONE);
                    }else{
                        binding.btnAdmin.setVisibility(View.VISIBLE);
                    }
                }catch (Throwable e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        binding.userLoggedName.setText(name);
    }


    private void setVariable() {

        binding.btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        binding.btnBuscar.setOnClickListener(v -> {
            String text = binding.buscarTxt.getText().toString();
            if (!text.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ListProductsActivity.class);
                intent.putExtra("text", text);
                intent.putExtra("isSearch", true);
                startActivity(intent);
            }
        });
    }

    private void initBestProduct() {
        DatabaseReference myRef = database.getReference("Product");
        binding.progressBarBestProducts.setVisibility(View.VISIBLE);
        ArrayList<Products> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestProduct").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Products.class));
                    }
                    if (!list.isEmpty()) {
                        binding.bestProductsView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter<BestProductsAdapter.viewholder> adapter = new BestProductsAdapter(list);
                        binding.bestProductsView.setAdapter(adapter);
                    }
                    binding.progressBarBestProducts.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Category.class));
                    }
                    if (!list.isEmpty()) {
                        binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                        RecyclerView.Adapter<CategoryAdapter.viewholder> adapter = new CategoryAdapter(list);
                        binding.categoryView.setAdapter(adapter);
                    }
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}