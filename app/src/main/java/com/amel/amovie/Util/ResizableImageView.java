package com.amel.amovie.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


import com.amel.amovie.R;

/**
 * Created by amel on 02/07/2017.
 */

public class ResizableImageView extends AppCompatImageView {
    private float mRatio = 1f;
    private double viewAspectRatio;
    private ViewAspectRatioMeasurer varm;

    public ResizableImageView(Context context) {
        super(context);
        varm = new ViewAspectRatioMeasurer(mRatio);
    }

    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        varm = new ViewAspectRatioMeasurer(mRatio);
    }

    public ResizableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
        varm = new ViewAspectRatioMeasurer(mRatio);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.ProportionalImageView);
            mRatio = styled.getFloat(R.styleable.ProportionalImageView_viewAspectRatio, 1f);
            styled.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        varm.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension( varm.getMeasuredWidth(), varm.getMeasuredHeight() );
    }
}
