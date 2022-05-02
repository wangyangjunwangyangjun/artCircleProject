package com.example.art.util.httpUtil;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpPostUtils extends AsyncTask<JSONObject,Integer,Boolean> {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String responseData;
    public String getResponseData() {
        return responseData;
    }
    @Override
    protected Boolean doInBackground(JSONObject... jsonObjects) {
        try{
            JSONObject json = jsonObjects[0].getJSONObject("data");
            RequestBody body = RequestBody.create(JSON,json.toString());
            Log.i("TESTPOST","url is "+jsonObjects[0].getString("url")+" body is"+json.toString());
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Content-Type","application/json")//添加头部
                    .url(jsonObjects[0].getString("url"))
                    .post(body)
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
        this.onSuccessListener.onSuccessPost();
    }
    private OnSuccessListener onSuccessListener;
    public interface OnSuccessListener {
        void onSuccessPost();
    }
    public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }
}
