package cs.android.extensions.view

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import cs.android.view.adapter.AnimatorAdapter

fun <T : View> T.visible(): Boolean = visibility == VISIBLE

fun <T : View> T.shown(): Boolean = isShown

fun <T : View> T.visible(visible: Boolean): T {
    visibility = if (visible) VISIBLE else GONE
    return this
}

fun <T : View> T.hide(): T {
    visibility = GONE
    return this;
}

fun <T : View> T.show(): T {
    visibility = VISIBLE
    return this;
}

fun <T : View> T.fadeIn(): ViewPropertyAnimator? {
    if (visible()) return null
    show()
    alpha = 0f
    return animate().alpha(1.0f).setDuration(150)
            .setInterpolator(AccelerateDecelerateInterpolator()).setListener(null)
}

fun <T : View> T.fadeOut(): ViewPropertyAnimator? {
    return if (!visible() || alpha == 0f) null else animate().alpha(0f).setDuration(300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setListener(object : AnimatorAdapter() {
                override fun onAnimationEnd(animator: Animator?) {
                    hide()
                }
            })
}

fun <T : View> T.onClick(onClick: (view: T) -> Unit): T {
    setOnClickListener { onClick(this) }
    return this
}

fun <T : View> T.width(function: (Int) -> Unit) {
    if (width == 0)
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                function(width)
            }
        })
    else function(width)
}

fun <T : View> T.height(function: (Int) -> Unit) {
    if (height == 0)
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                function(height)
            }
        })
    else function(height)
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    params?.setMargins(left, top, right, bottom)
    layoutParams = params
}

fun View.bottomMargin(bottom: Int) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    params?.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottom)
    layoutParams = params
}

fun View.topMargin(top: Int) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    params?.setMargins(params.leftMargin, top, params.rightMargin, params.bottomMargin)
    layoutParams = params
}

fun View.createBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    Canvas(bitmap).apply {
        background?.draw(this) ?: this.drawColor(Color.WHITE)
        draw(this)
    }
    return bitmap
}

fun View.removeFromSuperview() = apply {
    (parent as? ViewGroup)?.removeView(this)
}