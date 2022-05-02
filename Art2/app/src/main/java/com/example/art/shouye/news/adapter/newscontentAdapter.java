package com.example.art.shouye.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.info.info2;
import com.example.art.shouye.news.NewsContentActivity;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.MYURL;

public class newscontentAdapter extends RecyclerView.Adapter<newscontentAdapter.ViewHolder > {
    private List<info2> list;
    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_content_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        info2 i = list.get(position);
        Glide.with(context).load(MYURL+i.getCover()).into(holder.cover);
        holder.title.setText(i.getTitle());
        holder.time.setText(i.getTime().substring(0,10));
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsContentActivity.class);
                intent.putExtra("title",i.getTitle());
                intent.putExtra("keyword",i.getKeyword());
                intent.putExtra("editor",i.getEditor());
                intent.putExtra("time",i.getTime());
                intent.putExtra("content",i.getContent());
                intent.putExtra("url",i.getUrl());
                intent.putExtra("source",i.getSource());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView title;
        TextView time;
        ConstraintLayout newsItem;
        ViewHolder(View view){
            super(view);
            cover = view.findViewById(R.id.cover);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
        }
    }

    public newscontentAdapter(List<info2> list, Context context) {
        this.list = list;
        this.context = context;
    }
}
