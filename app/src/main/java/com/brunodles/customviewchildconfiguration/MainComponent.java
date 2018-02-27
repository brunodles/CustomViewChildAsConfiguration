package com.brunodles.customviewchildconfiguration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import java.util.HashSet;
import java.util.Set;

public class MainComponent extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

    private final CheckBox checkbox;
    private final Set<Integer> targetViews = new HashSet<>();

    public MainComponent(Context context) {
        this(context, null, 0);
    }

    public MainComponent(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_main_component, this, true);
        checkbox = findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);

        initAttrs(context, attrs);
    }

    @SuppressLint("ResourceType") @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.view_main_component, this, true);
        checkbox = findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MainComponent);

        int targetId = typedArray.getResourceId(R.styleable.MainComponent_targetId, 0);
        boolean targetVisibility = typedArray.getBoolean(
                R.styleable.MainComponent_checked, false);

        checkbox.setChecked(targetVisibility);

        targetViews.add(targetId);
        typedArray.recycle();
    }

    @Nullable
    private View findView(int targetId) {
        Context context = getContext();
        ViewParent parent = getParent();
        if (parent != null && parent instanceof ViewGroup) {
            View view = ((ViewGroup) parent).findViewById(targetId);
            if (view != null)
                return view;
        }
        if (context instanceof Activity)
            return ((Activity) context).findViewById(targetId);
        return null;
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ChildComponent) {
                int targetId = ((ChildComponent) childAt).getTargetId();
                targetViews.add(targetId);
            }
        }
        changeTargetViewsVisibility(checkbox.isChecked());
    }

    @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        changeTargetViewsVisibility(isChecked);
    }

    private void changeTargetViewsVisibility(boolean isChecked) {
        int visibility = isChecked ? View.VISIBLE : View.GONE;
        for (Integer id : targetViews) {
            View view = findView(id);
            if (view != null)
                view.setVisibility(visibility);
        }
    }
}
