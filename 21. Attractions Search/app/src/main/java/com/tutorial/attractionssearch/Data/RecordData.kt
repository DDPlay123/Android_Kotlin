package com.tutorial.attractionssearch.Data

import java.io.Serializable

data class RecordData(
    val name: String,
    val vicinity: String
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecordData

        if (name != other.name) return false
        if (vicinity != other.vicinity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + vicinity.hashCode()
        return result
    }
}