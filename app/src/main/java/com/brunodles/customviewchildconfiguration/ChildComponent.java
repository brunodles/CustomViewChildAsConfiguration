package com.brunodles.customviewchildconfiguration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public final class ChildComponent extends View {

    private int targetId;

    public ChildComponent(Context context) {
        this(context, null);
    }

    public ChildComponent(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChildComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        setVisibility(View.GONE);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChildComponent);
        targetId = typedArray.getResourceId(R.styleable.ChildComponent_targetId, 0);
        typedArray.recycle();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(0, 0);
    }

    @Override protected void onDraw(Canvas canvas) {
    }

    protected int getTargetId() {
        return targetId;
    }
}
