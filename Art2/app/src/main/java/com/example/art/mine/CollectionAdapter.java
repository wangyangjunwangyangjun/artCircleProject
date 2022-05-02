package com.example.art.mine;

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
import com.example.art.dataInterface.MuseumStaticData;
import com.example.art.info.CollectionItem;
import com.example.art.museumDetail.MuseumDetailActivity;
import com.example.art.shouye.ShouYeActivity;
import com.example.art.shouye.artist.ArtistStaticData;
import com.example.art.shouye.artwork.ArtworkStaticData;
import com.example.art.util.WebViewUtil.UrlForArtistActivity;
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
import static com.example.art.dataInterface.DataInterface.USERLOGO;
import static com.example.art.dataInterface.DataInterface.USERNAME;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder > {
    private List<CollectionItem> list;
    private Context context;
    private int type;
    @NonNull
    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_item,parent,false);
        return new CollectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.ViewHolder holder, int position) {
        CollectionItem i = list.get(position);
        Glide.with(context).load(MYURL+i.getLogo()).into(holder.logo);
        holder.name.setText(i.getName());
        if(type==3){
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
                                ArtistStaticData.ARTIST_ID = i.getId();
                                ArtistStaticData.ARTIST_NAME = i.getName();
                                ArtistStaticData.ARTIST_COVER = i.getLogo();
                                ArtistStaticData.IS_COLLECTED = list.length() != 0;
                                Intent intent = new Intent(context, UrlForArtistActivity.class);
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
        }else if(type==1){
            holder.logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpGetUtils threadForJudgeExist = new HttpGetUtils();
                    threadForJudgeExist.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
                        @Override
                        public void onSuccessGet() {
                            Log.i("TEST",threadForJudgeExist.getResponseData());
                            try {
                                JSONObject result = new JSONObject(threadForJudgeExist.getResponseData());
                                JSONArray list = result.getJSONArray("list");
                                MuseumStaticData.MUSEUMID = i.getId();
                                MuseumStaticData.MUSEUMNAME = i.getName();
                                MuseumStaticData.ISCOLLECTED = list.length() != 0;
                                Intent intent = new Intent(context, MuseumDetailActivity.class);
                                context.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    threadForJudgeExist.execute(MYURL+JUDGEEXIST,"userId",USERID,"collectionId",i.getId());
                }
            });
        }else if(type==2){
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
        }else if(type==4){
            holder.logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MineActivity.class);
                    //此处临时更改当前用户，为的是进入到不同用户主页
                    intent.putExtra("userId",i.getId());
                    intent.putExtra("userName",i.getName());
                    intent.putExtra("userLogo",i.getLogo());
                    context.startActivity(intent);
                }
            });
        }
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
    public CollectionAdapter(List<CollectionItem> list, Context context, int myType) {
        this.list = list;
        this.context = context;
        this.type = myType;
    }
}
