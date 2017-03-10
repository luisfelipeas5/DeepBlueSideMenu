package br.com.top10.deepbluesidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

public class DeepBlueLayout extends RelativeLayout {
    private static final int MENU_INDEX = 0;
    private static final int CONTENT_INDEX = 1;

    public DeepBlueLayout(Context context) {
        super(context);
        init();
    }

    public DeepBlueLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DeepBlueLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }

        View menuView = getChildAt(MENU_INDEX);
        menuView.layout(0, 0, menuView.getMeasuredWidth() - l, b - t);

        View contentView = getChildAt(CONTENT_INDEX);
        contentView.layout(0, 0, r - l, b - t);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void switchMode() {
        View contentView = getChildAt(CONTENT_INDEX);
        Animation contentAnimation = getContentAnimation();
        contentView.setAnimation(contentAnimation);
        contentAnimation.start();
    }

    private Animation getContentAnimation() {
        Animation animation = new Animation() {
            View contentView = getChildAt(CONTENT_INDEX);
            float xInitial = 0;
            float xDelta = getChildAt(MENU_INDEX).getWidth();

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                float xNew = xInitial + (xDelta * interpolatedTime);
                contentView.setX(xNew);
            }
        };
        animation.setDuration(1000);
        return animation;
    }
}
