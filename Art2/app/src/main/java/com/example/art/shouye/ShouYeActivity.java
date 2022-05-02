package com.example.art.shouye;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.bumptech.glide.Glide;
import com.example.art.R;
import com.example.art.aboutUs.AboutUsActivity;
import com.example.art.createShare.CreateShareActivity;
import com.example.art.info.info1;
import com.example.art.info.ShareItem;
import com.example.art.login.LoginActivity;
import com.example.art.mine.MineActivity;
import com.example.art.museumPlan.plan.CalendarMuseumActivity;
import com.example.art.myDrawing.MyDrawingActivity;
import com.example.art.scan.ScanActivity;
import com.example.art.scan.CreateQRActivity;
import com.example.art.search_example.adapter.SearchResultsListAdapter;
import com.example.art.shouye.adapter.LatestListAdapter;
import com.example.art.shouye.adapter.themeAdapter;
import com.example.art.shouye.adapter.ShufflingAdapter;
import com.example.art.shouye.artist.ArtistActivity;
import com.example.art.shouye.artwork.ArtworkActivity;
import com.example.art.shouye.museum.MuseumActivity;
import com.example.art.shouye.news.NewsActivity;
import com.example.art.shouye.search.ArtSuggestion;
import com.example.art.util.WebViewUtil.UrlForSearchActivity;
import com.example.art.util.httpUtil.HttpGetUtils;
import com.example.art.util.myViewPager.MyViewPager;
import com.example.art.util.roundImageView.RoundImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.art.dataInterface.DataInterface.GETALLSHARE;
import static com.example.art.dataInterface.DataInterface.GETSEARCH;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERLOGO;
import static com.example.art.dataInterface.DataInterface.USERNAME;

public class ShouYeActivity extends AppCompatActivity implements View.OnClickListener{
    Handler mHandler = new Handler();
    MyViewPager mShuffling;
    private List<String> list  = new ArrayList<>();
    private List<info1> themelist = new ArrayList<>();
    private static int CREATESHARECODE = 1;

    //子功能
    private RoundImageView news;
    private RoundImageView artwork;
    private RoundImageView artist;
    private RoundImageView museum;
    private FloatingActionButton createShare;
    private Button more;

