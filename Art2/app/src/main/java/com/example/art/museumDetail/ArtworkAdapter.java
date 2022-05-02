package com.example.art.museumDetail;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.art.shouye.artwork.ArtworkStaticData;
import com.example.art.util.WebViewUtil.UrlForArtworkActivity;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.roundImageView.RoundImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.JUDGEEXIST;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkAdapter.ViewHolder > {
    private List<CollectionItem> list;
    private Context context;
    @NonNull
    @Override
    public ArtworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item,parent,false);
        return new ArtworkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkAdapter.ViewHolder holder, int position) {
        CollectionItem i = list.get(position);
        Glide.with(context).load(MYURL+i.getLogo()).into(holder.logo);
        holder.name.setText(i.getName());
        holder.logo.setOnClickListener(new View.OnClickListener() {
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
                            ArtworkStaticData.ARTWORK_NAME = i.getName();
                            ArtworkStaticData.ARTWORK_COVER = i.getLogo();
                            ArtworkStaticData.IS_COLLECTED = list.length() != 0;
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
    public ArtworkAdapter(List<CollectionItem> list, Context context) {
        this.list = list;
        this.context = context;
    }
}
