package com.example.art.util.WebViewUtil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.art.R;
import com.example.art.util.httpUtil.HttpPostUtils;
import com.example.art.util.likeButton.LikeButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.art.dataInterface.DataInterface.ADDCOLLECTION;
import static com.example.art.dataInterface.DataInterface.ADD_HISTORY;
import static com.example.art.dataInterface.DataInterface.DELETECOLLECTION;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.shouye.artist.ArtistStaticData.ARTIST_COVER;
import static com.example.art.shouye.artist.ArtistStaticData.ARTIST_ID;
import static com.example.art.shouye.artist.ArtistStaticData.ARTIST_NAME;
import static com.example.art.shouye.artist.ArtistStaticData.ARTIST_URL;
import static com.example.art.shouye.artist.ArtistStaticData.IS_COLLECTED;


public class UrlForArtistActivity extends AppCompatActivity {
    private WebView myWebView;
    private long exitTime = 0;
    private LikeButton collectArtistLikeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_for_artist);
        //添加用户历史部分
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        addHistory(ARTIST_NAME,simpleDateFormat.format(date),USERID);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        myWebView.loadUrl(url);    //设置网址
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING); //支持内容重新布局
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setTextZoom(2);//设置文本的缩放倍数，默认为 100
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
        webSettings.setStandardFontFamily("");//设置 WebView 的字体，默认字体为 "sans-serif"
        webSettings.setDefaultFontSize(20);//设置 WebView 字体的大小，默认大小为 16
        webSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
        //收藏艺术品按钮
        collectArtistLikeButton = findViewById(R.id.collectArtistLikeButton);
        collectArtistLikeButton.setLiked(IS_COLLECTED);
        collectArtistLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!collectArtistLikeButton.isLiked()){
                    addCollectionForArtwork();
                    collectArtistLikeButton.setLiked(true);
                }else{
                    deleteCollectionForArtwork();
                    collectArtistLikeButton.setLiked(false);
                }
            }
        });
    }
    private void addCollectionForArtwork(){
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
            data.put("collectionId",ARTIST_ID);
            //这里得类型非常重要，2表示是艺术家类型，还有1和3
            data.put("type",2);
            data.put("cover",ARTIST_COVER);
            data.put("name",ARTIST_NAME);
            data.put("url",ARTIST_URL);
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addCollection.execute(body);
    }
    private void deleteCollectionForArtwork(){
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
            data.put("collectionId",ARTIST_ID);
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        deleteCollection.execute(body);
    }
    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //1.webView.canGoBack()判断网页是否能后退,可以则goback()
    //2.如果不可以连续点击两次退出App,否则弹出提示Toast
    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出网页",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //super.onBackPressed();
                finish();
            }

        }
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