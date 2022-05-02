package com.example.art.shareContent.comments;

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

import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.museumPlan.plan.PlanAdapter;
import com.example.art.util.AlterDialog.AlterDialogUtil;
import com.example.art.util.httpUtil.HttpPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.art.dataInterface.DataInterface.DELETE_COMMENT;
import static com.example.art.dataInterface.DataInterface.DELETE_PLAN;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder > {
    private List<CommentItem> list;
    private Context context;
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item,parent,false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        CommentItem i = list.get(position);
        holder.userName.setText(i.getUserName());
        holder.content.setText(i.getContent());
        Glide.with(context).load(MYURL+i.getUserLogo()).into(holder.userLogo);
        holder.type.setText(i.getType()==1?"普通":"专家");
        holder.type.setTextColor(context.getResources().getColor(i.getType()==1?R.color.drawingColor9:R.color.drawingColor2));
        //当前删除按钮是否要显示
        if(USERID.equals(i.getUserId())){
            holder.deleteComment.setVisibility(View.VISIBLE);
        }else{
            holder.deleteComment.setVisibility(View.INVISIBLE);
        }
        holder.deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("注意");
                builder.setMessage("确定要删除该评论吗？");
                builder.setIcon(R.drawable.applogo);
                builder.setCancelable(true);
                //设置正面按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpPostUtils deleteCommentThread = new HttpPostUtils();
                        deleteCommentThread.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
                            @Override
                            public void onSuccessPost() {
                                Log.i("TEST",deleteCommentThread.getResponseData());
                                onSuccessListener.onSuccessModify();
                            }
                        });
                        JSONObject body = new JSONObject();
                        JSONObject data = new JSONObject();
                        try {
                            body.put("url",MYURL+DELETE_COMMENT);
                            body.put("data",data);
                            data.put("commentId", i.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        deleteCommentThread.execute(body);
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
        TextView content;
        TextView userName;
        TextView type;
        ImageView userLogo;
        ImageView deleteComment;
        ViewHolder(View view){
            super(view);
            content = view.findViewById(R.id.content);
            userName = view.findViewById(R.id.userName);
            type = view.findViewById(R.id.type);
            userLogo = view.findViewById(R.id.userLogo);
            deleteComment = view.findViewById(R.id.deleteComment);
        }
    }
    public CommentAdapter(List<CommentItem> list, Context context) {
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
}