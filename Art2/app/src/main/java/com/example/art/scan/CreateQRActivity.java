package com.example.art.scan;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.art.R;
import com.example.art.fuzzySearch.FilterListener;
import com.example.art.fuzzySearch.MyAdapter;
import com.example.art.info.ScanItem;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.king.zxing.util.CodeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.GETAllARTWORK;
import static com.example.art.dataInterface.DataInterface.MYURL;

public class CreateQRActivity extends AppCompatActivity{
    protected EditText edtData;
    protected ImageView ivQr;
    private ListView lsv_ss;
    private List<ScanItem> scanList = new ArrayList<>();
    boolean isFilter;
    private MyAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createqr);
        //初始化界面
        initView();
        setData();// 给listView设置adapter
        setListeners();// 设置监听
    }
    private void setData() {
        initData();
        adapter = new MyAdapter(scanList, this, new FilterListener() {
            public void getFilterData(List<ScanItem> scanList) {
                setItemClick(scanList);
            }
        });
        lsv_ss.setAdapter(adapter);
    }
    protected void setItemClick(final List<ScanItem> filter_lists) {
        lsv_ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                edtData.setText(filter_lists.get(position).getName());
                //生成二维码
                Bitmap logo;
                logo= BitmapFactory.decodeResource(getResources(),R.drawable.applogo);
                Bitmap qrCode = CodeUtils.createQRCode(filter_lists.get(position).getUrl(), 800, logo);
                ivQr.setImageBitmap(qrCode);
            }
        });
    }
    private void initData() {
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                try {
                    JSONObject result = new JSONObject(thread.getResponseData());
                    JSONArray list = new JSONArray(result.getJSONArray("list").toString());
                    for(int i=0;i<list.length();i++){
                        JSONObject item = list.getJSONObject(i);
                        ScanItem temp = new ScanItem(item.getString("url"),item.getString("title"));
                        scanList.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+GETAllARTWORK);
    }

    private void setListeners() {
        setItemClick(scanList);
        edtData.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(adapter != null){
                    adapter.getFilter().filter(s);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void initView() {
        edtData = findViewById(R.id.edt_data);
        ivQr = findViewById(R.id.iv_qr);
        lsv_ss = findViewById(R.id.lsv_ss);// ListView控件
    }
}
