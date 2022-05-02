package com.example.art.util.AlterDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.art.R;
import com.example.art.info.CheckItemForAllMuseum;
import com.example.art.myDrawing.ScreenUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AlterDialogUtil {
    public static int checkItemForAllMuseum = -1;
    public static List<CheckItemForAllMuseum> ALL_MUSEUM_STATIC_DATA = new ArrayList<>();

    public static void canCloseDialog(DialogInterface dialogInterface, boolean close) {
        try {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, close);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void TipForGuide(Context context, int layout){
        View dialogView = View.inflate(context, layout, null);
//        //对话框设置
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        dialog.getWindow().setLayout(ScreenUtils.getScreenWidth(context) / 4 * 3, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    public static void singleChoiceDialogForAllMuseum(Context context, String title, final String[] strings, final TextView edit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(strings, checkItemForAllMuseum, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkItemForAllMuseum = which;
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit.setText(strings[checkItemForAllMuseum]);
                AlterDialogUtil.canCloseDialog(dialog, true);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlterDialogUtil.canCloseDialog(dialog, true);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
