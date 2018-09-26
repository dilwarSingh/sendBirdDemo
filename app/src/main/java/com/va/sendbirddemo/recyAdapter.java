package com.va.sendbirddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recyAdapter extends RecyclerView.Adapter<recyAdapter.viewHolder> {
    Context context;
    List<dataHolder> list;

    public recyAdapter(Context context, List<dataHolder> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_display, parent, false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        dataHolder data = list.get(position);

        holder.userId.setText(data.user);
        holder.message.setText(data.messgae);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView userId;
        TextView message;

        public viewHolder(View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            message = itemView.findViewById(R.id.message);
        }
    }
}
