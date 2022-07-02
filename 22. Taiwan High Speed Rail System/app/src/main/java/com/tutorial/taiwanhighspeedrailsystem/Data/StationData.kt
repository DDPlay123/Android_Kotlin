package com.tutorial.taiwanhighspeedrailsystem.Data

data class StationData(val station: String, val address: String,
                       val latitude: Float, val longitude: Float) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StationData

        if (station != other.station) return false
        if (address != other.address) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = station.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }
}