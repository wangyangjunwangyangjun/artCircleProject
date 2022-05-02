package com.example.art.mine;

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
import com.example.art.info.CollectionItem;
import com.example.art.util.roundImageView.RoundImageView;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.MYURL;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.ViewHolder > {
    private List<CollectionItem> list;
    private Context context;
    @NonNull
    @Override
    public AchievementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.achievement_list,parent,false);
        return new AchievementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementAdapter.ViewHolder holder, int position) {
        CollectionItem i = list.get(position);
        Glide.with(context).load(MYURL+i.getLogo()).into(holder.logo);
        holder.name.setText(i.getName());
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
    public AchievementAdapter(List<CollectionItem> list, Context context) {
        this.list = list;
        this.context = context;
    }
}
