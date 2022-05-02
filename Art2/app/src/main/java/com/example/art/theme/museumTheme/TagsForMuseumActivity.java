package com.example.art.theme.museumTheme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.art.R;
import com.example.art.dataInterface.MuseumStaticData;
import com.example.art.museumDetail.MuseumDetailActivity;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

import static com.example.art.dataInterface.DataInterface.CREATETAGSFORTYPE1;

public class TagsForMuseumActivity extends AppCompatActivity {
    private final List<String> tags = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_for_create);
        for (MuseumTagItem museumTagItem : CREATETAGSFORTYPE1) {
            tags.add(museumTagItem.getMuseumName());
        }
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
        mTagContainerLayout.setTags(tags);
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForMuseumActivity.this, MuseumDetailActivity.class);
                intent.putExtra("museumName",text);
                MuseumStaticData.MUSEUMID = CREATETAGSFORTYPE1[position].getMuseumId();
                MuseumStaticData.MUSEUMNAME = CREATETAGSFORTYPE1[position].getMuseumName();
                startActivity(intent);
            }
            @Override
            public void onTagLongClick(final int position, String text) {

            }
            @Override
            public void onSelectedTagDrag(int position, String text){
                // ...
            }
            @Override
            public void onTagCrossClick(int position) {
                // ...
            }
        });
    }
}