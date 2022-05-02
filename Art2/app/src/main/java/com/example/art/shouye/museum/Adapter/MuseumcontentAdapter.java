package com.example.art.shouye.museum.Adapter;

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
import com.example.art.info.MuseumItem;
import com.example.art.museumDetail.MuseumDetailActivity;
import com.example.art.dataInterface.MuseumStaticData;
import com.example.art.util.httpUtil.HttpGetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.JUDGEEXIST;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class MuseumcontentAdapter extends RecyclerView.Adapter<MuseumcontentAdapter.ViewHolder> {
    private List<MuseumItem> list;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView country;
        ViewHolder(View view){
            super(view);
            image = view.findViewById(R.id.museumimage);
            name = view.findViewById(R.id.museumname);
            country = view.findViewById(R.id.museumcountry);
        }
    }
    @NonNull
    @Override
    public MuseumcontentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.museumcontentlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumcontentAdapter.ViewHolder holder, int position) {
        MuseumItem i = list.get(position);
        Glide.with(context).load(MYURL+i.getCover()).into(holder.image);
        holder.name.setText(i.getName());
        holder.country.setText(i.getContury());
        holder.image.setOnClickListener(new View.OnClickListener() {
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
                            MuseumStaticData.MUSEUMID = i.getMuseumId();
                            MuseumStaticData.MUSEUMNAME = i.getName();
                            MuseumStaticData.ISCOLLECTED = list.length() != 0;
                            Intent intent = new Intent(context, MuseumDetailActivity.class);
                            context.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                threadForJudgeExist.execute(MYURL+JUDGEEXIST,"userId",USERID,"collectionId",i.getMuseumId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public MuseumcontentAdapter(List<MuseumItem> list, Context context) {
        this.list = list;
        this.context = context;
    }
}
