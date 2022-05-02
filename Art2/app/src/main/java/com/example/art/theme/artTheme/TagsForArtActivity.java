package com.example.art.theme.artTheme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.art.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import static com.example.art.dataInterface.DataInterface.ARTTAGSFORTYPE1;
import static com.example.art.dataInterface.DataInterface.ARTTAGSFORTYPE2;
import static com.example.art.dataInterface.DataInterface.ARTTAGSFORTYPE3;
import static com.example.art.dataInterface.DataInterface.ARTTAGSFORTYPE4;
import static com.example.art.dataInterface.DataInterface.ARTTAGSFORTYPE5;
import static com.example.art.dataInterface.DataInterface.ARTTAGSFORTYPE6;
import static com.example.art.dataInterface.DataInterface.ARTTAGSFORTYPE7;
import static com.example.art.dataInterface.DataInterface.ARTTAGSFORTYPE8;

public class TagsForArtActivity extends AppCompatActivity {
    private final List<String> tags1 = new ArrayList<>();
    private final List<String> tags2 = new ArrayList<>();
    private final List<String> tags3 = new ArrayList<>();
    private final List<String> tags4 = new ArrayList<>();
    private final List<String> tags5 = new ArrayList<>();
    private final List<String> tags6 = new ArrayList<>();
    private final List<String> tags7 = new ArrayList<>();
    private final List<String> tags8 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_for_art);
//        http://www.360doc.com/content/16/0419/20/27905781_552106575.shtml
        tags1.addAll(Arrays.asList(ARTTAGSFORTYPE1));
        tags2.addAll(Arrays.asList(ARTTAGSFORTYPE2));
        tags3.addAll(Arrays.asList(ARTTAGSFORTYPE3));
        tags4.addAll(Arrays.asList(ARTTAGSFORTYPE4));
        tags5.addAll(Arrays.asList(ARTTAGSFORTYPE5));
        tags6.addAll(Arrays.asList(ARTTAGSFORTYPE6));
        tags7.addAll(Arrays.asList(ARTTAGSFORTYPE7));
        tags8.addAll(Arrays.asList(ARTTAGSFORTYPE8));

        TagContainerLayout mTagContainerLayout1 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout);
        TagContainerLayout mTagContainerLayout2 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout2);
        TagContainerLayout mTagContainerLayout3 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout3);
        TagContainerLayout mTagContainerLayout4 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout4);
        TagContainerLayout mTagContainerLayout5 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout5);
        TagContainerLayout mTagContainerLayout6 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout6);
        TagContainerLayout mTagContainerLayout7 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout7);
        TagContainerLayout mTagContainerLayout8 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout8);

        mTagContainerLayout1.setTags(tags1);
        mTagContainerLayout2.setTags(tags2);
        mTagContainerLayout3.setTags(tags3);
        mTagContainerLayout4.setTags(tags4);
        mTagContainerLayout5.setTags(tags5);
        mTagContainerLayout6.setTags(tags6);
        mTagContainerLayout7.setTags(tags7);
        mTagContainerLayout8.setTags(tags8);

        mTagContainerLayout1.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForArtActivity.this, ThemeForArtActivity.class);
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
        mTagContainerLayout2.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForArtActivity.this, ThemeForArtActivity.class);
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
        mTagContainerLayout3.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForArtActivity.this, ThemeForArtActivity.class);
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
        mTagContainerLayout4.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForArtActivity.this, ThemeForArtActivity.class);
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
        mTagContainerLayout5.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForArtActivity.this, ThemeForArtActivity.class);
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
        mTagContainerLayout6.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForArtActivity.this, ThemeForArtActivity.class);
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
        mTagContainerLayout7.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForArtActivity.this, ThemeForArtActivity.class);
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
        mTagContainerLayout8.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Intent intent = new Intent(TagsForArtActivity.this, ThemeForArtActivity.class);
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