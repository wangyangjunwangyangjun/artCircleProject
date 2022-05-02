package com.example.art.shouye.news.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.art.R;
import com.example.art.info.info1;

import java.util.List;

public class newstypeAdapter extends RecyclerView.Adapter<newstypeAdapter.ViewHolder> {
    private List<info1> list;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newsandartistandartworktypelist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        info1 i = list.get(position);
        holder.textView.setText(i.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.newstext);
        }
    }
    public newstypeAdapter(List<info1> l){
        list = l;
    }
}