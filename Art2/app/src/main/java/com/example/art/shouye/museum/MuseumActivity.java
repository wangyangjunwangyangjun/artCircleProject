package com.example.art.shouye.museum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.art.R;
import com.example.art.info.MuseumItem;
import com.example.art.shouye.museum.Adapter.MuseumcontentAdapter;
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
import static com.example.art.dataInterface.DataInterface.RECOMMENDFORMUSEUMFROMARTWORK;
import static com.example.art.dataInterface.DataInterface.USERID;

public class MuseumActivity extends AppCompatActivity {
    private RecyclerView museum_recyclerView;
    private MuseumcontentAdapter museumcontentAdapter;
    private List<MuseumItem> museumList = new ArrayList<>();
    private Set<MuseumItem> museumSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);
        museum_recyclerView = findViewById(R.id.museumContentList);
        museumcontentAdapter= new MuseumcontentAdapter(museumList, MuseumActivity.this);
        museum_recyclerView.setAdapter(museumcontentAdapter);
        getCollectionForArtwork();
    }
    private void getMuseum(String artwork){
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
                        MuseumItem temp = new MuseumItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setCover(properties.getString("cover"));
                        temp.setContury(properties.getString("country"));
                        temp.setName(properties.getString("name"));
                        temp.setMuseumId(properties.getString("museumId"));
                        museumSet.add(temp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+RECOMMENDFORMUSEUMFROMARTWORK,"artwork",artwork,"k","1");
    }

    private void getMuseum_Last(String artwork){
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
                        MuseumItem temp = new MuseumItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setCover(properties.getString("cover"));
                        temp.setContury(properties.getString("country"));
                        temp.setName(properties.getString("name"));
                        temp.setMuseumId(properties.getString("museumId"));
                        museumSet.add(temp);
                    }
                    Iterator it = museumSet.iterator();
                    for (int i = 0; i < museumSet.size(); i++) {
                        MuseumItem temp = (MuseumItem) it.next();
                        museumList.add(temp);
                    }
                    museumcontentAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+RECOMMENDFORMUSEUMFROMARTWORK,"artwork",artwork,"k","1");
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
                        if(i==list.length()-1){
                            getMuseum_Last(name);
                        }else{
                            getMuseum(name);
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
