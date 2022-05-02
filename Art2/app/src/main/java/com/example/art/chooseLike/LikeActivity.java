package com.example.art.chooseLike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.art.R;
import com.example.art.info.LikeItem;
import com.example.art.shouye.ShouYeActivity;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.httpUtil.HttpPostUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.ADDCOLLECTION;
import static com.example.art.dataInterface.DataInterface.GETALLARTIST;
import static com.example.art.dataInterface.DataInterface.GETALLMUSEUM;
import static com.example.art.dataInterface.DataInterface.GETAllARTWORK;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.util.AlterDialog.AlterDialogUtil.TipForGuide;

public class LikeActivity extends AppCompatActivity {
    private ItemAdapter artworkAdapter;
    private ItemAdapter artistAdapter;
    private ItemAdapter museumAdapter;
    private List<LikeItem> artworkList = new ArrayList<>();
    private List<LikeItem> artistList = new ArrayList<>();
    private List<LikeItem> museumList = new ArrayList<>();
    private RecyclerView artworkRecyclerView;
    private RecyclerView artistRecyclerView;
    private RecyclerView museumRecyclerView;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        //显示引导信息
        TipForGuide(LikeActivity.this,R.layout.guide_like_activity);
        InitView();
        artworkAdapter = new ItemAdapter(artworkList,LikeActivity.this);
        artistAdapter = new ItemAdapter(artistList,LikeActivity.this);
        museumAdapter = new ItemAdapter(museumList,LikeActivity.this);
        artworkRecyclerView.setAdapter(artworkAdapter);
        artistRecyclerView.setAdapter(artistAdapter);
        museumRecyclerView.setAdapter(museumAdapter);

        //滑动惯性
        artworkRecyclerView.setHasFixedSize(true);
        artworkRecyclerView.setNestedScrollingEnabled(false);
        artistRecyclerView.setHasFixedSize(true);
        artistRecyclerView.setNestedScrollingEnabled(false);
        museumRecyclerView.setHasFixedSize(true);
        museumRecyclerView.setNestedScrollingEnabled(false);
        getData();

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<artworkList.size();i++){
                    LikeItem item = artworkList.get(i);
                    if(item.getStatus()==1){
                        addToCollection(USERID,item.getCollectionId(),1,item.getLogo(),item.getName(),item.getUrl());
                    }
                }
                for(int i=0;i<artistList.size();i++){
                    LikeItem item = artistList.get(i);
                    if(item.getStatus()==1){
                        addToCollection(USERID,item.getCollectionId(),2,item.getLogo(),item.getName(),item.getUrl());
                    }
                }
                for(int i=0;i<museumList.size();i++){
                    LikeItem item = museumList.get(i);
                    if(item.getStatus()==1){
                        addToCollection(USERID,item.getCollectionId(),3,item.getLogo(),item.getName(),"null");
                    }
                }
            }
        });
    }
    private void getData(){
        HttpGetUtils threadForMuseum = new HttpGetUtils();
        threadForMuseum.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",threadForMuseum.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadForMuseum.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    int maxSize = Math.min(list.length(), 20);
                    for(int i=0;i<maxSize;i++){
                        JSONObject item = list.getJSONObject(i);
                        LikeItem temp = new LikeItem();
                        temp.setLogo(item.getString("cover"));
                        temp.setName(item.getString("name"));
                        temp.setCollectionId(item.getString("id"));
                        temp.setStatus(0);
                        temp.setUrl(item.getString("url"));
                        museumList.add(temp);
                    }
                    museumAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForMuseum.execute(MYURL+GETALLMUSEUM);
        HttpGetUtils threadForArtwork = new HttpGetUtils();
        threadForArtwork.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",threadForArtwork.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadForArtwork.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    int maxSize = Math.min(list.length(), 20);
                    for(int i=0;i<maxSize;i++){
                        JSONObject item = list.getJSONObject(i);
                        LikeItem temp = new LikeItem();
                        temp.setLogo(item.getString("cover"));
                        temp.setName(item.getString("title"));
                        temp.setCollectionId(item.getString("id"));
                        temp.setStatus(0);
                        temp.setUrl(item.getString("url"));
                        artworkList.add(temp);
                    }
                    artworkAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForArtwork.execute(MYURL+GETAllARTWORK);
        HttpGetUtils threadForArtist = new HttpGetUtils();
        threadForArtist.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",threadForArtist.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadForArtist.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    //只显示前面的20个
                    int maxSize = Math.min(list.length(), 20);
                    for(int i=0;i<maxSize;i++){
                        JSONObject item = list.getJSONObject(i);
                        LikeItem temp = new LikeItem();
                        temp.setLogo(item.getString("cover"));
                        temp.setName(item.getString("name"));
                        temp.setCollectionId(item.getString("id"));
                        temp.setStatus(0);
                        temp.setUrl(item.getString("url"));
                        artistList.add(temp);
                    }
                    artistAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForArtist.execute(MYURL+GETALLARTIST);
    }
    private void InitView(){
        artworkRecyclerView = findViewById(R.id.artworkList);
        artistRecyclerView = findViewById(R.id.artistList);
        museumRecyclerView = findViewById(R.id.museumList);
    }
    private void addToCollection(String userId,String collectionId,int type,String cover,String name,String url){
        HttpPostUtils threadForAddCollection = new HttpPostUtils();
        threadForAddCollection.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",threadForAddCollection.getResponseData());
                Intent intent = new Intent(LikeActivity.this, ShouYeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("url",MYURL+ADDCOLLECTION);
            data.put("userId",userId);
            data.put("collectionId",collectionId);
            data.put("type",type);
            data.put("cover",cover);
            data.put("name",name);
            data.put("url",url);
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        threadForAddCollection.execute(body);
    }
}