package com.example.apus.animotion.animationview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.apus.animotion.R;
import com.example.apus.animotion.baseanimator.BaseAnimatorListener;
import com.example.apus.animotion.utils.ViewUtil;


public class ListItemView extends FrameLayout {
    private ImageView listItemImg;//app列表
    private ImageView fingerImg;//手
    private ImageView mCirclePtImgv;//单选框里的圆球
    private SwitchOnAnimView mSwitchOnAnimView;//整个单选框
    /** 开关中间的圆圈View需要移动的距离 */
    private float mCirclePtMoveDistance;
    private float mFingerMoveRightDiatance;//手右移动的距离
    private static final int CIRCLE_PT_ANIM_DURATION = 500;
    private float mMoveUpDistance=-300f;
    private float mFingerMoveDownDiatance=-60f;
    private float curTranslationX;
    private boolean isStopAnim = false;
    private Handler mHandler = new Handler();
    public ListItemView(Context context) {
        this(context, null);
    }
    public ListItemView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        LayoutInflater.from(context).inflate(R.layout.sample_list_item_view, this,true);
        initView();
    }

    //控件初始化
    public void initView(){
        listItemImg=(ImageView)findViewById(R.id.appListImg);
        fingerImg=(ImageView)findViewById(R.id.fingerImg);
        mSwitchOnAnimView=(SwitchOnAnimView)findViewById(R.id.switch_on_anim_view);
        mCirclePtImgv=(ImageView)mSwitchOnAnimView.findViewById(R.id.switch_anim_circle_point);
        fingerImg.bringToFront();
        // 下面距离要根据UI布局来确定
        mCirclePtMoveDistance = ViewUtil.dp2px(getContext(), 17.5f);
        mFingerMoveRightDiatance =ViewUtil.dp2px(getContext(),158f);
        mMoveUpDistance=ViewUtil.dp2px(getContext(),-100f);
        mFingerMoveDownDiatance=ViewUtil.dp2px(getContext(),5f);
    }
    public void startAnim() {
        isStopAnim = false;
        startAnimator();
    }
    /**
     * 动画开始
     */

    public void startAnimator() {
        curTranslationX = listItemImg.getTranslationX();
        ObjectAnimator listItemUpAnimator = ObjectAnimator.ofFloat(listItemImg, "translationY", curTranslationX, mMoveUpDistance);//app列表上移动画
        ObjectAnimator fingerUpAnimator = ObjectAnimator.ofFloat(fingerImg, "translationY", curTranslationX, mMoveUpDistance);//手上移动画
        ObjectAnimator mSwitchUpAnimator = ObjectAnimator.ofFloat(mSwitchOnAnimView, "translationY", curTranslationX, mMoveUpDistance);//单选框动画
        ObjectAnimator fingerRightAnimator = ObjectAnimator.ofFloat(fingerImg, "translationX", curTranslationX, mFingerMoveRightDiatance);//手右移动画
        ObjectAnimator fingerDownAnimator = ObjectAnimator.ofFloat(fingerImg, "translationY", mMoveUpDistance, mFingerMoveDownDiatance);//手下移动画
        AnimatorSet bouncer = new AnimatorSet();
        bouncer.playTogether(listItemUpAnimator, fingerUpAnimator);
        bouncer.playTogether(listItemUpAnimator, mSwitchUpAnimator);
        bouncer.play(fingerDownAnimator).after(listItemUpAnimator);
        bouncer.playTogether(fingerDownAnimator, fingerRightAnimator);
        bouncer.setStartDelay(800);
        bouncer.setDuration(800);
        bouncer.start();
        listItemUpAnimator.addListener(new BaseAnimatorListener(){
            @Override
            public void onAnimationStart(Animator animator) {
                // 启动动画之前先恢复初始状态
                listItemImg.setTranslationX(0);
                mCirclePtImgv.setTranslationX(0);
                fingerImg.setTranslationX(0);
                mSwitchOnAnimView.setTranslationX(0);
                mCirclePtImgv.setBackgroundResource(R.drawable.switch_off_circle_point);
            }
        });

        fingerDownAnimator.addListener(new BaseAnimatorListener() {
            //手下移完成之后开始单选框动画
            @Override
            public void onAnimationEnd(Animator animator) {
                if (mSwitchOnAnimView == null)
                    return;
                startCirclePointAnim();
            }
        });
    }

    /**
     * 手滑到单选框后，单选框动画
     */
    private void startCirclePointAnim() {
        if (mCirclePtImgv == null) {
            return;
        }
        mCirclePtImgv.setBackgroundResource(R.drawable.switch_on_circle_point);
        ObjectAnimator circlePtAnim = ObjectAnimator.ofFloat(mCirclePtImgv, "translationX", 0, mCirclePtMoveDistance);
        circlePtAnim.setDuration(CIRCLE_PT_ANIM_DURATION);
        circlePtAnim.setStartDelay(200);
        circlePtAnim.start();
        circlePtAnim.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //循环播放
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isStopAnim) {
                            return;
                        }
                        startAnim();
                    }
                }, 800);
            }
        });
    }
    /*
    停止动画
     */
    public void stopAnim() {
        isStopAnim = true;
    }
}
