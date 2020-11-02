package com.scitc.alertpopupwindow.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.scitc.alertpopupwindow.view.AlertTextDialog;
import com.scitc.alertpopupwindow.interfaces.OnClickItemAlertBoxListener;

public class AlertPopupWindowUtil {

    /**
     * 锚点-下面
     * 基本朝向下，当无法向下时再向上
     * 锚点向下
     */
    public static int TIP_TYPE_BOTTOM = AlertTextDialog.TIP_TYPE_BOTTOM;
    /**
     * 锚点-上面
     * 基本朝向上，当无法向上时再向下
     * 锚点向上
     */
    public static int TIP_TYPE_TOP =AlertTextDialog.TIP_TYPE_TOP;

    /**
     * 锚点-适应
     * 对齐点在屏幕上半部分，锚点向上
     * 对齐点在屏幕下半部分，锚点向下
     */
    public static int TIP_TYPE_ADAPT=AlertTextDialog.TIP_TYPE_ADAPT;


    /**
     * 字体--对齐方式-左对齐
     */
    public static int TEXT_ALIGN_TYPE_LEFT =AlertTextDialog.TEXT_ALIGN_TYPE_LEFT;
    /**
     * 字体--对齐方式-右对齐
     */
    public static int TEXT_ALIGN_TYPE_RIGHT =AlertTextDialog.TEXT_ALIGN_TYPE_RIGHT;
    /**
     * 字体--对齐方式-居中对齐
     */
    public static int TEXT_ALIGN_TYPE_CENTER =AlertTextDialog.TEXT_ALIGN_TYPE_CENTER;

    private PopupWindow popupWindow;
    private Context context;
    private RelativeLayout popupWindowBox;
    private PopupWindow.OnDismissListener onDismissListener;
    private OnShowListener onShowListener;
    private AlertTextDialog alertTextDialog;

    @SuppressLint("StaticFieldLeak")
    private static AlertPopupWindowUtil popupWindowUtil;


    public interface OnShowListener{
        void onShow();
    }


    public synchronized static AlertPopupWindowUtil getPopupWindowUtil(Context context) {
        if(popupWindowUtil==null){
            popupWindowUtil=new AlertPopupWindowUtil();
        }

        if(context!=null){
            popupWindowUtil.setContext(context);
            popupWindowUtil.init();
        }
        return popupWindowUtil;
    }



