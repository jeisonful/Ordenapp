package com.example.ordenapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ordenapp.Activity.OrderDetailActivity;
import com.example.ordenapp.Domain.Orders;
import com.example.ordenapp.R;
import java.util.ArrayList;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {
    private ArrayList<Orders> items;
    private Context context;

    public UserOrderAdapter(ArrayList<Orders> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_orderhistory, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orders orderDetails = items.get(position);

        holder.dateTimeTxt.setText(orderDetails.getDateTime());
        holder.orderIdTxt.setText("Orden #" + orderDetails.getId());
        String status = orderDetails.getStatus();
        holder.statusTxt.setText(status);
        if (status.equals("Pendiente")) {
            holder.statusTxt.setTextColor(Color.RED);
        } else if (status.equals("Enviada")) {
            holder.statusTxt.setTextColor(Color.BLUE);
        } else if (status.equals("Entregada")) {
            holder.statusTxt.setTextColor(Color.GREEN);
        }

        holder.btnSeeDetails.setOnClickListener(v -> {
            Intent intent=new Intent(context, OrderDetailActivity.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTimeTxt, orderIdTxt, statusTxt;
        TextView btnSeeDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeTxt = itemView.findViewById(R.id.dateTimeTxt);
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            btnSeeDetails = itemView.findViewById(R.id.btnSeeDetails);


        }
    }
}
