package cs.android.extensions

import cs.java.collections.CSList
import cs.java.collections.CSListImpl

fun <T> List<T>.at(index: Int): T? {
    return if (index in 0..(size - 1)) get(index) else null
}

fun <T> List<T>.one(): T? {
    return at(0)
}

fun <T> List<T>.twoo(): T? {
    return at(1)
}

fun <T> List<T>.three(): T? {
    return at(1)
}

fun <T> List<T>.index(item: T): Int {
    return indexOf(item)
}

fun <T> List<T>.getHasItems(): Boolean {
    return size > 0
}

fun <T> List<T>.isLast(item: T): Boolean {
    return end() === item
}

fun <T> List<T>.end(): T? {
    return at(lastIndex())
}

fun <T> List<T>.lastIndex(): Int {
    return size - 1
}

fun <T> List<T>.range(fromIndex: Int): CSList<T> {
    return range(fromIndex, size)
}

fun <T> List<T>.range(fromIndex: Int, toIndex: Int): CSList<T> {
    return CSListImpl<T>(subList(fromIndex, toIndex))
}

fun <T> List<T>.count(): Int {
    return size
}

fun <T> List<T>.has(item: T): Boolean {
    return contains(item)
}

fun <T> List<T>.length(): Int {
    return size
}