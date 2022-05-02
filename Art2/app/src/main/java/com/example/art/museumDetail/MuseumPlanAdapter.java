package com.example.art.museumDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.art.R;

import java.util.List;

public class MuseumPlanAdapter extends RecyclerView.Adapter<MuseumPlanAdapter.ViewHolder > {
    private List<planItem> list;
    private Context context;
    @NonNull
    @Override
    public MuseumPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_item,parent,false);
        return new MuseumPlanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumPlanAdapter.ViewHolder holder, int position) {
        planItem i = list.get(position);
        holder.time.setText(i.getTime());
        holder.note.setText(i.getNote());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView note;
        ViewHolder(View view){
            super(view);
            time = view.findViewById(R.id.time);
            note = view.findViewById(R.id.note);
        }
    }
    public MuseumPlanAdapter(List<planItem> list, Context context) {
        this.list = list;
        this.context = context;
    }
}