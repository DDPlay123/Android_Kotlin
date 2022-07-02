package com.tutorial.animationbuild

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageFrame = findViewById<ImageView>(R.id.image_frame)
        val imageTween = findViewById<ImageView>(R.id.image_tween)
        val frameStart = findViewById<Button>(R.id.frame_start)
        val frameStop = findViewById<Button>(R.id.frame_stop)
        val tweenAlpha = findViewById<Button>(R.id.tween_alpha)
        val tweenScale = findViewById<Button>(R.id.tween_scale)
        val tweenTranslate = findViewById<Button>(R.id.tween_translate)
        val tweenRotate = findViewById<Button>(R.id.tween_rotate)

        //逐格動畫
        imageFrame.setBackgroundResource(R.drawable.image_animation)
        frameStart.setOnClickListener {
            (imageFrame.background as AnimationDrawable).start()
        }
        frameStop.setOnClickListener {
            (imageFrame.background as AnimationDrawable).stop()
        }

        //捕間動畫
        tweenAlpha.setOnClickListener {
            val anim = AlphaAnimation(1.0f/*開始*/, 0.2f/*結束*/)
            anim.duration = 1000 //持續時間
            imageTween.startAnimation(anim)
        }
        tweenRotate.setOnClickListener {
            val anim = RotateAnimation(
                // 以左上角為基點
                0f, // 起始角
                360f, // 終止角
                // 加入XY座標
                RotateAnimation.RELATIVE_TO_SELF, // 相對於自身X坐標
                0.5f, // 0.5f表明是這個圖片的一半長度為x軸
                RotateAnimation.RELATIVE_TO_SELF, // 相對於自身Y坐標
                0.5f // 0.5f表明是這個圖片的一半長度為y軸
            )
            anim.duration = 1000
            imageTween.startAnimation(anim)
        }
        tweenScale.setOnClickListener {
            val anim = ScaleAnimation(
                1.0f,
                1.5f,
                1.0f,
                1.5f,
                // 加入XY座標
                RotateAnimation.RELATIVE_TO_SELF, // 相對於自身X坐標
                0.5f, // 0.5f表明是這個圖片的一半長度為x軸
                RotateAnimation.RELATIVE_TO_SELF, // 相對於自身Y坐標
                0.5f // 0.5f表明是這個圖片的一半長度為y軸
            )
            anim.duration = 1000
            imageTween.startAnimation(anim)
        }
        tweenTranslate.setOnClickListener {
            val anim = TranslateAnimation(
                // 往右上
                0f, //X 起點
                100f, //X 終點
                0f, //Y 起點
                -100f //Y 終點
            )
            anim.duration = 1000
            imageTween.startAnimation(anim)
        }
    }
}