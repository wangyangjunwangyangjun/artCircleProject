package com.example.art.initApp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.art.R;
import com.example.art.login.LoginActivity;
import com.example.art.shouye.ShouYeActivity;
import com.king.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import static com.king.zxing.CaptureFragment.KEY_RESULT;

public class GuideActivity extends AppCompatActivity {
    private ViewPager viewPager = null;
    private int[] resources = {
            R.layout.guide1,
            R.layout.guide2,
            R.layout.guide3,
            R.layout.guide4
    };
    private List<View> listViews = new ArrayList<View>();
    private LayoutInflater inflater = null;
    private Button start;
    private Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        //动态权限申请-摄像头
        if (ContextCompat.checkSelfPermission(
                GuideActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GuideActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            Log.i("TEST","授权完成");
        }

    }
    private void initView() {
        inflater = LayoutInflater.from(this);
        viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        start = (Button) findViewById(R.id.start);
        skip = findViewById(R.id.skip);
        start.setVisibility(View.INVISIBLE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        for(int i=0;i<resources.length;i++){
            View view = inflater.inflate(resources[i], null);
            listViews.add(view);
        }
        viewPager.setAdapter(new MyPageAdapter());
    }
    private class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return listViews.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(listViews.get(position));
            return listViews.get(position);
        }
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(listViews.get(position));
        }
    }
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageSelected(int arg0) {
            if (arg0 == resources.length -1){
                start.setVisibility(View.VISIBLE);
            }else {
                start.setVisibility(View.INVISIBLE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("TEST","授权完成");
                } else {
                    Log.i("TEST","权限被禁用");
                    finish();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
