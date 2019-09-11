package renetik.android.json.data.properties

import renetik.android.json.data.CSJsonData
import renetik.android.json.extensions.getInt
import renetik.android.json.extensions.put

class CSJsonInt(
    val data: CSJsonData,
    private val key: String,
    private val defaultValue: Int = 0
) {
    var integer: Int?
        get() = data.getInt(key)
        set(value) = data.put(key, value)

    var value
        get() = integer ?: defaultValue
        set(value) {
            integer = value
        }


    override fun toString() = "$value"
}