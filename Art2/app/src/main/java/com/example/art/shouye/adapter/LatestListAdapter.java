package com.example.art.shouye.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.info.ShareItem;
import com.example.art.mine.ModifyShareActivity;
import com.example.art.shareContent.ShareContentActivity;
import com.example.art.util.AlterDialog.AlterDialogUtil;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.httpUtil.HttpPostUtils;
import com.example.art.util.roundImageView.RoundImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.ADDCOLLECTION;
import static com.example.art.dataInterface.DataInterface.ADD_BROWSE;
import static com.example.art.dataInterface.DataInterface.DELETECOLLECTION;
import static com.example.art.dataInterface.DataInterface.DELETE_SHARE;
import static com.example.art.dataInterface.DataInterface.JUDGEEXIST;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class LatestListAdapter extends RecyclerView.Adapter<LatestListAdapter.ViewHolder> {
    private List<ShareItem> list;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        RoundImageView userImage;
        TextView userName;
        TextView title;
        TextView content_simple;
        TextView time;
        TextView browser;
        TextView favor;
        TextView comment;
        ImageView cover;
        ImageView favor_icon;
        TextView collectUser;
        ImageView deleteShare;
        ImageView modifyShare;

        ViewHolder(View view){
            super(view);
            userImage = view.findViewById(R.id.userImage);
            userName = view.findViewById(R.id.userName);
            title = view.findViewById(R.id.title);
            content_simple = view.findViewById(R.id.content_simple);
            time = view.findViewById(R.id.time);
            browser = view.findViewById(R.id.browser);
            favor = view.findViewById(R.id.favor);
            comment = view.findViewById(R.id.comment);
            cover = view.findViewById(R.id.cover);
            favor_icon = view.findViewById(R.id.favor_icon);
            collectUser = view.findViewById(R.id.collectUser);
            deleteShare = view.findViewById(R.id.deleteShare);
            modifyShare = view.findViewById(R.id.modifyShare);
        }
    }
    public LatestListAdapter(Context context, List<ShareItem> l){
        list = l;
        this.context = context;
    }

    @NonNull
    @Override
    public LatestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.latest_list,parent,false);
        return new LatestListAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LatestListAdapter.ViewHolder holder, int position) {
        ShareItem i = list.get(position);
        Glide.with(context).load(MYURL+i.getUserLogo()).into(holder.userImage);
        holder.userName.setText(i.getUserName());
        holder.title.setText(i.getTitle());
        holder.content_simple.setText(i.getContentSimple().substring(0,25)+"...");
        holder.time.setText(getTime_Str(i.getTime()));
        holder.browser.setText(i.getBrowse()+" 浏览量");
        holder.favor.setText(i.getFavor()+"");
        holder.comment.setText(i.getComment()+"");
        Glide.with(context).load(MYURL+i.getCover()).into(holder.cover);

        //判断是否要显示关注按钮
        //判断是否要显示删除和修改按钮
        if(i.getUserId().equals(USERID)){
            holder.collectUser.setVisibility(View.INVISIBLE);
            holder.deleteShare.setVisibility(View.VISIBLE);
            holder.modifyShare.setVisibility(View.VISIBLE);
        }else {
            holder.collectUser.setVisibility(View.VISIBLE);
            holder.deleteShare.setVisibility(View.INVISIBLE);
            holder.modifyShare.setVisibility(View.INVISIBLE);
        }

        //判断是否有关注
        HttpGetUtils threadForJudgeExist = new HttpGetUtils();
        threadForJudgeExist.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                if(threadForJudgeExist.getResponseData()!=null){
                    Log.i("TEST",threadForJudgeExist.getResponseData());
                    try {
                        JSONObject result = new JSONObject(threadForJudgeExist.getResponseData());
                        JSONArray list = result.getJSONArray("list");
                        holder.collectUser.setText(getAttention(list.length()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadForJudgeExist.execute(MYURL+JUDGEEXIST,"userId",USERID,"collectionId",i.getUserId());

        holder.collectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.collectUser.getText().toString().equals("关注")){
                    //关注用户
                    addToCollection(USERID,i.getUserId(),0,i.getUserLogo(),i.getUserName());
                    holder.collectUser.setText("已关注");
                }else{
                    //取消关注
                    deleteCollectionForUser(i.getUserId());
                    holder.collectUser.setText("关注");
                }
            }
        });

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShareContentActivity.class);
                intent.putExtra("cover",i.getCover());
                intent.putExtra("author",i.getUserName());
                intent.putExtra("title",i.getTitle());
                intent.putExtra("content",i.getContentSimple());
                intent.putExtra("time",getTime_Str(i.getTime()));
                intent.putExtra("shareId",i.getShareId());
                intent.putExtra("comment",i.getComment());
                context.startActivity(intent);
                //增加浏览量
                addBrowser(i.getShareId());
            }
        });

        holder.deleteShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("注意");
                builder.setMessage("确定要删除该动态吗？");
                builder.setIcon(R.drawable.applogo);
                builder.setCancelable(true);
                //设置正面按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpPostUtils deleteShareThread = new HttpPostUtils();
                        deleteShareThread.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
                            @Override
                            public void onSuccessPost() {
                                Log.i("TEST",deleteShareThread.getResponseData());
                                onSuccessListener.onSuccessDelete(position);
                            }
                        });
                        JSONObject body = new JSONObject();
                        JSONObject data = new JSONObject();
                        try {
                            body.put("url",MYURL+DELETE_SHARE);
                            body.put("data",data);
                            data.put("shareId", i.getShareId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        deleteShareThread.execute(body);
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

        holder.modifyShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ModifyShareActivity.class);
                intent.putExtra("cover",i.getCover());
                intent.putExtra("title",i.getTitle());
                intent.putExtra("content",i.getContentSimple());
                intent.putExtra("sharedId",i.getShareId());
                context.startActivity(intent);
            }
        });
    }


    private String getAttention(int attention){
        if(attention==0){
            return "关注";
        }else{
            return "已关注";
        }
    }

    private String getTime_Str(Date time){
        int year = time.getYear();
        int month = time.getMonth();
        int day = time.getDate();
        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();
        Date curDate =  new Date(System.currentTimeMillis());
        if (curDate.getYear()-year>0){
            return (curDate.getYear()-year)+" 年前";
        }else{
            if ((curDate.getMonth()+1)-month>0){
                return ((curDate.getMonth()+1)-month)+" 月前";
            }else{
                if (curDate.getDate()-day>0){
                    return (curDate.getDate()-day)+" 天前";
                }else{
                    if (curDate.getHours()-hour>0){
                        return (curDate.getHours()-hour)+" 小时前";
                    }else {
                        if (curDate.getMinutes()-minute>0){
                            return (curDate.getMinutes()-minute)+" 分钟前";
                        }else{
                            if(curDate.getSeconds()-second>0){
                                return (curDate.getSeconds()-second)+" 秒前";
                            }else{
                                return "刚刚";
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void addBrowser(String shareId){
        HttpPostUtils threadForAddBrowse = new HttpPostUtils();
        threadForAddBrowse.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",threadForAddBrowse.getResponseData());
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("url",MYURL+ADD_BROWSE);
            body.put("data",data);
            data.put("shareId", shareId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        threadForAddBrowse.execute(body);
    }

    private void addToCollection(String userId,String collectionId,int type,String cover,String name){
        HttpPostUtils threadForAddCollection = new HttpPostUtils();
        threadForAddCollection.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",threadForAddCollection.getResponseData());
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("url",MYURL+ADDCOLLECTION);
            data.put("userId",userId);
            data.put("collectionId",collectionId);
            data.put("type",type);
            data.put("cover",cover);
            data.put("name",name);
            data.put("url","null");
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        threadForAddCollection.execute(body);
    }

    private void deleteCollectionForUser(String userId){
        HttpPostUtils deleteCollection = new HttpPostUtils();
        deleteCollection.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",deleteCollection.getResponseData());
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("url",MYURL+DELETECOLLECTION);
            data.put("userId",USERID);
            data.put("collectionId",userId);
            body.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        deleteCollection.execute(body);
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
