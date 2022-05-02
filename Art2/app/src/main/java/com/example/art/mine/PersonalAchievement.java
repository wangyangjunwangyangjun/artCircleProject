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
import com.example.art.info.CollectionItem;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.GETMYACHIEVEMENT;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class PersonalAchievement extends Fragment {
    private RecyclerView mRecyclerView;
    private List<CollectionItem> achievementContentList = new ArrayList<>();
    private AchievementAdapter adapter;
    private RecyclerView.Adapter mAdapter;
    public static PersonalAchievement newInstance() {
        return new PersonalAchievement();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.two, container, false);
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
                        CollectionItem temp = new CollectionItem();
                        temp.setLogo(list.getJSONObject(i).getString("logo"));
                        temp.setName(list.getJSONObject(i).getString("name"));
                        temp.setStatus(list.getJSONObject(i).getInt("status"));
                        achievementContentList.add(temp);
                    }
                    //加载布局-需在获取数据之后
                    mRecyclerView = view.findViewById(R.id.achievementList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    adapter = new AchievementAdapter(achievementContentList, getActivity());
                    mAdapter = new RecyclerViewMaterialAdapter(adapter);
                    mRecyclerView.setAdapter(mAdapter);
                    MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpGetUtils.execute(MYURL + GETMYACHIEVEMENT,"userId",USERID);
    }
}
