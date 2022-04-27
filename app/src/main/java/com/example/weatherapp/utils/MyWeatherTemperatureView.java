package com.example.weatherapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.weatherapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyWeatherTemperatureView extends View {

    private LinearGradient linearGradient;
    private final int paddingStart;
    private final int spaceDim;
    private final int colorBlue;
    private final int colorGrey;
    private List<Integer> dayTemperature;
    private List<Integer> nightTemperature;
    private List<String> dayDegrees;
    private List<String> nightDegrees;
    private List<Float> dayStringXVal;
    private List<Float> dayStringYVal;
    private List<Float> nightStringXVal;
    private List<Float> nightStringYVal;
    private List<Float> dayXVal;
    private List<Float> dayYVal;
    private List<Float> nightYVal;
    private float maxDataVal;
    private float minDataVal;
    private float valDiff;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;
    private Paint paint5;
    private Rect rect;
    private Path path;

    public MyWeatherTemperatureView(Context context) {
       this(context, (AttributeSet) null);
    }

    public MyWeatherTemperatureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWeatherTemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dayTemperature = new ArrayList();
        this.nightTemperature = new ArrayList();
        this.dayDegrees = new ArrayList();
        this.nightDegrees = new ArrayList();
        this.dayStringXVal = new ArrayList();
        this.dayStringYVal = new ArrayList();
        this.nightStringXVal = new ArrayList();
        this.nightStringYVal = new ArrayList();
        this.dayXVal = new ArrayList();
        this.dayYVal = new ArrayList();
        this.nightYVal = new ArrayList();
        this.rect = new Rect();
        this.path = new Path();
        Resources resources = getResources();
        this.paddingStart = getPaddingStart();
        this.spaceDim = resources.getDimensionPixelSize(R.dimen.temperature_text_point_space);
        this.colorBlue = Color.parseColor("#26FFFFFF");
        this.colorGrey = Color.parseColor("#0DFFFFFF");
        paintInit();
    }

    private int getRectWidth(String str){
        this.paint2.getTextBounds(str, 0, str.length(), this.rect);
        return this.rect.width();
    }

    private void paintInit(){
        fillDummy();
        this.paint1 = new Paint();
        this.paint1.setAntiAlias(true);
        this.paint1.setColor(Color.parseColor("#FFFFFF"));
        Context context = getContext();
        Resources resources = context.getResources();
        int color = context.getColor(R.color.text_secondary_dark);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_text_size_body1);
        String string = getContext().getString(R.string.control_font_family_display);
        this.paint2 = new Paint();
        this.paint2.setAntiAlias(true);
        this.paint2.setColor(color);
        this.paint2.setTextSize((float)dimensionPixelSize);
        this.paint2.setTypeface(Typeface.create(string, Typeface.NORMAL));
        int a = ScreenHelper.convertToPixels(getContext(), 1.0f);
        this.paint3 = new Paint();
        this.paint3.setAntiAlias(true);
        float f = (float)a;
        this.paint3.setStrokeWidth(f);
        this.paint3.setColor(Color.parseColor("#80FFFFFF"));
        this.paint4 = new Paint();
        this.paint4.setAntiAlias(true);
        this.paint4.setStrokeWidth(f);
        this.paint4.setColor(Color.parseColor("#33FFFFFF"));
        this.paint5 = new Paint();
        this.paint5.setAntiAlias(true);
    }

    private void fillDummy() {
        ArrayList<Integer> dayTemperature = new ArrayList<>(9);
        dayTemperature.add(15);
        dayTemperature.add(28);
        dayTemperature.add(27);
        dayTemperature.add(32);
        dayTemperature.add(27);
        dayTemperature.add(21);
        ArrayList<Integer> nightTemperature = new ArrayList<>(9);
        nightTemperature.add(17);
        nightTemperature.add(8);
        nightTemperature.add(-2);
        nightTemperature.add(3);
        nightTemperature.add(12);
        nightTemperature.add(10);
        ArrayList dayDegrees = new ArrayList();
        for (Integer intValue : dayTemperature) {
            int intValue2 = intValue.intValue();
            dayDegrees.add(intValue2 + "°");
        }
        ArrayList nightDegrees = new ArrayList();
        for (Integer intValue3 : nightTemperature) {
            int intValue4 = intValue3.intValue();
            nightDegrees.add(intValue4 + "°");
        }
        setArrays(dayTemperature, nightTemperature, dayDegrees, nightDegrees);
    }

    public void setArrays(List<Integer> list, List<Integer> list2, List<String> list3, List<String> list4) {
        if (list != null && !list.isEmpty() && list2 != null && !list2.isEmpty()) {
            this.dayTemperature.clear();
            this.dayDegrees.clear();
            this.nightTemperature.clear();
            this.nightDegrees.clear();
            this.dayTemperature.addAll(list);
            this.dayDegrees.addAll(list3);
            this.nightTemperature.addAll(list2);
            this.nightDegrees.addAll(list4);
            this.maxDataVal = (float) ((Integer) Collections.max(this.dayTemperature)).intValue();
            this.minDataVal = (float) ((Integer) Collections.min(this.nightTemperature)).intValue();
            this.valDiff = this.maxDataVal - this.minDataVal;
            requestLayout();
        }
    }

    public void onDraw(Canvas canvas){
        for(int i = 0; i < this.dayDegrees.size(); i++){
            canvas.drawText(this.dayDegrees.get(i), this.dayStringXVal.get(i).floatValue(), this.dayStringYVal.get(i).floatValue(), this.paint2);
            canvas.drawText(this.nightDegrees.get(i), this.nightStringXVal.get(i).floatValue(), this.nightStringYVal.get(i).floatValue(), this.paint2);
        }
        for(int i2 = 1; i2 < this.dayXVal.size() - 1; i2++){
            canvas.drawCircle(this.dayXVal.get(i2).floatValue(), this.dayYVal.get(i2).floatValue(), (float) ScreenHelper.convertToPixels(getContext(), 1.5f), this.paint1);
            canvas.drawCircle(this.dayXVal.get(i2).floatValue(), this.nightYVal.get(i2).floatValue(), (float) ScreenHelper.convertToPixels(getContext(), 1.5f), this.paint1);
        }
        int size = this.dayXVal.size();
        int i3 = 0;
        while(i3 < size - 1){
            int i4 = i3 + 1;
            Canvas canvas1 = canvas;
            canvas1.drawLine(this.dayXVal.get(i3).floatValue(), this.dayYVal.get(i3).floatValue(), this.dayXVal.get(i4).floatValue(), this.dayYVal.get(i4).floatValue(), this.paint3);
            canvas1.drawLine(this.dayXVal.get(i3).floatValue(), this.nightYVal.get(i3).floatValue(), this.dayXVal.get(i4).floatValue(), this.nightYVal.get(i4).floatValue(), this.paint3);
            i3 = i4;
        }
        this.path.reset();
        this.path.moveTo(this.dayXVal.get(0).floatValue(), this.dayYVal.get(0).floatValue());
        for(int i5 = 1; i5 < this.dayXVal.size(); i5++){
            this.path.lineTo(this.dayXVal.get(i5).floatValue(), this.dayYVal.get(i5).floatValue());
        }
        for (int size1 = this.nightYVal.size() - 1; size1 >= 0; size1--) {
            this.path.lineTo(this.dayXVal.get(size1).floatValue(), this.nightYVal.get(size1).floatValue());
        }
        this.paint5.setShader(this.linearGradient);
        this.path.close();
        canvas.drawPath(this.path, this.paint5);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4){
        this.dayXVal.clear();
        this.dayYVal.clear();
        this.nightYVal.clear();
        this.dayStringXVal.clear();
        this.dayStringYVal.clear();
        this.nightStringXVal.clear();
        this.nightStringYVal.clear();
        int size = this.dayTemperature.size();
        int measuredWidth = (getMeasuredWidth() - (this.paddingStart * 2)) / size;
        int measuredHeight = getMeasuredHeight();
        Paint.FontMetrics fontMetrics = this.paint2.getFontMetrics();
        float f = fontMetrics.bottom - fontMetrics.top;
        float f1 = (float) measuredHeight;
        float f2 = (f1 - (f * 2.0f)) - ((float) (this.spaceDim * 2));
        for (int i5 = 0; i5 < size; i5++) {
            this.dayXVal.add(Float.valueOf(((float) (this.paddingStart + (measuredWidth * i5))) + (((float) measuredWidth) / 2.0f)));
            float intValue = this.maxDataVal - ((float) this.dayTemperature.get(i5).intValue());
            float f3 = this.valDiff;
            int i6 = this.spaceDim;
            this.dayYVal.add(Float.valueOf(((float) i6) + f + ((intValue / f3) * f2)));
            this.nightYVal.add(Float.valueOf(((f1 - f) - ((float) i6)) - (((((float) this.nightTemperature.get(i5).intValue()) - this.minDataVal) / f3) * f2)));
        }
        for (int i7 = 0; i7 < size; i7++) {
            float f4 = ((float) (this.paddingStart + (measuredWidth * i7))) + (((float) measuredWidth) / 2.0f);
            String str = this.dayDegrees.get(i7);
            this.dayStringXVal.add(Float.valueOf(f4 - (((float) getRectWidth(str.substring(0, str.length() - 1))) / 2.0f)));
            this.dayStringYVal.add(Float.valueOf((this.dayYVal.get(i7).floatValue() - ((float) this.spaceDim)) - fontMetrics.bottom));
        }
        for (int i8 = 0; i8 < size; i8++) {
            float f5 = ((float) (this.paddingStart + (measuredWidth * i8))) + (((float) measuredWidth) / 2.0f);
            String str2 = this.nightDegrees.get(i8);
            this.nightStringXVal.add(Float.valueOf(f5 - (((float) getRectWidth(str2.substring(0, str2.length() - 1))) / 2.0f)));
            this.nightStringYVal.add(Float.valueOf((this.nightYVal.get(i8).floatValue() + ((float) this.spaceDim)) - fontMetrics.top));
        }
        int measuredWidth2 = getMeasuredWidth();
        this.dayXVal.add(0, Float.valueOf(0.0f));
        this.dayXVal.add(Float.valueOf((float) measuredWidth2));
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, (float) i2, this.colorBlue, this.colorGrey, Shader.TileMode.MIRROR);
    }
}
