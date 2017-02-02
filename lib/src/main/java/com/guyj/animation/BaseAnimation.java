package com.guyj.animation;

import android.animation.Animator;
import android.view.View;

/**
 * 借鉴于
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public interface  BaseAnimation {

    Animator[] getAnimators(View view);

}
