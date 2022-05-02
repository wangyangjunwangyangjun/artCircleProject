package com.example.art.museumDetail;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.art.R;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.roundImageView.RoundImageView;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.art.dataInterface.DataInterface.GETMUSEUMDETAIL;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.MuseumStaticData.MUSEUMID;
import static com.example.art.dataInterface.MuseumStaticData.MUSEUMNAME;

public class MapToMuseum extends Fragment {
    private ObservableScrollView mScrollView;
    private RoundImageView baiduMap;
    private TextView location;
    public static MapToMuseum newInstance() {
        return new MapToMuseum();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_to_museum, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        baiduMap = view.findViewById(R.id.baiduMap);
        location = view.findViewById(R.id.location);
        //获取博物馆的相关信息
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                JSONObject result = null;
                try {
                    result = new JSONObject(thread.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    JSONObject item = list.getJSONObject(0);
                    location.setText(item.getString("country"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+GETMUSEUMDETAIL,"museumId",MUSEUMID);
        //打开百度地图进行导航
        baiduMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri= Uri.parse("baidumap://map/geocoder?src=andr.baidu.openAPIdemo&address="+MUSEUMNAME);  //打开地图定位
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                ComponentName cn = it.resolveActivity(getContext().getPackageManager());
                if(cn == null){
                    Toast.makeText(getContext(),"请先安装第三方导航软件",Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("TEST",cn.getPackageName());
                    startActivity(it);
                }
            }
        });
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
}