
## 带锚尖的popupWindow
## 带三角形的popupWindow
## 带指示的popupWindow


## 图片
![RUNOOB 图标](https://github.com/contradiction123/TestAlertPopupWindow/tree/master/image/S01102-14085413.png)
![RUNOOB 图标](https://github.com/contradiction123/TestAlertPopupWindow/tree/master/image/S01102-14085954.png)
![RUNOOB 图标](https://github.com/contradiction123/TestAlertPopupWindow/tree/master/image/S01102-14103372.png)
![RUNOOB 图标](https://github.com/contradiction123/TestAlertPopupWindow/tree/master/image/S01102-14103918.png)

## 版本--  com.github.contradiction123:TestAlertPopupWindow:1.0.0


## 使用
###第一步
-把它加到你的根目录里生成.gradle在存储库的末尾：
   ```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
   ```

###第二步
-添加引用
   ```java
	dependencies {
	        implementation 'com.github.contradiction123:TestAlertPopupWindow:1.0.0'
	}
   ```


## 版本
### [v1.0.0]版本----目前

- 目前只支持，弹出一句话，或者一段话-----不支持加入之定义xml(布局)
- 支持更改各种颜色，字体大小，锚尖的方向（上下）——不支持左右锚尖

### [v3.1.x]版本----未来
- 加入自定义布局（xml）
- 修复出现导航栏时，位置出现偏移

### bug
- 弹出这个popupWindow之后，再修改程序高度会出现高度的偏移，不会跟随变化（例如：虚拟导航栏的显示与隐藏）


## 使用

    |  方法   | 含义  |
    |  ----  | ----  |
    | show()  | 配置完毕后显示popupWindow |
    | setTipX()  | 对准点的X |
    | setTipY()  | 对准点的Y |
    | setAlertText()  | 提示的文字 |
    | setTextSizeDp()  | 字体大小(dp) |
    | setTextColor()  | 字体颜色 |
    | setPaddingTopTextDp()  | padding-top(dp) |
    | setPaddingBottomTextDp()  | padding-bottom(dp) |
    | setPaddingLeftTextDp()  | padding-left(dp) |
    | setPaddingRightTextDp()  | padding-right(dp) |
    | setBigBgColor()  | 底部背景颜色 |
    | setTipType()  | 锚点-朝向模式 |
    | setTipHeightDp()  | 锚点-高度 |
    | setTipWidthDp()  | 锚点-宽度 |
    | setTextBoxBgColor()  | 提示框的背景色 |
    | setTipSpacingDp()  | 锚点-距离对准点的距离(dp) |
    | setTextAlignType()  | 文字对齐方式 |
    | setTextLineSpacing()  | 文字行间距(dp) |
    | setCircleBorderRadiusDp()  | 提示框-圆角的-半径 |
    | setAllScreenWidth()  | 设置-提示框-是否占满屏幕宽度 |
    | setOnClickItemAlertBoxListener()  | 提示框的点击事件 |
    | setOnDismissListener()  | 设置 关闭的回调 |
    | setOnShowListener()  | 设置显示的回调 |


### 方法的参数介绍
- setTipType()
    默认：TIP_TYPE_ADAPT
    设置锚点的的朝向
    - TIP_TYPE_BOTTOM  ---------- 锚点朝下，但是当无法向下时会向上
    - TIP_TYPE_TOP     ---------- 锚点朝上，但是当无法向上时会向下
    - TIP_TYPE_ADAPT   ---------- 锚点适应，对齐点在屏幕上半部分，锚点向上，对齐点在屏幕下半部分，锚点向下

- setTextAlignType()
    默认：TEXT_ALIGN_TYPE_LEFT
    设置文字的对齐方式
    - TEXT_ALIGN_TYPE_LEFT      ---------- 字体--对齐方式-左对齐
    - TEXT_ALIGN_TYPE_RIGHT     ---------- 字体--对齐方式-右对齐
    - TEXT_ALIGN_TYPE_CENTER    ---------- 字体--对齐方式-居中对齐


## 使用示例

   ```java

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

   ```



