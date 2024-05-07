package com.example.ordenapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

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
        binding.btnCustomers.setOnClickListener(v -> {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        });
        binding.btnCategories.setOnClickListener(v -> {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        });


        binding.btnAgregarProducto.setOnClickListener(v -> {
            Toast.makeText(this, "Estoy agregando productos", Toast.LENGTH_SHORT).show();
        });
        binding.btnVerProductos.setOnClickListener(v -> {
            Toast.makeText(this, "Estoy viendo productos", Toast.LENGTH_SHORT).show();
        });
    }
}