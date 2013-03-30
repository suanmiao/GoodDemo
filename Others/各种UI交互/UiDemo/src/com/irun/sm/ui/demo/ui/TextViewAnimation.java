package com.irun.sm.ui.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/***
 * @author huangsm
 * @date 2012-12-13
 * @email huangsanm@gmail.com
 * @desc 文字动画效果
 */
public class TextViewAnimation extends Activity
{
    
    private TextView mTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_animation);
        
        mTextView = ((TextView) findViewById(R.id.text_anim));
        mTextView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                TranslateAnimation t = new TranslateAnimation(0, 0, 200, -300);
                ScaleAnimation s = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f);
                AlphaAnimation a = new AlphaAnimation(1f, 0f);
                
                AnimationSet as = new AnimationSet(false);
                as.addAnimation(t);
                as.addAnimation(s);
                as.addAnimation(a);
                as.setDuration(5000);
                
                v.startAnimation(as);
            }
        });
    }
    
    //动画
    private void anim(){
        Animation anim = (Animation) AnimationUtils.loadAnimation(this, R.anim.text_anim);
        anim.setDuration(2500);
        mTextView.startAnimation(anim);
    }
}
