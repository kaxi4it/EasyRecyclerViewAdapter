package com.guyj.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * 借鉴于
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SlideInBottomAnimation implements BaseAnimation {



    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }
}
