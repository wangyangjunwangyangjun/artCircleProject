package com.example.art.initApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.art.R;
import com.example.art.login.LoginActivity;

import org.json.JSONObject;

import java.lang.reflect.Method;

public class LoadingPage extends AppCompatActivity {

    public static final String lodingPageSuccess = "lodingPageSuccess";
    private Context mContext = this;
    public static Integer REQUEST_CODE = 1;

    //记录是否第一次登录
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        new FetchData().execute();
        //不要标题
    }
    public class FetchData extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //记录第一次登录
            sharedPreferences = getSharedPreferences("rememberLogin", Context.MODE_PRIVATE);
            boolean isLogin = sharedPreferences.getBoolean("rememberLogin",false);
            if(isLogin){
                Intent mainIntent = new Intent(LoadingPage.this, LoginActivity.class);
                mainIntent.putExtra(lodingPageSuccess,"success");
                startActivity(mainIntent);
                finish();
            }else {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("rememberLogin",true);
                editor.apply();
                Intent mainIntent = new Intent(LoadingPage.this, GuideActivity.class);
                mainIntent.putExtra(lodingPageSuccess,"success");
                startActivity(mainIntent);
                finish();
            }

        }
    }

    public boolean permission(){
        //判断权限
        Boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<Settings> clazz = Settings.class;
                Method canDrawOverlays;
                canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                result = (Boolean) canDrawOverlays.invoke(null, mContext);
            } catch (Exception e) {
                Log.e("TAG", Log.getStackTraceString(e));
            }
        }
        return result;
    }

    //申请权限，跳转到系统的权限申请界面
    private void requestAlertWindowPermission() {
//        Intent intent = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//        }
//        assert intent != null;
//        intent.setData(Uri.parse("package:" + getPackageName()));
//        startActivityForResult(intent, REQUEST_CODE);
    }
}