package com.example.art.shouye.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.art.R;
import com.example.art.info.info1;
import com.example.art.theme.artTheme.TagsForArtActivity;
import com.example.art.theme.auctionTheme.TagsForAuctionActivity;
import com.example.art.theme.museumTheme.TagsForMuseumActivity;

import java.util.List;

public class themeAdapter extends RecyclerView.Adapter<themeAdapter.ViewHolder> {
    private List<info1> list;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.themeimage);
            textView = view.findViewById(R.id.themetext);
        }
    }

    public themeAdapter(List<info1> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.themelist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        info1 i = list.get(position);
        holder.imageView.setImageResource(i.getImageId());
        holder.textView.setText(i.getName());
        if(position==0){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TagsForArtActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if(position==1){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TagsForAuctionActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if(position==2){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TagsForMuseumActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if(position==3){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"目前只有三个主题",Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(context, TagsForMoreActivity.class);
//                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
