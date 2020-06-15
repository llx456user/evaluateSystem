package com.tf.base.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {
    public static double formatDouble(double d) {

        // 如果需要四舍五入，可以使用RoundingMode.UP
        BigDecimal bg = new BigDecimal(d).setScale(5, RoundingMode.DOWN);

        return bg.doubleValue();
    }
    public static float formatFloat(float f) {

        // 如果需要四舍五入，可以使用RoundingMode.UP
        BigDecimal bg = new BigDecimal(f).setScale(5, RoundingMode.DOWN);

        return bg.floatValue();
    }
}
