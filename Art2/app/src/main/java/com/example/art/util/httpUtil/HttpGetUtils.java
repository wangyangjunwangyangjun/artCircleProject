package com.example.art.util.httpUtil;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpGetUtils extends AsyncTask<String,Integer,Boolean> {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String responseData;

    public String getResponseData() {
        return responseData;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        String params = "?";
        if (strings.length>1){
            for(int i=1;i<strings.length;i+=2){
                if(i==2){
                    params+=(strings[i]+"="+strings[i+1]);
                }else{
                    params+=("&"+strings[i]+"="+strings[i+1]);
                }
            }
        }
        Log.i("TESTGET"," url is "+strings[0]+params);
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Content-Type","application/json")//添加头部
//                    .addHeader(strings[1],strings[2])//添加头部
                    .url(strings[0]+params)
                    .build();
            Response response = client.newCall(request).execute();
            responseData = response.body().string();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        this.onSuccessListener.onSuccessGet();
    }
    //创建接口，成功时候回调
    private OnSuccessListener onSuccessListener;
    public interface OnSuccessListener {
        void onSuccessGet();
    }
    public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }
}