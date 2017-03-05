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
}
