package com.example.art.shouye.news.recyclerViewFrament;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.art.R;
import com.example.art.info.info2;
import com.example.art.shouye.news.adapter.newscontentAdapter;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.NEWS;

public class RecyclerViewFragmentForArtist extends Fragment {

    private RecyclerView mRecyclerView;
    private List<info2> newsContentList = new ArrayList<>();
    private newscontentAdapter adapter;
    private RecyclerView.Adapter mAdapter;


    public static RecyclerViewFragmentForArtist newInstance() {
        return new RecyclerViewFragmentForArtist();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyclerview_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取数据
        HttpGetUtils httpGetUtils = new HttpGetUtils();
        httpGetUtils.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST", "返回结果为" + httpGetUtils.getResponseData());
                try {
                    JSONObject data = new JSONObject(httpGetUtils.getResponseData());
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        info2 temp = new info2();
                        temp.setId(list.getJSONObject(i).getString("id"));
                        temp.setTime(list.getJSONObject(i).getString("time"));
                        temp.setType(list.getJSONObject(i).getInt("type"));
                        temp.setUrl(list.getJSONObject(i).getString("url"));
                        temp.setTitle(list.getJSONObject(i).getString("title"));
                        temp.setCover(list.getJSONObject(i).getString("cover"));
                        temp.setContent(list.getJSONObject(i).getString("content"));
                        temp.setKeyword(list.getJSONObject(i).getString("keyword"));
                        temp.setEditor(list.getJSONObject(i).getString("editor"));
                        temp.setSource(list.getJSONObject(i).getString("source"));
                        newsContentList.add(temp);
                    }
                    //加载布局-需在获取数据之后
                    mRecyclerView = view.findViewById(R.id.newsContentList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    adapter = new newscontentAdapter(newsContentList, getActivity());
                    mAdapter = new RecyclerViewMaterialAdapter(adapter);
                    mRecyclerView.setAdapter(mAdapter);
                    MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpGetUtils.execute(MYURL + NEWS, "type", "2");
    }
}
