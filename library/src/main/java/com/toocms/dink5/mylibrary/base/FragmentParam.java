package com.toocms.dink5.mylibrary.base;

/**
 * Created by pc on 2017/3/15.
 */

public class FragmentParam {
    public BaseFragment from;
    public Class<?> cls;
    public Object data;
    public FragmentParam.TYPE type;
    public boolean addToBackStack;

    public FragmentParam() {
        this.type = FragmentParam.TYPE.ADD;
        this.addToBackStack = true;
    }

    public static enum TYPE {
        ADD,
        REPLACE;

        private TYPE() {
        }
    }
}
