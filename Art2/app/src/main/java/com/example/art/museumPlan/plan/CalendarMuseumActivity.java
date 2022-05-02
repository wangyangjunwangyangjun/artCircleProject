package com.example.art.museumPlan.plan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.art.R;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.art.dataInterface.DataInterface.GETMUSEUMPLAN;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class CalendarMuseumActivity extends BaseActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener {

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

    private Map<String, List<MuseumItem>> data;

    private RecyclerView planRecyclerView;
    private PlanAdapter planAdapter;
    private List<PlanItem> planItemList = new ArrayList<>();

    public static void show(Context context) {
        context.startActivity(new Intent(context, CalendarMuseumActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar_museum;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStatusBarDarkMode();
        mTextMonthDay = findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        planRecyclerView = findViewById(R.id.planRecylerView);
        planAdapter = new PlanAdapter(planItemList,CalendarMuseumActivity.this);
        planAdapter.setOnSuccessListener(new PlanAdapter.OnSuccessListener() {
            @Override
            public void onSuccessModify() {
                InitData();
                InitPlan(mCalendarView.getCurYear(),mCalendarView.getCurMonth(),mCalendarView.getCurDay());
            }
        });
        planRecyclerView.setAdapter(planAdapter);
        InitPlan(mCalendarView.getCurYear(),mCalendarView.getCurMonth(),mCalendarView.getCurDay());
    }

    @Override
    protected void initData() {
        InitData();
    }

    private void InitPlan(int myYear,int myMonth,int myDay){
        planItemList.clear();
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                try {
                    JSONObject result = new JSONObject(thread.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        String id = list.getJSONObject(i).getString("id");
                        String time = list.getJSONObject(i).getString("time");
                        int status = list.getJSONObject(i).getInt("status");
                        String note = list.getJSONObject(i).getString("note");
                        String museumName = list.getJSONObject(i).getString("museumName");
                        String museumId = list.getJSONObject(i).getString("museumId");
                        int year = Integer.parseInt(time.substring(0,4));
                        int month = Integer.parseInt(time.substring(5,7));
                        int day = Integer.parseInt(time.substring(8,10));
                        if(year==myYear&&month==myMonth&&day==myDay){
                            PlanItem item = new PlanItem();
                            item.setMuseumName(museumName);
                            item.setTime(time.substring(0,10));
                            item.setStatus(status);
                            item.setNote(note);
                            item.setId(id);
                            item.setMuseumId(museumId);
                            planItemList.add(item);
                        }
                    }
                    planAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+GETMUSEUMPLAN,"userId",USERID);
    }

    private void InitData(){
        data = new HashMap<>();
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                try {
                    JSONObject result = new JSONObject(thread.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject item = list.getJSONObject(i);
                        String id = item.getString("id");
                        String time = item.getString("time");
                        String museumId = item.getString("museumId");
                        String note = item.getString("note");
                        int status = item.getInt("status");
                        Calendar calendar = new Calendar();
                        int year = Integer.parseInt(time.substring(0,4));
                        int month = Integer.parseInt(time.substring(5,7));
                        int day = Integer.parseInt(time.substring(8,10));
                        calendar.setYear(year);
                        calendar.setMonth(month);
                        calendar.setDay(day);
                        List<MuseumItem> museumItems = data.get(calendar.toString());
                        if(museumItems==null){
                            museumItems = new ArrayList<>();
                        }
                        MuseumItem museumItem = new MuseumItem();
                        museumItem.setNote(note);
                        museumItem.setStatus(status);
                        museumItems.add(museumItem);
                        data.put(calendar.toString(),museumItems);
                    }
                    refreshCalendar();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+GETMUSEUMPLAN,"userId",USERID);
    }

    private void refreshCalendar(){
        Map<String, Calendar> map = new HashMap<>();
        for(Map.Entry<String, List<MuseumItem>> entry : data.entrySet()){
            String mapKey = entry.getKey();
            List<MuseumItem> mapValue = entry.getValue();
            int status1 = 0;
            for(int i=0;i<mapValue.size();i++){
                if(mapValue.get(i).getStatus()==1){
                    status1++;
                }
            }
            final String text = "" + Math.round(1.0 * status1 / mapValue.size() * 100);
            map.put(getSchemeCalendar(Integer.parseInt(mapKey.substring(0,4)), Integer.parseInt(mapKey.substring(4,6)), Integer.parseInt(mapKey.substring(6,8)), 0xFF40db25, text).toString(),
                    getSchemeCalendar(Integer.parseInt(mapKey.substring(0,4)), Integer.parseInt(mapKey.substring(4,6)), Integer.parseInt(mapKey.substring(6,8)), 0xFF40db25, text));
        }
        mCalendarView.setSchemeDate(map);
    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        InitPlan(calendar.getYear(),calendar.getMonth(),calendar.getDay());
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }
}