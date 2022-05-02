package com.example.art.museumDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.art.R;
import com.example.art.info.CollectionItem;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.GETMUSEUMARTWORK;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.MuseumStaticData.MUSEUMNAME;

public class Artwork extends Fragment {
    private ObservableScrollView mScrollView;
    private RecyclerView artworkList;
    private List<CollectionItem> ArtworkList = new ArrayList<>();
    private ArtworkAdapter adapter;

    public static Artwork newInstance() {
        return new Artwork();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.artwork, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        artworkList = view.findViewById(R.id.artworkList);
        adapter = new ArtworkAdapter(ArtworkList,getActivity());
        artworkList.setAdapter(adapter);
        getData();
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }

    private void getData(){
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                JSONObject result = null;
                try {
                    result = new JSONObject(thread.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject item = null;
                        item = list.getJSONObject(i);
                        JSONArray _fields = item.getJSONArray("_fields");
                        JSONObject properties = _fields.getJSONObject(0).getJSONObject("properties");
                        String cover = properties.getString("cover");
                        String name = properties.getString("name");
                        String url = properties.getString("url");
                        String artworkId = properties.getString("artworkId");
                        CollectionItem temp = new CollectionItem();
                        temp.setName(name);
                        temp.setLogo(cover);
                        temp.setUrl(url);
                        temp.setId(artworkId);
                        ArtworkList.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+GETMUSEUMARTWORK,"museumName",MUSEUMNAME);
    }
}