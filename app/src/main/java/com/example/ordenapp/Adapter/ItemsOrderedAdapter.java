package com.example.ordenapp.Adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.example.ordenapp.Domain.OrderDetails;
import com.example.ordenapp.Domain.Orders;
import com.example.ordenapp.R;

import java.util.ArrayList;

public class ItemsOrderedAdapter extends RecyclerView.Adapter<ItemsOrderedAdapter.viewholder>{
    ArrayList<OrderDetails> items;
    Context context;

    public ItemsOrderedAdapter(ArrayList<OrderDetails> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public ItemsOrderedAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_itemsordered,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsOrderedAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.quantityTxt.setText(String.valueOf(items.get(position).getQuantity()));
        holder.priceTxt.setText(String.valueOf(items.get(position).getPrice()));

        Glide.with(context).load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView titleTxt, quantityTxt, priceTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
