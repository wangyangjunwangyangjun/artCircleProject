package com.example.art.shouye.artwork.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.info.ArtworkItem;
import com.example.art.shouye.artwork.ArtworkStaticData;
import com.example.art.util.WebViewUtil.UrlForArtworkActivity;
import com.example.art.util.httpUtil.HttpGetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.JUDGEEXIST;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class ArtworkContentAdapter extends RecyclerView.Adapter<ArtworkContentAdapter.ViewHolder> {
    private List<ArtworkItem> list;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView title;
        TextView museum;
        ViewHolder(View view){
            super(view);
            cover = view.findViewById(R.id.cover);
            title = view.findViewById(R.id.title);
            museum = view.findViewById(R.id.museum);
        }
    }
    public ArtworkContentAdapter(List<ArtworkItem> l, Context context){
        this.context = context;
        list = l;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artworkcontentlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArtworkItem i = list.get(position);
        Glide.with(context).load(MYURL+i.getImage()).into(holder.cover);
        holder.title.setText(i.getTitle());
        holder.museum.setText(i.getMuseum());
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取是否已经收藏
                HttpGetUtils threadForJudgeExist = new HttpGetUtils();
                threadForJudgeExist.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
                    @Override
                    public void onSuccessGet() {
                        Log.i("TEST",threadForJudgeExist.getResponseData());
                        try {
                            JSONObject result = new JSONObject(threadForJudgeExist.getResponseData());
                            JSONArray list = result.getJSONArray("list");
                            ArtworkStaticData.ARTWORK_ID = i.getId();
                            ArtworkStaticData.ARTWORK_NAME = i.getTitle();
                            ArtworkStaticData.ARTWORK_COVER = i.getImage();
                            ArtworkStaticData.IS_COLLECTED = list.length() != 0;
                            ArtworkStaticData.ARTWORK_URL = i.getUrl();
                            Intent intent = new Intent(context, UrlForArtworkActivity.class);
                            intent.putExtra("url",i.getUrl());
                            context.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                threadForJudgeExist.execute(MYURL+JUDGEEXIST,"userId",USERID,"collectionId",i.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}