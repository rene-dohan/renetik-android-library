package cs.android.extensions

import cs.java.collections.CSMap
import cs.java.lang.CSLang.NO
import cs.java.lang.CSLang.YES

fun Any.whenNull(value: CSMap<String, Any>?, function: () -> Unit): Boolean {
    if (value == null) {
        function()
        return YES
    }
    return NO
}

fun Any.whenNotNull(value: CSMap<String, Any>?, function: () -> Unit): Boolean {
    if (value != null) {
        function()
        return YES
    }
    return NO
}

fun <T, R> T.notNull(block: (T) -> R): R? {
    if (this != null) return block(this)
    return null
}