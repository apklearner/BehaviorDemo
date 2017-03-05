package behavior.behaviordemo.activities;

import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import behavior.behaviordemo.R;
import behavior.behaviordemo.base.BaseActivity;
import butterknife.BindView;

/**
 * Created by ly on 2017/3/3.
 */

public class TestActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_test)
    ImageView rotateImage;
    @BindView(R.id.btn_test)
    Button btn;
    @BindView(R.id.tv_test)
    TextView title;

    private int degress;
    private boolean addMode = true;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        super.initView();
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:

                if(addMode){
                    if(degress <90){
                        degress += 10;
                    }else {
                        addMode = false;
                    }


                }else {

                    if(degress>  0) {
                        degress -=10;
                    }else {
                        addMode = true;
                    }


                }


                rotateImage.setPivotY(rotateImage.getHeight());
                rotateImage.setPivotX(rotateImage.getWidth()/2);
                Log.i("123","height-->>>" +rotateImage.getHeight());
//                rotateImage.setPivotY(0);
                rotateImage.animate().rotationX(degress).setInterpolator(new LinearInterpolator());
                title.setText(degress+"");
                break;
        }
    }


}
