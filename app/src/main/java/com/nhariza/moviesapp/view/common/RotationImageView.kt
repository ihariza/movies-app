package com.nhariza.moviesapp.view.common

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LifecycleObserver
import com.nhariza.moviesapp.R


class RotationImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) :
    AppCompatImageView(context, attrs, defStyle), LifecycleObserver {

    private val rotationObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "rotation", 1f, 360f).apply {
            interpolator = LinearInterpolator()
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
        }
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0)
        try {
            val animate = typedArray.getBoolean(R.styleable.CircleView_animate, false)
            if (animate) {
                startAnimation()
            }
        } finally {
            typedArray.recycle()
        }
    }

    fun startAnimation() {
        visible()
        rotationObjectAnimator.start()
    }

    fun stopAnimation() {
        gone()
        rotationObjectAnimator.cancel()
    }
}