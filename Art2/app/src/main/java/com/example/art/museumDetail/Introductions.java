package com.example.art.museumDetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.art.R;
import com.example.art.dataInterface.MuseumStaticData;
import com.example.art.util.WebViewUtil.UrlForCommonActivity;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.art.dataInterface.DataInterface.GETMUSEUMDETAIL;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.MuseumStaticData.MUSEUMID;

public class Introductions extends Fragment {
    private ObservableScrollView mScrollView;
    private TextView introduction;
    private TextView location;
    private TextView url;
    public static Introductions newInstance() {
        return new Introductions();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.introduction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        introduction = view.findViewById(R.id.introduction);
        location = view.findViewById(R.id.location);
        url = view.findViewById(R.id.url);
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
                    introduction.setText(item.getString("introduction"));
                    location.setText(item.getString("country"));
                    url.setText(item.getString("url"));
                    MuseumStaticData.MUSUEMCOVER = item.getString("cover");
                    url.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), UrlForCommonActivity.class);
                            intent.putExtra("url",url.getText().toString());
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+GETMUSEUMDETAIL,"museumId",MUSEUMID);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
}