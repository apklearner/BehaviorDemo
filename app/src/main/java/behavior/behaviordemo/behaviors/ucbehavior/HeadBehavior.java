package behavior.behaviordemo.behaviors.ucbehavior;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import behavior.behaviordemo.Application;
import behavior.behaviordemo.R;

/**
 * Created by ly on 2017/3/5.
 */

public class HeadBehavior extends CoordinatorLayout.Behavior<View> {

    private Scroller scroller;

    private View child;

    public HeadBehavior() {
     super();
    }

    public HeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(Application.getContext());
    }

    private boolean isopen = true;

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if(this.child == null) this.child = child;
        return (nestedScrollAxes &CoordinatorLayout.SCROLL_AXIS_VERTICAL)!=0&&isopen;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if(canScroll(child,dy/2)) {
            child.setTranslationY(child.getTranslationY() - dy/2);
            consumed[1] = dy;

            Log.i("123","consume");
        }

    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);

        if(taskRunner!=null){
            child.removeCallbacks(taskRunner);
            taskRunner = null;
        }

        taskRunner = new TaskRunner(child);
        if(Math.abs(child.getTranslationY()) > getTotalRange()/2){
            Log.d("123", "onStopNestedScroll:           close");
            taskRunner.close();
        }else {
            Log.d("123", "onStopNestedScroll:           open");
            taskRunner.open();
        }
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
//        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        return isOpen();
    }

//    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
//        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
        return isOpen();
    }

    private int getTotalRange(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_head_offset);
    }

    private boolean canScroll(View child,float dy){

        float currentY = child.getTranslationY() - dy;
        if(currentY <=0 && currentY >=-getTotalRange()){
            return true;
        }

        return false;


    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }



    private TaskRunner taskRunner;

    private class TaskRunner implements  Runnable{

        View child;

        public TaskRunner(View child){
            this.child = child;
        }

        public void close(){
            int currentY = (int) child.getTranslationY();
            scroller.startScroll(0,currentY,0,-getTotalRange()-currentY,500);
            start();
            isopen = false;

        }

        public void open(){
            int currentY = (int) child.getTranslationY();
            scroller.startScroll(0,currentY,0, -currentY,500);
            start();
        }

        private void start(){
            handler.post(taskRunner);
        }

        @Override
        public void run() {
            if(scroller.computeScrollOffset()){
                child.setTranslationY(scroller.getCurrY());
                handler.post(taskRunner);
            }
        }
    }

    public void openData(){
        taskRunner = new TaskRunner(child);
        taskRunner.open();
        isopen = true;
    }


    public boolean isOpen(){
        return isopen;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    private Handler handler = new Handler();


}
