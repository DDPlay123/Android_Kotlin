package com.tutorial.taiwanhighspeedrailsystem.Data

data class LatLng(val longitude: Double, val latitude: Double) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LatLng

        if (longitude != other.longitude) return false
        if (latitude != other.latitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = longitude.hashCode()
        result = 31 * result + latitude.hashCode()
        return result
    }
}