package com.example.ordenapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ordenapp.R;
import com.example.ordenapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity {
ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
                        // Prepare user data map
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("Name", nombre);  // Assuming 'nombre' is defined and valid
                        userData.put("Email", email);  // Assuming 'email' is defined and valid
                        userData.put("Password", password);  // Be cautious with storing passwords in plain text
                        userData.put("Rank", 0);  // Default rank

                        // Update the child data at once
                        DatabaseReference userRef = databaseReference.child("User").child(firebaseUser.getUid());
                        userRef.updateChildren(userData)
                                .addOnSuccessListener(aVoid -> {
                                    // Successfully updated user information
                                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nombre)
                                            .build();
                                    firebaseUser.updateProfile(profileUpdate);

                                    // Notify user
                                    Toast.makeText(RegisterActivity.this, "Te has registrado correctamente.", Toast.LENGTH_SHORT).show();

                                    // Start the next activity
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                })
                                .addOnFailureListener(e -> {
                                    // Handle failure
                                    Toast.makeText(RegisterActivity.this, "Failed to update user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed: User is null", Toast.LENGTH_LONG).show();
                    }
                }
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors more appropriately here
                    Toast.makeText(RegisterActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }





}