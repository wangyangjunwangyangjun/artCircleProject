package com.example.art.shouye.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.art.R;
import com.example.art.shouye.news.recyclerViewFrament.RecyclerViewFragmentForArtist;
import com.example.art.shouye.news.recyclerViewFrament.RecyclerViewFragmentForArtwork;
import com.example.art.shouye.news.recyclerViewFrament.RecyclerViewFragmentForMuseum;
import com.example.art.shouye.news.recyclerViewFrament.RecyclerViewFragmentForSell;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import static com.example.art.dataInterface.DataInterface.MYURL;


public class NewsActivity extends AppCompatActivity {
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
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
                                MYURL + "/images/newsCover/newsCover1.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/newsCover/newsCover2.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/newsCover/newsCover3.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.color4,
                                MYURL + "/images/newsCover/newsCover4.jpg");
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
                    case 1:
                        return RecyclerViewFragmentForArtist.newInstance();
                    case 2:
                        return RecyclerViewFragmentForArtwork.newInstance();
                    case 3:
                        return RecyclerViewFragmentForSell.newInstance();
                    default:
                        return RecyclerViewFragmentForMuseum.newInstance();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "博物馆新闻";
                    case 1:
                        return "艺术品新闻";
                    case 2:
                        return "艺术家新闻";
                    case 3:
                        return "拍卖新闻";
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

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
//                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
