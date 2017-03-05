package behavior.behaviordemo;

import android.content.Context;

/**
 * Created by ly on 2017/3/4.
 */

public class Application extends android.app.Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

   public static Context getContext(){
       return context;
   }

}
