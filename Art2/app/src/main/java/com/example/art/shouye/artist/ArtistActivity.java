package com.example.art.shouye.artist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.art.R;
import com.example.art.info.ArtistItem;
import com.example.art.shouye.artist.Adapter.ArtistContentAdapter;
import com.example.art.util.httpUtil.HttpGetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.example.art.dataInterface.DataInterface.GETMYCOLLECTION;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.RECOMMENDFORARTISTFROMARTWORK;
import static com.example.art.dataInterface.DataInterface.USERID;

public class ArtistActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArtistContentAdapter adapter;
    private List<ArtistItem> artistList = new ArrayList<>();
    private Set<ArtistItem> artistSet = new HashSet<>();
    private List<String> artworks = new ArrayList<>();
    private String k = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        recyclerView = findViewById(R.id.artistcontentlist);
        adapter = new ArtistContentAdapter(artistList, ArtistActivity.this);
        recyclerView.setAdapter(adapter);
        getCollectionForArtwork();
    }
    private void getListFromArtwork(String artwork){
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                String resultStr = thread.getResponseData();
                try {
                    JSONObject result = new JSONObject(resultStr);
                    JSONArray list = new JSONArray();
                    list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        ArtistItem temp = new ArtistItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setArtistId(properties.getString("artistId"));
                        temp.setCover(properties.getString("cover"));
                        temp.setName(properties.getString("name"));
                        temp.setUrl(properties.getString("url"));
                        artistSet.add(temp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+RECOMMENDFORARTISTFROMARTWORK,"artwork",artwork,"k",k);
    }
    private void getListFromArtwork_Last(String artwork){
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                String resultStr = thread.getResponseData();
                try {
                    JSONObject result = new JSONObject(resultStr);
                    JSONArray list = new JSONArray();
                    list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        ArtistItem temp = new ArtistItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setArtistId(properties.getString("artistId"));
                        temp.setCover(properties.getString("cover"));
                        temp.setName(properties.getString("name"));
                        temp.setUrl(properties.getString("url"));
                        artistSet.add(temp);
                    }

                    Iterator it = artistSet.iterator();
                    for(int i=0;i<artistSet.size();i++){
                        ArtistItem temp = (ArtistItem) it.next();
                        artistList.add(temp);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+RECOMMENDFORARTISTFROMARTWORK,"artwork",artwork,"k",k);
    }
    private void getCollectionForArtwork() {
        HttpGetUtils threadGetCollectionForArtist = new HttpGetUtils();
        threadGetCollectionForArtist.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST", threadGetCollectionForArtist.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadGetCollectionForArtist.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        String name = item.getString("name");
                        artworks.add(name);
                    }
                    for (int i=0;i<artworks.size();i++) {
                        if(i==artworks.size()-1){
                            getListFromArtwork_Last(artworks.get(i));
                        }else{
                            getListFromArtwork(artworks.get(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadGetCollectionForArtist.execute(MYURL + GETMYCOLLECTION, "userId", USERID, "type", "1");
    }
}
