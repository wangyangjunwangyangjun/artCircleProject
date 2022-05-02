package com.example.art.myDrawing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.art.R;

public class MyDrawingActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawingBoard mDrawingBoard;
    private Slider mSlider;
    //代表颜色选项
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private ImageView iv8;
    private ImageView iv9;
    private ImageView iv10;
    private ImageView iv11;
    //对画板的操作
    private ImageView mPaint;
    private ImageView mEraser;
    private ImageView mClean;
    private ImageView mLast;
    private ImageView mNext;
    //记录画笔大小
    private float size;

    //获取像素点
    private int dip2x(float depValue){
        final float density = getResources().getDisplayMetrics().density;
        return (int)(depValue*density+0.5f);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drawing);
        initView();
        initEvent();
        addSliderListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //默认画笔大小
        size  = dip2x(10);
    }

    private void initView(){
        mDrawingBoard = findViewById(R.id.draw_board);
        mSlider = findViewById(R.id.slider);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv5 = findViewById(R.id.iv5);
        iv6 = findViewById(R.id.iv6);
        iv7 = findViewById(R.id.iv7);
        iv8 = findViewById(R.id.iv8);
        iv9 = findViewById(R.id.iv9);
        iv10 = findViewById(R.id.iv10);
        iv11 = findViewById(R.id.iv11);

        mPaint = findViewById(R.id.iv_paint);
        mEraser = findViewById(R.id.iv_eraser);
        mClean = findViewById(R.id.iv_clean);
        mLast = findViewById(R.id.iv_last);
        mNext = findViewById(R.id.iv_next);
    }
    //实现滑动小圆点改变画笔线条粗细大小
    private void addSliderListener(){
        mSlider.addListener(new Slider.OnSliderChangedListener() {
            @Override
            public void positionChanged(float p) {
                if (size > 0) {
                    mDrawingBoard.setmPaintSize((int) (p * size * 2));
                }
            }
        });
    }

    private void initEvent(){
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);
        iv7.setOnClickListener(this);
        iv8.setOnClickListener(this);
        iv9.setOnClickListener(this);
        iv10.setOnClickListener(this);
        iv11.setOnClickListener(this);

        mPaint.getBackground().setLevel(1);
        mPaint.getDrawable().setLevel(1);
        mPaint.setOnClickListener(this);
        mEraser.setOnClickListener(this);
        mClean.setOnClickListener(this);
        mLast.setOnClickListener(this);
        mNext.setOnClickListener(this);

    }

    //设置画板清空对话框
    private void alertDialogClean(){
        View dialogView = View.inflate(MyDrawingActivity.this, R.layout.clean_dialog, null);
        Button positiveBtn = dialogView.findViewById(R.id.positiveBtn);
        Button negativeBtn = dialogView.findViewById(R.id.negativeBtn);
        //对话框设置
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MyDrawingActivity.this);
        final AlertDialog dialog = builder.create();
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawingBoard.clean();
                dialog.dismiss();
            }
        });
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(dialogView);
        dialog.getWindow().setLayout(ScreenUtils.getScreenWidth(this) / 4 * 3, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv1:
                mDrawingBoard.setmPaintColor(Color.parseColor("#FF0000"));
                break;
            case R.id.iv2:
                mDrawingBoard.setmPaintColor(Color.parseColor("#FF7F00"));
                break;
            case R.id.iv3:
                mDrawingBoard.setmPaintColor(Color.parseColor("#FFFF00"));
                break;
            case R.id.iv4:
                mDrawingBoard.setmPaintColor(Color.parseColor("#00FF00"));
                break;
            case R.id.iv5:
                mDrawingBoard.setmPaintColor(Color.parseColor("#00FFFF"));
                break;
            case R.id.iv6:
                mDrawingBoard.setmPaintColor(Color.parseColor("#0000FF"));
                break;
            case R.id.iv7:
                mDrawingBoard.setmPaintColor(Color.parseColor("#8B00FF"));
                break;
            case R.id.iv8:
                mDrawingBoard.setmPaintColor(Color.parseColor("#DFDFDE"));
                break;
            case R.id.iv9:
                mDrawingBoard.setmPaintColor(Color.parseColor("#000000"));
                break;
            case R.id.iv10:
                mDrawingBoard.setmPaintColor(Color.parseColor("#740941"));
                break;
            case R.id.iv11:
                mDrawingBoard.setmPaintColor(Color.parseColor("#e1a5fa"));
                break;
            case R.id.iv_paint:
                if (mDrawingBoard.getMode() != DrawMode.PaintMode) {
                    mDrawingBoard.setMode(DrawMode.PaintMode);
                }
                mPaint.getDrawable().setLevel(1);
                mPaint.getBackground().setLevel(1);
                mEraser.getDrawable().setLevel(0);
                mEraser.getBackground().setLevel(0);
                break;
            case R.id.iv_eraser:
                if (mDrawingBoard.getMode() != DrawMode.EraserMode) {
                    mDrawingBoard.setMode(DrawMode.EraserMode);
                }
                mPaint.getDrawable().setLevel(0);
                mPaint.getBackground().setLevel(0);
                mEraser.getDrawable().setLevel(1);
                mEraser.getBackground().setLevel(1);
                break;
            case R.id.iv_clean:
                alertDialogClean();
                break;
            case R.id.iv_last:
                mDrawingBoard.lastStep();
                break;
            case R.id.iv_next:
                mDrawingBoard.nextStep();
                break;
        }
    }
}