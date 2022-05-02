package com.example.art.shareContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.shareContent.comments.CommentAdapter;
import com.example.art.shareContent.comments.CommentItem;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.httpUtil.HttpPostUtils;
import com.example.art.util.likeButton.LikeButton;
import com.example.art.util.likeButton.OnLikeListener;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.ADDCOMMENTS;
import static com.example.art.dataInterface.DataInterface.FINDFAVOR;
import static com.example.art.dataInterface.DataInterface.GETCOMMENTS;
import static com.example.art.dataInterface.DataInterface.LEVEL;
import static com.example.art.dataInterface.DataInterface.MODIFYFAVOR;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.dataInterface.DataInterface.USERLOGO;
import static com.example.art.dataInterface.DataInterface.USERNAME;

public class ShareContentActivity extends AppCompatActivity {
    private KenBurnsView shareContentCover;
    private TextView author;
    private TextView title;
    private TextView content;
    private TextView time;
    private RecyclerView commentsRecyclerView;
    private List<CommentItem> commentList = new ArrayList<>();
    private String shareId;
    private CommentAdapter commentAdapter;
    private TextView send;
    private EditText commentEditText;
    private TextView noteForComments;
    private LikeButton thumb_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_content);
        Intent intent = getIntent();
        String coverS = intent.getStringExtra("cover");
        String titleS = intent.getStringExtra("title");
        String contentS = intent.getStringExtra("content");
        String authorS = intent.getStringExtra("author");
        String timeS = intent.getStringExtra("time");
        shareId = intent.getStringExtra("shareId");

        author = findViewById(R.id.author);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        time = findViewById(R.id.time);
        shareContentCover = findViewById(R.id.shareContentCover);
        send = findViewById(R.id.send);
        commentEditText = findViewById(R.id.comment);
        noteForComments = findViewById(R.id.noteForComments);

        author.setText(authorS);
        title.setText(titleS);
        content.setText(contentS);
        time.setText(timeS);
        Glide.with(ShareContentActivity.this).load(MYURL+coverS).into(shareContentCover);

        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentAdapter = new CommentAdapter(commentList,ShareContentActivity.this);
        commentAdapter.setOnSuccessListener(new CommentAdapter.OnSuccessListener() {
            @Override
            public void onSuccessModify() {
                getCommentsData();
            }
        });
        commentsRecyclerView.setAdapter(commentAdapter);
        getCommentsData();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = commentEditText.getText().toString();
                if(!commentContent.equals("")){
                    Calendar now = Calendar.getInstance();
                    int year = now.get(Calendar.YEAR);
                    int month = now.get(Calendar.MONTH);
                    int day = now.get(Calendar.DATE);
                    int hour = now.get(Calendar.HOUR_OF_DAY);
                    int minute = now.get(Calendar.MINUTE);
                    int second = now.get(Calendar.SECOND);
                    String nowStr = ""+year+month+day+hour+minute+second;
                    AddComments(shareId+nowStr+USERID,commentContent);
                }else{
                    Toast.makeText(ShareContentActivity.this,"输入内容不能为空!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //点赞功能
        thumb_button  = findViewById(R.id.thumb_button);
        InitFavorButton();
        thumb_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                ModifyFavor(1);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                ModifyFavor(0);
            }
        });
    }
    private void ModifyFavor(int isFavored){
        HttpPostUtils threadForDeleteFavor = new HttpPostUtils();
        threadForDeleteFavor.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",threadForDeleteFavor.getResponseData());
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("data",data);
            body.put("url",MYURL+MODIFYFAVOR);
            data.put("userId",USERID);
            data.put("shareId",shareId);
            data.put("isFavored",isFavored);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        threadForDeleteFavor.execute(body);
    }
    private void InitFavorButton(){
        HttpPostUtils threadForInitFavor = new HttpPostUtils();
        threadForInitFavor.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",threadForInitFavor.getResponseData());
                try {
                    JSONObject result = new JSONObject(threadForInitFavor.getResponseData());
                    int isFavored = result.getInt("isFavored");
                    thumb_button.setLiked(isFavored == 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("data",data);
            body.put("url",MYURL+FINDFAVOR);
            data.put("userId",USERID);
            data.put("shareId",shareId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        threadForInitFavor.execute(body);
    }
    private void getCommentsData(){
        commentList.clear();
        HttpGetUtils thread = new HttpGetUtils();
        thread.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccessGet() {
                Log.i("TEST",thread.getResponseData());
                JSONObject result = null;
                try {
                    result = new JSONObject(thread.getResponseData());
                    JSONArray list = result.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        String content = list.getJSONObject(i).getString("content");
                        String userName = list.getJSONObject(i).getString("userName");
                        String userId = list.getJSONObject(i).getString("userId");
                        int type = list.getJSONObject(i).getInt("type");
                        String userLogo = list.getJSONObject(i).getString("userLogo");
                        String id = list.getJSONObject(i).getString("id");
                        CommentItem item = new CommentItem();
                        item.setType(type);
                        item.setContent(content);
                        item.setUserName(userName);
                        item.setUserLogo(userLogo);
                        item.setId(id);
                        item.setUserId(userId);
                        commentList.add(item);
                    }
                    noteForComments.setText("评论（"+commentList.size()+")");
                    commentAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.execute(MYURL+GETCOMMENTS,"shareId",shareId);
    }
    private void AddComments(String id, String content){
        HttpPostUtils threadForPost = new HttpPostUtils();
        threadForPost.setOnSuccessListener(new HttpPostUtils.OnSuccessListener() {
            @Override
            public void onSuccessPost() {
                Log.i("TEST",threadForPost.getResponseData());
                //更新数据
                getCommentsData();
                //清空数据
                commentEditText.setText("");
            }
        });
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("url",MYURL+ADDCOMMENTS);
            body.put("data",data);
            data.put("id", id);
            data.put("content", content);
            data.put("type", LEVEL);
            data.put("userName", USERNAME);
            data.put("userId", USERID);
            data.put("shareId", shareId);
            data.put("userLogo", USERLOGO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        threadForPost.execute(body);
    }
}