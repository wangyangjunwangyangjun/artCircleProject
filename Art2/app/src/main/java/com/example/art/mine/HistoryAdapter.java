package com.example.art.mine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.art.R;
import com.example.art.info.HistoryItem;
import com.example.art.shareContent.comments.CommentAdapter;
import com.example.art.util.AlterDialog.AlterDialogUtil;
import com.example.art.util.httpUtil.HttpPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.DELETE_COMMENT;
import static com.example.art.dataInterface.DataInterface.DELETE_HISTORY;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.dataInterface.DataInterface.USER_TEMP_ID;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder > {
    private List<HistoryItem> list;
    private Context context;
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list,parent,false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryItem i = list.get(position);
        holder.title.setText("内容："+i.getTitle());
        holder.time.setText("时间 - > "+i.getTime().substring(0,10));
        if(USER_TEMP_ID.equals(i.getUserId())){
            holder.deleteHistory.setVisibility(View.VISIBLE);
        }else{
            holder.deleteHistory.setVisibility(View.INVISIBLE);
        }
        holder.deleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("注意");
                builder.setMessage("确定要删除该历史记录吗？");
                builder.setIcon(R.drawable.applogo);
                builder.setCancelable(true);
                //设置正面按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpPostUtils deleteHistoryThread = new HttpPostUtils();
                        deleteHistoryThread.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
                            @Override
                            public void onSuccessPost() {
                                Log.i("TEST",deleteHistoryThread.getResponseData());
                                onSuccessListener.onSuccessDelete(position);
                            }
                        });
                        JSONObject body = new JSONObject();
                        JSONObject data = new JSONObject();
                        try {
                            body.put("url",MYURL+DELETE_HISTORY);
                            body.put("data",data);
                            data.put("historyId", i.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        deleteHistoryThread.execute(body);
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
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button p = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        p.setTextColor(Color.WHITE);
                        Button n = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        n.setTextColor(Color.WHITE);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView time;
        ImageView deleteHistory;
        ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
            deleteHistory = view.findViewById(R.id.deleteHistory);
        }
    }
    public HistoryAdapter(List<HistoryItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //创建接口，成功时候回调
    private OnSuccessListener onSuccessListener;
    public interface OnSuccessListener {
        void onSuccessDelete(int position);
    }
    public void setOnSuccessListener(OnSuccessListener onSuccessListener) {
        this.onSuccessListener = onSuccessListener;
    }
}