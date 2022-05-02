package com.example.art.login;

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
import android.widget.ImageView;

import com.example.art.R;
import com.example.art.createShare.BitmapToByte;
import com.example.art.createShare.SaveSD;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView upLoad;
    private byte[] newPhoto;
    private boolean getNewPhoto;
    private String uploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        upLoad = findViewById(R.id.upLoad);
        upLoad.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.upLoad:
                openPhoto();
                break;
        }
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
                String imgPath = cursor.getString(1); // 图片文件路径
                String imgSize = cursor.getString(3); // 图片大小
                String imgName = cursor.getString(2); // 图片文件名
                uploadImage = "/Pictures/"  + imgName;
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
                    Log.i("TEST", Arrays.toString(newPhoto));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
