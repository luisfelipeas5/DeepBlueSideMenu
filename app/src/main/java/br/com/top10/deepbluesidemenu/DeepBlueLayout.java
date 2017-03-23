package br.com.top10.deepbluesidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

public class DeepBlueLayout extends RelativeLayout {
    private static final int INDEX_MENU = 0;
    private static final int INDEX_CONTENT = 1;
    private static final int ROTATION_ANGLE_OPEN = -45;

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

        View menuView = getChildAt(INDEX_MENU);
        menuView.layout(0, 0, menuView.getMeasuredWidth() - l, b - t);

        View contentView = getChildAt(INDEX_CONTENT);
        contentView.layout(0, 0, r - l, b - t);
    }

    public void switchMode() {
        View contentView = getChildAt(INDEX_CONTENT);
        AnimationSet contentAnimation = getContentAnimation();
        contentView.startAnimation(contentAnimation);
    }

    private AnimationSet getContentAnimation() {
        Animation rotationAnimation = new Animation() {
            View contentView = getChildAt(INDEX_CONTENT);
            float rotationYInitial = contentView.getRotationY();
            float rotationYFinal = getRotationYFinal();

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                float rotationYNew = rotationYInitial + ((rotationYFinal - rotationYInitial) * interpolatedTime);
                contentView.setRotationY(rotationYNew);
            }
        };
        rotationAnimation.setDuration(500);

        Animation translateAnimation = new Animation() {
            View contentView = getChildAt(INDEX_CONTENT);
            float xInitial = contentView.getX();
            float xFinal = getXDelta();

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                float xNew = xInitial + ((xFinal - xInitial) * interpolatedTime);
                contentView.setX(xNew);
            }
        };
        translateAnimation.setDuration(500);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotationAnimation);

        return animationSet;
    }

    public Mode getMode() {
        View contentView = getChildAt(INDEX_CONTENT);
        float contentViewRotationY = contentView.getRotationY();
        if(contentViewRotationY == 0) {
            return Mode.CLOSED;
        }
        return Mode.OPENED;
    }

    private enum Mode {
        OPENED, CLOSED
    }

    private float getXDelta() {
        if(getMode() == Mode.CLOSED) {
            View contentView = getChildAt(INDEX_CONTENT);

            int xFactor = contentView.getWidth();
            double cos = Math.cos(Math.toRadians(ROTATION_ANGLE_OPEN));
            double contentNewWidth = Math.abs(xFactor * cos);

            View menuView = getChildAt(INDEX_MENU);
            int menuWidth = menuView.getMeasuredWidth();

            return (float) (menuWidth - Math.abs(contentNewWidth - contentView.getWidth()));
        }
        return 0;
    }

    private float getRotationYFinal() {
        if(getMode() == Mode.CLOSED) {
            return ROTATION_ANGLE_OPEN;
        }
        return 0;
    }
}
