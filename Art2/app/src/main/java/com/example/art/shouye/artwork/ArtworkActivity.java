package com.example.art.shouye.artwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.art.R;
import com.example.art.info.ArtworkItem;
import com.example.art.shouye.artwork.Adapter.ArtworkContentAdapter;
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
import static com.example.art.dataInterface.DataInterface.RECOMMENDFORARTWORKFROMARTIST;
import static com.example.art.dataInterface.DataInterface.RECOMMENDFORARTWORKFROMMUSEUM;
import static com.example.art.dataInterface.DataInterface.USERID;

public class ArtworkActivity extends AppCompatActivity {
    private Set<ArtworkItem> artworkSet = new HashSet<>();
    private List<ArtworkItem> artworkList = new ArrayList<>();
    private List<String> myArtists = new ArrayList<>();
    private List<String> myMuseums = new ArrayList<>();
    private String k = "1";
    private ArtworkContentAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork);

        recyclerView = findViewById(R.id.artworkList);
        adapter = new ArtworkContentAdapter(artworkList, ArtworkActivity.this);
        recyclerView.setAdapter(adapter);
        getCollectionForArtist();
    }

    private void getCollectionForArtist() {
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
                        myArtists.add(name);
                    }
                    getCollectionForMuseum();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadGetCollectionForArtist.execute(MYURL + GETMYCOLLECTION, "userId", USERID, "type", "2");
    }

    private void getCollectionForMuseum(){
        HttpGetUtils threadGetCollectionForMuseum = new HttpGetUtils();
        threadGetCollectionForMuseum.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST", threadGetCollectionForMuseum.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadGetCollectionForMuseum.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        String name = item.getString("name");
                        myMuseums.add(name);
                    }
                    for (int j=0;j<myArtists.size();j++) {
                        if(j==myArtists.size()-1){
                            getListFromArtist_Last(myArtists.get(j));
                        }else{
                            getListFromArtist(myArtists.get(j));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadGetCollectionForMuseum.execute(MYURL + GETMYCOLLECTION, "userId", USERID, "type", "3");
    }

    private void getListFromArtist(String myArtist) {
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST", thread.getResponseData());
                String resultStr = thread.getResponseData();
                try {
                    JSONObject result = new JSONObject(resultStr);
                    JSONArray list = new JSONArray();
                    list = result.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        ArtworkItem temp = new ArtworkItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setId(properties.getString("artworkId"));
                        temp.setImage(properties.getString("cover"));
                        temp.setTitle(properties.getString("name"));
                        temp.setMuseum(properties.getString("museum"));
                        temp.setStatus(properties.getString("status"));
                        temp.setUrl(properties.getString("url"));
                        artworkSet.add(temp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL + RECOMMENDFORARTWORKFROMARTIST, "artist", myArtist, "k", k);
    }

    private void getListFromArtist_Last(String myArtist) {
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST", thread.getResponseData());
                String resultStr = thread.getResponseData();
                try {
                    JSONObject result = new JSONObject(resultStr);
                    JSONArray list = new JSONArray();
                    list = result.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        ArtworkItem temp = new ArtworkItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setId(properties.getString("artworkId"));
                        temp.setImage(properties.getString("cover"));
                        temp.setTitle(properties.getString("name"));
                        temp.setMuseum(properties.getString("museum"));
                        temp.setUrl(properties.getString("url"));
                        artworkSet.add(temp);
                    }
                    for (int j=0;j<myMuseums.size();j++) {
                        if(j==myMuseums.size()-1){
                            getListFromMuseum_Last(myMuseums.get(j));
                        }else{
                            getListFromMuseum(myMuseums.get(j));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL + RECOMMENDFORARTWORKFROMARTIST, "artist", myArtist, "k", k);
    }

    private void getListFromMuseum(String myMuseum) {
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST", thread.getResponseData());
                String resultStr = thread.getResponseData();
                try {
                    JSONObject result = new JSONObject(resultStr);
                    JSONArray list = new JSONArray();
                    list = result.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        ArtworkItem temp = new ArtworkItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setId(properties.getString("artworkId"));
                        temp.setImage(properties.getString("cover"));
                        temp.setTitle(properties.getString("name"));
                        temp.setMuseum(properties.getString("museum"));
                        temp.setStatus(properties.getString("status"));
                        temp.setUrl(properties.getString("url"));
                        artworkSet.add(temp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL + RECOMMENDFORARTWORKFROMMUSEUM, "museum", myMuseum, "k", k);
    }

    private void getListFromMuseum_Last(String myMuseum) {
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST", thread.getResponseData());
                String resultStr = thread.getResponseData();
                try {
                    JSONObject result = new JSONObject(resultStr);
                    JSONArray list = new JSONArray();
                    list = result.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        ArtworkItem temp = new ArtworkItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setId(properties.getString("artworkId"));
                        temp.setImage(properties.getString("cover"));
                        temp.setTitle(properties.getString("name"));
                        temp.setMuseum(properties.getString("museum"));
                        temp.setUrl(properties.getString("url"));
                        artworkSet.add(temp);
                    }
                    Iterator it = artworkSet.iterator();
                    for (int i = 0; i < artworkSet.size(); i++) {
                        ArtworkItem temp = (ArtworkItem) it.next();
                        artworkList.add(temp);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL + RECOMMENDFORARTWORKFROMMUSEUM, "museum", myMuseum, "k", k);
    }
}
