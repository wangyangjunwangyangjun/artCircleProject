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
import com.example.art.info.ShareItem;
import com.example.art.shouye.adapter.LatestListAdapter;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.GETMYSHARE;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class PersonalShare extends Fragment {
    private RecyclerView mRecyclerView;
    private List<ShareItem> shareContentList = new ArrayList<>();
    private LatestListAdapter adapter;
    private RecyclerView.Adapter mAdapter;
    public static PersonalShare newInstance() {
        return new PersonalShare();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.three, container, false);
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
                        ShareItem temp = new ShareItem();
                        Log.i("TEST",list.getJSONObject(i).getString("time"));
                        temp.setTime(
                                new Date(
                                        Integer.parseInt(list.getJSONObject(i).getString("time").substring(0,4))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(5,7))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(8,10))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(11,13))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(14,16))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(17,19))
                                )
                        );
                        temp.setBrowse(list.getJSONObject(i).getInt("browse"));
                        temp.setComment(list.getJSONObject(i).getInt("comment"));
                        temp.setContentSimple(list.getJSONObject(i).getString("contentSimple"));
                        temp.setFavor(list.getJSONObject(i).getInt("favor"));
                        temp.setTitle(list.getJSONObject(i).getString("title"));
                        temp.setUserLogo(list.getJSONObject(i).getString("userLogo"));
                        temp.setCover(list.getJSONObject(i).getString("cover"));
                        temp.setUserName(list.getJSONObject(i).getString("userName"));
                        temp.setUserId(list.getJSONObject(i).getString("userId"));
                        temp.setShareId(list.getJSONObject(i).getString("shareId"));
                        shareContentList.add(temp);
                    }

                    //加载布局-需在获取数据之后
                    mRecyclerView = view.findViewById(R.id.shareList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    adapter = new LatestListAdapter(getActivity(),shareContentList);
                    adapter.setOnSuccessListener(new LatestListAdapter.OnSuccessListener() {
                        @Override
                        public void onSuccessDelete(int position) {
                            shareContentList.remove(position);
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
        httpGetUtils.execute(MYURL + GETMYSHARE, "id", USERID);
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpGetUtils httpGetUtils = new HttpGetUtils();
        httpGetUtils.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                shareContentList.clear();
                Log.i("TEST", "返回结果为" + httpGetUtils.getResponseData());
                try {
                    JSONObject data = new JSONObject(httpGetUtils.getResponseData());
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        ShareItem temp = new ShareItem();
                        Log.i("TEST", list.getJSONObject(i).getString("time"));
                        temp.setTime(
                                new Date(
                                        Integer.parseInt(list.getJSONObject(i).getString("time").substring(0, 4))
                                        , Integer.parseInt(list.getJSONObject(i).getString("time").substring(5, 7))
                                        , Integer.parseInt(list.getJSONObject(i).getString("time").substring(8, 10))
                                        , Integer.parseInt(list.getJSONObject(i).getString("time").substring(11, 13))
                                        , Integer.parseInt(list.getJSONObject(i).getString("time").substring(14, 16))
                                        , Integer.parseInt(list.getJSONObject(i).getString("time").substring(17, 19))
                                )
                        );
                        temp.setBrowse(list.getJSONObject(i).getInt("browse"));
                        temp.setComment(list.getJSONObject(i).getInt("comment"));
                        temp.setContentSimple(list.getJSONObject(i).getString("contentSimple"));
                        temp.setFavor(list.getJSONObject(i).getInt("favor"));
                        temp.setTitle(list.getJSONObject(i).getString("title"));
                        temp.setUserLogo(list.getJSONObject(i).getString("userLogo"));
                        temp.setCover(list.getJSONObject(i).getString("cover"));
                        temp.setUserName(list.getJSONObject(i).getString("userName"));
                        temp.setUserId(list.getJSONObject(i).getString("userId"));
                        temp.setShareId(list.getJSONObject(i).getString("shareId"));
                        shareContentList.add(temp);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpGetUtils.execute(MYURL + GETMYSHARE, "id", USERID);
    }
}
