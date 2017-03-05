package behavior.behaviordemo.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ly on 2017/3/3.
 */

public class IntentHelper {

    public static void openAct(Context context,Class mclass){
        Intent intent = new Intent(context,mclass);
        context.startActivity(intent);
    }
}
