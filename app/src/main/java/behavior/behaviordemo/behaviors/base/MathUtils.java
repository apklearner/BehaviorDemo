package behavior.behaviordemo.behaviors.base;

/**
 * Created by ly on 2017/3/4.
 */

 class MathUtils {
    static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    public static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
}
