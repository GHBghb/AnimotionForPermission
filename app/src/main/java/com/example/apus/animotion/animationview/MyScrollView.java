package com.example.apus.animotion.animationview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 这个类的主要作用是重写了ScrollView，主要目的是，消除了在动画过程中若手动滑动ScrollView会破坏动画
 */
public class MyScrollView extends ScrollView
{

	public MyScrollView(Context context)
	{
		super(context);

	}

	public MyScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
	}
	
	public MyScrollView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event)   //这个方法如果返回 true 的话 两个手指移动，启动一个按下的手指的移动不能被传播出去。
	{
		super.onInterceptTouchEvent(event);
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)//这个方法如果 true 则整个Activity 的 onTouchEvent() 不会被系统回调
	{
		super.onTouchEvent(event);
		return false;
	}
		
}
