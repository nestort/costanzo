package com.trinidad.costanzo.util;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ResizableImageView extends android.support.v7.widget.AppCompatImageView {
    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizableImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d == null) {
            super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int imageHeight = d.getIntrinsicHeight();
        int imageWidth = d.getIntrinsicWidth();

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float imageRatio = 0.0F;
        if (imageHeight > 0) {
            imageRatio = imageWidth / imageHeight;
        }
        float sizeRatio = 0.0F;
        if (heightSize > 0) {
            sizeRatio = widthSize / heightSize;
        }

        int width;
        int height;
        if (imageRatio >= sizeRatio) {
            width = widthSize;
            height = width * imageHeight / imageWidth;
        } else {
            height = heightSize;
            width = height * imageWidth / imageHeight;
        }

        setMeasuredDimension(width, height);
    }
}