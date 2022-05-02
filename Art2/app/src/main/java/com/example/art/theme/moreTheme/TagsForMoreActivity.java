package com.example.art.theme.moreTheme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.art.R;
import com.example.art.theme.artTheme.ThemeForArtActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

import static com.example.art.dataInterface.DataInterface.MORETAGS;

public class TagsForMoreActivity extends AppCompatActivity {
    private final List<String> tags = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_for_more);
        tags.addAll(Arrays.asList(MORETAGS));
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
        mTagContainerLayout.setTags(tags);
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForMoreActivity.this, ThemeForArtActivity.class);
                intent.putExtra("tag",text);
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