package com.example.art.mine;

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
import com.example.art.util.httpUtil.HttpGetUtils;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.art.dataInterface.DataInterface.GET_USER_INFO;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class PersonalInfo extends Fragment {
    private ObservableScrollView mScrollView;
    private TextView userName;
    private TextView sex;
    private TextView tel;
    private TextView userSign;
    public static PersonalInfo newInstance() {
        return new PersonalInfo();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        userName = (TextView) view.findViewById(R.id.userName);
        sex = (TextView) view.findViewById(R.id.sex);
        tel = (TextView) view.findViewById(R.id.tel);
        userSign = (TextView) view.findViewById(R.id.userSign);
        HttpGetUtils threadForUserInfo = new HttpGetUtils();
        threadForUserInfo.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                String result = threadForUserInfo.getResponseData();
                Log.i("TEST",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray list = jsonObject.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject temp = list.getJSONObject(i);
                        userName.setText(temp.getString("userName"));
                        sex.setText(temp.getString("sex"));
                        tel.setText(temp.getString("tel"));
                        userSign.setText(temp.getString("userSign"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        threadForUserInfo.execute(MYURL+GET_USER_INFO,"userId",USERID);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
}