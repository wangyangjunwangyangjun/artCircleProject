package com.example.art.mine;

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
import com.example.art.info.HistoryItem;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.GETMYHISTORY;
import static com.example.art.dataInterface.DataInterface.MYURL;

public class PersonalHistory extends Fragment {
    private RecyclerView mRecyclerView;
    private List<HistoryItem> historyContentList = new ArrayList<>();
    private HistoryAdapter adapter;
    private RecyclerView.Adapter mAdapter;
    public static PersonalHistory newInstance() {
        return new PersonalHistory();
    }@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.five, container, false);
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
                        HistoryItem temp = new HistoryItem();
                        temp.setId(list.getJSONObject(i).getString("id"));
                        temp.setTitle(list.getJSONObject(i).getString("title"));
                        temp.setTime(list.getJSONObject(i).getString("time"));
                        temp.setUserId(list.getJSONObject(i).getString("userId"));
                        historyContentList.add(temp);
                    }
                    //加载布局-需在获取数据之后
                    mRecyclerView = view.findViewById(R.id.historyList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    adapter = new HistoryAdapter(historyContentList, getActivity());
                    adapter.setOnSuccessListener(new HistoryAdapter.OnSuccessListener() {
                        @Override
                        public void onSuccessDelete(int position) {
                            historyContentList.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                    mAdapter = new RecyclerViewMaterialAdapter(adapter);
                    mRecyclerView.setAdapter(mAdapter);
                    MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpGetUtils.execute(MYURL + GETMYHISTORY);
    }
}
