package com.fuck.manspace.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Moushao on 2017/8/21.
 */

public class MarginUtils {
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
