package behavior.behaviordemo.behaviors;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ly on 2017/3/3.
 */

public class RotateImageBehavior extends AppBarLayout.Behavior {
    private int startBottom = -1;
    private int degreeValue ;

    public RotateImageBehavior(){
        super();
    }

    public RotateImageBehavior(Context context, AttributeSet attrs) {
        super(context,attrs);
    }



    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.i("123","scroll_start");
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        Log.i("123","scroll_pre");
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }



}
