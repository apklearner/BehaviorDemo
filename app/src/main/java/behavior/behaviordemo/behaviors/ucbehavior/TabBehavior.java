package behavior.behaviordemo.behaviors.ucbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import behavior.behaviordemo.R;
import behavior.behaviordemo.behaviors.base.HeaderScrollingViewBehavior;

/**
 * Created by ly on 2017/3/5.
 */

public class TabBehavior extends HeaderScrollingViewBehavior {

    public TabBehavior() {
    }

    public TabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.uc_header;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChild(child,dependency);

        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public View findFirstDependency(List<View> views) {
        for(int i =0;i<views.size() ;i++){
            if(views.get(i).getId() == R.id.uc_header){
                return views.get(i);
            }
        }
        return null;
    }

    private void offsetChild(View child,View dependency){
        child.setTranslationY(dependency.getTranslationY()*(HeightUtils.getHeaderHeight() -   HeightUtils.getTItleHeight())/HeightUtils.getHeaderOffset());
    }
}
