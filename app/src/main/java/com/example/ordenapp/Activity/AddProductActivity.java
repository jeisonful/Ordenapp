package com.example.ordenapp.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ordenapp.Domain.Products;
import com.example.ordenapp.R;
import com.example.ordenapp.SplashScreens.SplashScreenSuccess;
import com.example.ordenapp.databinding.ActivityAddProductBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends BaseActivity {
    private ActivityAddProductBinding binding;
    private String lastKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        getLastProductID();
        setupCategoriesSpinner();
        setupBestProductSpinner();
        addProduct();

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void addProduct() {
        binding.btnCancelar.setOnClickListener(v -> {
            finish();
        });

        binding.btnEnviar.setOnClickListener(v -> {
            boolean BestProduct = false;
            int CategoryId = 0;

            if ("Selecciona la categoría".equals(binding.spinnerCategories.getSelectedItem().toString()) ||
                    "¿Mejor producto de hoy?".equals(binding.spinnerBestProduct.getSelectedItem().toString()) ||
                    binding.txtTitulo.getText().toString().isEmpty() ||
                    binding.txtDescripcion.getText().toString().isEmpty() ||
                    binding.txtPrecio.getText().toString().isEmpty() ||
                    binding.txtImg.getText().toString().isEmpty()) {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            }else {

                int Id = Integer.parseInt(lastKey);
                String Description = binding.txtDescripcion.getText().toString();
                String ImagePath = binding.txtImg.getText().toString();
                double Price = Double.parseDouble(binding.txtPrecio.getText().toString());
                int Star = 5;
                String Title = binding.txtTitulo.getText().toString();
                int UnitsInStock = 0;

                String selectedOption = (String) binding.spinnerBestProduct.getSelectedItem();
                if ("Sí".equals(selectedOption)) {
                    BestProduct = true;
                } else if ("No".equals(selectedOption)) {
                    BestProduct = false;
                }

                int selectedPosition = binding.spinnerCategories.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 0:
                        CategoryId = 0; // Bebidas
                        break;
                    case 1:
                        CategoryId = 1; // Lácteos
                        break;
                    case 2:
                        CategoryId = 2; // Frutas y Verduras
                        break;
                    case 3:
                        CategoryId = 3; // Panadería
                        break;
                    case 4:
                        CategoryId = 4; // Limpieza
                        break;
                    case 5:
                        CategoryId = 5; // Baño
                        break;
                    case 6:
                        CategoryId = 6; // Embutidos
                        break;
                    case 7:
                        CategoryId = 7; // Más
                        break;
                }

                productList.child(String.valueOf(Id)).child("Id").setValue(Id);
                productList.child(String.valueOf(Id)).child("Description").setValue(Description);
                productList.child(String.valueOf(Id)).child("CategoryId").setValue(CategoryId);
                productList.child(String.valueOf(Id)).child("ImagePath").setValue(ImagePath);
                productList.child(String.valueOf(Id)).child("Price").setValue(Price);
                productList.child(String.valueOf(Id)).child("Star").setValue(Star);
                productList.child(String.valueOf(Id)).child("Title").setValue(Title);
                productList.child(String.valueOf(Id)).child("UnitsInStock").setValue(UnitsInStock);
                productList.child(String.valueOf(Id)).child("BestProduct").setValue(BestProduct);
                startActivity(new Intent(AddProductActivity.this, SplashScreenSuccess.class));
                finish();
            }
        });
    }


    private void setupCategoriesSpinner() {
        Spinner spinnerCategories = findViewById(R.id.spinner_categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);
    }

    private void setupBestProductSpinner() {
        Spinner spinnerBestProduct = findViewById(R.id.spinner_bestProduct);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.best_product_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBestProduct.setAdapter(adapter);
    }

    private void getLastProductID() {
        Query query = productList.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String lastProductId = snapshot.getChildren().iterator().next().getKey();
                    assert lastProductId != null;
                    lastKey = String.valueOf(Integer.parseInt(lastProductId) + 1);
                } else {
                    lastKey = "0";
                }
                binding.txtId.setText("ID: " + lastKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
