package com.tata.testalertpopupwindow;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.scitc.alertpopupwindow.util.AlertPopupWindowUtil;
import com.scitc.alertpopupwindow.interfaces.OnClickItemAlertBoxListener;

public class MainActivity extends AppCompatActivity {

    private String TAG="MainActivity";
    private RelativeLayout testBox;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)actionBar.hide();
        initView();
        onListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onListener() {
        testBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    setShowClickXY(motionEvent);
                    showAlertPopupWindow(motionEvent);
                }
                return true;
            }
        });
    }

    private void showAlertPopupWindow(MotionEvent motionEvent){
        AlertPopupWindowUtil.getPopupWindowUtil(this)
                .setAlertText("这是一个提示框")
                .setTipX(motionEvent.getX())
                .setTipY(motionEvent.getY())
                .setTextSizeDp(16)
                .setTextColor(Color.WHITE)
                .setTextBoxBgColor(Color.parseColor("#FF7700"))
                .setPaddingLeftTextDp(18)
                .setPaddingRightTextDp(24)
                .setPaddingTopTextDp(14)
                .setPaddingBottomTextDp(18)
                .setAllScreenWidth(false)
                .setTextAlignType(AlertPopupWindowUtil.TEXT_ALIGN_TYPE_CENTER)
                .setTipType(AlertPopupWindowUtil.TIP_TYPE_ADAPT)
                .setCircleBorderRadiusDp(8)
                .setTipSpacingDp(10)
                .setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Log.e(TAG, "===onDismiss: 消失了");
                    }
                })
                .setOnShowListener(new AlertPopupWindowUtil.OnShowListener() {
                    @Override
                    public void onShow() {
                        Log.e(TAG, "===onShow: 出现了");
                    }
                })
                .setOnClickItemAlertBoxListener(new OnClickItemAlertBoxListener() {
                    @Override
                    public void onClickItemAlertBox() {
                        Log.e(TAG, "===onClickItemAlertBox: 点击了提示框");
                    }
                })
                .show();
    }

    @SuppressLint("SetTextI18n")
    private void setShowClickXY(MotionEvent motionEvent){
        text.setText("点击了： X："+motionEvent.getX()+"---Y:"+motionEvent.getY());
    }

    private void initView() {
        testBox = (RelativeLayout) findViewById(R.id.testBox);
        text = (TextView) findViewById(R.id.text);
    }

}