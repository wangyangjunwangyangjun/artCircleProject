package com.example.art.mine;

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

import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.createShare.BitmapToByte;
import com.example.art.createShare.SaveSD;
import com.example.art.shouye.adapter.LatestListAdapter;
import com.example.art.util.httpUtil.HttpPostFileUtil;
import com.example.art.util.httpUtil.HttpPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.art.dataInterface.DataInterface.CREATESHARE;
import static com.example.art.dataInterface.DataInterface.MODIFY_SHARE;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.dataInterface.DataInterface.USERLOGO;
import static com.example.art.dataInterface.DataInterface.USERNAME;

public class ModifyShareActivity extends AppCompatActivity {
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
    private String sharedId;
    private Button modifyShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_share);
        upLoad = findViewById(R.id.upLoad);
        editText_title = findViewById(R.id.editText_title);
        editText_content = findViewById(R.id.editText_content);

        //???????????????
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        cover = intent.getStringExtra("cover");
        sharedId = intent.getStringExtra("sharedId");

        editText_title.setText(title);
        editText_content.setText(content);
        Glide.with(ModifyShareActivity.this).load(MYURL+cover).into(upLoad);

        //????????????
        modifyShare = findViewById(R.id.modifyShare);
        upLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhoto();
            }
        });
        modifyShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify();
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
    private void modify(){
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
            body.put("url",MYURL+MODIFY_SHARE);
            data.put("title",editText_title.getText().toString());
            data.put("content",editText_content.getText().toString());
            data.put("cover",cover);
            data.put("shareId",sharedId);
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        createThread.execute(body);
    }
}