package com.example.weatherapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.weatherapp.R;

public class ClearableEditText extends androidx.appcompat.widget.AppCompatEditText {
    private GestureDetector gestureDetector;
    private int clearButtonHeight;
    private int clearButtonWidth;
    private Drawable clearButtonDrawable;
    private int clearButtonPadding;
    private int clearButtonMode;
    private boolean clearButtonVisibility;
    public static final int[] ClearableEditTextArray = {R.attr.clearButtonAlwaysVisible, R.attr.clearButtonDrawable, R.attr.clearButtonHeight, R.attr.clearButtonPadding, R.attr.clearButtonPosition, R.attr.clearButtonWidth};

    private class GestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        private GestureDetectorListener() {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (!(ClearableEditText.this.getCompoundDrawables()[0] == null && ClearableEditText.this.getCompoundDrawables()[2] == null)) {
                int width = ClearableEditText.this.clearButtonDrawable.getBounds().width();
                int height = ClearableEditText.this.clearButtonDrawable.getBounds().height();
                int paddingLeft = ClearableEditText.this.clearButtonMode == 0 ?
                        ClearableEditText.this.getPaddingLeft() :
                        (ClearableEditText.this.getWidth() - ClearableEditText.this.getPaddingRight()) - width;
                int height1 = (((ClearableEditText.this.getHeight() + ClearableEditText.this.getPaddingTop())
                        - ClearableEditText.this.getPaddingBottom()) - height) / 2;
                int i = width + paddingLeft;
                int i1 = height + height1;
                if (e.getX() > ((float) paddingLeft) && e.getX() < ((float) i) && e.getY() > ((float) height1) && e.getY() < ((float) i1)) {
                    ClearableEditText.this.clearText();
                }
            }
            return false;
        }

    }

    private class TextChangeListener implements TextWatcher {
        private TextChangeListener() {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean z;
            ClearableEditText clearableEditText;
            if(s == null || s.length() == 0){
                clearableEditText = ClearableEditText.this;
                z = false;
            }else if(ClearableEditText.this.isFocused()){
                clearableEditText = ClearableEditText.this;
                z = true;
            }else{
                return;
            }
            clearableEditText.setClearButtonVisible(z);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    public ClearableEditText(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.clearableEditTextStyle);
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        this.clearButtonVisibility = false;
        float f = context.getResources().getDisplayMetrics().density;
        TypedArray obtainedStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, ClearableEditTextArray, defStyleAttr, R.style.ClearableEditText);
        try {
            this.clearButtonMode = obtainedStyledAttributes.getInt(4, 2);
            if (!(this.clearButtonMode == 0 || this.clearButtonMode == 2)) {
                this.clearButtonMode = 2;
            }
            this.clearButtonVisibility = obtainedStyledAttributes.getBoolean(0, false);
            this.clearButtonPadding = obtainedStyledAttributes.getDimensionPixelSize(3, (int) (5.0f * f));
            this.clearButtonDrawable = obtainedStyledAttributes.getDrawable(1);
            if (this.clearButtonDrawable == null) {
                this.clearButtonDrawable = getResources().getDrawable(R.drawable.ic_clear);
            }
            this.clearButtonWidth = obtainedStyledAttributes.getDimensionPixelSize(5, -1);
            this.clearButtonHeight = obtainedStyledAttributes.getDimensionPixelSize(2, -1);
            if (this.clearButtonWidth == -1 || this.clearButtonHeight == -1) {
                this.clearButtonWidth = this.clearButtonHeight = (int) (f * 16.0f);
            }
            this.clearButtonDrawable.setBounds(0, 0, this.clearButtonWidth, this.clearButtonHeight);
            obtainedStyledAttributes.recycle();
            this.gestureDetector = new GestureDetector(context, new GestureDetectorListener());
            addTextChangedListener(new TextChangeListener());
            setClearButtonVisible(false);
        } catch (Throwable throwable) {
            obtainedStyledAttributes.recycle();
            throw throwable;
        }
    }

    public void setClearButtonVisible(boolean z) {
        int i;
        Drawable drawable;
        if(z || this.clearButtonVisibility){
            setCompoundDrawablePadding(this.clearButtonPadding);
            i = this.clearButtonMode;
            drawable = this.clearButtonDrawable;
        }else{
            i = this.clearButtonMode;
            drawable = null;
        }
        setCompoundDrawable(i, drawable);
    }

    public Drawable getClearButtonDrawable() {return this.clearButtonDrawable;}

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent e){
        boolean onTouchEvent = super.onTouchEvent(e);
        if(getCompoundDrawables()[this.clearButtonMode == 0 ? (char) 0 : 2] != null){
            this.gestureDetector.onTouchEvent(e);
        }
        return onTouchEvent;
    }

    public void setClearButtonAlwaysVisible(boolean z){
        this.clearButtonVisibility = z;
        setClearButtonVisible(true);
    }

    private void setCompoundDrawable(int i, Drawable drawable){
        if(i == 0){
            setCompoundDrawables(drawable, null, null, null);
        }else if(i == 2){
            setCompoundDrawables(null, null, drawable, null);
        }
    }

    public void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        setClearButtonVisible(z && getText().toString().length() != 0);
    }

    public void clearText() {
        setText("");
        setClearButtonVisible(false);
    }

    public void setClearButtonDrawable(Drawable drawable){
        if(drawable != null){
            this.clearButtonDrawable = drawable;
            if(getCompoundDrawables()[this.clearButtonMode == 0 ? (char) 0 : 2] != null){
                setClearButtonVisible(true);
                return;
            }
            return;
        }
        throw new NullPointerException("Drawable can not be null.");
    }

    public void setClearButtonPosition(int i){
        if(i == 2 || i == 0){
            setCompoundDrawable(i, getClearButtonDrawable());
            this.clearButtonMode = i;
            return;
        }
        throw new IllegalArgumentException("Position can only be one of: POSITION_START or POSITION_END.");
    }

}
