package com.example.art.shouye.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.util.WebViewUtil.UrlForCommonActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;

import static com.example.art.dataInterface.DataInterface.MYURL;

public class NewsContentActivity extends AppCompatActivity {
    private KenBurnsView newsContentCover;
    private TextView title;
    private TextView keyword;
    private TextView editor;
    private TextView time;
    private TextView content;
    private TextView url;
    private TextView source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        newsContentCover = findViewById(R.id.newsContentCover);
        title = findViewById(R.id.title);
        keyword = findViewById(R.id.keyword);
        editor = findViewById(R.id.editor);
        time = findViewById(R.id.time);
        content = findViewById(R.id.content);
        url = findViewById(R.id.url);
        source = findViewById(R.id.source);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        keyword.setText(intent.getStringExtra("keyword"));
        editor.setText(intent.getStringExtra("editor"));
        time.setText(intent.getStringExtra("time"));
        content.setText(intent.getStringExtra("content"));
        url.setText(intent.getStringExtra("url"));
        source.setText(intent.getStringExtra("source"));
        Glide.with(NewsContentActivity.this).load(MYURL+"/images/newsContent/p_1_1.jpg").into(newsContentCover);

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(NewsContentActivity.this, UrlForCommonActivity.class);
                intent1.putExtra("url",url.getText().toString());
                startActivity(intent1);
            }
        });
    }
}
