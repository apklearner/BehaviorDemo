package behavior.behaviordemo.behaviors.ucbehavior;

import behavior.behaviordemo.Application;
import behavior.behaviordemo.R;

/**
 * Created by ly on 2017/3/5.
 */

public class HeightUtils {

    public static int getHeaderOffset(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_head_offset);
    }

    public static int getHeaderHeight(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_head_height);
    }

    public static int getTItleHeight(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_title_height);

    }

    public static int getTabHeight(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.uc_tab_heght);

    }

    public static int getQQ1HeadHeight(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.qq1_head_height);
    }
    public static int getQQ1HeadMaxHeight(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.qq1_head_max_height);
    }

    public static int getQQ1IconHeight(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.qq1_head_icon_height);
    }

    public static int getQQ1BottomHeight(){
        return Application.getContext().getResources().getDimensionPixelSize(R.dimen.qq1_head_bottom_height);

    }

}
