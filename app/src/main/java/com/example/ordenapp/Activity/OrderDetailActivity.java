package com.example.ordenapp.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ordenapp.Adapter.ItemsOrderedAdapter;
import com.example.ordenapp.Domain.OrderDetails;
import com.example.ordenapp.Domain.Orders;
import com.example.ordenapp.databinding.ActivityOrderDetailBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrderDetailActivity extends BaseActivity {
ActivityOrderDetailBinding binding;

private Orders object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        getIntentExtra();
        setVariable();
        initDetails();
    }

    private void initDetails() {
        DatabaseReference myRef = database.getReference("OrderDetails");
        ArrayList<OrderDetails> list = new ArrayList<>();
        Query query = myRef.orderByChild("Id").equalTo(object.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        OrderDetails orderDetails = issue.getValue(OrderDetails.class);
                        list.add(orderDetails);
                    }
                    if (!list.isEmpty()) {
                        binding.orderDetailsView.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this, LinearLayoutManager.VERTICAL, false));
                        RecyclerView.Adapter<ItemsOrderedAdapter.viewholder> adapter = new ItemsOrderedAdapter(list);
                        binding.orderDetailsView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setVariable() {

        binding.btnBack.setOnClickListener(v -> finish());
        binding.idOrderTxt.setText("Orden #"+object.getId());
        binding.totalTxt.setText("$"+object.getTotal());
        binding.totalFeeTxt.setText("$"+object.getSubtotal());
        binding.taxTxt.setText("$"+object.getTax());
        binding.shippingAddressTxt.setText(object.getShippingAddress());
    }

    private void getIntentExtra() {
        object = (Orders) getIntent().getSerializableExtra("object");
    }
}