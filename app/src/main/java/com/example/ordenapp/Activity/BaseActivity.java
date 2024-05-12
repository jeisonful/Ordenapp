package com.example.ordenapp.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ordenapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ordenapp-3184b-default-rtdb.firebaseio.com");
    DatabaseReference productList = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ordenapp-3184b-default-rtdb.firebaseio.com/Product");
    String currentFirebaseUser = FirebaseAuth.getInstance().getUid();
    public String TAG = "Ordenapp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }
}