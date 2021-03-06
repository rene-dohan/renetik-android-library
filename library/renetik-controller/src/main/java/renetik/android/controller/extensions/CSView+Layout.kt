package renetik.android.controller.extensions

import android.view.View
import renetik.android.controller.base.CSView
import renetik.android.extensions.dpToPixel
import renetik.android.java.extensions.isSet
import renetik.android.java.math.CSPoint

val CSView<*>.layoutWidth get() = this.view.layoutParams.width
val CSView<*>.layoutHeight get() = this.view.layoutParams.height
val CSView<*>.left get() = this.view.left
val CSView<*>.top get() = this.view.top
val CSView<*>.center get() = CSPoint(left + width / 2, top + height / 2)
val CSView<*>.width get() = view.width
val CSView<*>.height get() = view.height
val CSView<*>.locationOnScreen: CSPoint
    get() {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return CSPoint(location[0], location[1])
    }

fun CSView<*>.setPercentAspectWidth(viewId: Int, percent: Int) =
    setPercentAspectWidth(findView<View>(viewId)!!, percent)

fun CSView<*>.setPercentAspectWidth(view: View, percent: Int) {
    val onePercent = displayWidth / 100.toFloat()
    val wantedWidth = onePercent * percent

    val scalingFactor = wantedWidth / view.layoutParams.width
    val scaledHeight = (view.layoutParams.height * scalingFactor).toInt()

    val layoutParams = view.layoutParams
    layoutParams.width = wantedWidth.toInt()
    layoutParams.height = scaledHeight

    view.layoutParams = layoutParams
}

fun CSView<*>.setPercentWidth(viewId: Int, percent: Int, minimal: Int, maximal: Int) =
    setPercentWidth(findView<View>(viewId)!!, percent, minimal, maximal)

fun CSView<*>.setPercentWidth(view: View, percent: Int, minimal: Int = 0, maximal: Int = 0) {
    val onePercent = (displayWidth / 100).toDouble()
    var wantedSize = (onePercent * percent).toInt()
    if (minimal.isSet && wantedSize < minimal)
        wantedSize = minimal
    else if (maximal.isSet && wantedSize > maximal) wantedSize = maximal
    val layoutParams = view.layoutParams
    layoutParams.width = wantedSize
    view.layoutParams = layoutParams
}

fun CSView<*>.size(width: Int, height: Int) = apply {
    width(width)
    height(height)
}

fun CSView<*>.width(value: Int) = apply {
    if (value > 0) {
        val params = view.layoutParams
        params.width = dpToPixel(value)
        view.layoutParams = params
    }
}

fun CSView<*>.height(value: Int) = apply {
    if (value > 0) {
        val params = view.layoutParams
        params.height = dpToPixel(value)
        view.layoutParams = params
    }
}

private fun CSView<*>.setSize(width: Boolean, size: Int, dip: Boolean) {
    var value = size
    val params = view.layoutParams
    if (value > 0 && dip) value = dpToPixel(value)
    if (width) params.width = value
    else params.height = value
    view.layoutParams = params
}
