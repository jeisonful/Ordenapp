package com.example.ordenapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordenapp.Domain.OrderDetails;
import com.example.ordenapp.R;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.viewholder>{
    ArrayList<OrderDetails> items;
    Context context;

    public OrderHistoryAdapter(ArrayList<OrderDetails> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public OrderHistoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_orderhistory,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.viewholder holder, int position) {
        holder.dateTimeTxt.setText(String.valueOf(items.get(position).getDateTime()));
        holder.orderIdTxt.setText("#"+ items.get(position).getId());
        holder.statusTxt.setText(items.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView dateTimeTxt, orderIdTxt, statusTxt;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            dateTimeTxt = itemView.findViewById(R.id.dateTimeTxt);
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
        }
    }
}
