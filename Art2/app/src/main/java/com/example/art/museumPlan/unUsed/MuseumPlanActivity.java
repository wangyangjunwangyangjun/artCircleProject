package com.example.art.museumPlan.unUsed;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.art.R;
import com.example.art.museumPlan.unUsed.DateInfo;
import com.example.art.museumPlan.unUsed.MuseumInfo;
import com.example.art.museumPlan.unUsed.OrderInfo;
import com.example.art.museumPlan.unUsed.ScrollablePanelAdapter;
import com.example.art.util.httpUtil.HttpGetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.art.dataInterface.DataInterface.GETMUSEUMPLAN;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.museumPlan.unUsed.OrderInfo.Status.UNFINISHED;

public class MuseumPlanActivity extends AppCompatActivity {
//    public static final SimpleDateFormat DAY_UI_MONTH_DAY_FORMAT = new SimpleDateFormat("MM-dd");
//    public static final SimpleDateFormat WEEK_FORMAT = new SimpleDateFormat("EEE", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_plan);
//        final ScrollablePanel scrollablePanel = (ScrollablePanel) findViewById(R.id.scrollable_panel);
//        final ScrollablePanelAdapter scrollablePanelAdapter = new ScrollablePanelAdapter();
//        generateTestData(scrollablePanelAdapter);
//        scrollablePanel.setPanelAdapter(scrollablePanelAdapter);
    }

//    private void generateTestData(ScrollablePanelAdapter scrollablePanelAdapter) {
//        HttpGetUtils thread = new HttpGetUtils();
//        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
//            @Override
//            public void onSuccessGet() {
//                Log.i("TEST",thread.getResponseData());
//                JSONObject result = null;
//                try {
//                    result = new JSONObject(thread.getResponseData());
//                    JSONArray list = new JSONArray(result.get("list"));
//                    for(int i=0;i<list.length();i++){
//                        String museumName = list.getJSONObject(i).getString("museumName");
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.execute(MYURL+GETMUSEUMPLAN);
//        List<MuseumInfo> museumInfoList = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            MuseumInfo museumInfo = new MuseumInfo();
//            museumInfo.setMuseumLocation("SUPK");
//            museumInfo.setMuseumId(i);
//            museumInfo.setMuseumName("20" + i);
//            museumInfoList.add(museumInfo);
//        }
//        scrollablePanelAdapter.setRoomInfoList(museumInfoList);
//
//        List<DateInfo> dateInfoList = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        for (int i = 0; i < 365; i++) {
//            DateInfo dateInfo = new DateInfo();
//            String date = DAY_UI_MONTH_DAY_FORMAT.format(calendar.getTime());
//            String week = WEEK_FORMAT.format(calendar.getTime());
//            dateInfo.setDate(date);
//            dateInfo.setWeek(week);
//            dateInfoList.add(dateInfo);
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//        scrollablePanelAdapter.setDateInfoList(dateInfoList);
//
//        List<List<OrderInfo>> ordersList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            List<OrderInfo> orderInfoList = new ArrayList<>();
//            for (int j = 0; j < 365; j++) {
//                OrderInfo orderInfo = new OrderInfo();
//                orderInfo.setGuestName("NO." + i + j);
//                orderInfo.setBegin(true);
//                orderInfo.setStatus(UNFINISHED);
//                orderInfoList.add(orderInfo);
//            }
//            ordersList.add(orderInfoList);
//        }
//        scrollablePanelAdapter.setOrdersList(ordersList);
//    }
}
