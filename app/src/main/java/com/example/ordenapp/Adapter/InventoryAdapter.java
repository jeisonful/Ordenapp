package com.example.ordenapp.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.ordenapp.Domain.Products;
import com.example.ordenapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.viewholder> {
    ArrayList<Products> items;
    Context context;

    public InventoryAdapter(ArrayList<Products> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public InventoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_inventory, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText("$" + items.get(position).getPrice());
        holder.unitsTxt.setText("Unidades disponible: "+ items.get(position).getUnitsInStock());
        holder.txtId.setText("ID: "+items.get(position).getId());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        holder.btnEditar.setOnClickListener(v -> {
            openEditDialog(items.get(position));
        });

        holder.btnEntrada.setOnClickListener(v -> {

        });

        holder.btnSalida.setOnClickListener(v -> {

        });
    }

    private void openEditDialog(Products products) {
        DialogPlus dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.update_product))
                .setExpanded(true,1200)
                .create();

        View view = dialog.getHolderView();
        EditText Id = view.findViewById(R.id.txtId);
        EditText Title = view.findViewById(R.id.txtTitulo);
        EditText Description = view.findViewById(R.id.txtDescripcion);
        EditText Price = view.findViewById(R.id.txtPrecio);
        EditText ImagePath = view.findViewById(R.id.txtImg);
        Spinner spinnerCategories = view.findViewById(R.id.spinner_categories);
        Spinner spinnerBestProduct = view.findViewById(R.id.spinner_bestProduct);


        Button btnCancelar = view.findViewById(R.id.btnCancelar);
        Button btnActualizar = view.findViewById(R.id.btnActualizar);

        Id.setText("ID: "+products.getId());
        Title.setText(products.getTitle());
        Description.setText(products.getDescription());
        Price.setText(String.valueOf(products.getPrice()));
        ImagePath.setText(products.getImagePath());

        // Adaptador para el Spinner de best products
        ArrayAdapter<CharSequence> bestProductAdapter = ArrayAdapter.createFromResource(context,
                R.array.best_product_array, android.R.layout.simple_spinner_item);
        bestProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adaptador para el Spinner de categorías
        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(context,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el adaptador para el Spinner de best products
        spinnerBestProduct.setAdapter(bestProductAdapter);

        // Establecer el adaptador para el Spinner de categorías
        spinnerCategories.setAdapter(categoriesAdapter);

       spinnerCategories.setSelection(products.getCategoryId()+1);
       if(products.isBestProduct()){
           spinnerBestProduct.setSelection(1);
       }else {
           spinnerBestProduct.setSelection(2);
       }



        dialog.show();

        btnCancelar.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnActualizar.setOnClickListener(v -> {

            boolean best_product = false;
            int category_id = 0;

            if ("Selecciona la categoría".equals(spinnerCategories.getSelectedItem().toString()) ||
                    "¿Mejor producto de hoy?".equals(spinnerBestProduct.getSelectedItem().toString()) ||
                    Title.getText().toString().isEmpty() ||
                    Description.getText().toString().isEmpty() ||
                    Price.getText().toString().isEmpty() ||
                    ImagePath.getText().toString().isEmpty()) {
                Toast.makeText(context, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
            }else {


                String selectedOption = (String) spinnerBestProduct.getSelectedItem();
                if ("Sí".equals(selectedOption)) {
                    best_product = true;
                } else if ("No".equals(selectedOption)) {
                    best_product = false;
                }

                int selectedPosition = spinnerCategories.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 1:
                        category_id = 0; // Bebidas
                        break;
                    case 2:
                        category_id = 1; // Lácteos
                        break;
                    case 3:
                        category_id = 2; // Frutas y Verduras
                        break;
                    case 4:
                        category_id = 3; // Panadería
                        break;
                    case 5:
                        category_id = 4; // Limpieza
                        break;
                    case 6:
                        category_id = 5; // Baño
                        break;
                    case 7:
                        category_id = 6; // Embutidos
                        break;
                    case 8:
                        category_id = 7; // Más
                        break;
                }

                Map<String, Object> map = new HashMap<>();
                map.put("Title", Title.getText().toString());
                map.put("Description", Description.getText().toString());
                map.put("Price", Double.parseDouble(Price.getText().toString()));
                map.put("CategoryId", category_id);
                map.put("ImagePath", ImagePath.getText().toString());
                map.put("BestProduct", best_product);

                FirebaseDatabase.getInstance().getReference().child("Product")
                        .child(String.valueOf(products.getId())).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                updateProductsFromDatabase();
                                Toast.makeText(context, "Ha sido actualizado correctamente.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "No se ha podido actualizar.", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });

            }

        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private void updateProductsFromDatabase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Product");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Products> updatedProducts = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Products product = dataSnapshot.getValue(Products.class);
                    updatedProducts.add(product);
                }
                // Actualizar la lista items con los productos actualizados
                items.clear();
                items.addAll(updatedProducts);
                notifyDataSetChanged(); // Notificar al adaptador para que se actualice el RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar cancelación de la consulta
            }
        });
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, unitsTxt, txtId;
        Button btnEditar, btnEntrada, btnSalida;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            txtId = itemView.findViewById(R.id.txtId);
            unitsTxt = itemView.findViewById(R.id.unitsTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            pic = itemView.findViewById(R.id.img);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEntrada = itemView.findViewById(R.id.btnEntrada);
            btnSalida = itemView.findViewById(R.id.btnSalida);
        }
    }
}
