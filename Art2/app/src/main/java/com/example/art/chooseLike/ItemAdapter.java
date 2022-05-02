package com.example.art.chooseLike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.info.LikeItem;
import com.example.art.util.roundImageView.RoundImageView;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.MYURL;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder > {
    private List<LikeItem> list;
    private Context context;
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item,parent,false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        LikeItem i = list.get(position);
        Glide.with(context).load(MYURL+i.getLogo()).into(holder.logo);
        holder.name.setText(i.getName());
        holder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikeItem item = list.get(position);
                if(item.getStatus()==1){
                    item.setStatus(0);
                    holder.container.setBackground(context.getResources().getDrawable(R.drawable.ripple_bg));
                }else{
                    item.setStatus(1);
                    holder.container.setBackground(context.getResources().getDrawable(R.drawable.ripple_bg_4));
                }
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikeItem item = list.get(position);
                if(item.getStatus()==1){
                    item.setStatus(0);
                    holder.container.setBackground(context.getResources().getDrawable(R.drawable.ripple_bg));
                }else{
                    item.setStatus(1);
                    holder.container.setBackground(context.getResources().getDrawable(R.drawable.ripple_bg_4));
                }
            }
        });
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LikeItem item = list.get(position);
                if(item.getStatus()==1){
                    item.setStatus(0);
                    holder.container.setBackground(context.getResources().getDrawable(R.drawable.ripple_bg));
                }else{
                    item.setStatus(1);
                    holder.container.setBackground(context.getResources().getDrawable(R.drawable.ripple_bg_4));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        RoundImageView logo;
        TextView name;
        ConstraintLayout container;
        ViewHolder(View view){
            super(view);
            logo = view.findViewById(R.id.logo);
            name = view.findViewById(R.id.name);
            container = view.findViewById(R.id.container);
        }
    }
    public ItemAdapter(List<LikeItem> list, Context context) {
        this.list = list;
        this.context = context;
    }
}
