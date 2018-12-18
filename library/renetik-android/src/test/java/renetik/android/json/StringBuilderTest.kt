package renetik.android.json

import org.junit.Assert.assertEquals
import org.junit.Test
import renetik.java.extensions.cutStart
import renetik.java.extensions.deleteLast
import renetik.java.extensions.remove

class StringBuilderTest {
    @Test
    fun deleteLast() {
        val builder = StringBuilder("12345678")
        assertEquals("123456", builder.deleteLast(2).toString())
    }

    @Test
    fun cutStart() {
        val builder = StringBuilder("12345678")
        assertEquals("345678", builder.cutStart(2).toString())
    }

    @Test
    fun removeStartLength() {
        val builder = StringBuilder("12345678")
        assertEquals("125678", builder.remove(2, 2).toString())
    }
}
