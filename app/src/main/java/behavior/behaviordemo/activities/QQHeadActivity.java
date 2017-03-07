package behavior.behaviordemo.activities;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import behavior.behaviordemo.R;
import behavior.behaviordemo.base.BaseActivity;
import behavior.behaviordemo.behaviors.qq1.HeadBehavior2;
import behavior.behaviordemo.behaviors.ucbehavior.HeightUtils;
import behavior.behaviordemo.widget.reveal.callback.OnScrollChangeListener;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ly on 2017/3/5.
 */

public class QQHeadActivity extends BaseActivity implements OnScrollChangeListener {

    private HeadBehavior2 headBehavior;

    @BindView(R.id.qq1_header)
    RelativeLayout relativeLayout;
    @BindView(R.id.nest)
    NestedScrollView scrollView;
    @BindView(R.id.icon)
    CircleImageView icon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_qq_1;
    }

    @Override
    protected void initView() {
        super.initView();
        icon.setPivotX(HeightUtils.getQQ1IconHeight()/2);
        icon.setPivotY(HeightUtils.getQQ1IconHeight());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) relativeLayout.getLayoutParams();
        headBehavior = (HeadBehavior2) params.getBehavior();
        headBehavior.setOnScrollChangeListener(this);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.i("123","scrollY--->>>" +scrollY);
                if(scrollY <=0){
                    headBehavior.childScrollComplete(true);
                }else {
                    headBehavior.childScrollComplete(false);
                }
            }


        });

    }

    @Override
    public void onScroll(float transY) {
            if(transY <0){

//                Log.i("123","startY--->>>        " +transY +"     "+ getStartTransY() +"     " + getScaleSize(transY));
//                if(Math.abs(transY) >= Math.abs(getStartTransY()) && Math.abs(transY) <= HeightUtils.getQQ1HeadHeight()){
//                    icon.animate().scaleX(getScaleSize(transY)).scaleY(getScaleSize(transY));

//                }
                float scale = 1- 0.5f*Math.abs(transY)/(HeightUtils.getQQ1HeadHeight() - HeightUtils.getTItleHeight());
                icon.animate().scaleX(scale).scaleY(scale).setDuration(0);
//                if(Math.abs(transY) >= HeightUtils.getQQ1HeadHeight() - HeightUtils.getTItleHeight()*2){
//                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                }else {
//                    toolbar.setBackgroundColor(Color.TRANSPARENT);
//                }

                int  alpha = (int) (255*Math.abs(transY)/(HeightUtils.getQQ1HeadHeight() - HeightUtils.getTItleHeight()));
                toolbar.setBackgroundColor(Color.argb(alpha,255,64,129));
//                Log.i("123","startY--->>>        " +transY +"     "+(HeightUtils.getQQ1HeadHeight() - HeightUtils.getTItleHeight()) );
//                Log.i("123","alpha--->>>        "+alpha);

            }
    }

    private int getStartTransY(){
        return -(HeightUtils.getQQ1HeadHeight() - HeightUtils.getQQ1BottomHeight() -HeightUtils.getTItleHeight() - HeightUtils.getQQ1IconHeight()/2);
    }


    public float getScaleSize(float transY){
      return Math.abs(transY)/(-2*getMiddleHeight()) + (3 - (HeightUtils.getQQ1HeadHeight()*2 - getMiddleHeight())/(-getMiddleHeight()))/4;
    }

    private int getMiddleHeight(){
        return HeightUtils.getQQ1BottomHeight() + HeightUtils.getQQ1IconHeight()/2 +HeightUtils.getTItleHeight();
    }

}
