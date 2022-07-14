package com.tutorial.taiwanhighspeedrailsystem.Data

data class RestaurantItem(val name: String,
                          val vicinity: String,
                          val lat: Double,
                          val lng: Double,
                          val distance: Float,
                          val rating: Double,
                          val reviewsNumber: Int,
                          val photo: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RestaurantItem

        if (name != other.name) return false
        if (vicinity != other.vicinity) return false
        if (lat != other.lat) return false
        if (lng != other.lng) return false
        if (distance != other.distance) return false
        if (rating != other.rating) return false
        if (reviewsNumber != other.reviewsNumber) return false
        if (photo != other.photo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + vicinity.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lng.hashCode()
        result = 31 * result + distance.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + reviewsNumber
        result = 31 * result + photo.hashCode()
        return result
    }
}