package com.example.apus.animotion.animationview;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.apus.animotion.R;
public class SwitchOnAnimView extends FrameLayout {


    /**
     * 这个类的主要目的 是单选按钮布局
     * @param context
     */
    public SwitchOnAnimView(Context context) {
        this(context, null);
    }

    public SwitchOnAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.finger_switch_on_guide_layout,this,true);
    }

}