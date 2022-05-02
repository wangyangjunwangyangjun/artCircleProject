package com.example.art.shouye.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.example.art.util.WebViewUtil.UrlForCommonActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.lunBoTuUrl;

public class ShufflingAdapter extends PagerAdapter {
    private List<String> listViews;
    private Context context;
    @Override
    public int getCount() {
        if(listViews!=null){
            return Integer.MAX_VALUE;//伪无限循环
        }
        return 0;
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    @Override
    public Object instantiateItem(View container, int position) {
        int realPosition = position%listViews.size();
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(MYURL+listViews.get(realPosition)).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UrlForCommonActivity.class);
                intent.putExtra("url",lunBoTuUrl);
                context.startActivity(intent);
            }
        });
        ((ViewPager) container).addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView((View) object);
    }
    public void setData(List<String> list,Context context) {
        this.context = context;
        listViews = new ArrayList<>();
        listViews.addAll(list);
        notifyDataSetChanged();
    }

}