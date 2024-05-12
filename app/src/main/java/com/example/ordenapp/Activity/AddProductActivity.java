package com.example.ordenapp.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import com.example.ordenapp.R;
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
