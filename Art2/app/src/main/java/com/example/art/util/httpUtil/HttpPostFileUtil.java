package com.example.art.util.httpUtil;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.UPLOAD;

public class HttpPostFileUtil extends AsyncTask<File,Integer,Boolean> {
    private static final MediaType FILE = MediaType.parse("application/octet-stream");
    private String responseData;
    public String getResponseData() {
        return responseData;
    }
    @Override
    protected Boolean doInBackground(File... files) {
        try{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("myPhoto",files[0].getName(),
                            RequestBody.create(MediaType.parse("application/jpg"),
                                    files[0]))
                    .build();
            Request request = new Request.Builder()
                    .url(MYURL+UPLOAD)
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
//            RequestBody body = RequestBody.create(FILE,files[0]);
////            Log.i("TESTPOST","url is "+files[0]);
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(MYURL+UPLOAD)
//                    .post(body)
//                    .build();
//            Response response = client.newCall(request).execute();
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
    private HttpPostFileUtil.OnSuccessListener onSuccessListener;
    public interface OnSuccessListener {
        void onSuccessPost();
    }
    public void setOnSuccessListener(HttpPostFileUtil.OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }
}