package com.example.art.scan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.art.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.king.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import static com.king.zxing.CaptureFragment.KEY_RESULT;

public class ScanActivity extends AppCompatActivity {
    private FloatingActionButton scan;
    private WebView url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        iniView();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //动态权限申请-摄像头
                if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                } else {
                    //跳转到扫描界面
                    Intent intent = new Intent(ScanActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String result = data.getStringExtra(KEY_RESULT);
                Log.i("TETS",result);
                //封面
                WebSettings webSettings= url.getSettings();
                webSettings.setDefaultTextEncodingName("utf-8") ;//这句话去掉也没事。。只是设置了编码格式
                webSettings.setJavaScriptEnabled(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setUseWideViewPort(true);
                webSettings.setDomStorageEnabled(true);//必须保留。。否则无法播放优酷视频网页。。其他的可以
                url.setWebChromeClient(new WebChromeClient());//重写一下。有的时候可能会出现问题
                url.setWebViewClient(new WebViewClient(){//不写的话自动跳到默认浏览器了。。跳出APP了。。
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {//这个方法必须重写。否则会出现优酷视频周末无法播放。周一-周五可以播放的问题
                        if(url.startsWith("intent")||url.startsWith("youku")){
                            return true;
                        }else{
                            return super.shouldOverrideUrlLoading(view, url);
                        }
                    }
                });
                //封面名字
                url.loadUrl(result);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //跳转到扫描界面
                    Intent intent = new Intent(ScanActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 1);
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
    private void iniView(){
        url = findViewById(R.id.url);
        scan = findViewById(R.id.scan);
    }
}
