package com.example.ordenapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordenapp.Adapter.OrderHistoryAdapter;
import com.example.ordenapp.R;
import com.example.ordenapp.databinding.ActivityAdminBinding;
import com.example.ordenapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AdminActivity extends BaseActivity {
private ActivityAdminBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        binding.userName.setText("Bienvenid@, "+Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());

        setVariable();
        getOrderCounter();
        getCustomerCounter();
        getProductCounter();
    }

    private void getProductCounter() {
        DatabaseReference productCounterRef = FirebaseDatabase.getInstance().getReference("Product");
        productCounterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long productCounterValue = dataSnapshot.getChildrenCount();
                    if(productCounterValue >99){
                        binding.productsCounter.setText("99+");
                    }else{
                        binding.productsCounter.setText(String.valueOf(productCounterValue));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void getCustomerCounter() {
        DatabaseReference customerCounterRef = FirebaseDatabase.getInstance().getReference("User");
        customerCounterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long customerCounterValue = dataSnapshot.getChildrenCount();
                    if(customerCounterValue >99){
                        binding.customersCounter.setText("99+");
                    }else{
                        binding.customersCounter.setText(String.valueOf(customerCounterValue));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void getOrderCounter() {
        DatabaseReference orderCounterRef = FirebaseDatabase.getInstance().getReference("ordersCounter");
        orderCounterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long orderCounterValue = dataSnapshot.getValue(Long.class);
                    if(orderCounterValue >99){
                        binding.orderCounter.setText("99+");
                    }else{
                        binding.orderCounter.setText(String.valueOf(orderCounterValue));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setVariable() {
        binding.btnVerProductos.setVisibility(View.GONE);
        binding.btnAgregarProducto.setVisibility(View.GONE);
        binding.imgProductos.setVisibility(View.VISIBLE);
        binding.productosTxt.setVisibility(View.VISIBLE);

        binding.imgOrders.setVisibility(View.VISIBLE);
        binding.ordersTxt.setVisibility(View.VISIBLE);
        binding.btnOrderDelivered.setVisibility(View.GONE);
        binding.btnOrderPending.setVisibility(View.GONE);
        binding.btnOrderShipped.setVisibility(View.GONE);

        binding.btnOrderPending.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, MyOrdersActivity.class);
            intent.putExtra("Status", "pending");
            startActivity(intent);
        });
        binding.btnOrderDelivered.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, MyOrdersActivity.class);
            intent.putExtra("Status", "delivered");
            startActivity(intent);
        });
        binding.btnOrderShipped.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, MyOrdersActivity.class);
            intent.putExtra("Status", "shipped");
            startActivity(intent);
        });
        binding.btnOrders.setOnClickListener(v -> {
           if(binding.btnOrderDelivered.getVisibility() == View.GONE){
               binding.imgOrders.setVisibility(View.GONE);
               binding.ordersTxt.setVisibility(View.GONE);
               binding.btnOrderDelivered.setVisibility(View.VISIBLE);
               binding.btnOrderPending.setVisibility(View.VISIBLE);
               binding.btnOrderShipped.setVisibility(View.VISIBLE);
           }else{
               binding.imgOrders.setVisibility(View.VISIBLE);
               binding.ordersTxt.setVisibility(View.VISIBLE);
               binding.btnOrderDelivered.setVisibility(View.GONE);
               binding.btnOrderPending.setVisibility(View.GONE);
               binding.btnOrderShipped.setVisibility(View.GONE);
           }
        });

        binding.btnProducts.setOnClickListener(v -> {

            if(binding.btnAgregarProducto.getVisibility()==View.GONE){
                binding.btnVerProductos.setVisibility(View.VISIBLE);
                binding.btnAgregarProducto.setVisibility(View.VISIBLE);
                binding.imgProductos.setVisibility(View.GONE);
                binding.productosTxt.setVisibility(View.GONE);

            }else {
                binding.btnVerProductos.setVisibility(View.GONE);
                binding.btnAgregarProducto.setVisibility(View.GONE);
                binding.imgProductos.setVisibility(View.VISIBLE);
                binding.productosTxt.setVisibility(View.VISIBLE);
            }
        });
        binding.btnAgregarProducto.setOnClickListener(v -> {
        });
        binding.btnVerProductos.setOnClickListener(v -> {
        });

        binding.btnCustomers.setOnClickListener(v -> {
        });
        binding.btnCategories.setOnClickListener(v -> {
        });
    }
}