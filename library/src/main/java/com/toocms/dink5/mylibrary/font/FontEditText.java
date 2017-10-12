package com.toocms.dink5.mylibrary.font;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * Created by Administrator on 2016/11/7.
 */

public class FontEditText extends android.widget.EditText {

    public FontEditText(Context context) {
        this(context, null);
    }

    public FontEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        String type ="fonts/楷体.ttf";
        String fonts = attrs.getAttributeValue(null, "fonts");
        if (fonts != null) {
            switch (fonts) {
                case "a":
                    type = "fonts/华康少女字体.ttf";
                    break;
                case "b":
                    type = "fonts/方正启体简体.ttf";
                    break;
                case "c":
                    type = "fonts/明兰.ttf";
                    break;
                case "d":
                    type = "fonts/隶书.ttf";
                    break;
            }
        }
        Typeface typeface = getTypeface();
        int style = Typeface.NORMAL;
        if(typeface!=null){
            style = typeface.getStyle();
        }
        Typeface NewTypeface = Typeface.createFromAsset(context.getAssets(), type);
        setTypeface(NewTypeface,style);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }
}
