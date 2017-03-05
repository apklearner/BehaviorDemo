package behavior.behaviordemo.activities;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import behavior.behaviordemo.R;
import behavior.behaviordemo.base.BaseActivity;
import behavior.behaviordemo.views.CircleGroup;
import butterknife.BindView;

/**
 * Created by ly on 2017/3/3.
 */

public class RotateActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_rotate)
    ImageView img;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.backview)
    View backview;
    @BindView(R.id.button_rotate)
    ImageView button;
    @BindView(R.id.circleGroup)
    CircleGroup circleGroup;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_rotate;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        appbar.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int total = appBarLayout.getTotalScrollRange();
        int degree = total/60;
        img.setPivotY(img.getHeight());
        img.setPivotX(img.getWidth()/2);
        img.animate().rotationX(Math.abs(verticalOffset)/degree).setInterpolator(new LinearInterpolator()).setDuration(0);


        if(button.getTop() < (toolbar.getBottom() - toolbar.getHeight())){
            if(buttonVisiable){
                showButton(false);
            }

        }else if(button.getTop() > toolbar.getBottom()){
            if(!buttonVisiable){
                showButton(true);
            }
        }


        if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() -5 ){
            if(circleGroup.getVisibility() != View.VISIBLE){
                circleGroup.showAnim();
                buttonVisiable = false;
            }
        }else if(verticalOffset == 0){
            if(!buttonVisiable){
                showButton(true);
            }
        }


    }


    private boolean buttonVisiable = true;

    private synchronized  void showButton(boolean value){
        if(value){

            button.animate().scaleX(1f).scaleY(1f).setInterpolator(new OvershootInterpolator());
            buttonVisiable = true;
            circleGroup.hideAnim();

        }else {
            button.animate().scaleX(0f).scaleY(0f).setInterpolator(new AccelerateDecelerateInterpolator());
            buttonVisiable = false;

            circleGroup.showAnim();
        }
    }

}
