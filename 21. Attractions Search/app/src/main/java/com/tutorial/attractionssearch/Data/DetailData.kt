package com.tutorial.attractionssearch.Data

import java.io.Serializable

data class DetailData(
    val name: String,
    val lat: Float,
    val lng: Float,
    val vicinity: String,
    val photo: String,
    val star: Int,
    val landscape: ArrayList<String>
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DetailData

        if (name != other.name) return false
        if (lat != other.lat) return false
        if (lng != other.lng) return false
        if (vicinity != other.vicinity) return false
        if (photo != other.photo) return false
        if (star != other.star) return false
        if (landscape != other.landscape) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lng.hashCode()
        result = 31 * result + vicinity.hashCode()
        result = 31 * result + photo.hashCode()
        result = 31 * result + star
        result = 31 * result + landscape.hashCode()
        return result
    }
}