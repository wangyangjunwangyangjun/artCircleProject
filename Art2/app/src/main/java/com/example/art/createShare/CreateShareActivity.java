package com.example.art.createShare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.art.R;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.httpUtil.HttpPostFileUtil;
import com.example.art.util.httpUtil.HttpPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.art.dataInterface.DataInterface.CREATESHARE;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.UPLOAD;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.dataInterface.DataInterface.USERLOGO;
import static com.example.art.dataInterface.DataInterface.USERNAME;

public class CreateShareActivity extends AppCompatActivity {
    private ImageView upLoad;
    private byte[] newPhoto;
    private boolean getNewPhoto;
    private String uploadImag;
    private String urlString = null;
    private AsyncTask asyncTask;
    private String imgPath;
    private String imgName;
    private String mimetype;
    private String imgSize;
    private EditText editText_title;
    private EditText editText_content;
    private String cover;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_share);
        upLoad = findViewById(R.id.upLoad);
        editText_title = findViewById(R.id.editText_title);
        editText_content = findViewById(R.id.editText_content);
        create = findViewById(R.id.create);
        upLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhoto();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
                finish();
            }
        });
    }

    private void openPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {// ????????????????????????
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();// ????????????????????????????????????
                mimetype = cursor.getString(1); // ??????????????????
                imgName = cursor.getString(2); // ???????????????
                imgSize = cursor.getString(3); // ????????????

                uploadImag = "/Pictures/" + imgName;
                BitmapFactory.Options options = new BitmapFactory.Options();
                // ?????????options.inJustDecodeBounds ??????true???????????????????????????
                options.inJustDecodeBounds = true;
                // ?????????Bitmap.Config.ARGB_8888
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                try {
                    //?????????????????????????????????????????????????????????????????????
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                    //??????????????????????????????
                    //Bitmap bitmap = BitmapFactory.decodeFile(imgPath,options);
                    int heitht = options.outHeight;
                    // ??????????????????????????????
                    int size = heitht / 800;
                    if (size <= 0) {
                        size = 2;
                    }
                   /*inSampleSize????????????????????????????????????????????????????????????
                      ?????????????????????2?????????????????????????????????????????????????????????1/2???
                      ?????????????????????????????????1/4*/
                    options.inSampleSize = size;
                    // ??????options.inJustDecodeBounds???????????????false
                    options.inPurgeable = true;// ????????????????????????
                    options.inInputShareable = true;//???????????????????????????????????????????????????
                    options.inJustDecodeBounds = false;
                    //???????????????????????????????????????????????????
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                    SaveSD.saveBitmap(imgName, bitmap);//saveBitmap???????????????????????????SDcard????????????
                    upLoad.setImageBitmap(bitmap);
                    imgPath = "/sdcard/" + imgName;
                    //????????????Bitmap??????????????????,?????????????????????
                    newPhoto = BitmapToByte.saveBitmap(bitmap);
                    getNewPhoto = true;
                    uploadFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ??????OkHttp????????????
    public void uploadFile() {
        File file = new File(imgPath);
        HttpPostFileUtil thread = new HttpPostFileUtil();
        thread.setOnSuccessListener(new HttpPostFileUtil.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",thread.getResponseData());
                try {
                    JSONObject result = new JSONObject(thread.getResponseData());
                    cover = result.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(file);
    }
    private void create(){
        HttpPostUtils createThread = new HttpPostUtils();
        createThread.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",createThread.getResponseData());
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            body.put("url",MYURL+CREATESHARE);
            data.put("userId",USERID);
            data.put("title",editText_title.getText().toString());
            data.put("contentSimple",editText_content.getText().toString());
            data.put("time",simpleDateFormat.format(date));
            data.put("userName",USERNAME);
            data.put("userLogo",USERLOGO);
            data.put("cover",cover);
            data.put("shareId",USERID+simpleDateFormat.format(date));
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        createThread.execute(body);
    }
}