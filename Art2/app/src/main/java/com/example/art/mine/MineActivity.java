package com.example.art.mine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.art.R;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.dataInterface.DataInterface.USER_TEMP_ID;

public class MineActivity extends AppCompatActivity {
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        //判断当前要进入的是哪一个用户
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String userName = intent.getStringExtra("userName");
        String userLogo = intent.getStringExtra("userLogo");
        if(userId!=null){
            USER_TEMP_ID = USERID;
            USERID = userId;
        }

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
                                MYURL + "/images/mineCover/18.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/mineCover/24.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/mineCover/30.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/mineCover/35.jpg");
                    case 4:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/mineCover/40.jpg");
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
                        return PersonalInfo.newInstance();
                    case 1:
                        return PersonalAchievement.newInstance();
                    case 2:
                        return PersonalShare.newInstance();
                    case 3:
                        return PersonalCollection.newInstance();
                    default:
                        return PersonalHistory.newInstance();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 5) {
                    case 0:
                        return "基本信息";
                    case 1:
                        return "用户成就";
                    case 2:
                        return "用户动态";
                    case 3:
                        return "用户收藏";
                    case 4:
                        return "浏览历史";
                }
                return "";
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(viewPager);

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                }
            });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        USERID = USER_TEMP_ID;
    }
}
