package renetik.android.java.extensions

import kotlin.reflect.KClass

fun <T : Any> KClass<T>.createInstance() = java.createInstance()