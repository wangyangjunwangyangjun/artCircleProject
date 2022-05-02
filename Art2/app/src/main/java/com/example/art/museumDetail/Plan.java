package com.example.art.museumDetail;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.art.R;
import com.example.art.dataInterface.DataInterface;
import com.example.art.myDrawing.ScreenUtils;
import com.example.art.util.WebViewUtil.UrlForCommonActivity;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.httpUtil.HttpPostUtils;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.loper7.date_time_picker.dialog.CardDatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.ADDPLAN;
import static com.example.art.dataInterface.DataInterface.GETMUSEUMDETAIL;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.dataInterface.MuseumStaticData.MUSEUMID;
import static com.example.art.dataInterface.MuseumStaticData.MUSEUMNAME;

public class Plan extends Fragment {
    private ObservableScrollView mScrollView;
    private TextView order;
    private RecyclerView planRecylerView;
    private ImageView addPlan;
    private MuseumPlanAdapter museumPlanAdapter;
    private List<planItem> planList = new ArrayList<>();
    public static Plan newInstance() {
        return new Plan();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        order = view.findViewById(R.id.order);
        planRecylerView =view.findViewById(R.id.planRecylerView);
        addPlan = view.findViewById(R.id.addPlan);
        museumPlanAdapter = new MuseumPlanAdapter(planList,getActivity());
        planRecylerView.setAdapter(museumPlanAdapter);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UrlForCommonActivity.class);
                intent.putExtra("url",order.getText().toString());
                startActivity(intent);
            }
        });
        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMuseumPlan();
            }
        });
        //获取预定链接
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                JSONObject result = null;
                try {
                    result = new JSONObject(thread.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    JSONObject item = list.getJSONObject(0);
                    order.setText(item.getString("order"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+GETMUSEUMDETAIL,"museumId",MUSEUMID);
        //获取计划列表
        HttpGetUtils threadForPlan = new HttpGetUtils();
        threadForPlan.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                JSONObject result = null;
                try {
                    result = new JSONObject(threadForPlan.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject temp = list.getJSONObject(i);
                        planItem item = new planItem();

                        item.setTime("时间："+temp.getString("time").substring(0,10)
                                +" "+temp.getString("time").substring(11,19));
                        item.setNote("备注："+temp.getString("note"));
                        planList.add(item);
                    }
                    museumPlanAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        threadForPlan.execute(MYURL+ DataInterface.GETMUSEUMPLAN,"museumId",MUSEUMID,"userId",USERID);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
    private void addMuseumPlan(){
        View dialogView = View.inflate(getActivity(), R.layout.add_plan, null);
        //设置view
        final TextView time = dialogView.findViewById(R.id.time);
        final TextView note = dialogView.findViewById(R.id.note);
        final Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        final Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);
        //时间监听
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CardDatePickerDialog.Builder(getActivity())
                        .setTitle("选择日期及时间")
                        .showBackNow(false)
                        .setOnChoose("确定", new CardDatePickerDialog.OnChooseListener() {
                            @Override
                            public void onChoose(long aLong) {
                                Date dateTime = new Date(aLong);
                                time.setText(
                                        f(dateTime.getYear() + 1900) + "-"
                                                + f(dateTime.getMonth() + 1) + "-"
                                                + f(dateTime.getDate()) + " "
                                                + f(dateTime.getHours()) + ":"
                                                + f(dateTime.getMinutes()) + ":"
                                                + f(dateTime.getSeconds()));
                            }
                        })
                        .setOnCancel("取消", new CardDatePickerDialog.OnCancelListener() {
                            @Override
                            public void onCancel() {
                                Toast.makeText(getActivity(), "你已取消", Toast.LENGTH_SHORT).show();
                            }
                        }).build().show();
            }
        });
//        //对话框设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planList.clear();
                HttpPostUtils thread = new HttpPostUtils();
                thread.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
                    @Override
                    public void onSuccessPost() {
                        Log.i("TEST",thread.getResponseData());
                        //获取计划列表
                        HttpGetUtils threadForPlan = new HttpGetUtils();
                        threadForPlan.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
                            @Override
                            public void onSuccessGet() {
                                Log.i("TEST",thread.getResponseData());
                                JSONObject result = null;
                                try {
                                    result = new JSONObject(threadForPlan.getResponseData());
                                    JSONArray list = result.getJSONArray("list");
                                    for(int i=0;i<list.length();i++){
                                        JSONObject temp = list.getJSONObject(i);
                                        planItem item = new planItem();
                                        item.setTime("时间："+temp.getString("time").substring(0,10)
                                                +" "+temp.getString("time").substring(11,19));
                                        item.setNote("备注："+temp.getString("note"));
                                        planList.add(item);
                                    }
                                    museumPlanAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        threadForPlan.execute(MYURL+ DataInterface.GETMUSEUMPLAN,"museumId",MUSEUMID,"userId",USERID);
                        dialog.dismiss();
                    }
                });
                JSONObject body = new JSONObject();
                try {
                    body.put("url",MYURL+ADDPLAN);
                    JSONObject data = new JSONObject();
                    data.put("time",time.getText().toString());
                    data.put("userId", USERID);
                    data.put("museumId", MUSEUMID);
                    data.put("museumName", MUSEUMNAME);
                    data.put("note", note.getText().toString());
                    body.put("data",data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                thread.execute(body);
            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.getWindow().setLayout(ScreenUtils.getScreenWidth(getActivity()) / 4 * 3, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    private String f(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return num + "";
    }
}