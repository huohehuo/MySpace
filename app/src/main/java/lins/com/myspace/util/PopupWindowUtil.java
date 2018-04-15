package lins.com.myspace.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import lins.com.myspace.R;
import lins.com.myspace.io.ChangeView;

/**
 * Created by LINS on 2016/12/22.
 * Please Try Hard
 */
public class PopupWindowUtil {
    private Context context;
    private static PopupWindowUtil popupWindowUtil;
    private View dialogView;

    private PopupWindowUtil(Context context) {
        this.context = context;
    }

    public static PopupWindowUtil getSystemUtils(Context context) {
        if (popupWindowUtil == null) {
            popupWindowUtil = new PopupWindowUtil(context);
        }
        return popupWindowUtil;
    }

    public void showPup(View view, final ChangeView changeView) {
        View popView = LayoutInflater.from(context).inflate(R.layout.item_who_input, null);
        final EditText editText = (EditText) popView.findViewById(R.id.et_left_name);
        Button add = (Button) popView.findViewById(R.id.btn_left_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView.changeData(editText.getText().toString());
            }
        });

        final PopupWindow popupWindow = new PopupWindow(popView, GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.res8));
        popupWindow.setAnimationStyle(R.anim.loading_animation);
        popupWindow.setWidth(400);
        popupWindow.setHeight(450);
        popupWindow.showAsDropDown(view);
    }


    public void popupWindow(View v, final ChangeView changeView) {
        AlertDialog.Builder builder = myBuilder(context);
        final AlertDialog dialog = builder.show();
        //点击屏幕外侧，dialog是否消失
        dialog.setCanceledOnTouchOutside(false);
//监听dialog里的button
/*
 * 监听btn发邮件
 */
        final EditText ed1 = (EditText) dialogView.findViewById(R.id.dialog_input_title);
        Button ortherbtnemil = (Button) dialogView.findViewById(R.id.dialog_input_add);
        ortherbtnemil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ed1.getText().toString() != null && !ed1.getText().toString().equals("")) {
                    changeView.changeData(ed1.getText().toString());
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "请输入包名", Toast.LENGTH_SHORT).show();
                }

            }
        });
/*
 * 监听imgbtn关闭dialog
 */
        ImageButton customviewtvimgCancel = (ImageButton) dialogView.findViewById(R.id.customviewtvimgCancel);
        customviewtvimgCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    protected AlertDialog.Builder myBuilder(Context dialogWindows) {

        final LayoutInflater inflater = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(dialogWindows);
        dialogView = inflater.inflate(R.layout.dialog_input, null);

        return builder.setView(dialogView);
    }



}





