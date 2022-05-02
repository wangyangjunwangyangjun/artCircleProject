package com.example.art.shouye.artist.Adapter;

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
import com.example.art.info.ArtistItem;
import com.example.art.shouye.artist.ArtistStaticData;
import com.example.art.util.WebViewUtil.UrlForArtistActivity;
import com.example.art.util.httpUtil.HttpGetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.JUDGEEXIST;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class ArtistContentAdapter extends RecyclerView.Adapter<ArtistContentAdapter.ViewHolder> {
    private List<ArtistItem> list;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView artistImage;
        TextView artistName;
        ViewHolder(View view){
            super(view);
            artistImage = view.findViewById(R.id.artistImage);
            artistName = view.findViewById(R.id.artistName);
        }
    }
    public ArtistContentAdapter(List<ArtistItem> l, Context context){
        list = l;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artistcontentlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArtistItem i = list.get(position);
        Glide.with(context).load(MYURL+i.getCover()).into(holder.artistImage);
        holder.artistName.setText(i.getName());
        holder.artistImage.setOnClickListener(new View.OnClickListener() {
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
                            ArtistStaticData.ARTIST_ID = i.getArtistId();
                            ArtistStaticData.ARTIST_NAME = i.getName();
                            ArtistStaticData.ARTIST_COVER = i.getCover();
                            ArtistStaticData.IS_COLLECTED = list.length() != 0;
                            ArtistStaticData.ARTIST_URL = i.getUrl();
                            Intent intent = new Intent(context, UrlForArtistActivity.class);
                            intent.putExtra("url",i.getUrl());
                            context.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                threadForJudgeExist.execute(MYURL+JUDGEEXIST,"userId",USERID,"collectionId",i.getArtistId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
