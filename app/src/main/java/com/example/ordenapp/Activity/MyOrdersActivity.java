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
import com.example.ordenapp.Adapter.OrderHistoryAdapter;
import com.example.ordenapp.Domain.Category;
import com.example.ordenapp.Domain.OrderDetails;
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
        initMyOrders();
    }

    private void initMyOrders() {
        DatabaseReference myRef = database.getReference("OrderDetails");
        binding.progressBarOrderHistory.setVisibility(View.VISIBLE);
        ArrayList<OrderDetails> list = new ArrayList<>();
        Query query = myRef.orderByChild("UserID").equalTo(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(OrderDetails.class));
                    }
                    if (!list.isEmpty()) {
                        binding.historyOrdersView.setLayoutManager(new LinearLayoutManager(MyOrdersActivity.this, LinearLayoutManager.VERTICAL, false));
                        RecyclerView.Adapter<OrderHistoryAdapter.viewholder> adapter = new OrderHistoryAdapter(list);
                        binding.historyOrdersView.setAdapter(adapter);
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