package com.example.ordenapp.Activity;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordenapp.Adapter.CartAdapter;
import com.example.ordenapp.Domain.Products;
import com.example.ordenapp.Helper.ChangeNumberItemsListener;
import com.example.ordenapp.Helper.ManagmentCart;
import com.example.ordenapp.R;
import com.example.ordenapp.databinding.ActivityCartBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CartActivity extends BaseActivity {
private ActivityCartBinding binding;
private RecyclerView.Adapter adapter;
private ManagmentCart managmentCart;

    private double tax;
    private double total;
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
        total = (double) Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) /100;
        double itemTotal = (double) Math.round(managmentCart.getTotalFee() * 100) /100;
        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());

        binding.placeOrder.setOnClickListener(v -> {
            saveOrderToFirebase();
            managmentCart.clearCart();
            finish();
            startActivity(new Intent(CartActivity.this, CartActivity.class));
        });
    }
    private void saveOrderToFirebase() {
        final int[] getTotalItems = {0};
        ArrayList<Products> productList = ((CartAdapter) adapter).getProductList();
        DatabaseReference ordersCounterRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ordenapp-3184b-default-rtdb.firebaseio.com/ordersCounter");
        ordersCounterRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Long currentCount = mutableData.getValue(Long.class);
                if (currentCount == null) {
                    mutableData.setValue(0);
                } else {
                    mutableData.setValue(currentCount + 1);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    Toast.makeText(CartActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (committed && dataSnapshot != null) {
                    Long orderCounter = dataSnapshot.getValue(Long.class);
                    if (orderCounter != null) {
                        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");

                        for (int i = 0; i < productList.size(); i++) {
                            Products product = productList.get(i);
                            String orderId = ordersRef.push().getKey();

                            HashMap<String, Object> productData = new HashMap<>();
                            productData.put("Id", orderCounter);
                            productData.put("ProductID", product.getId());
                            productData.put("Quantity", product.getNumberInCart());
                            getTotalItems[0] += product.getNumberInCart();


                            assert orderId != null;
                            ordersRef.child(orderId).setValue(productData);
                        }

                        DatabaseReference orderDetailsRef = FirebaseDatabase.getInstance().getReference("OrderDetails").child(orderCounter.toString());
                        HashMap<String, Object> orderDetails = new HashMap<>();
                        orderDetails.put("UserID", currentFirebaseUser);
                        orderDetails.put("ShippingAddress", "Testing");
                        orderDetails.put("Status", "pending");
                        orderDetails.put("ItemsQuantity", getTotalItems[0]);
                        orderDetails.put("Total", total);
                        orderDetailsRef.setValue(orderDetails);

                        Toast.makeText(CartActivity.this, "Orden realizada correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CartActivity.this, "Error al obtener el contador de órdenes", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CartActivity.this, "Error al incrementar el contador de órdenes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}