    //搜索框
    private DrawerLayout drawer;
    private final String TAG = "TEST";
    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 1000;
    private FloatingSearchView mSearchView;
    private RecyclerView mSearchResultsList;
    private SearchResultsListAdapter mSearchResultsAdapter;
    private boolean mIsDarkSearchTheme = false;
    private String mLastQuery = "ArtCircle";
    private ImageView searchBg;
    private List<ShareItem> latestList = new ArrayList<>();
    private LatestListAdapter latestListAdapter;
    private List<ArtSuggestion> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye);
        //初始化轮播图
        //https://blog.csdn.net/weixin_44241851/article/details/105875177
        list.add("/images/main/1.jpg");
        list.add("/images/main/2.jpg");
        list.add("/images/main/3.jpg");
        list.add("/images/main/4.jpg");
        list.add("/images/main/5.jpg");
        list.add("/images/main/6.jpg");
        mShuffling = findViewById(R.id.shuffling);
        ShufflingAdapter mShufflingAdapter = new ShufflingAdapter();
        mShufflingAdapter.setData(list,ShouYeActivity.this);
        mShuffling.setAdapter(mShufflingAdapter);
        //recycleView——the theme
        info1 theme1 = new info1("赏艺术",R.drawable.artappreciation);
        info1 theme2 = new info1("拍艺术",R.drawable.auction);
        info1 theme3 = new info1("览艺术",R.drawable.artisticcreation);
        info1 theme4 = new info1("更多主题",R.drawable.more);
        themelist.add(theme1);
        themelist.add(theme2);
        themelist.add(theme3);
        themelist.add(theme4);
        RecyclerView recyclerView = findViewById(R.id.themelist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        themeAdapter adapter = new themeAdapter(themelist,ShouYeActivity.this);
        recyclerView.setAdapter(adapter);
        //监听点击事件
        news = findViewById(R.id.news);
        artwork = findViewById(R.id.artwork);
        artist = findViewById(R.id.artist);
        museum = findViewById(R.id.museum);
        news.setOnClickListener(this);
        artwork.setOnClickListener(this);
        artist.setOnClickListener(this);
        museum.setOnClickListener(this);
        //用户头像设置和用户账号名称设置
        NavigationView navigationView = findViewById(R.id.navigationView);
        RoundImageView loginLogo =  navigationView.getHeaderView(0).findViewById(R.id.loginLogo);
        Glide.with(ShouYeActivity.this).load(MYURL+USERLOGO).into(loginLogo);
        TextView loginName = navigationView.getHeaderView(0).findViewById(R.id.loginName);
        loginName.setText(USERNAME);

        loginLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShouYeActivity.this, MineActivity.class);
                startActivity(intent);
            }
        });
        //登出
        Button loginOut =  navigationView.getHeaderView(0).findViewById(R.id.loginOut);
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShouYeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //EditText search
        searchBg = findViewById(R.id.searchBg);

        //latest list
        getLatest();
        latestListAdapter = new LatestListAdapter(ShouYeActivity.this,latestList);
        latestListAdapter.setOnSuccessListener(new LatestListAdapter.OnSuccessListener() {
            @Override
            public void onSuccessDelete(int position) {
                latestList.remove(position);
                latestListAdapter.notifyDataSetChanged();
            }
        });
        RecyclerView latestList = findViewById(R.id.latestList);
        latestList.setAdapter(latestListAdapter);

        //解决 ScrollView嵌套RecyclerView后无法惯性滑动的问题
        latestList.setHasFixedSize(true);
        latestList.setNestedScrollingEnabled(false);
        //设置侧边栏按钮点击事件
        navigationView.setCheckedItem(R.id.home);
        drawer = findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.scan:
                        Intent intent = new Intent(ShouYeActivity.this, ScanActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.qr_code:
                        Intent intent1 = new Intent(ShouYeActivity.this, CreateQRActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.drawing_board:
                        Intent intent2 = new Intent(ShouYeActivity.this, MyDrawingActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.plan:
                        CalendarMuseumActivity.show(ShouYeActivity.this);
                        break;
                    case R.id.about_us:
                        Intent intent3 = new Intent(ShouYeActivity.this, AboutUsActivity.class);
                        startActivity(intent3);
                        break;
                }
                drawer.closeDrawers();
                return true;
            }
        });
        //搜索框
        mSearchView = findViewById(R.id.floating_search_view);
        mSearchView.attachNavigationDrawerToMenuButton(drawer);
        mSearchResultsList = findViewById(R.id.search_results_list);
        setupFloatingSearch();
        setupResultsList();
        //创建一个动态
        createShare = findViewById(R.id.createShare);
        createShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShouYeActivity.this, CreateShareActivity.class);
                startActivityForResult(intent, CREATESHARECODE);
            }
        });
        more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShouYeActivity.this,"目前只有三个主题",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getLatest(){
        latestList.clear();
        //获取数据
        HttpGetUtils httpGetUtils = new HttpGetUtils();
        httpGetUtils.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
            @Override
            public void onSuccessGet() {
                Log.i("TEST", "返回结果为" + httpGetUtils.getResponseData());
                try {
                    JSONObject data = new JSONObject(httpGetUtils.getResponseData());
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        ShareItem temp = new ShareItem();
                        temp.setTime(
                                new Date(
                                        Integer.parseInt(list.getJSONObject(i).getString("time").substring(0,4))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(5,7))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(8,10))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(11,13))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(14,16))
                                        ,Integer.parseInt(list.getJSONObject(i).getString("time").substring(17,19))
                                )
                        );
                        temp.setBrowse(list.getJSONObject(i).getInt("browse"));
                        temp.setComment(list.getJSONObject(i).getInt("comment"));
                        temp.setContentSimple(list.getJSONObject(i).getString("contentSimple"));
                        temp.setFavor(list.getJSONObject(i).getInt("favor"));
                        temp.setTitle(list.getJSONObject(i).getString("title"));
                        temp.setUserLogo(list.getJSONObject(i).getString("userLogo"));
                        temp.setCover(list.getJSONObject(i).getString("cover"));
                        temp.setUserName(list.getJSONObject(i).getString("userName"));
                        temp.setShareId(list.getJSONObject(i).getString("shareId"));
                        temp.setUserId(list.getJSONObject(i).getString("userId"));
                        latestList.add(temp);
                    }
                    latestListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        httpGetUtils.execute(MYURL + GETALLSHARE);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.post(mRunnable);
    }
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = mShuffling.getCurrentItem();
            mShuffling.setCurrentItem(++currentItem, false);
            mHandler.postDelayed(this, 3500);
        }
    };
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mRunnable);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.news:
                Intent intent1 = new Intent(ShouYeActivity.this,NewsActivity.class);
                startActivity(intent1);
                break;
            case R.id.artwork:
                Intent intent2 = new Intent(ShouYeActivity.this, ArtworkActivity.class);
                startActivity(intent2);
                break;
            case R.id.artist:
                Intent intent3 = new Intent(ShouYeActivity.this, ArtistActivity.class);
                startActivity(intent3);
                break;
            case R.id.museum:
                Intent intent4 = new Intent(ShouYeActivity.this, MuseumActivity.class);
                startActivity(intent4);
                break;
        }
    }

    //搜索函数
    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.showProgress();
                    //从后台取数据
                    HttpGetUtils threadForSearch = new HttpGetUtils();
                    threadForSearch.setOnSuccessListener(new HttpGetUtils.OnSuccessListener() {
                        @Override
                        public void onSuccessGet() {
                            results = new ArrayList<>();
                            Log.i("TEST",threadForSearch.getResponseData());
                            try {
                                JSONObject result = new JSONObject(threadForSearch.getResponseData());
                                JSONArray list = new JSONArray();
                                list = result.getJSONArray("list");
                                for(int i=0;i<list.length();i++){
                                    JSONObject properties = list.getJSONObject(i).getJSONArray("_fields").getJSONObject(0).getJSONObject("properties");
                                    if(!properties.getString("url").equals("null")){
                                        ArtSuggestion artSuggestion = new ArtSuggestion(properties.getString("name"),properties.getString("url"),properties.getString("cover"));
                                        results.add(artSuggestion);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mSearchView.swapSuggestions(results);
                            mSearchView.hideProgress();
                        }
                    });
                    threadForSearch.execute(MYURL+GETSEARCH,"key",newQuery);
                }
                Log.d(TAG, "onSearchTextChanged()");
            }
        });
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                Log.d(TAG, "click->"+searchSuggestion.getBody());
                ArtSuggestion artSuggestion = (ArtSuggestion) searchSuggestion;
                Intent intent = new Intent(ShouYeActivity.this, UrlForSearchActivity.class);
                intent.putExtra("data",artSuggestion);
                startActivity(intent);
            }
            @Override
            public void onSearchAction(String query) {
                Log.d(TAG, "onSearchAction()");
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                List<SearchSuggestion> history = new ArrayList<>();
                mSearchView.swapSuggestions(history);
                searchBg.setVisibility(View.VISIBLE);
                Log.d(TAG, "onFocus()");
            }
            @Override
            public void onFocusCleared() {
                mSearchView.setSearchBarTitle(mLastQuery);
                searchBg.setVisibility(View.GONE);
                Log.d(TAG, "onFocusCleared()");
            }
        });
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                //菜单监听
            }
        });
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                Log.d(TAG, "onHomeClicked()");
            }
        });
        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
                ArtSuggestion artSuggestion = (ArtSuggestion) item;
                String textColor = mIsDarkSearchTheme ? "#ffffff" : "#000000";
                String textLight = mIsDarkSearchTheme ? "#bfbfbf" : "#787878";

                if (artSuggestion.ismIsHistory()) {
                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_history_black_24dp, null));
                    Util.setIconColor(leftIcon, Color.parseColor(textColor));
                    leftIcon.setAlpha(.36f);
                } else {
                    leftIcon.setAlpha(.80f);
                    Glide.with(suggestionView).load(MYURL+results.get(itemPosition).getMyCover()).into(leftIcon);
                }
                textView.setTextColor(Color.parseColor(textColor));
                String text = artSuggestion.getBody()
                        .replaceFirst(mSearchView.getQuery(),
                                "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
                textView.setText(Html.fromHtml(text));
            }
        });
        mSearchView.setOnSuggestionsListHeightChanged(new FloatingSearchView.OnSuggestionsListHeightChanged() {
            @Override
            public void onSuggestionsListHeightChanged(float newHeight) {
                mSearchResultsList.setTranslationY(newHeight);
            }
        });
        mSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {
                Log.d(TAG, "onClearSearchClicked()");
            }
        });
    }
    private void setupResultsList() {
        mSearchResultsAdapter = new SearchResultsListAdapter();
        mSearchResultsList.setAdapter(mSearchResultsAdapter);
        mSearchResultsList.setLayoutManager(new LinearLayoutManager(ShouYeActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                latestList.clear();
                getLatest();
                latestListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLatest();
    }
}