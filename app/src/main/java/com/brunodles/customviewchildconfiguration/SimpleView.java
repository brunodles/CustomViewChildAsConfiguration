package com.brunodles.customviewchildconfiguration;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CompoundButton;

public class SimpleView extends AppCompatCheckBox
        implements CompoundButton.OnCheckedChangeListener {

    private int targetId;

    public SimpleView(Context context) {
        this(context, null);
    }

    public SimpleView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.checkboxStyle);
    }

    public SimpleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleView);
        targetId = typedArray.getResourceId(R.styleable.SimpleView_sv_targetId, -1);
        typedArray.recycle();

        this.setOnCheckedChangeListener(this);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        changeTargetViewVisibility(this.isChecked());
    }

    @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        changeTargetViewVisibility(isChecked);
    }

    private void changeTargetViewVisibility(boolean isChecked) {
        View targetView = null;
        int visibility = isChecked ? View.VISIBLE : View.GONE;

        // Find on Parent
        ViewParent parent = this.getParent();
        if (parent instanceof ViewGroup)
            targetView = ((ViewGroup) parent).findViewById(targetId);

        // Find on Activity
        if (targetView == null) {
            Context context = this.getContext();
            if (context instanceof Activity)
                targetView = ((Activity) context).findViewById(targetId);
        }

        // Change visibility
        if (targetView != null)
            targetView.setVisibility(visibility);
    }
}
