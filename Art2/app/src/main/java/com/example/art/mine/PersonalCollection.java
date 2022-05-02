package com.example.art.mine;

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

import static com.example.art.dataInterface.DataInterface.GETMYCOLLECTION;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class PersonalCollection extends Fragment {
    private ObservableScrollView mScrollView;
    private final List<CollectionItem> museumList = new ArrayList<>();
    private final List<CollectionItem> artistList = new ArrayList<>();
    private final List<CollectionItem> artworkList = new ArrayList<>();
    private final List<CollectionItem> userList = new ArrayList<>();
    private RecyclerView museumListRecyclerView;
    private RecyclerView artistListRecyclerView;
    private RecyclerView artworkListRecyclerView;
    private RecyclerView userListRecyclerView;
    public static PersonalCollection newInstance() {
        return new PersonalCollection();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.four, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        museumListRecyclerView = view.findViewById(R.id.museumRecyclerView);
        artistListRecyclerView = view.findViewById(R.id.artistListRecyclerView);
        artworkListRecyclerView = view.findViewById(R.id.artworkListRecyclerView);
        userListRecyclerView = view.findViewById(R.id.userListRecyclerView);
        CollectionAdapter museumAdapter = new CollectionAdapter(museumList,getActivity(),1);
        CollectionAdapter artistAdapter = new CollectionAdapter(artistList,getActivity(),2);
        CollectionAdapter artworkAdapter = new CollectionAdapter(artworkList,getActivity(),3);
        CollectionAdapter userAdapter = new CollectionAdapter(userList,getActivity(),4);
        museumListRecyclerView.setAdapter(museumAdapter);
        artistListRecyclerView.setAdapter(artistAdapter);
        artworkListRecyclerView.setAdapter(artworkAdapter);
        userListRecyclerView.setAdapter(userAdapter);

        //获取数据
        HttpGetUtils threadForMuseum = new HttpGetUtils();
        threadForMuseum.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",threadForMuseum.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadForMuseum.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject item = list.getJSONObject(i);
                        String name = item.getString("name");
                        String cover = item.getString("cover");
                        String collectionId = item.getString("collectionId");
                        CollectionItem temp = new CollectionItem();
                        temp.setName(name);
                        temp.setLogo(cover);
                        temp.setId(collectionId);
                        museumList.add(temp);
                    }
                    museumAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForMuseum.execute(MYURL+GETMYCOLLECTION,"userId",USERID,"type","3");
        HttpGetUtils threadForArtist = new HttpGetUtils();
        threadForArtist.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",threadForArtist.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadForArtist.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject item = list.getJSONObject(i);
                        String name = item.getString("name");
                        String cover = item.getString("cover");
                        String collectionId = item.getString("collectionId");
                        String url = item.getString("url");
                        CollectionItem temp = new CollectionItem();
                        temp.setName(name);
                        temp.setLogo(cover);
                        temp.setId(collectionId);
                        temp.setUrl(url);
                        artistList.add(temp);
                    }
                    artistAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        threadForArtist.execute(MYURL+GETMYCOLLECTION,"userId",USERID,"type","2");
        HttpGetUtils threadForArtwork = new HttpGetUtils();
        threadForArtwork.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",threadForArtwork.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadForArtwork.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject item = list.getJSONObject(i);
                        String name = item.getString("name");
                        String cover = item.getString("cover");
                        String collectionId = item.getString("collectionId");
                        String url = item.getString("url");
                        CollectionItem temp = new CollectionItem();
                        temp.setName(name);
                        temp.setLogo(cover);
                        temp.setId(collectionId);
                        temp.setUrl(url);
                        artworkList.add(temp);
                    }
                    artworkAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForArtwork.execute(MYURL+GETMYCOLLECTION,"userId",USERID,"type","1");
        HttpGetUtils threadForUser = new HttpGetUtils();
        threadForUser.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",threadForUser.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadForUser.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject item = list.getJSONObject(i);
                        String name = item.getString("name");
                        String cover = item.getString("cover");
                        String collectionId = item.getString("collectionId");
                        CollectionItem temp = new CollectionItem();
                        temp.setName(name);
                        temp.setLogo(cover);
                        temp.setId(collectionId);
                        userList.add(temp);
                    }
                    userAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForUser.execute(MYURL+GETMYCOLLECTION,"userId",USERID,"type","0");
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
}
