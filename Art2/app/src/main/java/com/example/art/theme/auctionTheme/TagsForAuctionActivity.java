package com.example.art.theme.auctionTheme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.art.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

import static com.example.art.dataInterface.DataInterface.AUCTIONTAGSFORTYPE1;
import static com.example.art.dataInterface.DataInterface.AUCTIONTAGSFORTYPE2;

public class TagsForAuctionActivity extends AppCompatActivity {
    private final List<String> tags1 = new ArrayList<>();
    private final List<String> tags2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_for_auction);

        tags1.addAll(Arrays.asList(AUCTIONTAGSFORTYPE1));
        tags2.addAll(Arrays.asList(AUCTIONTAGSFORTYPE2));

        TagContainerLayout mTagContainerLayout1 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
        TagContainerLayout mTagContainerLayout2 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout2);

        mTagContainerLayout1.setTags(tags1);
        mTagContainerLayout1.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
//                Intent intent = new Intent(TagsForAuctionActivity.this, ThemeForArtActivity.class);
//                intent.putExtra("tag",text);
//                startActivity(intent);
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
        mTagContainerLayout2.setTags(tags2);
        mTagContainerLayout2.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
//                Intent intent = new Intent(TagsForAuctionActivity.this, ThemeForArtActivity.class);
//                intent.putExtra("tag",text);
//                startActivity(intent);
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