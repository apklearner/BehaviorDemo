package behavior.behaviordemo.behaviors.qq1;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import behavior.behaviordemo.R;
import behavior.behaviordemo.behaviors.base.HeaderScrollingViewBehavior;
import behavior.behaviordemo.behaviors.ucbehavior.HeightUtils;

/**
 * Created by ly on 2017/3/5.
 */

public class ContentBehavior extends HeaderScrollingViewBehavior {


    public ContentBehavior() {
    }

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View findFirstDependency(List<View> views) {
        for (int i =0;i<views.size();i++){
            if(views.get(i).getId() == R.id.qq1_header){
                return views.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.qq1_header;
    }

    @Override
    public int getScrollRange(View v) {
        return super.getScrollRange(v) - HeightUtils.getTItleHeight();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChild(child,dependency);
        return super.onDependentViewChanged(parent, child, dependency);
    }


    private void offsetChild(View child,View dependency){
        child.setTranslationY(dependency.getTranslationY());
    }
}
