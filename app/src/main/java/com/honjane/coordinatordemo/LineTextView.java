package com.honjane.coordinatordemo;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 多行textView显示指定行数
 * Date: 2016-08-25
 *
 * @author honjane
 */

public class LineTextView extends LinearLayout {
    private TextView mContentView;
    private ImageView mArrowView;
    private int mMaxLine;
    private String mTextContent;

    public LineTextView(Context context) {
        this(context, null);
    }

    public LineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public LineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttr(attrs);
        bindListener();
    }

    private void initView() {
        setOrientation(VERTICAL);
        mContentView = new TextView(getContext());
        addView(mContentView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mArrowView = new ImageView(getContext());
        mArrowView.setImageResource(R.mipmap.ic_launcher);
        int padding = dip2px(getContext(), 5);
        mArrowView.setPadding(padding, padding, padding, padding);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        addView(mArrowView, params);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.LineTextStyle);
        int textColor = typeArray.getColor(R.styleable.LineTextStyle_textColor, Color.BLACK);
        int textSize = typeArray.getDimensionPixelSize(R.styleable.LineTextStyle_textSize, 12);
        mMaxLine = typeArray.getInt(R.styleable.LineTextStyle_line, 2);
        mTextContent = typeArray.getString(R.styleable.LineTextStyle_text);
        bindTextView(textSize, textColor, mMaxLine, mTextContent);
        typeArray.recycle();
    }

    private void bindTextView(int textColor, int textSize, final int maxLine, String textContent) {
        mContentView.setText(textContent);
        mContentView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mContentView.setTextColor(textColor);
        mContentView.setHeight(mContentView.getLineHeight() * maxLine);
        //保证绘制完成才能回去到getLineCount值
        post(new Runnable() {
            @Override
            public void run() {
                mArrowView.setVisibility(mContentView.getLineCount() > maxLine ? VISIBLE : GONE);
            }
        });
    }

    private void bindListener() {
        setOnClickListener(new OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                mContentView.clearAnimation();
                final int deltaValue;
                final int startValue = mContentView.getHeight();
                int duration = 300;
                //arrow 倒转动画
                if (isExpand) {
                    deltaValue = mContentView.getLineHeight() * mContentView.getLineCount() - startValue;
                    RotateAnimation expandAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    expandAnimation.setDuration(duration);
                    expandAnimation.setFillAfter(true);
                    mArrowView.startAnimation(expandAnimation);
                } else {
                    deltaValue = mContentView.getLineHeight() * mMaxLine - startValue;
                    RotateAnimation foldAnimation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    foldAnimation.setDuration(duration);
                    foldAnimation.setFillAfter(true);
                    mArrowView.startAnimation(foldAnimation);
                }
                //text 展开动画
                Animation textAnim = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation trans) {
                        super.applyTransformation(interpolatedTime, trans);
                        mContentView.setHeight((int) (startValue + deltaValue * interpolatedTime));
                    }
                };
                textAnim.setDuration(duration);
                mContentView.startAnimation(textAnim);
            }
        });
    }

    public String getText() {
        return mTextContent;
    }

    public void setText(CharSequence charSequence) {
        mContentView.setText(charSequence);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}