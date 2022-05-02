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

        //初始化数据
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        cover = intent.getStringExtra("cover");
        sharedId = intent.getStringExtra("sharedId");

        editText_title.setText(title);
        editText_content.setText(content);
        Glide.with(ModifyShareActivity.this).load(MYURL+cover).into(upLoad);

        //修改完成
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
        if (requestCode == 1) {// 选取图片的返回值
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();// 指向查询结果的第一个位置
                mimetype = cursor.getString(1); // 图片文件路径
                imgName = cursor.getString(2); // 图片文件名
                imgSize = cursor.getString(3); // 图片大小

                uploadImag = "/Pictures/" + imgName;
                BitmapFactory.Options options = new BitmapFactory.Options();
                // 此时把options.inJustDecodeBounds 设回true，即只读边不读内容
                options.inJustDecodeBounds = true;
                // 默认是Bitmap.Config.ARGB_8888
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                try {
                    //此时不会把图片读入内存，只会获取图片宽高等信息
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                    //上面一句和下面的类似
                    //Bitmap bitmap = BitmapFactory.decodeFile(imgPath,options);
                    int heitht = options.outHeight;
                    // 根据需要设置压缩比例
                    int size = heitht / 800;
                    if (size <= 0) {
                        size = 2;
                    }
                   /*inSampleSize表示缩略图大小为原始图片大小的几分之一，
                      即如果这个值为2，则取出的缩略图的宽和高都是原始图片的1/2，
                      图片大小就为原始大小的1/4*/
                    options.inSampleSize = size;
                    // 设置options.inJustDecodeBounds重新设置为false
                    options.inPurgeable = true;// 同时设置才会有效
                    options.inInputShareable = true;//。当系统内存不够时候图片自动被回收
                    options.inJustDecodeBounds = false;
                    //此时图片会按比例压缩后被载入内存中
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                    SaveSD.saveBitmap(imgName, bitmap);//saveBitmap这个是我定义保存到SDcard中的方法
                    upLoad.setImageBitmap(bitmap);
                    imgPath = "/sdcard/" + imgName;
                    //将图片从Bitmap变为二进制流,保存到数据库中
                    newPhoto = BitmapToByte.saveBitmap(bitmap);
                    getNewPhoto = true;
                    uploadFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 使用OkHttp上传文件
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