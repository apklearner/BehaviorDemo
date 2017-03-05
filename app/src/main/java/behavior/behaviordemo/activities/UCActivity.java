package behavior.behaviordemo.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.widget.FrameLayout;

import behavior.behaviordemo.R;
import behavior.behaviordemo.base.BaseActivity;
import behavior.behaviordemo.behaviors.ucbehavior.HeadBehavior;
import butterknife.BindView;

/**
 * Created by ly on 2017/3/4.
 */

public class UCActivity extends BaseActivity {

    private HeadBehavior behavior;

    @BindView(R.id.uc_header)
    FrameLayout frameLayout;

    @BindView(R.id.nest)
    NestedScrollView nestedScrollView;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_uc;
    }

//    @OnClick(R.id.uc_header)
//    void btnClick(){
//        Toast.makeText(this,"Pic",Toast.LENGTH_SHORT).show();
//    }


    @Override
    protected void initView() {
        super.initView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) frameLayout.getLayoutParams();
        behavior = (HeadBehavior) params.getBehavior();
    }

    @Override
    public void onBackPressed() {
//        ((HeadBehavior)behavior).openData();
        if(!behavior.isOpen()){
            nestedScrollView.scrollTo(0,0);
            behavior.openData();
        }else {
            super.onBackPressed();
        }
    }
}
