package behavior.behaviordemo.behaviors.qq1;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.lang.ref.WeakReference;

import behavior.behaviordemo.Application;
import behavior.behaviordemo.behaviors.ucbehavior.HeightUtils;
import behavior.behaviordemo.widget.reveal.callback.OnScrollChangeListener;

/**
 * Created by ly on 2017/3/5.
 *
 *  这个是用 offsetTopAndBottom 方法处理的 有的手机可能会有问题
 */

public class HeadBehavior extends CoordinatorLayout.Behavior<View> {

    private boolean contentScrollComplete = true;


    private Scroller mScroller;

    private ResizeRunnable resizeRunnable = new ResizeRunnable();

    private TaskRunnable taskRunnable = new TaskRunnable();

    private WeakReference<View> child;

    private WeakReference<View> mLastNestedScrollingChildRef;

    private Handler handler = new Handler();

    public HeadBehavior() {
    }

    public HeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(Application.getContext());
    }
//





    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        Log.i("123" ,"onInterceptTouchEvent    ");
        if(this.child == null)
            this.child = new WeakReference<View>(child);

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                if(parent.isPointInChildBounds(child,x,y)){
                    onStartNestedScroll(parent,child,child,child,0);
                    return true;
                }
                break;
        }
        return false;
    }

    private int mlastY;
    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        Log.i("123" ,"onTouchEvent    ");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("123" ,"onTouchEvent   DOWN  ");
                mlastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("123" ,"onTouchEvent   MOVE ");
                int dy = (int) (mlastY - ev.getY());
                onNestedPreScroll(parent,child,child,0,dy,new int[]{0,0});
                mlastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("123" ,"onTouchEvent   MOVE ");
                onStopNestedScroll(parent,child,child);
                break;
        }

        return true;
    }

    /** **************    add      end ************************  */



    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if(this.child == null)
            this.child = new WeakReference<View>(child);

        handler.removeCallbacks(resizeRunnable);
        handler.removeCallbacks(taskRunnable);

        mLastNestedScrollingChildRef = null;

        return ((nestedScrollAxes& CoordinatorLayout.SCROLL_AXIS_VERTICAL)!=0)&&contentScrollComplete;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        Log.i("123","nestpre top  bottom start --->>>    " +child.getTop() + "   "+child.getBottom()+ "      " +dy  );
       if(!contentScrollComplete) return;
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        /**
         *  TransY 的head滑动不响应 改成 offsetTopandBottom 方法
         */


        /**
         * 上滑
         */
        if(dy >0){

//            Log.i("123","dy > 0  " +child.getBottom() +"   "+ getSpaceHeight());
            if(child.getBottom() -dy >= getSpaceHeight()) {

                if (child.getTop() == 0) {
                    if (params.height != HeightUtils.getQQ1HeadHeight()) {
                        if (params.height - dy > HeightUtils.getQQ1HeadHeight()) {
                            params.height -= dy;
                        } else {
                            params.height = HeightUtils.getQQ1HeadHeight();
                        }
                        child.setLayoutParams(params);
                        child.setBottom(params.height);
                    } else {
                        child.offsetTopAndBottom(-dy);
                    }
                } else {
                    child.offsetTopAndBottom(-dy);
                }
            }else {

                if(child.getBottom() != getSpaceHeight()){
                    child.setBottom(getSpaceHeight());
                    child.setTop(getSpaceHeight() - getTotalHeight() );
                }

            }


        }else if(dy <0){


            if(child.getTop() - dy <= 0 ){
                child.offsetTopAndBottom(-dy);
            }else {
                if(child.getTop() <0|| child.getTop() >0 ){
                    child.offsetTopAndBottom( - child.getTop());
                }else {


                    if(child.getTop() == 0){
                        if (params.height - dy <= HeightUtils.getQQ1HeadMaxHeight()) {
                            params.height -= dy;
                            child.setLayoutParams(params);
                            child.setBottom(params.height);
                        }
                    }

                }
            }
        }

        if(l!=null) l.onScroll(child.getTop());
        consumed[1] = dy;

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
        }

        else if(Math.abs(child.getTop()) > (getTotalHeight() - getSpaceHeight())/2){
           taskRunnable = new TaskRunnable();
            taskRunnable.close();
        }else {
            taskRunnable  = new TaskRunnable();
            taskRunnable.open();
        }


        if(child.getBottom() <= getSpaceHeight() ) contentScrollComplete = false;

        mLastNestedScrollingChildRef = new WeakReference<>(target);

    }


    private int getSpaceHeight(){
        return HeightUtils.getTItleHeight();
    }

    private int getTotalHeight(){
        return HeightUtils.getQQ1HeadHeight();
    }


    public void childScrollComplete(boolean contentScrollComplete){
        this.contentScrollComplete = contentScrollComplete;
    }


    private class ResizeRunnable implements  Runnable{


        public void closeResize(){
            if(child == null || child.get() == null) return;
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
           float currentTop = child.get().getTop();
            mScroller.startScroll(0, (int) currentTop, 0,(int) (- getTotalHeight() +getSpaceHeight() - currentTop),500);
            handler.post(taskRunnable);
        }

        public void open(){
            float currentTop = child.get().getTop();
            mScroller.startScroll(0, (int) currentTop, 0, (int) -currentTop,500);
            handler.post(taskRunnable);
        }

        @Override
        public void run() {
            if(mScroller.computeScrollOffset()){
                child.get().setTop(mScroller.getCurrY());
                child.get().setBottom(mScroller.getCurrY()+ getTotalHeight());
                if(l!=null) l.onScroll(child.get().getTop());
                handler.post(taskRunnable);
            }
        }
    }


    private OnScrollChangeListener l;


    public void setOnScrollChangeListener(OnScrollChangeListener l){
        this.l  = l;
    }


}
