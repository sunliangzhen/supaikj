package com.toocms.dink5.mylibrary.font;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by ROYGEM-0830-1 on 2017/10/11.
 */

public class CustomFontCompatDelegate implements LayoutInflaterFactory {
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        switch (name) {
            case "TextView":
                return new FontTextView(context, attrs);
            case "EditText":
                return new FontEditText(context, attrs);
        }
        return null;
    }
}
