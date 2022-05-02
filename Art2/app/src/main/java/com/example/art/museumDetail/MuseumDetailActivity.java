package com.example.art.museumDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.art.R;
import com.example.art.util.httpUtil.HttpPostUtils;
import com.example.art.util.likeButton.LikeButton;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.art.dataInterface.DataInterface.ADDCOLLECTION;
import static com.example.art.dataInterface.DataInterface.ADD_HISTORY;
import static com.example.art.dataInterface.DataInterface.DELETECOLLECTION;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.dataInterface.MuseumStaticData.ISCOLLECTED;
import static com.example.art.dataInterface.MuseumStaticData.MUSEUMID;
import static com.example.art.dataInterface.MuseumStaticData.MUSEUMNAME;
import static com.example.art.dataInterface.MuseumStaticData.MUSUEMCOVER;

public class MuseumDetailActivity extends AppCompatActivity {
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    private LikeButton heart_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_detail);
        //添加用户历史部分
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        addHistory(MUSEUMNAME,simpleDateFormat.format(date),USERID);

        setTitle("");
        //MaterialViewPager
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/museumDetail/26.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/museumDetail/27.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/museumDetail/28.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/museumDetail/29.jpg");
                }
                return null;
            }
        });

        toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

        ViewPager viewPager = mViewPager.getViewPager();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return Introductions.newInstance();
                    case 1:
                        return Plan.newInstance();
                    case 2:
                        return Artwork.newInstance();
                    default:
                        return MapToMuseum.newInstance();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "简介";
                    case 1:
                        return "计划";
                    case 2:
                        return "文物";
                    case 3:
                        return "地图";
                }
                return "";
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(viewPager);

        TextView logo = findViewById(R.id.logo_white);
        logo.setText(MUSEUMNAME);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.notifyHeaderChanged();
            }
        });
        //收藏
        heart_button = findViewById(R.id.heart_button);
        heart_button.setLiked(ISCOLLECTED);
        heart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!heart_button.isLiked()){
                    addCollectionForMuseum();
                    heart_button.setLiked(true);
                }else{
                    deleteCollectionForMuseum();
                    heart_button.setLiked(false);
                }
            }
        });
    }
    private void addCollectionForMuseum(){
        HttpPostUtils addCollection = new HttpPostUtils();
        addCollection.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",addCollection.getResponseData());
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("url",MYURL+ADDCOLLECTION);
            data.put("userId",USERID);
            data.put("collectionId",MUSEUMID);
            data.put("type",3);
            data.put("cover",MUSUEMCOVER);
            data.put("name",MUSEUMNAME);
            data.put("url","null");
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addCollection.execute(body);
    }
    private void deleteCollectionForMuseum(){
        HttpPostUtils deleteCollection = new HttpPostUtils();
        deleteCollection.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",deleteCollection.getResponseData());
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("url",MYURL+DELETECOLLECTION);
            data.put("userId",USERID);
            data.put("collectionId",MUSEUMID);
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        deleteCollection.execute(body);
    }
    private void addHistory(String title,String time,String userId){
        HttpPostUtils addHistory = new HttpPostUtils();
        addHistory.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",addHistory.getResponseData());
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("url",MYURL+ADD_HISTORY);
            data.put("userId",userId);
            data.put("title",title);
            data.put("time",time);
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addHistory.execute(body);
    }
}
