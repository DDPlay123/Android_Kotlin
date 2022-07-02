package com.tutorial.attractionssearch.Data

import java.io.Serializable

data class GalleryData(
    val photo: String
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GalleryData

        if (photo != other.photo) return false

        return true
    }

    override fun hashCode(): Int {
        return photo.hashCode()
    }
}