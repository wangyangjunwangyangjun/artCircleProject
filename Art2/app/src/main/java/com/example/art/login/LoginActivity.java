package com.example.art.login;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.art.R;
import com.example.art.chooseLike.LikeActivity;
import com.example.art.shouye.ShouYeActivity;
import com.example.art.util.HashUtil;
import com.example.art.util.httpUtil.HttpPostUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.example.art.dataInterface.DataInterface.LOGIN;
import static com.example.art.dataInterface.DataInterface.MYURL;
import static com.example.art.dataInterface.DataInterface.USERID;
import static com.example.art.dataInterface.DataInterface.USERLOGO;
import static com.example.art.dataInterface.DataInterface.LEVEL;
import static com.example.art.dataInterface.DataInterface.USERNAME;
import static com.example.art.dataInterface.DataInterface.USER_TEMP_ID;

public class LoginActivity extends Activity implements View.OnClickListener, HttpPostUtils.OnSuccessListener {
    private TextView mBtnLogin;
    private EditText inputAccount;
    private EditText inputPassword;
    private HttpPostUtils httpPostUtils;
    private int code;
    private String msg;
    private String userMobile;
    private String departName;
    private String departId;
    private String departRole;
    private String isDeleted;

    private View mInputLayout;
    private float mWidth, mHeight;
    private View progress;
    private LinearLayout mName, mPsw;

    private CheckBox cbIsRememberPass;
    private SharedPreferences sharedPreferences;
    private String inputAccountStr;
    private String inputPasswordStr;

    //判断第一次登录
    private SharedPreferences sharedPreferencesForLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        bindView();
        addListener();
        sharedPreferences=getSharedPreferences("rememberPass", Context.MODE_PRIVATE);
        sharedPreferencesForLike=getSharedPreferences("rememberLike", Context.MODE_PRIVATE);

        boolean isRemember=sharedPreferences.getBoolean("rememberPass",false);
        if(isRemember){
            String name=sharedPreferences.getString("name","");
            String pass=sharedPreferences.getString("pass","");
            inputAccount.setText(name);
            inputPassword.setText(pass);
            cbIsRememberPass.setChecked(true);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_btn_login:
                loginByPassword();
                break;
        }
    }
    private void addListener(){
        mBtnLogin.setOnClickListener(this);
    }
    private void loginByPassword(){
        // 计算出控件的高与宽
        mWidth = mBtnLogin.getMeasuredWidth();
        mHeight = mBtnLogin.getMeasuredHeight();
        // 隐藏输入框
        mName.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);
        inputAnimator(mInputLayout, mWidth, mHeight);

        inputAccountStr = inputAccount.getText().toString();
        inputPasswordStr = inputPassword.getText().toString();

        httpPostUtils = new HttpPostUtils();
        httpPostUtils.setOnSuccessListener(this);
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("data",data);
            body.put("url",MYURL+LOGIN);
            data.put("userId",inputAccountStr);
            data.put("userPass", HashUtil.md5(inputPasswordStr));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("TEST",body.toString());
        httpPostUtils.execute(body);

        Log.i("TEST",inputAccountStr+"//"+inputPasswordStr);
    }
    private void bindView(){
        mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
        inputAccount = findViewById(R.id.inputAccount);
        inputPassword =findViewById(R.id.inputPassword);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = findViewById(R.id.input_layout_name);
        mPsw = findViewById(R.id.input_layout_psw);
        cbIsRememberPass = findViewById(R.id.checkbox);
    }
    private void inputAnimator(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();
    }

    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }

    //登录返回
    @Override
    public void onSuccessPost() {
        String result = httpPostUtils.getResponseData();
        JSONObject retObj = null;
        try {
            if(result!=null){
                retObj = new JSONObject(result);
                code = retObj.getInt("code");
                msg = retObj.getString("msg");
                Log.i("TEST",result);
                if(code==0){
                    //暂存密码
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    if(cbIsRememberPass.isChecked()){
                        editor.putBoolean("rememberPass",true);
                        editor.putString("name",inputAccountStr);
                        editor.putString("pass",inputPasswordStr);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    USERNAME = retObj.getString("userName");
                    USERLOGO = retObj.getString("userLogo");
                    LEVEL = retObj.getString("level");
                    USERID = inputAccountStr;
                    USER_TEMP_ID = inputAccountStr;

                    SharedPreferences.Editor editorForLike = sharedPreferencesForLike.edit();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(sharedPreferencesForLike.getBoolean("rememberLike",false)){
                                Intent intent = new Intent(LoginActivity.this, ShouYeActivity.class);
                                startActivity(intent);
                            }else{
                                editorForLike.putBoolean("rememberLike",true);
                                editorForLike.apply();
                                Intent intent = new Intent(LoginActivity.this, LikeActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }
                    },2000);
                }else{
                    Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recovery();
                        }
                    },2100);
                }
            }else{
                Toast.makeText(LoginActivity.this,"请检查当前网络是否正常",Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recovery();
                    }
                },2100);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}