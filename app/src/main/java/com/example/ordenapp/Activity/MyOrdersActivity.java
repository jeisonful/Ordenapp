package com.example.ordenapp.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordenapp.Adapter.BestProductsAdapter;
import com.example.ordenapp.Adapter.CategoryAdapter;
import com.example.ordenapp.Adapter.ItemsOrderedAdapter;
import com.example.ordenapp.Adapter.OrderHistoryAdapter;
import com.example.ordenapp.Adapter.UserOrderAdapter;
import com.example.ordenapp.Domain.Category;
import com.example.ordenapp.Domain.OrderDetails;
import com.example.ordenapp.Domain.Orders;
import com.example.ordenapp.Domain.Products;
import com.example.ordenapp.databinding.ActivityMyOrdersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyOrdersActivity extends BaseActivity {
    private ActivityMyOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        setVariable();
        initMyOrders();
    }
    private void setVariable() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void initMyOrders() {
        Query query;
        DatabaseReference myRef = database.getReference("Orders");
        binding.progressBarOrderHistory.setVisibility(View.VISIBLE);
        ArrayList<Orders> list = new ArrayList<>();

        if(Objects.equals(getIntent().getStringExtra("Status"), "pending")){
            query = myRef.orderByChild("Status").equalTo("Pendiente");
            binding.textView6.setText("Órdenes pendientes");
        }else if(Objects.equals(getIntent().getStringExtra("Status"), "shipped")){
            query = myRef.orderByChild("Status").equalTo("Enviada");
            binding.textView6.setText("Órdenes enviadas");
        }
        else if(Objects.equals(getIntent().getStringExtra("Status"), "delivered")){
            query = myRef.orderByChild("Status").equalTo("Entregada");
            binding.textView6.setText("Órdenes entregadas");
        }
        else {
            binding.textView6.setText("Mi historial de órdenes");
            query = myRef.orderByChild("UserID").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Orders.class));
                    }
                    if (!list.isEmpty()) {


                        if(binding.textView6.getText().equals("Mi historial de órdenes")){
                            binding.historyOrdersView.setLayoutManager(new LinearLayoutManager(MyOrdersActivity.this, LinearLayoutManager.VERTICAL, false));
                            UserOrderAdapter adapter = new UserOrderAdapter(list);
                            binding.historyOrdersView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else {
                            binding.historyOrdersView.setLayoutManager(new LinearLayoutManager(MyOrdersActivity.this, LinearLayoutManager.VERTICAL, false));
                            OrderHistoryAdapter adapter = new OrderHistoryAdapter(list);
                            binding.historyOrdersView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                    }
                    binding.progressBarOrderHistory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}