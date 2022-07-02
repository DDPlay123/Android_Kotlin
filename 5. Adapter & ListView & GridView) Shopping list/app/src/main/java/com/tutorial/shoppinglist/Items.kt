package com.tutorial.shoppinglist

data class Items(val photo: Int, val name: String, val price: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Items

        if (photo != other.photo) return false
        if (name != other.name) return false
        if (price != other.price) return false

        return true
    }
    override fun hashCode(): Int {
        var result = photo
        result = 31 * result + name.hashCode()
        result = 31 * result + price
        return result
    }
}