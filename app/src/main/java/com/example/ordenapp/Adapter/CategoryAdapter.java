package com.example.ordenapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ordenapp.Activity.ListProductsActivity;
import com.example.ordenapp.Domain.Category;
import com.example.ordenapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    ArrayList<Category> items;
    Context context;



    public CategoryAdapter(ArrayList<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new viewholder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.cat_background);
        assert drawable != null;
        drawable = drawable.mutate();
        String colorHtml = "";
        int color;



        holder.titleTxt.setText(items.get(position).getName());

        switch (position) {
            case 0:
                colorHtml = "#F4A460";
                color = Color.parseColor(colorHtml);
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                holder.pic.setBackground(drawable);
                break;
            case 1:
                colorHtml = "#FFF8DC";
                color = Color.parseColor(colorHtml);
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                holder.pic.setBackground(drawable);
                break;
            case 2:
                colorHtml = "#7FFF00";
                color = Color.parseColor(colorHtml);
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                holder.pic.setBackground(drawable);
                break;
            case 3:
                colorHtml = "#F5DEB3";
                color = Color.parseColor(colorHtml);
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                holder.pic.setBackground(drawable);
                break;
            case 4:
                colorHtml = "#7FFFD4";
                color = Color.parseColor(colorHtml);
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                holder.pic.setBackground(drawable);
                break;
            case 5:
                colorHtml = "#00FFFF";
                color = Color.parseColor(colorHtml);
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                holder.pic.setBackground(drawable);
                break;
            case 6:
                colorHtml = "#AA0A0A";
                color = Color.parseColor(colorHtml);
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                holder.pic.setBackground(drawable);
                break;
            case 7: {
                holder.pic.setBackgroundResource(R.drawable.cat_background);
                break;
            }
        }
        int drawableResourceId = context.getResources().getIdentifier(items.get(position).getImagePath()
                , "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListProductsActivity.class);
            intent.putExtra("Id", items.get(position).getId());
            intent.putExtra("Name", items.get(position).getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.catNameTxt);
            pic = itemView.findViewById(R.id.imgCat);
        }
    }
}
