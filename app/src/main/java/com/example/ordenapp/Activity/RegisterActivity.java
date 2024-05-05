package com.example.ordenapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.ordenapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity {
ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        setVariable();
    }

    private void setVariable() {

        binding.btnRegister.setOnClickListener(v ->{
            createUser();
        });
        binding.btnLoguearme.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void createUser(){
        String nombre = binding.nombreTxt.getText().toString();
        String email = binding.emailTxt.getText().toString();
        String password = binding.passwordTxt.getText().toString();
        String password2 = binding.passwordConfirmarTxt.getText().toString();

        if(password.length()<8){
            Toast.makeText(RegisterActivity.this, "La contraseña debe contener 8 caracteres", Toast.LENGTH_SHORT).show();
        } else if (!password2.equals(password)) {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        } else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("Name", nombre);
                        userData.put("Email", email);
                        userData.put("Password", password);
                        userData.put("Rank", 0);

                        DatabaseReference userRef = databaseReference.child("User").child(firebaseUser.getUid());
                        userRef.updateChildren(userData)
                                .addOnSuccessListener(aVoid -> {
                                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nombre)
                                            .build();
                                    firebaseUser.updateProfile(profileUpdate);

                                    Toast.makeText(RegisterActivity.this, "Te has registrado correctamente.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(RegisterActivity.this, "Failed to update user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed: User is null", Toast.LENGTH_LONG).show();
                    }
                }
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(RegisterActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }





}