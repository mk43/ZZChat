package org.fitzeng.zzchat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.fitzeng.zzchat.R;


public class TitleBar extends RelativeLayout {
    private Context context;

    private Button btnLeft;
    private Button btnRight;
    private TextView tvTitle;

    private String leftText;
    private float leftTextSize;
    private int leftTextColor;
    private Drawable leftBackground;

    private String rightText;
    private float rightTextSize;
    private int rightTextColor;
    private Drawable rightBackground;

    private String titleText;
    private float titleTextSize;
    private int titleTextColor;
    private Drawable titleBackground;

    private titleBarClickListener listener;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        findAttrs(attrs);
        initViews();
        setTitleBarLayoutParams();
        setButtonClickListener();
    }

    public void setTitleText(String titleText) {
        tvTitle.setText(titleText);
    }

    /**
     * implement click events
     */
    public interface titleBarClickListener {
        void leftButtonClick();
        void rightButtonClick();
    }

    public void setTitleBarClickListetner(titleBarClickListener listetner) {
        this.listener = listetner;
    }

    private void setButtonClickListener() {
        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftButtonClick();
            }
        });

        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightButtonClick();
            }
        });
    }

    /**
     * Setting titlebar's layout
     */
    private void setTitleBarLayoutParams() {

        btnLeft.setAllCaps(false);
        btnRight.setAllCaps(false);

        LayoutParams btnLeftLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(btnLeft, btnLeftLayoutParams);

        LayoutParams btnRightLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnRightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(btnRight, btnRightLayoutParams);

        LayoutParams titleLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(tvTitle, titleLayoutParams);
    }

    /**
     * Init titlebar's widgets
     */
    private void initViews() {
        btnLeft = new Button(context);
        btnRight = new Button(context);
        tvTitle = new TextView(context);

        btnLeft.setText(leftText);
        btnLeft.setTextSize(leftTextSize);
        btnLeft.setTextColor(leftTextColor);
        btnLeft.setBackground(leftBackground);

        btnRight.setText(rightText);
        btnRight.setTextSize(rightTextSize);
        btnRight.setTextColor(rightTextColor);
        btnRight.setBackground(rightBackground);

        tvTitle.setText(titleText);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setBackground(titleBackground);

        tvTitle.setGravity(Gravity.CENTER);

        setBackgroundColor(0xFF01AAFF);
    }

    /**
     * @param attrs this is titlebar's attribute set for binding with widgets
     */
    private void findAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        leftText = typedArray.getString(R.styleable.TitleBar_leftText);
        leftTextSize = typedArray.getDimension(R.styleable.TitleBar_leftTextSize, 24);
        leftTextColor = typedArray.getColor(R.styleable.TitleBar_leftTextColor, 0);
        leftBackground = typedArray.getDrawable(R.styleable.TitleBar_leftBackground);

        titleText = typedArray.getString(R.styleable.TitleBar_titleText);
        titleTextSize = typedArray.getDimension(R.styleable.TitleBar_titleTextSize, 24);
        titleTextColor = typedArray.getColor(R.styleable.TitleBar_titleTextColor, 0);
        titleBackground = typedArray.getDrawable(R.styleable.TitleBar_titleBackground);

        rightText = typedArray.getString(R.styleable.TitleBar_rightText);
        rightTextSize = typedArray.getDimension(R.styleable.TitleBar_rightTextSize, 24);
        rightTextColor = typedArray.getColor(R.styleable.TitleBar_rightTextColor, 0);
        rightBackground = typedArray.getDrawable(R.styleable.TitleBar_rightBackground);

        typedArray.recycle();
    }
}
