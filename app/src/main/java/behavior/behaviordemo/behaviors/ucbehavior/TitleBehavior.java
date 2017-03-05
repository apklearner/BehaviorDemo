package behavior.behaviordemo.behaviors.ucbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import behavior.behaviordemo.Application;
import behavior.behaviordemo.R;

/**
 * Created by ly on 2017/3/5.
 */

public class TitleBehavior extends CoordinatorLayout.Behavior<View> {

  public   TitleBehavior(){}


    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.uc_header;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetTitle(child,dependency);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        params.topMargin = -getTitleHeight();
        parent.onLayoutChild(child,layoutDirection);
        return true;
    }

    private int getTitleHeight(){
           return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_title_height);
    }

    private void offsetTitle(View child,View dependency){
        child.setTranslationY(-dependency.getTranslationY()*HeightUtils.getTItleHeight()/HeightUtils.getHeaderOffset());
    }


}
