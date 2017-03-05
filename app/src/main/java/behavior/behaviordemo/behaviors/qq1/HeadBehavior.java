package behavior.behaviordemo.behaviors.qq1;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import java.lang.ref.WeakReference;

import behavior.behaviordemo.Application;
import behavior.behaviordemo.behaviors.ucbehavior.HeightUtils;
import behavior.behaviordemo.widget.reveal.callback.OnScrollChangeListener;

/**
 * Created by ly on 2017/3/5.
 */

public class HeadBehavior extends CoordinatorLayout.Behavior<View> {

    private boolean contentScrollComplete = true;


    private Scroller mScroller;

    private ResizeRunnable resizeRunnable = new ResizeRunnable();

    private TaskRunnable taskRunnable = new TaskRunnable();

    private WeakReference<View> child;

    private Handler handler = new Handler();

    public HeadBehavior() {
    }

    public HeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(Application.getContext());
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        if(this.child == null)
            this.child = new WeakReference<View>(child);

        this.child.get().removeCallbacks(resizeRunnable);
        this.child.get().removeCallbacks(taskRunnable);
        handler.removeCallbacks(resizeRunnable);
        handler.removeCallbacks(taskRunnable);
        return ((nestedScrollAxes& CoordinatorLayout.SCROLL_AXIS_VERTICAL)!=0)&&contentScrollComplete;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        Log.i("123","head transY  start--->>>" +child.getTranslationY());
       if(!contentScrollComplete) return;

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//        Log.i("123","tansY --->>" + child.getTranslationY() +"     " +params.height);
        if(child.getTranslationY() - dy <= -HeightUtils.getQQ1HeadHeight() +HeightUtils.getTItleHeight()){
            child.setTranslationY(-HeightUtils.getQQ1HeadHeight() +HeightUtils.getTItleHeight());
            return;
        }

        if(child.getTranslationY() - dy <= 0){

//            if(child.getTranslationY() - dy < - HeightUtils.getQQ1HeadHeight() + HeightUtils.getTItleHeight())
//                return;

            if(params.height  > HeightUtils.getQQ1HeadHeight()){
                params.height -= dy;
                child.setLayoutParams(params);
            }else {
                if(params.height <HeightUtils.getQQ1HeadHeight()){
                    params.height = HeightUtils.getQQ1HeadHeight();
                    child.setLayoutParams(params);
                }

                child.setTranslationY(child.getTranslationY()  -dy);
            }


        }else {

            if(child.getTranslationY() > 0 ){
                child.setTranslationY(0);
            }
            if(params.height - dy < HeightUtils.getQQ1HeadMaxHeight()){
                params.height -= dy;

            }else {
                params.height  = HeightUtils.getQQ1HeadMaxHeight();
            }
            child.setLayoutParams(params);


        }
        if(l!=null) l.onScroll(child.getTranslationY());

        consumed[1] = dy;
        Log.i("123","head transY  end--->>>" +child.getTranslationY());
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        resizeRunnable = null;
        taskRunnable = null;

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if(params.height > HeightUtils.getQQ1HeadHeight()){
            resizeRunnable = new ResizeRunnable();
            resizeRunnable.closeResize();
        }else if(Math.abs(child.getTranslationY()) > (HeightUtils.getQQ1HeadHeight() - HeightUtils.getTItleHeight())/2){
           taskRunnable = new TaskRunnable();
            taskRunnable.close();
        }else {
            taskRunnable  = new TaskRunnable();
            taskRunnable.open();
        }

    }


    public void childScrollComplete(boolean contentScrollComplete){
        this.contentScrollComplete = contentScrollComplete;
    }


    private class ResizeRunnable implements  Runnable{


        public void closeResize(){
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.get().getLayoutParams();
            int currentHeight = params.height;
            mScroller.startScroll(0,currentHeight,0,-currentHeight + HeightUtils.getQQ1HeadHeight(),500);
            handler.post(resizeRunnable);
        }


        @Override
        public void run() {
            if(mScroller.computeScrollOffset()){
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.get().getLayoutParams();
                params.height = mScroller.getCurrY();
                child.get().setLayoutParams(params);
                handler.post(resizeRunnable);
            }
        }
    }


    public class TaskRunnable implements Runnable{

        public void close(){
           float currentY = child.get().getTranslationY();
            mScroller.startScroll(0, (int) currentY, 0,(int) (- HeightUtils.getQQ1HeadHeight() +HeightUtils.getTItleHeight() - currentY),500);
            handler.post(taskRunnable);
        }

        public void open(){
            float currentY = child.get().getTranslationY();
            mScroller.startScroll(0, (int) currentY, 0, (int) -currentY,500);
            handler.post(taskRunnable);
        }

        @Override
        public void run() {
            if(mScroller.computeScrollOffset()){
                child.get().setTranslationY(mScroller.getCurrY());
                if(l!=null) l.onScroll(child.get().getTranslationY());
                handler.post(taskRunnable);
            }
        }
    }


    private OnScrollChangeListener l;


    public void setOnScrollChangeListener(OnScrollChangeListener l){
        this.l  = l;
    }

}
