package behavior.behaviordemo.views;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import behavior.behaviordemo.widget.reveal.ViewAnimationUtils;

/**
 * Created by ly on 2017/3/4.
 */

public class CircleGroup extends RelativeLayout {

    private int maxWidth;
    private int maxHeight;


    public CircleGroup(Context context) {
        super(context);
    }

    public CircleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        maxWidth = getMeasuredWidth();
        maxHeight = getMeasuredHeight();
    }

    public void showAnim(){
        setVisibility(VISIBLE);
        Animator animator = ViewAnimationUtils.createCircularReveal(this,maxWidth/2,maxHeight/2,10,maxWidth/2);
        animator.setDuration(150).setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public void hideAnim(){
        Animator animator = ViewAnimationUtils.createCircularReveal(this,maxWidth/2,maxHeight/2,maxWidth/2,10);
        animator.setDuration(150).setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

}

