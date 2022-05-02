package com.example.art.theme.artTheme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.art.R;
import com.example.art.info.ArtworkItem;
import com.example.art.shouye.artwork.Adapter.ArtworkContentAdapter;
import com.example.art.util.httpUtil.HttpGetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.ARTTHEMEFORTAG;
import static com.example.art.dataInterface.DataInterface.MYURL;

public class ThemeForArtActivity extends AppCompatActivity {
    private List<ArtworkItem> artworkList = new ArrayList<>();
    private String tag;
    TextView note;
    private RecyclerView recyclerView;
    private ArtworkContentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_for_art);
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        note = findViewById(R.id.note);
        note.setText(tag);
        //适配器
        recyclerView = findViewById(R.id.artworkList);
        adapter= new ArtworkContentAdapter(artworkList, ThemeForArtActivity.this);
        recyclerView.setAdapter(adapter);
        getListFromArtist();
    }
    private void getListFromArtist(){
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                String resultStr = thread.getResponseData();
                try {
                    JSONObject result = new JSONObject(resultStr);
                    JSONArray list  = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        ArtworkItem temp = new ArtworkItem();
                        JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                        temp.setId(properties.getString("artworkId"));
                        temp.setImage(properties.getString("cover"));
                        temp.setTitle(properties.getString("name"));
                        temp.setMuseum(properties.getString("museum"));
                        temp.setUrl(properties.getString("url"));
                        temp.setStatus(properties.getString("status"));
                        artworkList.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+ARTTHEMEFORTAG,"tag",tag);
    }
}