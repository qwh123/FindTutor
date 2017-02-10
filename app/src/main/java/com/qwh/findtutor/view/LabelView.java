package com.qwh.findtutor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
/**
 * com.qwh.findtutor.view
 * 开发者 https://github.com/yanbober/AvatarLabelView
 * 时间: 17:21
 * 邮箱:2529509180@qq.com
 * 类作用：
 */
public class LabelView extends View {
    private LabelViewHelper mLabelViewHelper;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mLabelViewHelper = new LabelViewHelper(context, attrs);
    }

    public void setTextContent(String content) {
        mLabelViewHelper.setTextContent(content);
        invalidate();
    }

    public void setTextTitle(String title) {
        mLabelViewHelper.setTextTitle(title);
        invalidate();
    }

    public void setLabelBackGroundColor(int color) {
        mLabelViewHelper.setLabelBackGroundColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLabelViewHelper.drawLabel(this, canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int rotateViewWH= (int) (mLabelViewHelper.getBgTriangleHeight() * Math.sqrt(2));
        setMeasuredDimension(rotateViewWH, rotateViewWH);
    }
}
