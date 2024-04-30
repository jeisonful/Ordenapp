package com.example.ordenapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.ordenapp.Activity.DetailActivity;
import com.example.ordenapp.Domain.Products;
import com.example.ordenapp.R;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.viewholder> {
    ArrayList<Products> items;
    Context context;

    public ProductListAdapter(ArrayList<Products> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ProductListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_product, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText("$" + items.get(position).getPrice());
        holder.rateTxt.setText("" + items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

       holder.itemView.setOnClickListener(v -> {
         Intent intent=new Intent(context, DetailActivity.class);
       intent.putExtra("object", items.get(position));
         context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, rateTxt;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            rateTxt = itemView.findViewById(R.id.rateTxt);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            pic = itemView.findViewById(R.id.img);

        }
    }
}