    /**
     * 初始化 popupWindow
     */
    private void init(){
        popupWindowBox=new RelativeLayout(context);
        popupWindow = new PopupWindow(popupWindowBox, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        initPopupWindowView();
        onListener();
    }

    /**
     * 显示 popupWindow
     */
    private void showShare() {
        if(context==null){
            return;
        }
        final View view=((Activity)context).getWindow().getDecorView();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(view,0,0,0);
                // 获取popwindow焦点
                popupWindow.setFocusable(true);
                // 设置popwindow如果点击外面区域，便关闭。
                popupWindow.setOutsideTouchable(false);
                popupWindow.update();

                if(onShowListener!=null){
                    onShowListener.onShow();
                }
            }
        },0);
    }



    /**
     * 初始化 alertTextDialog
     */
    private void initPopupWindowView(){
        alertTextDialog=new AlertTextDialog(context);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        alertTextDialog.setLayoutParams(layoutParams);
        popupWindowBox.addView(alertTextDialog);
    }

    /**
     * 监听事件
     */
    private void onListener() {
        alertTextDialog.setOnNoClickItemAlertBoxListener(new AlertTextDialog.OnNoClickItemAlertBoxListener() {
            @Override
            public void onNoClickItemAlertBox() {
                dismiss();
            }
        });
    }

    /**‘
     * 关闭 popupWindow
     */
    private void dismiss(){
        if(onDismissListener!=null){
            onDismissListener.onDismiss();
        }
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }

    /**
     * 设置上下文
     * @param context 设置上下文
     */
    private void setContext(Context context) {
        this.context = context;
    }



    /**
     * 显示
     */
    public void show(){
        if(alertTextDialog!=null&&popupWindowUtil!=null){
            alertTextDialog.show();
            popupWindowUtil.showShare();
        }else {
            dismiss();
        }
    }


    /**
     * 设置-显示-文字
     * 对外暴露
     * @param alertText 显示-文字
     */
    public AlertPopupWindowUtil setAlertText(String alertText) {
        if(alertTextDialog!=null) {
            alertTextDialog.setAlertText(alertText);
        }
        return this;
    }
    /**
     * 设置-字体-大小
     * 对外暴露
     * @param textSizeDp 字体-大小
     */
    public AlertPopupWindowUtil setTextSizeDp(int textSizeDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTextSizeDp(textSizeDp);
        }
        return this;
    }
    /**
     * 设置-字体-颜色
     * 对外暴露
     * @param textColor 字体-颜色
     */
    public AlertPopupWindowUtil setTextColor(int textColor) {

        if(alertTextDialog!=null) {
            alertTextDialog.setTextColor(textColor);
        }
        return this;
    }
    /**
     * 设置padding-top
     * 对外暴露
     * @param paddingTopTextDp padding-top
     */
    public AlertPopupWindowUtil setPaddingTopTextDp(int paddingTopTextDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setPaddingTopTextDp(paddingTopTextDp);
        }
        return this;
    }

    /**
     * 设置padding-bottom
     * 对外暴露
     * @param paddingBottomTextDp padding-bottom
     */
    public AlertPopupWindowUtil setPaddingBottomTextDp(int paddingBottomTextDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setPaddingBottomTextDp(paddingBottomTextDp);
        }
        return this;
    }

    /**
     * 设置padding-left
     * 对外暴露
     * @param paddingLeftTextDp padding-left
     */
    public AlertPopupWindowUtil setPaddingLeftTextDp(int paddingLeftTextDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setPaddingLeftTextDp(paddingLeftTextDp);
        }
        return this;
    }

    /**
     * 设置padding-right
     * 对外暴露
     * @param paddingRightTextDp padding-right
     */
    public AlertPopupWindowUtil setPaddingRightTextDp(int paddingRightTextDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setPaddingRightTextDp(paddingRightTextDp);
        }
        return this;
    }

    /**
     * 设置-背景-最大的背景颜色
     * 对外暴露
     * @param bigBgColor 背景-最大的背景颜色
     */
    public AlertPopupWindowUtil setBigBgColor(int bigBgColor) {
        if(alertTextDialog!=null) {
            alertTextDialog.setBigBgColor(bigBgColor);
        }
        return this;
    }
    /**
     * 设置-锚点-指向的方向
     * 对外暴露
     * @param tipType 锚点-指向的方向
     */
    public AlertPopupWindowUtil setTipType(int tipType) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTipType(tipType);
        }
        return this;
    }
    /**
     * 设置-锚点的高度
     * 对外暴露
     * @param tipHeightDp 锚点的高度
     */
    public AlertPopupWindowUtil setTipHeightDp(int tipHeightDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTipHeightDp(tipHeightDp);
        }
        return this;
    }
    /**
     * 设置-锚点的宽度
     * 对外暴露
     * @param tipWidthDp 锚点的宽度
     */
    public AlertPopupWindowUtil setTipWidthDp(float tipWidthDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTipWidthDp(tipWidthDp);
        }
        return this;
    }
    /**
     * 设置-对准点的-X
     * 对外暴露
     * @param tipX 对准点的-X
     */
    public AlertPopupWindowUtil setTipX(float tipX) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTipX(tipX);
        }
        return this;
    }

    /**
     * 设置-对准点的-Y
     * 对外暴露
     * @param tipY 对准点的-Y
     */
    public AlertPopupWindowUtil setTipY(float tipY) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTipY(tipY);
        }
        return this;
    }
    /**
     * 设置-提示框-背景颜色
     * 对外暴露
     * @param textBoxBgColor 提示框-背景颜色
     */
    public AlertPopupWindowUtil setTextBoxBgColor(int textBoxBgColor) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTextBoxBgColor(textBoxBgColor);
        }
        return this;
    }

    /**
     * 设置-锚点-距离对准点的距离
     * 对外暴露
     * @param tipSpacingDp 锚点-距离对准点的距离
     */
    public AlertPopupWindowUtil setTipSpacingDp(int tipSpacingDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTipSpacingDp(tipSpacingDp);
        }
        return this;
    }


    /**
     * 设置-文字-对齐方式
     * 对外暴露
     * @param textAlignType 文字-对齐方式
     */
    public AlertPopupWindowUtil setTextAlignType(int textAlignType) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTextAlignType(textAlignType);
        }
        return this;
    }
    /**
     * 设置-文字-行间距
     * 对外暴露
     * @param textLineSpacing 文字-行间距
     */
    public AlertPopupWindowUtil setTextLineSpacing(int textLineSpacing) {
        if(alertTextDialog!=null) {
            alertTextDialog.setTextLineSpacing(textLineSpacing);
        }
        return this;
    }

    /**
     * 设置-提示框-圆角的-半径
     * 对外暴露
     * @param circleBorderRadiusDp 提示框-圆角的-半径
     */
    public AlertPopupWindowUtil setCircleBorderRadiusDp(int circleBorderRadiusDp) {
        if(alertTextDialog!=null) {
            alertTextDialog.setCircleBorderRadiusDp(circleBorderRadiusDp);
        }
        return this;
    }
    /**
     * 设置-提示框-是否占满屏幕宽度
     * 对外暴露
     * @param isAllScreenWidth 是否占满屏幕宽度
     */
    public AlertPopupWindowUtil setAllScreenWidth(boolean isAllScreenWidth) {
        if(alertTextDialog!=null) {
            alertTextDialog.setAllScreenWidth(isAllScreenWidth);
        }
        return this;
    }




    /**
     * 提示框的点击事件
     * @param onClickItemAlertBoxListener 监听接口
     */
    public AlertPopupWindowUtil setOnClickItemAlertBoxListener(OnClickItemAlertBoxListener onClickItemAlertBoxListener){
        if(alertTextDialog!=null) {
            alertTextDialog.setOnClickItemAlertBoxListener(onClickItemAlertBoxListener);
        }
        return this;
    }

    /**
     * 设置 关闭的回调
     * @param onDismissListener 回调接口
     */
    public AlertPopupWindowUtil setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    /**
     * 设置显示的回调
     * @param onShowListener 回调接口
     */
    public AlertPopupWindowUtil setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
        return this;
    }

}
