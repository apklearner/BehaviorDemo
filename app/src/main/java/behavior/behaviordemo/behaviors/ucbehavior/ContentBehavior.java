package behavior.behaviordemo.behaviors.ucbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import behavior.behaviordemo.Application;
import behavior.behaviordemo.R;
import behavior.behaviordemo.behaviors.base.HeaderScrollingViewBehavior;

/**
 * Created by ly on 2017/3/4.
 */

public  class ContentBehavior extends HeaderScrollingViewBehavior {

    public ContentBehavior(){}

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        return false;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        Log.i("123","content    changed");
        offsetChildAsNeeded(parent,child,dependency);
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        Log.i("123","child  " + child.getTranslationY());
//        if(Math.abs(child.getTranslationY())>= (getHeadTotalRange() - getTitleRange() - HeightUtils.getTabHeight())){
//            parent.requestDisallowInterceptTouchEvent(true);
//            return false;
//        }
//        parent.requestDisallowInterceptTouchEvent(false);
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public View findFirstDependency(List<View> views) {

        for(int i =0;i<views.size();i++){
            if(isDependOn(views.get(i))){
                return views.get(i);
            }
        }
        return null;
    }

    /**
     *  重要！！！ 滑动区域展示区域 “额外” 大小,默认是dependcy的高度，实际结果就是整个屏幕的区域大小展示
     *   final int height = availableHeight - header.getMeasuredHeight() + getScrollRange(header)
     *
     *   对于此例 联动并没有滑动到屏幕顶端 如果不设置 则有部分展示区域在屏幕之外（这里是不会滑回屏幕内的） 会显示不全！！！
     *   正确的做法是 减去 联动child顶端的留白 这样展示区域刚好在屏幕之内
     *
     */

    @Override
    public int getScrollRange(View v) {
        if(isDependOn(v)){


            return v.getMeasuredHeight() - HeightUtils.getTItleHeight() - HeightUtils.getTabHeight();
        }
        return super.getScrollRange(v);
    }

    private boolean isDependOn(View view){
      return  view!= null && view.getId() == R.id.uc_header;
  }


    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {

       child.setTranslationY(dependency.getTranslationY()*(getHeadTotalRange() - getTitleRange() - HeightUtils.getTabHeight())/getHeadOffsetRange());
    }

    private int getHeadOffsetRange(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_head_offset);
    }

    private int getHeadTotalRange(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_head_height);
    }

    private int getTitleRange(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_title_height);
    }


}
