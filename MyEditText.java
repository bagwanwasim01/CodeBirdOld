package com.example.wasim.compileit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * Created by wasim on 01-09-2019.
 */

public class MyEditText extends EditText {
    private Rect rect;
    private Paint paint;

    public MyEditText(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        rect=new Rect();
        paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int baseline = getBaseline();
        for (int i = 0; i < getLineCount(); i++) {
            canvas.drawText("" + (i + 1), rect.left, baseline, paint);
            baseline += getLineHeight();
        }
        super.onDraw(canvas);
    }
}
