package behavior.behaviordemo;

import android.view.View;
import android.widget.Button;

import behavior.behaviordemo.activities.RotateActivity;
import behavior.behaviordemo.activities.TestActivity;
import behavior.behaviordemo.activities.UCActivity;
import behavior.behaviordemo.base.BaseActivity;
import behavior.behaviordemo.utils.IntentHelper;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_test)
    Button btn1;
    @BindView(R.id.btn_rotate)
    Button btn2;
    @BindView(R.id.btn_uc)
    Button btn3;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(){
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test:
                IntentHelper.openAct(this, TestActivity.class);
                break;
            case R.id.btn_rotate:
                IntentHelper.openAct(this, RotateActivity.class);
                break;
            case R.id.btn_uc:
                IntentHelper.openAct(this, UCActivity.class);
                break;
        }
    }
}
