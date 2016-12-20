package com.guyj.listener;

import android.view.View;

/**
 * 作　　者: guyj
 * 修改日期: 2016/12/20
 * 描　　述: 添加针对itemChild的长按点击事件
 * 备　　注:
 */
public interface EasyOnItemChildLongClickListener {
    boolean onLongClick(View view, int position);
}
