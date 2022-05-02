package com.example.art.museumPlan.plan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.art.R;
import com.example.art.info.CheckItemForAllMuseum;
import com.example.art.myDrawing.ScreenUtils;
import com.example.art.util.AlterDialog.AlterDialogUtil;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.httpUtil.HttpPostUtils;
import com.loper7.date_time_picker.dialog.CardDatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.DELETE_PLAN;
import static com.example.art.dataInterface.DataInterface.FULFILL_PLAN;
import static com.example.art.dataInterface.DataInterface.GETALLMUSEUM;
import static com.example.art.dataInterface.DataInterface.MODIFY_PLAN;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.util.AlterDialog.AlterDialogUtil.ALL_MUSEUM_STATIC_DATA;
import static com.example.art.util.AlterDialog.AlterDialogUtil.checkItemForAllMuseum;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder > {
    private List<PlanItem> list;
    private Context context;
    @NonNull
    @Override
    public PlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_list,parent,false);
        return new PlanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.ViewHolder holder, int position) {
        PlanItem item = list.get(position);
        holder.museumName.setText(item.getMuseumName());
        holder.time.setText(item.getTime());
        holder.status.setChecked(item.getStatus() != 0);
        holder.note.setText(item.getNote());
        holder.deletePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("注意");
                builder.setMessage("确定要删除该计划吗？");
                builder.setIcon(R.drawable.applogo);
                builder.setCancelable(true);
                //设置正面按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpPostUtils deletePlanThread = new HttpPostUtils();
                        deletePlanThread.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
                            @Override
                            public void onSuccessPost() {
                                Log.i("TEST",deletePlanThread.getResponseData());
                                onSuccessListener.onSuccessModify();
                            }
                        });
                        JSONObject body = new JSONObject();
                        JSONObject data = new JSONObject();
                        try {
                            body.put("url",MYURL+DELETE_PLAN);
                            body.put("data",data);
                            data.put("planId", item.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        deletePlanThread.execute(body);
                        AlterDialogUtil.canCloseDialog(dialog, true);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlterDialogUtil.canCloseDialog(dialogInterface, true);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.modifyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String museumId = item.getMuseumId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = View.inflate(context, R.layout.modify_plan, null);
                //设置view
                final TextView time = dialogView.findViewById(R.id.time);
                final TextView museum = dialogView.findViewById(R.id.museum);
                final EditText note = dialogView.findViewById(R.id.note);
                //初始化数据
                time.setText(item.getTime());
                museum.setText(item.getMuseumName());
                note.setText(item.getNote());
                //输入框点击监听
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CardDatePickerDialog.Builder(context)
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
                                        Toast.makeText(context, "你已取消", Toast.LENGTH_SHORT).show();
                                    }
                                }).build().show();
                    }
                });
                museum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkItemForAllMuseum = -1;
                        HttpGetUtils threadForMuseum = new HttpGetUtils();
                        threadForMuseum.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
                            @Override
                            public void onSuccessGet() {
                                Log.i("TEST",threadForMuseum.getResponseData());
                                try {
                                    JSONObject result = new JSONObject(threadForMuseum.getResponseData());
                                    JSONArray list = result.getJSONArray("list");
                                    String[] strings = new String[list.length()];
                                    for(int i=0;i<list.length();i++){
                                        JSONObject item = list.getJSONObject(i);
                                        String museumName = item.getString("name");
                                        String museumId = item.getString("id");
                                        CheckItemForAllMuseum checkItemForAllMuseum = new CheckItemForAllMuseum();
                                        checkItemForAllMuseum.setMuseumId(museumId);
                                        checkItemForAllMuseum.setMuseumName(museumName);
                                        strings[i] = museumName;
                                        ALL_MUSEUM_STATIC_DATA.add(checkItemForAllMuseum);
                                    }
                                    AlterDialogUtil.singleChoiceDialogForAllMuseum(context,"请选择博物馆",strings,museum);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        threadForMuseum.execute(MYURL+GETALLMUSEUM);
                    }
                });

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpPostUtils modifyPlanThread = new HttpPostUtils();
                        modifyPlanThread.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
                            @Override
                            public void onSuccessPost() {
                                Log.i("TEST",modifyPlanThread.getResponseData());
                                onSuccessListener.onSuccessModify();
                            }
                        });
                        JSONObject body = new JSONObject();
                        JSONObject data = new JSONObject();
                        try {
                            body.put("url",MYURL+MODIFY_PLAN);
                            body.put("data",data);
                            data.put("planId", item.getId());
                            data.put("time", time.getText().toString());
                            if(checkItemForAllMuseum==-1){
                                data.put("museumId", museumId);
                            }else{
                                data.put("museumId", ALL_MUSEUM_STATIC_DATA.get(checkItemForAllMuseum).getMuseumId());
                            }
                            data.put("note", note.getText().toString());
                            data.put("museumName", museum.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        modifyPlanThread.execute(body);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlterDialogUtil.canCloseDialog(dialog, true);
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.setView(dialogView);
                dialog.getWindow().setLayout(ScreenUtils.getScreenWidth(context) / 4 * 3, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag = 0;
                if(holder.status.isChecked()){
                    flag = 1;
                }
                HttpPostUtils statusThread = new HttpPostUtils();
                statusThread.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
                    @Override
                    public void onSuccessPost() {
                        Log.i("TEST",statusThread.getResponseData());
                        onSuccessListener.onSuccessModify();
                    }
                });
                JSONObject body = new JSONObject();
                try {
                    body.put("url",MYURL+FULFILL_PLAN);
                    JSONObject data = new JSONObject();
                    data.put("status",flag);
                    data.put("id",item.getId());
                    body.put("data",data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                statusThread.execute(body);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView museumName;
        TextView time;
        CheckBox status;
        TextView note;
        ImageView deletePlan;
        ImageView modifyPlan;
        ViewHolder(View view){
            super(view);
            museumName = view.findViewById(R.id.museumName);
            time = view.findViewById(R.id.time);
            status = view.findViewById(R.id.status);
            note = view.findViewById(R.id.note);
            deletePlan = view.findViewById(R.id.deletePlan);
            modifyPlan = view.findViewById(R.id.modifyPlan);
        }
    }
    public PlanAdapter(List<PlanItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //创建接口，成功时候回调
    private OnSuccessListener onSuccessListener;
    public interface OnSuccessListener {
        void onSuccessModify();
    }
    public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }
    private String f(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return num + "";
    }
}