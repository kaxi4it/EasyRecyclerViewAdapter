package com.guyj.utils;

/**
 * Created by guyj on 2019/1/8.
 * 描述:
 */

public class ClickUtils {
    private static final int MIN_DELAY_TIME = 300;  // 两次点击间隔不能少于300ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
