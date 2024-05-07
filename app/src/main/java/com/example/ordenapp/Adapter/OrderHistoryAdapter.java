package com.example.ordenapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ordenapp.Activity.OrderDetailActivity;
import com.example.ordenapp.Domain.Orders;
import com.example.ordenapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ORDER_HISTORY = 1;
    private static final int TYPE_ORDER_PENDING = 2;
    private static final int TYPE_ORDER_SHIPPED = 3;

    private ArrayList<Orders> items;
    private Context context;

    public OrderHistoryAdapter(ArrayList<Orders> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case TYPE_ORDER_PENDING:
                View pendingView = inflater.inflate(R.layout.viewholder_orderspending, parent, false);
                return new PendingViewHolder(pendingView);
            case TYPE_ORDER_SHIPPED:
                View shippedView = inflater.inflate(R.layout.viewholder_ordersshipped, parent, false);
                return new ShippedViewHolder(shippedView);
            default:
                View historyView = inflater.inflate(R.layout.viewholder_orderhistory, parent, false);
                return new HistoryViewHolder(historyView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Orders order = items.get(position);

        switch (holder.getItemViewType()) {
            case TYPE_ORDER_PENDING:
                PendingViewHolder pendingViewHolder = (PendingViewHolder) holder;
                pendingViewHolder.bind(order);
                break;
            case TYPE_ORDER_SHIPPED:
                ShippedViewHolder shippedViewHolder = (ShippedViewHolder) holder;
                shippedViewHolder.bind(order);
                break;
            default:
                HistoryViewHolder historyViewHolder = (HistoryViewHolder) holder;
                historyViewHolder.bind(order);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Orders order = items.get(position);
        switch (order.getStatus()) {
            case "Pendiente":
                return TYPE_ORDER_PENDING;
            case "Enviada":
                return TYPE_ORDER_SHIPPED;
            default:
                return TYPE_ORDER_HISTORY;
        }
    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTimeTxt;
        private TextView orderIdTxt;
        private TextView statusTxt;
        private TextView btnSeeDetails;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeTxt = itemView.findViewById(R.id.dateTimeTxt);
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            btnSeeDetails = itemView.findViewById(R.id.btnSeeDetails);
        }

        public void bind(Orders order) {
            dateTimeTxt.setText(order.getDateTime());
            orderIdTxt.setText("Orden #" + order.getId());
            String status = order.getStatus();
            statusTxt.setText(status);
            if ("Pendiente".equals(status)) {
                statusTxt.setTextColor(Color.RED);
            } else if ("Enviada".equals(status)) {
                statusTxt.setTextColor(Color.BLUE);
            } else if ("Entregada".equals(status)) {
                statusTxt.setTextColor(Color.GREEN);
            }
            btnSeeDetails.setOnClickListener(v -> {
                Intent intent=new Intent(context, OrderDetailActivity.class);
                intent.putExtra("object", order);
                context.startActivity(intent);
            });
        }
    }

    private class PendingViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTimeTxt;
        private TextView orderIdTxt;
        private TextView statusTxt;
        private TextView btnSeeDetails;
        private TextView btnEnviar;

        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeTxt = itemView.findViewById(R.id.dateTimeTxt);
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            btnSeeDetails = itemView.findViewById(R.id.btnSeeDetails);
            btnEnviar = itemView.findViewById(R.id.btnEnviar);

        }

        public void bind(Orders order) {
            dateTimeTxt.setText(order.getDateTime());
            orderIdTxt.setText("Orden #" + order.getId());
            statusTxt.setText(order.getStatus());
            String status = order.getStatus();
            if ("Pendiente".equals(status)) {
                statusTxt.setTextColor(Color.RED);
            } else if ("Enviada".equals(status)) {
                statusTxt.setTextColor(Color.BLUE);
            } else if ("Entregada".equals(status)) {
                statusTxt.setTextColor(Color.GREEN);
            }
            btnSeeDetails.setOnClickListener(v -> {
                Intent intent=new Intent(context, OrderDetailActivity.class);
                intent.putExtra("object", order);
                context.startActivity(intent);
            });

            btnEnviar.setOnClickListener(v -> {
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders").child(String.valueOf(order.getId()));
                orderRef.child("Status").setValue("Enviada")
                        .addOnSuccessListener(aVoid -> Toast.makeText(context, "Orden marcada como enviada", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Error al marcar la orden como enviada", Toast.LENGTH_SHORT).show();
                        });
                notifyDataSetChanged();
            });
        }
    }

    private class ShippedViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTimeTxt;
        private TextView orderIdTxt;
        private TextView statusTxt;
        private TextView btnSeeDetails;
        private TextView btnEntregar;

        public ShippedViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeTxt = itemView.findViewById(R.id.dateTimeTxt);
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            btnSeeDetails = itemView.findViewById(R.id.btnSeeDetails);
            btnEntregar = itemView.findViewById(R.id.btnEntregar);
        }

        public void bind(Orders order) {
            dateTimeTxt.setText(order.getDateTime());
            orderIdTxt.setText("Orden #" + order.getId());
            statusTxt.setText(order.getStatus());
            String status = order.getStatus();
            if ("Pendiente".equals(status)) {
                statusTxt.setTextColor(Color.RED);
            } else if ("Enviada".equals(status)) {
                statusTxt.setTextColor(Color.BLUE);
            } else if ("Entregada".equals(status)) {
                statusTxt.setTextColor(Color.GREEN);
            }
            btnSeeDetails.setOnClickListener(v -> {
                Intent intent=new Intent(context, OrderDetailActivity.class);
                intent.putExtra("object", order);
                context.startActivity(intent);
            });

            btnEntregar.setOnClickListener(v -> {
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders").child(String.valueOf(order.getId()));
                orderRef.child("Status").setValue("Entregada")
                        .addOnSuccessListener(aVoid -> Toast.makeText(context, "Orden marcada como entregada", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Error al marcar la orden como entregada", Toast.LENGTH_SHORT).show();
                        });
            });
        }
    }
}
