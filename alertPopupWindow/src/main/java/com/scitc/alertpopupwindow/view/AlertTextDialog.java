package com.scitc.alertpopupwindow.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.scitc.alertpopupwindow.interfaces.OnClickItemAlertBoxListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AlertTextDialog extends View {

    /**
     * 锚点--方位
     */
    private int tipType;
    /**
     * 锚点-下面
     * 基本朝向下，当无法向下时再向上
     * 锚点向下
     */
    public static int TIP_TYPE_BOTTOM =0;
    /**
     * 锚点-上面
     * 基本朝向上，当无法向上时再向下
     * 锚点向上
     */
    public static int TIP_TYPE_TOP =1;

    /**
     * 锚点-适应
     * 对齐点在屏幕上半部分，锚点向上
     * 对齐点在屏幕下半部分，锚点向下
     */
    public static int TIP_TYPE_ADAPT=2;


    /**
     * 字体--对齐方式
     */
    private int textAlignType;
    /**
     * 字体--对齐方式-左对齐
     */
    public static int TEXT_ALIGN_TYPE_LEFT =0;
    /**
     * 字体--对齐方式-右对齐
     */
    public static int TEXT_ALIGN_TYPE_RIGHT =1;
    /**
     * 字体--对齐方式-居中对齐
     */
    public static int TEXT_ALIGN_TYPE_CENTER =2;


    /**
     * 用户设置--提示的文字
     */
    private String alertText;
    /**
     * 处理过的文字列表
     */
    private List<String> alertTextList;
    /**
     * 文字-字体大小
     */
    private int textSizeDp;
    /**
     * 文字-字体颜色
     */
    private int textColor;
    /**
     * 文字-字体行高
     */
    private int textLineSpacing;

    /**
     * 提示框的padding-top
     */
    private int paddingTopTextDp;
    /**
     * 提示框的padding-bottom
     */
    private int paddingBottomTextDp;
    /**
     * 提示框的padding-left
     */
    private int paddingLeftTextDp;
    /**
     * 提示框的padding-right
     */
    private int paddingRightTextDp;


    /**
     * 整个背景颜色
     */
    private int bigBgColor;
    /**
     * 提示框的背景颜色
     */
    private int textBoxBgColor;

    /**
     * 锚点的高度
     */
    private float tipHeightDp;
    /**
     * 锚点的宽度
     */
    private float tipWidthDp;
    /**
     * 锚点距离设置点的高度
     */
    private int tipSpacingDp;

    /**
     * 字体的画笔
     */
    private Paint textPaint;
    /**
     * 背景的画笔
     */
    private Paint bgPaint;


    /**
     * 锚点对准点的-X
     */
    private float tipX;

    /**
     * 锚点对准点的-Y
     */
    private float tipY;

    /**
     * 提示框圆角半径
     */
    private int circleBorderRadiusDp;

    /**
     * 是否全屏幕
     */
    private boolean isAllScreenWidth;
    /**
     * 提示框的位置信息
     */
    private RectF drawTextBoxRectF;

    /**
     * 提示框的点击事件
     */
    public OnClickItemAlertBoxListener onClickItemAlertBoxListener;
    /**
     * 没有点击到-提示框的点击事件
     */
    private OnNoClickItemAlertBoxListener onNoClickItemAlertBoxListener;

    private Context context;


    /**
     * 没有点击到提示框   接口
     */
    public interface OnNoClickItemAlertBoxListener{
        void onNoClickItemAlertBox();
    }

    public AlertTextDialog(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public AlertTextDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public AlertTextDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }



    /**
     * 初始化数据
     */
    private void init(){
        textSizeDp=dp2px(context,12);
        textColor= Color.WHITE;
        paddingTopTextDp =dp2px(context,6);
        paddingBottomTextDp =dp2px(context,6);
        paddingLeftTextDp =dp2px(context,12);
        paddingRightTextDp =dp2px(context,12);
        bigBgColor =Color.parseColor("#30000000");
        textBoxBgColor =Color.parseColor("#60000000");
        tipHeightDp =dp2px(context,10);
        tipWidthDp =dp2px(context,20);
        tipSpacingDp =dp2px(context,6);
        textLineSpacing =dp2px(context,6);
        circleBorderRadiusDp =0;
        isAllScreenWidth =true;
        tipType=TIP_TYPE_ADAPT;
        textAlignType= TEXT_ALIGN_TYPE_LEFT;


        initPaint();
    }


    /**
     * 初始化 画笔
     */
    private void initPaint(){

        textPaint=new TextPaint();
        textPaint.setTextSize(textSizeDp);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);

        bgPaint=new TextPaint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(TextUtils.isEmpty(alertText)){
            return;
        }
        Log.e("TAG", "===onDraw: Y:"+tipY );
        //绘制最大的背景
        drawBigBg(canvas);
        //将提示文字进行格式化处理
        this.alertTextList=stringsToList(textPaint,alertText);
        //获取提示框的位置
        drawTextBoxRectF=getTextBoxRectF();
        //绘制提示框
        drawTextBox(canvas,drawTextBoxRectF);
        //绘制锚点
        drawTip(canvas,drawTextBoxRectF);
        //绘制文字
        drawText(canvas,drawTextBoxRectF);

    }

    /**
     * 绘制锚点
     * @param canvas canvas
     * @param drawTextBoxRectF 提示框的位置信息
     */
    private void drawTip(Canvas canvas, RectF drawTextBoxRectF) {
        Path path = new Path();
        RectF rectF1=new RectF();
        RectF rectF2;
        RectF rectF3;

        //获取锚点的x
        float tempTipX=correctTipX(drawTextBoxRectF);

        //这里的判断的锚点位置是经过重新计算的位置
        //不是设置的方位
        //计算的方法-----getIsUpTipBox
        if(tipType== TIP_TYPE_BOTTOM){
            rectF1.top=tipY- tipSpacingDp;
            rectF1.left=tempTipX;
            rectF2= getTipRectF2TOP(rectF1,drawTextBoxRectF);
            rectF3= getTipRectF3TOP(rectF2);
        }else {
            rectF1.top=tipY+ tipSpacingDp;
            rectF1.left=tempTipX;
            rectF2= getTipRectF2Bottom(rectF1,drawTextBoxRectF);
            rectF3= getTipRectF3Bottom(rectF2);
        }

        path.moveTo(rectF3.left, rectF3.top);
        path.lineTo(rectF1.left, rectF1.top);
        path.lineTo(rectF2.left, rectF2.top);
        path.close();
        bgPaint.setColor(textBoxBgColor);
        canvas.drawPath(path, bgPaint);
    }

    /**
     * 绘制文字
     * @param canvas canvas
     * @param drawTextBoxRectF 提示框的位置信息
     */
    private void drawText(Canvas canvas,RectF drawTextBoxRectF) {
        float textWidth=0;

        for (String s : alertTextList) {
            if(textPaint.measureText(s)>textWidth){
                textWidth=textPaint.measureText(s);
            }
        }

        for (int i = 0; i < alertTextList.size(); i++) {
            float x;
            float y;
            if(isAllScreenWidth) {
                if (textAlignType == TEXT_ALIGN_TYPE_LEFT) {
                    x = drawTextBoxRectF.left + paddingLeftTextDp;
                } else if (textAlignType == TEXT_ALIGN_TYPE_RIGHT) {
                    x = drawTextBoxRectF.right - paddingRightTextDp - textPaint.measureText(alertTextList.get(i));
                } else {
                    x = (drawTextBoxRectF.right - drawTextBoxRectF.left) / 2.0F - textPaint.measureText(alertTextList.get(i)) / 2.0F;
                }
            }else {
                x = drawTextBoxRectF.left + paddingLeftTextDp;
            }
            y=drawTextBoxRectF.top + paddingTopTextDp+(textSizeDp*(i+1))+ textLineSpacing *i;
            canvas.drawText(alertTextList.get(i),x,y,textPaint);
        }
    }

    /**
     * 绘制提示框
     * @param canvas canvas
     * @param drawTextBoxRectF 提示框的位置信息
     */
    private void drawTextBox(Canvas canvas,RectF drawTextBoxRectF) {
        bgPaint.setColor(textBoxBgColor);
        if(circleBorderRadiusDp >=(drawTextBoxRectF.bottom - drawTextBoxRectF.top) / 2) {
            canvas.drawRoundRect(drawTextBoxRectF, (drawTextBoxRectF.bottom - drawTextBoxRectF.top) / 2, (drawTextBoxRectF.bottom - drawTextBoxRectF.top) / 2, bgPaint);
        }else if(circleBorderRadiusDp==0){
            canvas.drawRect(drawTextBoxRectF,bgPaint);
        }else {
            canvas.drawRoundRect(drawTextBoxRectF, circleBorderRadiusDp, circleBorderRadiusDp, bgPaint);
        }
    }


    /**
     * 绘制背景
     * @param canvas canvas
     */
    private void drawBigBg(Canvas canvas) {
        RectF rectF=new RectF();
        rectF.top=0;
        rectF.left=0;
        rectF.right=getWidth();
        rectF.bottom=getBottom();
        bgPaint.setColor(bigBgColor);
        canvas.drawRect(rectF,bgPaint);
    }








    /**
     * 防止设置了圆角，锚点还在最边缘
     * 锚点与 提示框之间出现间隔
     * @param drawTextBoxRectF 提示框的位置信息
     * @return 新的tipX
     */
    private float correctTipX(RectF drawTextBoxRectF){
        float tempTipX;
        float radius;
        if(circleBorderRadiusDp>=(drawTextBoxRectF.bottom - drawTextBoxRectF.top) / 2){
            radius = (drawTextBoxRectF.bottom - drawTextBoxRectF.top) / 2;
        }else if(circleBorderRadiusDp ==0) {
            radius=0;
        }else {
            radius=circleBorderRadiusDp;
        }
        if (tipX + radius + tipWidthDp / 2.0F > getWidth()) {
            tempTipX = getWidth() - radius - tipWidthDp / 2.0F;
        } else {
            if (tipX - radius - tipWidthDp / 2.0F < 0) {
                tempTipX = radius + tipWidthDp * 0.5F;
            } else {
                tempTipX = tipX;
            }
        }
        return tempTipX;
    }



    /**
     * 获取提示框的位置
     * @return RectF 提示框的位置信息
     */
    private RectF getTextBoxRectF(){
        RectF rectF=new RectF();
        float textWidth=0;
        //先确定最大的文字宽度
        for (String s : alertTextList) {
            if(textPaint.measureText(s)>textWidth){
                textWidth=textPaint.measureText(s);
            }
        }

        //确定提示框的宽度-高度
        float boxWidth=textWidth+ paddingLeftTextDp + paddingRightTextDp;
        float boxHeight=textSizeDp*alertTextList.size()+(textLineSpacing *alertTextList.size()- textLineSpacing)+ paddingBottomTextDp + paddingTopTextDp;

        //设置锚点在提示框的上面还是下面
        boxHeight=getBoxHeightAndSettingTipType(boxHeight);

        //设置提示框的位置信息
        rectF.top=getBoxRectFTop(boxHeight);
        rectF.bottom=rectF.top+boxHeight;

        if(isAllScreenWidth) {
            rectF.left = 0;
            rectF.right = getWidth();
        }else {
            rectF.left = getBoxRectFLeft(boxWidth);
            rectF.right = rectF.left + boxWidth;
        }
        return rectF;
    }


    /**
     * 获取提示框left的位置信息
     * @param boxWidth 提示框的宽度
     * @return 提示框的left
     */
    private float getBoxRectFLeft(float boxWidth){
        float left=Math.max(tipX-boxWidth/2.0F,0);
        if(left+boxWidth>getWidth()){
            left=left-(left+boxWidth-getWidth());
        }
        return left;
    }

    /**
     * 获取提示框的top位置信息
     * @param boxHeight 提示框的高度
     * @return 提示框的top
     */
    private float getBoxRectFTop(float boxHeight){
        float top;
        if(tipType== TIP_TYPE_BOTTOM){
            top=tipY- tipSpacingDp -tipHeightDp-boxHeight;
        }else {
            top=tipY+ tipSpacingDp +tipHeightDp;
        }
        return top;
    }




    /**
     * 设置锚点在提示框的上面还是下面
     * @param boxHeight 提示框的高度
     */
    private float getBoxHeightAndSettingTipType(float boxHeight){
        float newBoxHeight=boxHeight;
        //当提示框的高度 小于对准点的上半部分和下半部分
        if(boxHeight+tipSpacingDp<tipY&&boxHeight+tipSpacingDp<getHeight()-tipY) {
            tipSettingTypeAdapt(boxHeight);
        }else if(boxHeight+tipSpacingDp<tipY){
            // 小于对准点的上半部分
            tipType = TIP_TYPE_BOTTOM;
        }else if(boxHeight+tipSpacingDp<getHeight()-tipY){
            // 小于对准点的下半部分
            tipType = TIP_TYPE_TOP;
        }else {
            //提示框的高度 大于上半部分 和大于下半部分
            newBoxHeight=judgeTipTypeResetBoxHeightAndShowTextNumber();
        }
        return newBoxHeight;
    }

    /**
     * 设置锚点的提示框的上面还是下面
     * 重置提示框的高度
     * 重置显示的文字多少
     * 删除一些文字--减少提示框高度
     */
    private float judgeTipTypeResetBoxHeightAndShowTextNumber(){
        float newBoxHeight;
        List<String> stringList;


        if(tipY>getHeight()-tipY){
            //上面空位置大于下面空位置

            tipType = TIP_TYPE_BOTTOM;
            float haveHeight=tipY-tipSpacingDp*2-tipHeightDp;
            stringList=resetAlertList(haveHeight);
        }else {
            //下面空位置大于上面空位置
            tipType = TIP_TYPE_TOP;
            float haveHeight=(getHeight()-tipY)-tipSpacingDp*2-tipHeightDp;
            stringList=resetAlertList(haveHeight);
        }
        alertTextList.clear();
        alertTextList.addAll(stringList);
        newBoxHeight=textSizeDp*alertTextList.size()+(textLineSpacing *alertTextList.size()- textLineSpacing)+ paddingBottomTextDp + paddingTopTextDp;
        return newBoxHeight;
    }

    /**
     * 重置显示的文字
     * @param limitHeight 限制文字的高度
     * @return 处理好的list
     */
    private List<String> resetAlertList(float limitHeight){
        List<String> stringList=new ArrayList<>();
        limitHeight=limitHeight-paddingBottomTextDp-paddingTopTextDp;

        int boxHeight=0;
        for (int i = 0; i < alertTextList.size(); i++) {
            int height=textSizeDp;
            if(i!=0){
                height+=textLineSpacing;
            }
            if(boxHeight+height>limitHeight){
                stringList.remove(stringList.size()-1);
                stringList.add("...");
                break;
            }else {
                boxHeight+=height;
                stringList.add(alertTextList.get(i));
            }
        }
        return stringList;
    }

    /**
     * 设置锚点的提示框的上面还是下面
     * 这个是提示框的高度小于tipY的上半部分
     * 这个是提示框的高度小于tipY的下半部分
     * @param boxHeight 提示框的高度
     */
    private void tipSettingTypeAdapt(float boxHeight){
        if (tipType == TIP_TYPE_ADAPT) {
            if (tipY > getHeight() / 2.0F) {
                tipType = TIP_TYPE_BOTTOM;
            } else {
                tipType = TIP_TYPE_TOP;
            }
        }

        if (tipType == TIP_TYPE_BOTTOM) {
            if (tipY - 1.2 * boxHeight < 0) {
                tipType = TIP_TYPE_TOP;
            }
        } else if (tipType == TIP_TYPE_TOP) {
            if (tipY + 1.2 * boxHeight > getHeight()) {
                tipType = TIP_TYPE_BOTTOM;
            }
        }
    }


    /**
     * 将字符串 转换为 屏幕能放下的 list
     * @param textPaint 字的画笔
     * @param textString 需要处理的字符串
     * @return 处理好的字符串list
     */
    private List<String> stringsToList(Paint textPaint,String textString){
        List<String> stringList=getStringList(textString);
        List<String> stringListNew=new ArrayList<>();
        int pintWidth=getWidth()-paddingLeftTextDp-paddingRightTextDp;
        for (int i = 0; i < stringList.size(); i++) {
            String string= stringList.get(i);
            float textWidth=textPaint.measureText(string);
            while (textWidth>=pintWidth){
                List<String> strings=getNewStringAndOldString(string,textPaint);
                stringListNew.add(strings.get(0));
                string=strings.get(1);
                textWidth=textPaint.measureText(string);
            }
            stringListNew.add(string);
        }
        return stringListNew;
    }

    /**
     * 将字符串 转换成
     * list.get(0)---屏幕能放的下的字符串
     * list.get(1)---还剩下的字符串
     * @param textString 需要处理的字符串
     * @param textPaint 字的画笔
     * @return 新字符串和剩下的字符串组成的list
     */
    private List<String> getNewStringAndOldString(String textString,Paint textPaint){
        String newString="";
        int pintWidth=getWidth()-paddingLeftTextDp-paddingRightTextDp;

        while (textPaint.measureText(newString)+10<pintWidth){
            if(textString.length()<1){
                break;
            }else {
                String string=textString.substring(0,1);
                if(textPaint.measureText(newString+string)+10>pintWidth){
                    break;
                }
                newString+=string;
                textString=textString.substring(1);
            }
        }
        List<String> stringList=new ArrayList<>();
        stringList.add(newString);
        stringList.add(textString);
        return stringList;
    }


    /**
     * 将字符串以 \n  换行符 分割开
     * @param textString 字符串
     * @return 切割好的list
     */
    private List<String> getStringList(String textString){
        return new ArrayList<>(Arrays.asList(textString.split("\n")));
    }




    /**
     * 锚点在下时 获取最左边的点
     * @param rectF2 第二个点
     * @return 第三个点
     */
    private RectF getTipRectF3Bottom(RectF rectF2){
        RectF rectF3=new RectF();
        rectF3.top=rectF2.top;
        rectF3.left=rectF2.left-tipWidthDp;
        return rectF3;
    }

    /**
     *
     * 锚点在下时 获取最右边的点
     * @param rectF1 第一个点
     * @param drawTextBoxRectF 提示框的大小
     * @return 第二个点
     */
    private RectF getTipRectF2Bottom(RectF rectF1,RectF drawTextBoxRectF){
        RectF rectF2=new RectF();
        float x=rectF1.left+tipWidthDp/2.0F;
        rectF2.top= drawTextBoxRectF.top;
        rectF2.left=x;
        return rectF2;
    }

    /**
     * 锚点在上时 获取最左边的点
     * @param rectF2 第二个点
     * @return 第三个点
     */
    private RectF getTipRectF3TOP(RectF rectF2){
        RectF rectF3=new RectF();
        rectF3.top=rectF2.top;
        rectF3.left=rectF2.left-tipWidthDp;
        return rectF3;
    }

    /**
     *
     * 锚点在上时 获取最右边的点
     * @param rectF1 第一个点
     * @param drawTextBoxRectF 提示框的大小
     * @return 第二个点
     */
    private RectF getTipRectF2TOP(RectF rectF1,RectF drawTextBoxRectF){
        RectF rectF2=new RectF();
        float x=rectF1.left+tipWidthDp/2.0F;
        rectF2.top= drawTextBoxRectF.bottom;
        rectF2.left=x;
        return rectF2;
    }


    /**
     * 显示
     */
    public void show(){
        initPaint();
        invalidate();
    }

    /**
     * 设置-显示-文字
     * 对外暴露
     * @param alertText 显示-文字
     */
    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }

    /**
     * 设置-字体-大小
     * 对外暴露
     * @param textSizeDp 字体-大小
     */
    public void setTextSizeDp(int textSizeDp) {
        this.textSizeDp = dp2px(context,textSizeDp);
    }

    /**
     * 设置-字体-颜色
     * 对外暴露
     * @param textColor 字体-颜色
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设置padding-top
     * 对外暴露
     * @param paddingTopTextDp padding-top
     */
    public void setPaddingTopTextDp(int paddingTopTextDp) {
        this.paddingTopTextDp = dp2px(context,paddingTopTextDp);
    }

    /**
     * 设置padding-bottom
     * 对外暴露
     * @param paddingBottomTextDp padding-bottom
     */
    public void setPaddingBottomTextDp(int paddingBottomTextDp) {
        this.paddingBottomTextDp = dp2px(context,paddingBottomTextDp);
    }

    /**
     * 设置padding-left
     * 对外暴露
     * @param paddingLeftTextDp padding-left
     */
    public void setPaddingLeftTextDp(int paddingLeftTextDp) {
        this.paddingLeftTextDp = dp2px(context,paddingLeftTextDp);
    }

    /**
     * 设置padding-right
     * 对外暴露
     * @param paddingRightTextDp padding-right
     */
    public void setPaddingRightTextDp(int paddingRightTextDp) {
        this.paddingRightTextDp = dp2px(context,paddingRightTextDp);
    }

    /**
     * 设置-背景-最大的背景颜色
     * 对外暴露
     * @param bigBgColor 背景-最大的背景颜色
     */
    public void setBigBgColor(int bigBgColor) {
        this.bigBgColor = bigBgColor;
    }

    /**
     * 设置-锚点-指向的方向
     * 对外暴露
     * @param tipType 锚点-指向的方向
     */
    public void setTipType(int tipType) {
        this.tipType = tipType;
    }

    /**
     * 设置-锚点的宽度
     * 对外暴露
     * @param tipHeightDp 锚点的宽度
     */
    public void setTipHeightDp(float tipHeightDp) {
        this.tipHeightDp = dp2px(context,tipHeightDp);
    }

    /**
     * 设置-锚点的宽度
     * 对外暴露
     * @param tipWidthDp 锚点的宽度
     */
    public void setTipWidthDp(float tipWidthDp) {
        this.tipWidthDp = dp2px(context,tipWidthDp);
    }
    /**
     * 设置-对准点的-X
     * 对外暴露
     * @param tipX 对准点的-X
     */
    public void setTipX(float tipX) {
        this.tipX = tipX;
    }

    /**
     * 设置-对准点的-Y
     * 对外暴露
     * @param tipY 对准点的-Y
     */
    public void setTipY(float tipY) {
        this.tipY = tipY+getActionBarHeight();
    }

    /**
     * 设置-提示框-背景颜色
     * 对外暴露
     * @param textBoxBgColor 提示框-背景颜色
     */
    public void setTextBoxBgColor(int textBoxBgColor) {
        this.textBoxBgColor = textBoxBgColor;
    }


    /**
     * 设置-锚点-距离对准点的距离
     * 对外暴露
     * @param tipSpacingDp 锚点-距离对准点的距离
     */
    public void setTipSpacingDp(int tipSpacingDp) {
        this.tipSpacingDp = dp2px(context, tipSpacingDp);
    }

    /**
     * 设置-文字-对齐方式
     * 对外暴露
     * @param textAlignType 文字-对齐方式
     */
    public void setTextAlignType(int textAlignType) {
        this.textAlignType = textAlignType;
    }

    /**
     * 设置-文字-行间距
     * 对外暴露
     * @param textLineSpacing 文字-行间距
     */
    public void setTextLineSpacing(int textLineSpacing) {
        this.textLineSpacing = textLineSpacing;
    }




    /**
     * 设置-提示框-圆角的-半径
     * 对外暴露
     * @param circleBorderRadiusDp 提示框-圆角的-半径
     */
    public void setCircleBorderRadiusDp(int circleBorderRadiusDp) {
        this.circleBorderRadiusDp = dp2px(context,circleBorderRadiusDp);
    }

    /**
     * 设置-提示框-是否占满屏幕宽度
     * 对外暴露
     * @param isAllScreenWidth 是否占满屏幕宽度
     */
    public void setAllScreenWidth(boolean isAllScreenWidth) {
        this.isAllScreenWidth = isAllScreenWidth;
    }


    /**
     * 获取 ActionBar 的高度
     * @return ActionBar 的高度
     */
    private int getActionBarHeight() {
        if(context==null){
            return 0;
        }

        ActionBar actionBar=((AppCompatActivity)context).getSupportActionBar();
        int actionBarHeight=0;
        if(actionBar!=null){
            actionBarHeight=actionBar.getHeight();
        }
        return actionBarHeight;
    }

    /**
     * dp转px
     * 私有
     * @param context 上下文
     * @param dpValue 转换值--dp
     * @return 返回转换完成的值--px
     */
    private int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }


    /**
     * 判断这个点是不是在提示框内
     * @param event 触摸点
     * @return 是/不是
     */
    private boolean judgeXyInAlertBox(MotionEvent event){
        return event.getX() > drawTextBoxRectF.left && event.getX() < drawTextBoxRectF.right && event.getY() > drawTextBoxRectF.top && event.getY() < drawTextBoxRectF.bottom;
    }


    /**
     * 提示框的点击事件
     * @param onClickItemAlertBoxListener 监听接口
     */
    public void setOnClickItemAlertBoxListener(OnClickItemAlertBoxListener onClickItemAlertBoxListener) {
        this.onClickItemAlertBoxListener = onClickItemAlertBoxListener;
    }
    /**
     * 没有点击到提示框
     * @param onNoClickItemAlertBoxListener 监听接口
     */
    public void setOnNoClickItemAlertBoxListener(OnNoClickItemAlertBoxListener onNoClickItemAlertBoxListener) {
        this.onNoClickItemAlertBoxListener = onNoClickItemAlertBoxListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if(!judgeXyInAlertBox(event)){
                    if(onNoClickItemAlertBoxListener!=null){
                        onNoClickItemAlertBoxListener.onNoClickItemAlertBox();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(judgeXyInAlertBox(event)){
                    if(onClickItemAlertBoxListener!=null){
                        onClickItemAlertBoxListener.onClickItemAlertBox();
                    }
                }else {
                    if(onNoClickItemAlertBoxListener!=null){
                        onNoClickItemAlertBoxListener.onNoClickItemAlertBox();
                    }
                }
                break;
        }
        return true;
    }

}
