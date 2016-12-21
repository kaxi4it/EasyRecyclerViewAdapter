package com.guyj.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * 作　　者: guyj
 * 修改日期: 2016/12/20
 * 描　　述:
 * 备　　注:
 */
public interface EasyOnItemChildTouchListener {
    boolean onTouch(View v, MotionEvent event, int position);
}
